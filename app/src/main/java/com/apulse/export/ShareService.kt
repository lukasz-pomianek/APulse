package com.apulse.export

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import com.apulse.export.model.ExportFormat
import com.apulse.export.model.ExportOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShareService @Inject constructor(
    private val context: Context,
    private val exportService: ExportService
) {
    
    companion object {
        private const val AUTHORITY = "com.apulse.fileprovider"
    }
    
    suspend fun shareSession(
        sessionId: String,
        format: ExportFormat = ExportFormat.APULSE_LIGHT,
        includeHeaders: Boolean = false,
        includeBodies: Boolean = false
    ): Intent = withContext(Dispatchers.IO) {
        val options = ExportOptions(
            format = format,
            includeHeaders = includeHeaders,
            includeBodies = includeBodies,
            redactSensitiveData = true,
            sessionIds = listOf(sessionId)
        )
        
        val fileName = generateFileName(format)
        val file = createTempFile(fileName)
        val uri = FileProvider.getUriForFile(context, AUTHORITY, file)
        
        val result = exportService.exportSessions(options, uri)
        result.getOrThrow() // Throw if export failed
        
        createShareIntent(uri, format, fileName)
    }
    
    suspend fun shareSessions(
        sessionIds: List<String>,
        format: ExportFormat = ExportFormat.APULSE_LIGHT,
        includeHeaders: Boolean = false,
        includeBodies: Boolean = false
    ): Intent = withContext(Dispatchers.IO) {
        val options = ExportOptions(
            format = format,
            includeHeaders = includeHeaders,
            includeBodies = includeBodies,
            redactSensitiveData = true,
            sessionIds = sessionIds
        )
        
        val fileName = generateFileName(format, sessionIds.size)
        val file = createTempFile(fileName)
        val uri = FileProvider.getUriForFile(context, AUTHORITY, file)
        
        val result = exportService.exportSessions(options, uri)
        result.getOrThrow()
        
        createShareIntent(uri, format, fileName)
    }
    
    suspend fun shareAllSessions(
        format: ExportFormat = ExportFormat.APULSE_LIGHT,
        includeHeaders: Boolean = false,
        includeBodies: Boolean = false
    ): Intent = withContext(Dispatchers.IO) {
        val options = ExportOptions(
            format = format,
            includeHeaders = includeHeaders,
            includeBodies = includeBodies,
            redactSensitiveData = true
        )
        
        val fileName = generateFileName(format, suffix = "all")
        val file = createTempFile(fileName)
        val uri = FileProvider.getUriForFile(context, AUTHORITY, file)
        
        val result = exportService.exportSessions(options, uri)
        result.getOrThrow()
        
        createShareIntent(uri, format, fileName)
    }
    
    fun createQuickShareIntent(
        sessionId: String,
        title: String = "APulse Session"
    ): Intent {
        // Create a simple text share with session summary
        val shareText = buildString {
            append("APulse Network Session\n")
            append("Session: $title\n")
            append("Shared from APulse Android\n")
            append("\nTo view full details, import this file into APulse.")
        }
        
        return Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
            putExtra(Intent.EXTRA_SUBJECT, "APulse Session: $title")
        }
    }
    
    suspend fun exportToDownloads(
        sessionIds: List<String>,
        format: ExportFormat = ExportFormat.APULSE_FULL,
        fileName: String? = null
    ): Result<Uri> = withContext(Dispatchers.IO) {
        try {
            val options = ExportOptions(
                format = format,
                includeBodies = true,
                includeHeaders = true,
                includeMetadata = true,
                redactSensitiveData = true,
                sessionIds = sessionIds
            )
            
            val actualFileName = fileName ?: generateFileName(format, sessionIds.size)
            
            // Use MediaStore to save to Downloads folder
            val uri = createDownloadsUri(actualFileName, format)
            val result = exportService.exportSessions(options, uri)
            result.fold(
                onSuccess = { Result.success(uri) },
                onFailure = { Result.failure(it) }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun generateFileName(
        format: ExportFormat,
        sessionCount: Int = 1,
        suffix: String? = null
    ): String {
        val timestamp = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .let { "${it.year}${it.monthNumber.toString().padStart(2, '0')}${it.dayOfMonth.toString().padStart(2, '0')}_${it.hour.toString().padStart(2, '0')}${it.minute.toString().padStart(2, '0')}" }
        
        val sessionPart = when {
            suffix != null -> suffix
            sessionCount == 1 -> "session"
            else -> "${sessionCount}_sessions"
        }
        
        val extension = when (format) {
            ExportFormat.APULSE_FULL -> "apulse"
            ExportFormat.APULSE_LIGHT -> "json"
            ExportFormat.HAR -> "har"
            ExportFormat.JSON -> "json"
            ExportFormat.CSV -> "csv"
        }
        
        return "apulse_${sessionPart}_${timestamp}.${extension}"
    }
    
    private fun createTempFile(fileName: String): File {
        val tempDir = File(context.cacheDir, "exports").apply { mkdirs() }
        return File(tempDir, fileName)
    }
    
    private fun createDownloadsUri(fileName: String, format: ExportFormat): Uri {
        // Create a content URI for Downloads folder
        // This is a simplified implementation - in a real app you'd use MediaStore
        val downloadsDir = File(context.getExternalFilesDir(null), "Downloads").apply { mkdirs() }
        val file = File(downloadsDir, fileName)
        return Uri.fromFile(file)
    }
    
    private fun createShareIntent(uri: Uri, format: ExportFormat, fileName: String): Intent {
        val mimeType = when (format) {
            ExportFormat.APULSE_FULL -> "application/zip"
            ExportFormat.APULSE_LIGHT, ExportFormat.JSON -> "application/json"
            ExportFormat.HAR -> "application/json"
            ExportFormat.CSV -> "text/csv"
        }
        
        val shareText = when (format) {
            ExportFormat.APULSE_FULL -> "APulse session with full data (headers, bodies, metadata)"
            ExportFormat.APULSE_LIGHT -> "APulse session summary"
            ExportFormat.HAR -> "HAR file (HTTP Archive) - compatible with Chrome DevTools, Charles, etc."
            ExportFormat.JSON -> "Raw JSON export of APulse data"
            ExportFormat.CSV -> "CSV export for analysis in Excel/Sheets"
        }
        
        return Intent().apply {
            action = Intent.ACTION_SEND
            type = mimeType
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_TEXT, shareText)
            putExtra(Intent.EXTRA_SUBJECT, "APulse Network Data: $fileName")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }
    
    // QR Code sharing for quick session exchange
    suspend fun generateSessionQRData(sessionId: String): String = withContext(Dispatchers.IO) {
        // Generate a lightweight session summary that can be encoded in a QR code
        // This would contain basic info + a link to download the full session
        "apulse://session/$sessionId"
    }
    
    // Email sharing with formatted content
    suspend fun createEmailShareIntent(
        sessionId: String,
        recipientEmail: String? = null
    ): Intent = withContext(Dispatchers.IO) {
        val options = ExportOptions(
            format = ExportFormat.APULSE_LIGHT,
            includeHeaders = false,
            includeBodies = false,
            redactSensitiveData = true,
            sessionIds = listOf(sessionId)
        )
        
        val fileName = generateFileName(ExportFormat.APULSE_LIGHT)
        val file = createTempFile(fileName)
        val uri = FileProvider.getUriForFile(context, AUTHORITY, file)
        
        exportService.exportSessions(options, uri)
        
        Intent().apply {
            action = Intent.ACTION_SEND
            type = "application/json"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, "APulse Network Session")
            putExtra(
                Intent.EXTRA_TEXT,
                buildString {
                    append("Hi,\n\n")
                    append("I'm sharing a network debugging session from APulse.\n\n")
                    append("To view this session:\n")
                    append("1. Install APulse on your Android device\n")
                    append("2. Open the attached file with APulse\n\n")
                    append("This session contains network request data for debugging purposes.\n\n")
                    append("Best regards")
                }
            )
            recipientEmail?.let {
                putExtra(Intent.EXTRA_EMAIL, arrayOf(it))
            }
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }
    
    // Slack sharing integration
    fun createSlackShareIntent(sessionSummary: String): Intent {
        val slackText = buildString {
            append("🔍 *APulse Network Session*\n\n")
            append(sessionSummary)
            append("\n\n_Shared from APulse Android_")
        }
        
        return Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, slackText)
            setPackage("com.Slack") // Direct to Slack if installed
        }
    }
}