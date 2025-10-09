package com.apulse.capture.webview

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.apulse.capture.interceptor.CaptureSettings
import com.apulse.data.db.APulseDatabase
import com.apulse.data.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import java.util.*
class WebViewNetworkCapture(
    private val database: APulseDatabase,
    private val captureSettings: CaptureSettings
) {
    
    private val scope = CoroutineScope(Dispatchers.IO)
    
    fun createWebViewClient(): WebViewClient {
        return APulseWebViewClient()
    }
    
    private inner class APulseWebViewClient : WebViewClient() {
        
        override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
            request?.let { captureWebRequest(it) }
            return super.shouldInterceptRequest(view, request)
        }
        
        private fun captureWebRequest(request: WebResourceRequest) {
            if (!captureSettings.isCaptureEnabled()) return
            if (!captureSettings.shouldCaptureUrl(request.url.toString())) return
            
            val requestId = UUID.randomUUID().toString()
            val startTime = Clock.System.now()
            
            scope.launch {
                try {
                    // Create network request entry
                    val networkRequest = NetworkRequest(
                        id = requestId,
                        sessionId = getOrCreateCurrentSession().id,
                        url = request.url.toString(),
                        method = request.method ?: "GET",
                        protocol = request.url.scheme,
                        host = request.url.host ?: "",
                        path = request.url.path ?: "",
                        query = request.url.query,
                        startTime = startTime,
                        tags = listOf("webview")
                    )
                    
                    database.networkRequestDao().insertRequest(networkRequest)
                    
                    // Create request headers
                    request.requestHeaders?.let { headers ->
                        val requestHeaders = RequestHeaders(
                            id = UUID.randomUUID().toString(),
                            requestId = requestId,
                            headers = headers.mapValues { (name, value) ->
                                captureSettings.redactHeaderIfNeeded(name, value)
                            }
                        )
                        database.requestHeadersDao().insertHeaders(requestHeaders)
                    }
                    
                    // Create app metadata
                    val appMetadata = AppMetadata(
                        id = UUID.randomUUID().toString(),
                        requestId = requestId,
                        screenName = "WebView",
                        customData = mapOf("source" to "webview")
                    )
                    database.appMetadataDao().insertMetadata(appMetadata)
                    
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        
        private suspend fun getOrCreateCurrentSession(): Session {
            val activeSession = database.sessionDao().getActiveSession()
            
            return if (activeSession != null) {
                activeSession
            } else {
                val now = Clock.System.now()
                val newSession = Session(
                    id = UUID.randomUUID().toString(),
                    name = "WebView Session ${now}",
                    createdAt = now,
                    updatedAt = now,
                    isActive = true,
                    tags = listOf("webview")
                )
                database.sessionDao().insertSession(newSession)
                database.sessionDao().deactivateOtherSessions(newSession.id)
                newSession
            }
        }
    }
}