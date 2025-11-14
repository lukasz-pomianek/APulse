package com.apulse.data.repository

import com.apulse.data.db.APulseDatabase
import com.apulse.data.model.AppLog
import com.apulse.data.model.NetworkRequest
import com.apulse.data.model.NetworkRequestDetails
import com.apulse.data.model.RequestWithDetails
import com.apulse.data.model.Session
import com.apulse.data.model.SessionWithStats
import com.apulse.service.SessionManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlin.time.Duration.Companion.days

class APulseRepository(
    private val database: APulseDatabase,
    private val sessionManager: SessionManager
) {
    
    // Session operations
    fun getAllSessions(): Flow<List<Session>> = database.sessionDao().getAllSessions()
    
    fun getActiveSession(): Flow<Session?> = sessionManager.currentSession
    
    suspend fun createSession(name: String, description: String? = null, tags: List<String> = emptyList()): Session {
        return sessionManager.createSession(name, description, tags)
    }
    
    suspend fun activateSession(sessionId: String): Boolean {
        return sessionManager.activateSession(sessionId)
    }
    
    suspend fun deleteSession(sessionId: String): Boolean {
        return sessionManager.deleteSession(sessionId)
    }
    
    suspend fun getSessionWithStats(sessionId: String): SessionWithStats? {
        return sessionManager.getSessionWithStats(sessionId)
    }
    
    // Lightweight version for list display - only calculates basic counts
    suspend fun getSessionWithBasicStats(sessionId: String): SessionWithStats? {
        return try {
            val session = database.sessionDao().getSessionSuspend(sessionId) ?: return null
            val requestCount = database.networkRequestDao().getRequestCountForSessionSuspend(sessionId)
            val logCount = database.sessionDao().getLogCountForSession(sessionId)
            
            SessionWithStats(
                session = session,
                requestCount = requestCount,
                logCount = logCount,
                totalSize = session.totalSize,
                successCount = 0, // Skip heavy calculations for list view
                errorCount = 0,
                distinctHosts = 0,
                averageDuration = 0
            )
        } catch (e: Exception) {
            android.util.Log.e("APulseRepository", "Error calculating basic stats for session $sessionId", e)
            null
        }
    }
    
    // Request operations
    fun getAllRequests(): Flow<List<NetworkRequest>> = database.networkRequestDao().getAllRequests()
    
    fun getRequestsForSession(sessionId: String): Flow<List<NetworkRequest>> {
        return database.networkRequestDao().getRequestsForSession(sessionId)
    }
    
    fun getCurrentSessionRequests(): Flow<List<NetworkRequest>> {
        return sessionManager.currentSessionId.flatMapLatest { sessionId ->
            if (sessionId != null) {
                database.networkRequestDao().getRequestsForSession(sessionId)
            } else {
                flowOf(emptyList())
            }
        }
    }
    
    fun searchRequests(sessionId: String, query: String): Flow<List<NetworkRequest>> {
        return database.networkRequestDao().searchRequestsInSession(sessionId, query)
    }
    
    fun getBookmarkedRequests(): Flow<List<NetworkRequest>> {
        return database.networkRequestDao().getBookmarkedRequests()
    }
    
    // Methods for lazy loading RequestWithDetails (without related data)
    fun getAllRequestsAsRequestWithDetails(): Flow<List<RequestWithDetails>> {
        return database.networkRequestDao().getAllRequests().map { requests ->
            requests.map { request ->
                RequestWithDetails(
                    request = request,
                    requestHeaders = null,
                    requestBody = null,
                    responseHeaders = null,
                    responseBody = null
                )
            }
        }
    }
    
    fun getRequestsForSessionAsRequestWithDetails(sessionId: String): Flow<List<RequestWithDetails>> {
        return database.networkRequestDao().getRequestsForSession(sessionId).map { requests ->
            requests.map { request ->
                RequestWithDetails(
                    request = request,
                    requestHeaders = null,
                    requestBody = null,
                    responseHeaders = null,
                    responseBody = null
                )
            }
        }
    }
    
    fun getCurrentSessionRequestsAsRequestWithDetails(): Flow<List<RequestWithDetails>> {
        return sessionManager.currentSessionId.flatMapLatest { sessionId ->
            if (sessionId != null) {
                getRequestsForSessionAsRequestWithDetails(sessionId)
            } else {
                flowOf(emptyList())
            }
        }
    }
    
    suspend fun getRequestDetails(requestId: String): NetworkRequestDetails? {
        return try {
            val request = database.networkRequestDao().getRequest(requestId) ?: return null
            
            // Load data safely, checking sizes first
            val requestHeaders = try {
                database.requestHeadersDao().getHeadersForRequest(requestId)
            } catch (e: Exception) {
                android.util.Log.w("APulseRepository", "Failed to load request headers for $requestId: ${e.message}")
                null
            }
            
            val responseHeaders = try {
                database.responseHeadersDao().getHeadersForRequest(requestId)
            } catch (e: Exception) {
                android.util.Log.w("APulseRepository", "Failed to load response headers for $requestId: ${e.message}")
                null
            }
            
            // For body data, check if it exists and its size before loading
            val requestBody = try {
                database.requestBodyDao().getBodyForRequest(requestId)
            } catch (e: Exception) {
                android.util.Log.w("APulseRepository", "Failed to load request body for $requestId: ${e.message}")
                null
            }
            
            val responseBody = try {
                database.responseBodyDao().getBodyForRequest(requestId)
            } catch (e: Exception) {
                android.util.Log.w("APulseRepository", "Failed to load response body for $requestId: ${e.message}")
                null
            }
            
            val appMetadata = try {
                database.appMetadataDao().getMetadataForRequest(requestId)
            } catch (e: Exception) {
                android.util.Log.w("APulseRepository", "Failed to load app metadata for $requestId: ${e.message}")
                null
            }
            
            NetworkRequestDetails(
                request = request,
                requestHeaders = requestHeaders,
                responseHeaders = responseHeaders,
                requestBody = requestBody,
                responseBody = responseBody,
                appMetadata = appMetadata
            )
        } catch (e: Exception) {
            android.util.Log.e("APulseRepository", "Error loading request details for $requestId", e)
            null
        }
    }
    
    // Safe method that loads request details with size limits to avoid CursorWindow issues
    suspend fun getRequestDetailsSafe(requestId: String): NetworkRequestDetails? {
        return try {
            val request = database.networkRequestDao().getRequest(requestId) ?: return null
            
            // Always load headers - they are typically small
            val requestHeaders = try {
                database.requestHeadersDao().getHeadersForRequest(requestId)
            } catch (e: Exception) {
                android.util.Log.w("APulseRepository", "Failed to load request headers: ${e.message}")
                null
            }
            
            val responseHeaders = try {
                database.responseHeadersDao().getHeadersForRequest(requestId)
            } catch (e: Exception) {
                android.util.Log.w("APulseRepository", "Failed to load response headers: ${e.message}")
                null
            }
            
            // For body data, check size first, then load only if reasonable
            val requestBody = try {
                // Check size first without loading the content
                val bodySize = database.requestBodyDao().getBodySizeForRequest(requestId)
                if (bodySize != null && bodySize > 500000) { // 500KB limit
                    android.util.Log.w("APulseRepository", "Request body too large ($bodySize bytes), skipping")
                    null
                } else {
                    database.requestBodyDao().getBodyForRequest(requestId)
                }
            } catch (e: Exception) {
                android.util.Log.w("APulseRepository", "Failed to load request body: ${e.message}")
                null
            }
            
            val responseBody = try {
                // Check size first without loading the content
                val bodySize = database.responseBodyDao().getBodySizeForRequest(requestId)
                if (bodySize != null && bodySize > 500000) { // 500KB limit
                    android.util.Log.w("APulseRepository", "Response body too large ($bodySize bytes), skipping")
                    null
                } else {
                    database.responseBodyDao().getBodyForRequest(requestId)
                }
            } catch (e: Exception) {
                android.util.Log.w("APulseRepository", "Failed to load response body: ${e.message}")
                null
            }
            
            val appMetadata = try {
                database.appMetadataDao().getMetadataForRequest(requestId)
            } catch (e: Exception) {
                android.util.Log.w("APulseRepository", "Failed to load app metadata: ${e.message}")
                null
            }

            NetworkRequestDetails(
                request = request,
                requestHeaders = requestHeaders,
                responseHeaders = responseHeaders,
                requestBody = requestBody,
                responseBody = responseBody,
                appMetadata = appMetadata
            )
        } catch (e: Exception) {
            android.util.Log.e("APulseRepository", "Error in getRequestDetailsSafe for $requestId", e)
            null
        }
    }
    
    // Ultra-safe method that loads only basic request info + headers (no body data)
    suspend fun getRequestBasicDetails(requestId: String): NetworkRequestDetails? {
        return try {
            val request = database.networkRequestDao().getRequest(requestId) ?: return null
            
            val requestHeaders = try {
                database.requestHeadersDao().getHeadersForRequest(requestId)
            } catch (e: Exception) {
                android.util.Log.w("APulseRepository", "Failed to load request headers: ${e.message}")
                null
            }
            
            val responseHeaders = try {
                database.responseHeadersDao().getHeadersForRequest(requestId)
            } catch (e: Exception) {
                android.util.Log.w("APulseRepository", "Failed to load response headers: ${e.message}")
                null
            }
            
            val appMetadata = try {
                database.appMetadataDao().getMetadataForRequest(requestId)
            } catch (e: Exception) {
                android.util.Log.w("APulseRepository", "Failed to load app metadata: ${e.message}")
                null
            }
            
            // Return without body data to avoid CursorWindow issues
            NetworkRequestDetails(
                request = request,
                requestHeaders = requestHeaders,
                responseHeaders = responseHeaders,
                requestBody = null,
                responseBody = null,
                appMetadata = appMetadata
            )
        } catch (e: Exception) {
            android.util.Log.e("APulseRepository", "Error in getRequestBasicDetails for $requestId", e)
            null
        }
    }
    
    // Load body data separately if needed
    suspend fun getRequestBodySafe(requestId: String): com.apulse.data.model.RequestBody? {
        return try {
            val bodySize = database.requestBodyDao().getBodySizeForRequest(requestId)
            if (bodySize != null && bodySize > 500000) {
                android.util.Log.w("APulseRepository", "Request body too large ($bodySize bytes)")
                return null
            }
            database.requestBodyDao().getBodyForRequest(requestId)
        } catch (e: Exception) {
            android.util.Log.e("APulseRepository", "Error loading request body for $requestId", e)
            null
        }
    }
    
    suspend fun getResponseBodySafe(requestId: String): com.apulse.data.model.ResponseBody? {
        return try {
            val bodySize = database.responseBodyDao().getBodySizeForRequest(requestId)
            if (bodySize != null && bodySize > 500000) {
                android.util.Log.w("APulseRepository", "Response body too large ($bodySize bytes)")
                return null
            }
            database.responseBodyDao().getBodyForRequest(requestId)
        } catch (e: Exception) {
            android.util.Log.e("APulseRepository", "Error loading response body for $requestId", e)
            null
        }
    }
    
    suspend fun toggleBookmark(requestId: String): Boolean {
        val request = database.networkRequestDao().getRequest(requestId) ?: return false
        val newBookmarkStatus = !request.isBookmarked
        database.networkRequestDao().updateBookmarkStatus(requestId, newBookmarkStatus)
        return newBookmarkStatus
    }
    
    suspend fun addTagToRequest(requestId: String, tag: String): Boolean {
        val request = database.networkRequestDao().getRequest(requestId) ?: return false
        val newTags = ((request.tags ?: emptyList()) + tag).distinct()
        database.networkRequestDao().updateTags(requestId, newTags)
        return true
    }
    
    suspend fun removeTagFromRequest(requestId: String, tag: String): Boolean {
        val request = database.networkRequestDao().getRequest(requestId) ?: return false
        val newTags = (request.tags ?: emptyList()) - tag
        database.networkRequestDao().updateTags(requestId, newTags)
        return true
    }
    
    suspend fun addNoteToRequest(requestId: String, note: String): Boolean {
        database.networkRequestDao().updateNotes(requestId, note)
        return true
    }
    
    // Analytics and stats
    suspend fun getSessionStats(): List<SessionWithStats> {
        val sessions = getAllSessions().first()
        return sessions.map { session ->
            sessionManager.getSessionWithStats(session.id) ?: SessionWithStats(session = session)
        }
    }
    
    suspend fun getRequestsByStatusRange(sessionId: String, minStatus: Int, maxStatus: Int): Flow<List<NetworkRequest>> {
        return database.networkRequestDao().getRequestsByStatusRange(sessionId, minStatus, maxStatus)
    }
    
    suspend fun getRequestsByHost(sessionId: String, host: String): Flow<List<NetworkRequest>> {
        return database.networkRequestDao().getRequestsByHost(sessionId, host)
    }
    
    fun getHostsForSession(sessionId: String): Flow<List<String>> {
        return database.networkRequestDao().getHostsForSession(sessionId)
    }
    
    fun getMethodsForSession(sessionId: String): Flow<List<String>> {
        return database.networkRequestDao().getMethodsForSession(sessionId)
    }
    
    // Cleanup operations
    suspend fun clearSession(sessionId: String) {
        database.networkRequestDao().deleteRequestsForSession(sessionId)
        
        // Reset session stats
        database.sessionDao().updateSessionStats(sessionId, 0, 0, kotlinx.datetime.Clock.System.now())
    }
    
    suspend fun clearAllData() {
        // Get sessions outside transaction since runInTransaction is not suspend
        val sessions = database.sessionDao().getAllSessions().first()
        
        database.runInTransaction {
            // Delete all data but keep at least one session
            if (sessions.size > 1) {
                sessions.drop(1).forEach { session ->
                    database.sessionDao().deleteSessionById(session.id)
                }
            }
        }
        
        // Clear the remaining session (this needs to be outside transaction for suspend calls)
        sessions.firstOrNull()?.let { session ->
            clearSession(session.id)
        }
    }
    
    suspend fun cleanupOldData(olderThanDays: Int = 30) {
        val cutoffTime = kotlinx.datetime.Clock.System.now()
            .minus(olderThanDays.days)
        
        // Clean up old requests
        database.networkRequestDao().deleteOldRequests(cutoffTime)
        
        // Clean up old sessions (but keep at least one)
        sessionManager.cleanupOldSessions(olderThanDays)
    }
    
    // Utility methods
    suspend fun getDatabaseSize(): Long {
        // This would need a custom query to calculate database size
        // For now, return an estimate based on request/response counts
        val totalRequests = database.networkRequestDao().getAllRequests().first().size
        return totalRequests * 1024L // Rough estimate of 1KB per request
    }
    
    suspend fun getTotalRequestCount(): Int {
        return database.networkRequestDao().getAllRequests().first().size
    }
    
    suspend fun getTotalSessionCount(): Int {
        return database.sessionDao().getSessionCount()
    }
    
    // Log operations
    fun getAllLogsSummary(): Flow<List<AppLog>> {
        return database.appLogDao().getAllLogsSummary()
    }
    
    fun getLogsForSession(sessionId: String): Flow<List<AppLog>> {
        return database.appLogDao().getLogsForSession(sessionId)
    }
    
    suspend fun getFullLogDetails(logId: String): AppLog? {
        return database.appLogDao().getLogById(logId)
    }
}