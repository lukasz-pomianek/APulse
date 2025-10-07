package com.apulse.export

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import com.apulse.data.db.APulseDatabase
import com.apulse.data.model.*
import com.apulse.export.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.URL
import java.net.URLEncoder
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExportService @Inject constructor(
    private val database: APulseDatabase,
    private val context: Context
) {
    
    private val json = Json { 
        prettyPrint = true
        ignoreUnknownKeys = true
        encodeDefaults = false
    }
    
    suspend fun exportSessions(
        options: ExportOptions,
        outputUri: Uri
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val sessions = getSessionsToExport(options)
            val exportData = when (options.format) {
                ExportFormat.APULSE_FULL -> exportToAPulseFull(sessions, options)
                ExportFormat.APULSE_LIGHT -> exportToAPulseLight(sessions, options)
                ExportFormat.HAR -> exportToHAR(sessions, options)
                ExportFormat.JSON -> exportToJSON(sessions, options)
                ExportFormat.CSV -> exportToCSV(sessions, options)
            }
            
            writeToFile(exportData, outputUri, options.format)
            Result.success("Export completed successfully")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private suspend fun getSessionsToExport(options: ExportOptions): List<SessionWithFullData> {
        val allSessions = database.sessionDao().getAllSessions().first()
        
        val filteredSessions = allSessions.filter { session ->
            // Filter by session IDs if specified
            if (options.sessionIds != null && session.id !in options.sessionIds) return@filter false
            
            // Filter by date range if specified
            options.dateRange?.let { range ->
                if (session.createdAt < range.from || session.createdAt > range.to) return@filter false
            }
            
            // Filter by tags if specified
            options.tags?.let { requiredTags ->
                if (session.tags.none { it in requiredTags }) return@filter false
            }
            
            true
        }
        
        return filteredSessions.map { session ->
            val requests = database.networkRequestDao().getRequestsForSession(session.id).first()
            val fullRequests = requests.map { request ->
                val requestHeaders = if (options.includeHeaders) {
                    database.requestHeadersDao().getHeadersForRequest(request.id)
                } else null
                
                val responseHeaders = if (options.includeHeaders) {
                    database.responseHeadersDao().getHeadersForRequest(request.id)
                } else null
                
                val requestBody = if (options.includeBodies) {
                    database.requestBodyDao().getBodyForRequest(request.id)?.let { body ->
                        if (options.maxBodySize != null && body.size > options.maxBodySize) {
                            body.copy(bodyText = "[Body too large: ${body.size} bytes]", body = null)
                        } else body
                    }
                } else null
                
                val responseBody = if (options.includeBodies) {
                    database.responseBodyDao().getBodyForRequest(request.id)?.let { body ->
                        if (options.maxBodySize != null && body.size > options.maxBodySize) {
                            body.copy(bodyText = "[Body too large: ${body.size} bytes]", body = null)
                        } else body
                    }
                } else null
                
                val appMetadata = if (options.includeMetadata) {
                    database.appMetadataDao().getMetadataForRequest(request.id)
                } else null
                
                RequestWithFullData(
                    request = request,
                    requestHeaders = requestHeaders,
                    responseHeaders = responseHeaders,
                    requestBody = requestBody,
                    responseBody = responseBody,
                    appMetadata = appMetadata
                )
            }
            
            SessionWithFullData(session, fullRequests)
        }
    }
    
    private suspend fun exportToAPulseFull(
        sessions: List<SessionWithFullData>,
        options: ExportOptions
    ): String {
        val totalRequests = sessions.sumOf { it.requests.size }
        val totalSize = sessions.sumOf { session -> 
            session.requests.sumOf { it.request.requestSize + it.request.responseSize }
        }
        
        val dateRange = if (sessions.isNotEmpty()) {
            val allDates = sessions.flatMap { session ->
                session.requests.map { it.request.startTime }
            }
            APulseDateRange(
                from = allDates.minOrNull() ?: Clock.System.now(),
                to = allDates.maxOrNull() ?: Clock.System.now()
            )
        } else null
        
        val exportData = APulseExport(
            exportedAt = Clock.System.now(),
            sessions = sessions.map { sessionWithData ->
                val requests = sessionWithData.requests
                val stats = calculateSessionStats(requests)
                
                APulseSessionExport(
                    session = sessionWithData.session,
                    requests = requests.map { requestData ->
                        APulseRequestExport(
                            request = requestData.request,
                            requestHeaders = requestData.requestHeaders,
                            responseHeaders = requestData.responseHeaders,
                            requestBody = requestData.requestBody,
                            responseBody = requestData.responseBody,
                            appMetadata = requestData.appMetadata
                        )
                    },
                    stats = stats
                )
            },
            metadata = APulseExportMetadata(
                totalSessions = sessions.size,
                totalRequests = totalRequests,
                totalSize = totalSize,
                dateRange = dateRange,
                includesBodies = options.includeBodies,
                redacted = options.redactSensitiveData
            )
        )
        
        return json.encodeToString(exportData)
    }
    
    private suspend fun exportToAPulseLight(
        sessions: List<SessionWithFullData>,
        options: ExportOptions
    ): String {
        val exportData = APulseLightExport(
            exportedAt = Clock.System.now(),
            sessions = sessions.map { sessionWithData ->
                APulseLightSession(
                    id = sessionWithData.session.id,
                    name = sessionWithData.session.name,
                    createdAt = sessionWithData.session.createdAt,
                    requests = sessionWithData.requests.map { requestData ->
                        APulseLightRequest(
                            id = requestData.request.id,
                            method = requestData.request.method,
                            url = requestData.request.url,
                            statusCode = requestData.request.statusCode,
                            startTime = requestData.request.startTime,
                            duration = requestData.request.duration,
                            requestSize = requestData.request.requestSize,
                            responseSize = requestData.request.responseSize,
                            error = requestData.request.error,
                            tags = requestData.request.tags,
                            isBookmarked = requestData.request.isBookmarked
                        )
                    }
                )
            }
        )
        
        return json.encodeToString(exportData)
    }
    
    private suspend fun exportToHAR(
        sessions: List<SessionWithFullData>,
        options: ExportOptions
    ): String {
        val allRequests = sessions.flatMap { it.requests }
        
        val harEntries = allRequests.mapNotNull { requestData ->
            convertToHAREntry(requestData)
        }
        
        val har = HAR(
            log = HARLog(
                creator = HARCreator(
                    name = "APulse",
                    version = "1.0",
                    comment = "Exported from APulse Android"
                ),
                entries = harEntries
            )
        )
        
        return json.encodeToString(har)
    }
    
    private fun convertToHAREntry(requestData: RequestWithFullData): HAREntry? {
        val request = requestData.request
        val duration = request.duration ?: 0L
        
        // Convert headers
        val requestHeaders = requestData.requestHeaders?.headers?.map { (name, value) ->
            HARHeader(name, value)
        } ?: emptyList()
        
        val responseHeaders = requestData.responseHeaders?.headers?.map { (name, value) ->
            HARHeader(name, value)
        } ?: emptyList()
        
        // Parse query string
        val queryParams = try {
            URL(request.url).query?.split("&")?.mapNotNull { param ->
                val parts = param.split("=", limit = 2)
                if (parts.size == 2) {
                    HARQueryParam(parts[0], parts[1])
                } else null
            } ?: emptyList()
        } catch (e: Exception) {
            emptyList<HARQueryParam>()
        }
        
        // Create HAR request
        val harRequest = HARRequest(
            method = request.method,
            url = request.url,
            headers = requestHeaders,
            queryString = queryParams,
            headersSize = -1,
            bodySize = request.requestSize,
            postData = requestData.requestBody?.let { body ->
                HARPostData(
                    mimeType = body.contentType ?: "application/octet-stream",
                    text = body.bodyText
                )
            }
        )
        
        // Create HAR response
        val harResponse = HARResponse(
            status = request.statusCode ?: 0,
            statusText = request.statusMessage ?: "",
            headers = responseHeaders,
            content = HARContent(
                size = request.responseSize,
                mimeType = request.mimeType ?: "application/octet-stream",
                text = requestData.responseBody?.bodyText
            ),
            headersSize = -1,
            bodySize = request.responseSize
        )
        
        return HAREntry(
            startedDateTime = request.startTime.toString(),
            time = duration,
            request = harRequest,
            response = harResponse,
            timings = HARTimings(
                send = 0,
                wait = duration,
                receive = 0
            )
        )
    }
    
    private suspend fun exportToJSON(
        sessions: List<SessionWithFullData>,
        options: ExportOptions
    ): String {
        // Simple JSON export of all data
        return json.encodeToString(sessions)
    }
    
    private suspend fun exportToCSV(
        sessions: List<SessionWithFullData>,
        options: ExportOptions
    ): String {
        val csvBuilder = StringBuilder()
        
        // CSV Header
        csvBuilder.append("Session,Method,URL,Host,Status,Start Time,Duration (ms),Request Size,Response Size,Error,Tags,Bookmarked\n")
        
        sessions.forEach { sessionData ->
            sessionData.requests.forEach { requestData ->
                val request = requestData.request
                csvBuilder.append("\"${sessionData.session.name}\",")
                csvBuilder.append("\"${request.method}\",")
                csvBuilder.append("\"${request.url}\",")
                csvBuilder.append("\"${request.host}\",")
                csvBuilder.append("${request.statusCode ?: ""},")
                csvBuilder.append("\"${request.startTime}\",")
                csvBuilder.append("${request.duration ?: ""},")
                csvBuilder.append("${request.requestSize},")
                csvBuilder.append("${request.responseSize},")
                csvBuilder.append("\"${request.error ?: ""}\",")
                csvBuilder.append("\"${request.tags.joinToString(";")}\",")
                csvBuilder.append("${request.isBookmarked}\n")
            }
        }
        
        return csvBuilder.toString()
    }
    
    private fun calculateSessionStats(requests: List<RequestWithFullData>): APulseSessionStats {
        val successCount = requests.count { (it.request.statusCode ?: 0) in 200..299 }
        val errorCount = requests.count { (it.request.statusCode ?: 0) >= 400 || it.request.error != null }
        val distinctHosts = requests.map { it.request.host }.distinct().size
        val averageDuration = if (requests.isNotEmpty()) {
            requests.mapNotNull { it.request.duration }.average().toLong()
        } else 0L
        
        val methodBreakdown = requests.groupingBy { it.request.method }.eachCount()
        val statusBreakdown = requests.groupingBy { (it.request.statusCode ?: 0).toString() }.eachCount()
        val hostBreakdown = requests.groupingBy { it.request.host }.eachCount()
        
        return APulseSessionStats(
            requestCount = requests.size,
            totalSize = requests.sumOf { it.request.requestSize + it.request.responseSize },
            successCount = successCount,
            errorCount = errorCount,
            distinctHosts = distinctHosts,
            averageDuration = averageDuration,
            methodBreakdown = methodBreakdown,
            statusBreakdown = statusBreakdown,
            hostBreakdown = hostBreakdown
        )
    }
    
    private suspend fun writeToFile(data: String, outputUri: Uri, format: ExportFormat) {
        val contentResolver = context.contentResolver
        
        contentResolver.openOutputStream(outputUri)?.use { outputStream ->
            if (format == ExportFormat.APULSE_FULL) {
                // For APulse format, create a ZIP file with JSON + metadata
                ZipOutputStream(outputStream).use { zip ->
                    // Add main data file
                    zip.putNextEntry(ZipEntry("apulse_data.json"))
                    zip.write(data.toByteArray())
                    zip.closeEntry()
                    
                    // Add metadata file
                    zip.putNextEntry(ZipEntry("metadata.json"))
                    val metadata = mapOf(
                        "version" to "1.0",
                        "format" to "APulse",
                        "exportedAt" to Clock.System.now().toString()
                    )
                    zip.write(json.encodeToString(metadata).toByteArray())
                    zip.closeEntry()
                }
            } else {
                // For other formats, write directly
                BufferedWriter(OutputStreamWriter(outputStream)).use { writer ->
                    writer.write(data)
                }
            }
        }
    }
}

// Data classes for internal use
private data class SessionWithFullData(
    val session: Session,
    val requests: List<RequestWithFullData>
)

private data class RequestWithFullData(
    val request: NetworkRequest,
    val requestHeaders: RequestHeaders?,
    val responseHeaders: ResponseHeaders?,
    val requestBody: RequestBody?,
    val responseBody: ResponseBody?,
    val appMetadata: AppMetadata?
)