package com.apulse.capture.interceptor

import android.content.Context
import com.apulse.data.db.APulseDatabase
import com.apulse.data.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import okhttp3.*
import okio.Buffer
import okio.BufferedSource
import java.io.IOException
import java.net.URL
import java.util.*
class APulseInterceptor(
    private val context: Context,
    private val database: APulseDatabase,
    private val captureSettings: CaptureSettings
) : Interceptor {
    
    private val scope = CoroutineScope(Dispatchers.IO)
    
    companion object {
        private const val MAX_CONTENT_LENGTH = 10 * 1024 * 1024L // 10MB default
    }
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        
        // Check if capture is enabled
        if (!captureSettings.isCaptureEnabled()) {
            return chain.proceed(request)
        }
        
        // Check if this URL should be captured
        if (!captureSettings.shouldCaptureUrl(request.url.toString())) {
            return chain.proceed(request)
        }
        
        val requestId = UUID.randomUUID().toString()
        val startTime = Clock.System.now()
        
        // Capture request details
        val networkRequest = createNetworkRequest(request, requestId, startTime)
        val requestHeaders = createRequestHeaders(request, requestId)
        val requestBody = createRequestBody(request, requestId)
        val appMetadata = createAppMetadata(requestId)
        
        // Save request data to database
        scope.launch {
            try {
                val currentSession = getOrCreateCurrentSession()
                val requestWithSession = networkRequest.copy(sessionId = currentSession.id)
                
                database.networkRequestDao().insertRequest(requestWithSession)
                requestHeaders?.let { database.requestHeadersDao().insertHeaders(it) }
                requestBody?.let { database.requestBodyDao().insertBody(it) }
                appMetadata?.let { database.appMetadataDao().insertMetadata(it) }
            } catch (e: Exception) {
                // Log error but don't fail the request
                e.printStackTrace()
            }
        }
        
        // Proceed with the request and capture response
        val response = try {
            chain.proceed(request)
        } catch (e: IOException) {
            // Capture network error
            scope.launch {
                updateRequestWithError(requestId, e, startTime)
            }
            throw e
        }
        
        val endTime = Clock.System.now()
        val duration = endTime.toEpochMilliseconds() - startTime.toEpochMilliseconds()
        
        // Capture response details
        val responseHeaders = createResponseHeaders(response, requestId)
        val responseBody = createResponseBody(response, requestId)
        
        scope.launch {
            try {
                // Update request with response details
                val updatedRequest = networkRequest.copy(
                    endTime = endTime,
                    duration = duration,
                    statusCode = response.code,
                    statusMessage = response.message,
                    responseSize = response.body?.contentLength() ?: 0L,
                    mimeType = response.body?.contentType()?.toString()
                )
                
                database.networkRequestDao().updateRequest(updatedRequest)
                responseHeaders?.let { database.responseHeadersDao().insertHeaders(it) }
                responseBody?.let { database.responseBodyDao().insertBody(it) }
                
                // Update session statistics
                updateSessionStats(updatedRequest.sessionId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        
        return response
    }
    
    private fun createNetworkRequest(request: Request, requestId: String, startTime: Instant): NetworkRequest {
        val url = request.url
        return NetworkRequest(
            id = requestId,
            sessionId = "", // Will be set later
            url = url.toString(),
            method = request.method,
            protocol = url.scheme,
            host = url.host,
            path = url.encodedPath,
            query = url.encodedQuery,
            startTime = startTime,
            requestSize = request.body?.contentLength() ?: 0L
        )
    }
    
    private fun createRequestHeaders(request: Request, requestId: String): RequestHeaders? {
        val headers = request.headers
        if (headers.size == 0) return null
        
        val headersMap = mutableMapOf<String, String>()
        for (i in 0 until headers.size) {
            val name = headers.name(i)
            val value = headers.value(i)
            headersMap[name] = captureSettings.redactHeaderIfNeeded(name, value)
        }
        
        return RequestHeaders(
            id = UUID.randomUUID().toString(),
            requestId = requestId,
            headers = headersMap,
            rawHeaders = headers.toString()
        )
    }
    
    private fun createRequestBody(request: Request, requestId: String): RequestBody? {
        val body = request.body ?: return null
        
        val contentLength = body.contentLength()
        if (contentLength > captureSettings.maxBodySize) {
            return RequestBody(
                id = UUID.randomUUID().toString(),
                requestId = requestId,
                bodyText = "[Body too large: ${contentLength} bytes]",
                contentType = body.contentType()?.toString(),
                size = contentLength
            )
        }
        
        return try {
            val buffer = Buffer()
            body.writeTo(buffer)
            val content = buffer.readUtf8()
            
            RequestBody(
                id = UUID.randomUUID().toString(),
                requestId = requestId,
                bodyText = captureSettings.redactBodyIfNeeded(content),
                contentType = body.contentType()?.toString(),
                size = contentLength
            )
        } catch (e: Exception) {
            RequestBody(
                id = UUID.randomUUID().toString(),
                requestId = requestId,
                bodyText = "[Error reading body: ${e.message}]",
                contentType = body.contentType()?.toString(),
                size = contentLength
            )
        }
    }
    
    private fun createResponseHeaders(response: Response, requestId: String): ResponseHeaders? {
        val headers = response.headers
        if (headers.size == 0) return null
        
        val headersMap = mutableMapOf<String, String>()
        for (i in 0 until headers.size) {
            val name = headers.name(i)
            val value = headers.value(i)
            headersMap[name] = captureSettings.redactHeaderIfNeeded(name, value)
        }
        
        return ResponseHeaders(
            id = UUID.randomUUID().toString(),
            requestId = requestId,
            headers = headersMap,
            rawHeaders = headers.toString()
        )
    }
    
    private fun createResponseBody(response: Response, requestId: String): com.apulse.data.model.ResponseBody? {
        val body = response.body ?: return null
        val contentType = body.contentType()
        val contentLength = body.contentLength()
        
        if (contentLength > captureSettings.maxBodySize) {
            return com.apulse.data.model.ResponseBody(
                id = UUID.randomUUID().toString(),
                requestId = requestId,
                bodyText = "[Body too large: ${contentLength} bytes]",
                contentType = contentType?.toString(),
                size = contentLength
            )
        }
        
        return try {
            val source = body.source()
            source.request(Long.MAX_VALUE)
            val buffer = source.buffer
            val content = buffer.clone().readUtf8()
            
            val isJson = contentType?.subtype == "json" || 
                        contentType?.toString()?.contains("json") == true
            val isImage = contentType?.type == "image"
            val isXml = contentType?.subtype == "xml" || 
                       contentType?.toString()?.contains("xml") == true
            
            com.apulse.data.model.ResponseBody(
                id = UUID.randomUUID().toString(),
                requestId = requestId,
                bodyText = if (isImage) "[Image content]" else captureSettings.redactBodyIfNeeded(content),
                body = if (isImage) buffer.clone().readByteArray() else null,
                contentType = contentType?.toString(),
                size = contentLength,
                isImage = isImage,
                isJson = isJson,
                isXml = isXml
            )
        } catch (e: Exception) {
            com.apulse.data.model.ResponseBody(
                id = UUID.randomUUID().toString(),
                requestId = requestId,
                bodyText = "[Error reading body: ${e.message}]",
                contentType = contentType?.toString(),
                size = contentLength
            )
        }
    }
    
    private fun createAppMetadata(requestId: String): AppMetadata {
        return AppMetadata(
            id = UUID.randomUUID().toString(),
            requestId = requestId,
            buildType = if (context.applicationInfo.flags and 
                           android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE != 0) "debug" else "release",
            appVersion = try {
                val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                packageInfo.versionName
            } catch (e: Exception) {
                "unknown"
            },
            threadName = Thread.currentThread().name,
            osVersion = android.os.Build.VERSION.RELEASE,
            deviceInfo = "${android.os.Build.MANUFACTURER} ${android.os.Build.MODEL}"
        )
    }
    
    private suspend fun getOrCreateCurrentSession(): Session {
        val activeSession = database.sessionDao().getActiveSession()
        
        return if (activeSession != null) {
            activeSession
        } else {
            val now = Clock.System.now()
            val newSession = Session(
                id = UUID.randomUUID().toString(),
                name = "Session ${now}",
                createdAt = now,
                updatedAt = now,
                isActive = true
            )
            database.sessionDao().insertSession(newSession)
            database.sessionDao().deactivateOtherSessions(newSession.id)
            newSession
        }
    }
    
    private suspend fun updateRequestWithError(requestId: String, error: IOException, startTime: Instant) {
        try {
            val request = database.networkRequestDao().getRequest(requestId) ?: return
            val updatedRequest = request.copy(
                endTime = Clock.System.now(),
                error = error.message,
                duration = Clock.System.now().toEpochMilliseconds() - startTime.toEpochMilliseconds()
            )
            database.networkRequestDao().updateRequest(updatedRequest)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private suspend fun updateSessionStats(sessionId: String) {
        try {
            val requestCount = database.networkRequestDao().getRequestCountForSession(sessionId)
            val totalSize = database.networkRequestDao().getTotalSizeForSession(sessionId) ?: 0L
            database.sessionDao().updateSessionStats(sessionId, requestCount, totalSize, Clock.System.now())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}