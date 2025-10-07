package com.apulse.export

import android.content.Context
import android.net.Uri
import com.apulse.data.db.APulseDatabase
import com.apulse.data.model.*
import com.apulse.export.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import java.util.zip.ZipInputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImportService @Inject constructor(
    private val database: APulseDatabase,
    private val context: Context
) {
    
    private val json = Json { 
        ignoreUnknownKeys = true
        coerceInputValues = true
    }
    
    suspend fun importFromUri(
        inputUri: Uri,
        importOptions: ImportOptions = ImportOptions()
    ): Result<ImportResult> = withContext(Dispatchers.IO) {
        try {
            val contentResolver = context.contentResolver
            val mimeType = contentResolver.getType(inputUri)
            
            contentResolver.openInputStream(inputUri)?.use { inputStream ->
                val content = when {
                    mimeType == "application/zip" || inputUri.toString().endsWith(".apulse") -> {
                        // Handle APulse ZIP format
                        readAPulseZipFile(inputStream)
                    }
                    mimeType == "application/json" || inputUri.toString().endsWith(".har") -> {
                        // Handle JSON/HAR files
                        BufferedReader(InputStreamReader(inputStream)).use { it.readText() }
                    }
                    else -> {
                        // Try to read as text
                        BufferedReader(InputStreamReader(inputStream)).use { it.readText() }
                    }
                }
                
                val result = parseAndImport(content, importOptions)
                Result.success(result)
            } ?: Result.failure(Exception("Cannot open input stream"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private suspend fun readAPulseZipFile(inputStream: java.io.InputStream): String {
        ZipInputStream(inputStream).use { zip ->
            var entry = zip.nextEntry
            while (entry != null) {
                if (entry.name == "apulse_data.json") {
                    return zip.bufferedReader().readText()
                }
                entry = zip.nextEntry
            }
            throw Exception("Invalid APulse file: missing apulse_data.json")
        }
    }
    
    private suspend fun parseAndImport(content: String, options: ImportOptions): ImportResult {
        // Try to determine format and parse accordingly
        return when (detectFormat(content)) {
            DetectedFormat.APULSE_FULL -> importAPulseFull(content, options)
            DetectedFormat.APULSE_LIGHT -> importAPulseLight(content, options)
            DetectedFormat.HAR -> importHAR(content, options)
            DetectedFormat.UNKNOWN -> throw Exception("Unknown file format")
        }
    }
    
    private fun detectFormat(content: String): DetectedFormat {
        return try {
            val jsonElement = Json.parseToJsonElement(content)
            
            when {
                content.contains("\"log\"") && content.contains("\"entries\"") -> DetectedFormat.HAR
                content.contains("\"version\"") && content.contains("\"sessions\"") && content.contains("APulseExport") -> DetectedFormat.APULSE_FULL
                content.contains("\"version\"") && content.contains("\"sessions\"") -> DetectedFormat.APULSE_LIGHT
                else -> DetectedFormat.UNKNOWN
            }
        } catch (e: SerializationException) {
            DetectedFormat.UNKNOWN
        }
    }
    
    private suspend fun importAPulseFull(content: String, options: ImportOptions): ImportResult {
        val exportData = json.decodeFromString<APulseExport>(content)
        val importedSessions = mutableListOf<String>()
        val importedRequests = mutableListOf<String>()
        
        database.runInTransaction {
            exportData.sessions.forEach { sessionExport ->
                val sessionId = if (options.mergeIntoExistingSession != null) {
                    options.mergeIntoExistingSession
                } else {
                    val newSessionId = UUID.randomUUID().toString()
                    val importedSession = sessionExport.session.copy(
                        id = newSessionId,
                        name = "${sessionExport.session.name} (Imported)",
                        isActive = false,
                        createdAt = Clock.System.now(),
                        updatedAt = Clock.System.now()
                    )
                    database.sessionDao().insertSession(importedSession)
                    importedSessions.add(newSessionId)
                    newSessionId
                }
                
                // Import requests
                sessionExport.requests.forEach { requestExport ->
                    val newRequestId = UUID.randomUUID().toString()
                    val importedRequest = requestExport.request.copy(
                        id = newRequestId,
                        sessionId = sessionId
                    )
                    database.networkRequestDao().insertRequest(importedRequest)
                    importedRequests.add(newRequestId)
                    
                    // Import headers
                    requestExport.requestHeaders?.let { headers ->
                        val importedHeaders = headers.copy(
                            id = UUID.randomUUID().toString(),
                            requestId = newRequestId
                        )
                        database.requestHeadersDao().insertHeaders(importedHeaders)
                    }
                    
                    requestExport.responseHeaders?.let { headers ->
                        val importedHeaders = headers.copy(
                            id = UUID.randomUUID().toString(),
                            requestId = newRequestId
                        )
                        database.responseHeadersDao().insertHeaders(importedHeaders)
                    }
                    
                    // Import bodies
                    requestExport.requestBody?.let { body ->
                        val importedBody = body.copy(
                            id = UUID.randomUUID().toString(),
                            requestId = newRequestId
                        )
                        database.requestBodyDao().insertBody(importedBody)
                    }
                    
                    requestExport.responseBody?.let { body ->
                        val importedBody = body.copy(
                            id = UUID.randomUUID().toString(),
                            requestId = newRequestId
                        )
                        database.responseBodyDao().insertBody(importedBody)
                    }
                    
                    // Import metadata
                    requestExport.appMetadata?.let { metadata ->
                        val importedMetadata = metadata.copy(
                            id = UUID.randomUUID().toString(),
                            requestId = newRequestId
                        )
                        database.appMetadataDao().insertMetadata(importedMetadata)
                    }
                }
            }
        }
        
        return ImportResult(
            sessionsImported = importedSessions.size,
            requestsImported = importedRequests.size,
            importedSessionIds = importedSessions,
            format = "APulse Full",
            warnings = emptyList()
        )
    }
    
    private suspend fun importAPulseLight(content: String, options: ImportOptions): ImportResult {
        val exportData = json.decodeFromString<APulseLightExport>(content)
        val importedSessions = mutableListOf<String>()
        val importedRequests = mutableListOf<String>()
        
        database.runInTransaction {
            exportData.sessions.forEach { sessionData ->
                val sessionId = if (options.mergeIntoExistingSession != null) {
                    options.mergeIntoExistingSession
                } else {
                    val newSessionId = UUID.randomUUID().toString()
                    val importedSession = Session(
                        id = newSessionId,
                        name = "${sessionData.name} (Imported)",
                        createdAt = sessionData.createdAt,
                        updatedAt = Clock.System.now(),
                        isActive = false,
                        totalRequests = sessionData.requests.size,
                        totalSize = sessionData.requests.sumOf { it.requestSize + it.responseSize }
                    )
                    database.sessionDao().insertSession(importedSession)
                    importedSessions.add(newSessionId)
                    newSessionId
                }
                
                // Import requests
                sessionData.requests.forEach { lightRequest ->
                    val newRequestId = UUID.randomUUID().toString()
                    val importedRequest = NetworkRequest(
                        id = newRequestId,
                        sessionId = sessionId,
                        url = lightRequest.url,
                        method = lightRequest.method,
                        host = extractHostFromUrl(lightRequest.url),
                        path = extractPathFromUrl(lightRequest.url),
                        query = extractQueryFromUrl(lightRequest.url),
                        startTime = lightRequest.startTime,
                        duration = lightRequest.duration,
                        statusCode = lightRequest.statusCode,
                        requestSize = lightRequest.requestSize,
                        responseSize = lightRequest.responseSize,
                        error = lightRequest.error,
                        isBookmarked = lightRequest.isBookmarked,
                        tags = lightRequest.tags
                    )
                    database.networkRequestDao().insertRequest(importedRequest)
                    importedRequests.add(newRequestId)
                }
            }
        }
        
        return ImportResult(
            sessionsImported = importedSessions.size,
            requestsImported = importedRequests.size,
            importedSessionIds = importedSessions,
            format = "APulse Light",
            warnings = listOf("Light format - headers and bodies not included")
        )
    }
    
    private suspend fun importHAR(content: String, options: ImportOptions): ImportResult {
        val harData = json.decodeFromString<HAR>(content)
        val warnings = mutableListOf<String>()
        
        val sessionId = if (options.mergeIntoExistingSession != null) {
            options.mergeIntoExistingSession
        } else {
            val newSessionId = UUID.randomUUID().toString()
            val session = Session(
                id = newSessionId,
                name = "HAR Import - ${Clock.System.now()}",
                description = "Imported from HAR file",
                createdAt = Clock.System.now(),
                updatedAt = Clock.System.now(),
                isActive = false,
                totalRequests = harData.log.entries.size,
                tags = listOf("har-import")
            )
            database.sessionDao().insertSession(session)
            newSessionId
        }
        
        val importedRequests = mutableListOf<String>()
        
        database.runInTransaction {
            harData.log.entries.forEach { entry ->
                try {
                    val newRequestId = UUID.randomUUID().toString()
                    val startTime = parseHARDateTime(entry.startedDateTime)
                    
                    val request = NetworkRequest(
                        id = newRequestId,
                        sessionId = sessionId,
                        url = entry.request.url,
                        method = entry.request.method,
                        host = extractHostFromUrl(entry.request.url),
                        path = extractPathFromUrl(entry.request.url),
                        query = extractQueryFromUrl(entry.request.url),
                        startTime = startTime,
                        duration = entry.time,
                        statusCode = entry.response.status,
                        statusMessage = entry.response.statusText,
                        requestSize = entry.request.bodySize.takeIf { it > 0 } ?: 0,
                        responseSize = entry.response.bodySize.takeIf { it > 0 } ?: entry.response.content.size,
                        mimeType = entry.response.content.mimeType
                    )
                    database.networkRequestDao().insertRequest(request)
                    importedRequests.add(newRequestId)
                    
                    // Import request headers
                    if (entry.request.headers.isNotEmpty()) {
                        val requestHeaders = RequestHeaders(
                            id = UUID.randomUUID().toString(),
                            requestId = newRequestId,
                            headers = entry.request.headers.associate { it.name to it.value }
                        )
                        database.requestHeadersDao().insertHeaders(requestHeaders)
                    }
                    
                    // Import response headers
                    if (entry.response.headers.isNotEmpty()) {
                        val responseHeaders = ResponseHeaders(
                            id = UUID.randomUUID().toString(),
                            requestId = newRequestId,
                            headers = entry.response.headers.associate { it.name to it.value }
                        )
                        database.responseHeadersDao().insertHeaders(responseHeaders)
                    }
                    
                    // Import request body
                    entry.request.postData?.text?.let { bodyText ->
                        val requestBody = RequestBody(
                            id = UUID.randomUUID().toString(),
                            requestId = newRequestId,
                            bodyText = bodyText,
                            contentType = entry.request.postData?.mimeType,
                            size = bodyText.length.toLong()
                        )
                        database.requestBodyDao().insertBody(requestBody)
                    }
                    
                    // Import response body
                    entry.response.content.text?.let { bodyText ->
                        val responseBody = ResponseBody(
                            id = UUID.randomUUID().toString(),
                            requestId = newRequestId,
                            bodyText = bodyText,
                            contentType = entry.response.content.mimeType,
                            size = entry.response.content.size,
                            isJson = entry.response.content.mimeType.contains("json"),
                            isXml = entry.response.content.mimeType.contains("xml"),
                            isImage = entry.response.content.mimeType.startsWith("image/")
                        )
                        database.responseBodyDao().insertBody(responseBody)
                    }
                    
                } catch (e: Exception) {
                    warnings.add("Failed to import entry: ${e.message}")
                }
            }
        }
        
        return ImportResult(
            sessionsImported = 1,
            requestsImported = importedRequests.size,
            importedSessionIds = listOf(sessionId),
            format = "HAR",
            warnings = warnings
        )
    }
    
    private fun extractHostFromUrl(url: String): String {
        return try {
            java.net.URL(url).host
        } catch (e: Exception) {
            url.substringAfter("://").substringBefore("/").substringBefore("?")
        }
    }
    
    private fun extractPathFromUrl(url: String): String {
        return try {
            java.net.URL(url).path
        } catch (e: Exception) {
            "/" + url.substringAfter("://").substringAfter("/").substringBefore("?")
        }
    }
    
    private fun extractQueryFromUrl(url: String): String? {
        return try {
            java.net.URL(url).query
        } catch (e: Exception) {
            url.substringAfter("?", "").takeIf { it.isNotEmpty() }
        }
    }
    
    private fun parseHARDateTime(dateTimeString: String): Instant {
        return try {
            Instant.parse(dateTimeString)
        } catch (e: Exception) {
            Clock.System.now()
        }
    }
}

data class ImportOptions(
    val mergeIntoExistingSession: String? = null,
    val createNewSession: Boolean = true,
    val preserveTimestamps: Boolean = true,
    val skipInvalidEntries: Boolean = true
)

data class ImportResult(
    val sessionsImported: Int,
    val requestsImported: Int,
    val importedSessionIds: List<String>,
    val format: String,
    val warnings: List<String> = emptyList()
)

private enum class DetectedFormat {
    APULSE_FULL,
    APULSE_LIGHT,
    HAR,
    UNKNOWN
}