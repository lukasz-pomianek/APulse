package com.apulse.data.repository

import com.apulse.data.db.APulseDatabase
import com.apulse.data.model.*
import com.apulse.service.SessionManager
import kotlinx.coroutines.flow.*
import kotlinx.datetime.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class APulseRepository @Inject constructor(
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
    
    suspend fun getRequestDetails(requestId: String): NetworkRequestDetails? {
        val request = database.networkRequestDao().getRequest(requestId) ?: return null
        
        val requestHeaders = database.requestHeadersDao().getHeadersForRequest(requestId)
        val responseHeaders = database.responseHeadersDao().getHeadersForRequest(requestId)
        val requestBody = database.requestBodyDao().getBodyForRequest(requestId)
        val responseBody = database.responseBodyDao().getBodyForRequest(requestId)
        val appMetadata = database.appMetadataDao().getMetadataForRequest(requestId)
        
        return NetworkRequestDetails(
            request = request,
            requestHeaders = requestHeaders,
            responseHeaders = responseHeaders,
            requestBody = requestBody,
            responseBody = responseBody,
            appMetadata = appMetadata
        )
    }
    
    suspend fun toggleBookmark(requestId: String): Boolean {
        val request = database.networkRequestDao().getRequest(requestId) ?: return false
        val newBookmarkStatus = !request.isBookmarked
        database.networkRequestDao().updateBookmarkStatus(requestId, newBookmarkStatus)
        return newBookmarkStatus
    }
    
    suspend fun addTagToRequest(requestId: String, tag: String): Boolean {
        val request = database.networkRequestDao().getRequest(requestId) ?: return false
        val newTags = (request.tags + tag).distinct()
        database.networkRequestDao().updateTags(requestId, newTags)
        return true
    }
    
    suspend fun removeTagFromRequest(requestId: String, tag: String): Boolean {
        val request = database.networkRequestDao().getRequest(requestId) ?: return false
        val newTags = request.tags - tag
        database.networkRequestDao().updateTags(requestId, newTags)
        return true
    }
    
    suspend fun addNoteToRequest(requestId: String, note: String): Boolean {
        database.networkRequestDao().updateNotes(requestId, note)
        return true
    }
    
    // Analytics and stats
    fun getSessionStats(): Flow<List<SessionWithStats>> {
        return getAllSessions().map { sessions ->
            sessions.map { session ->
                sessionManager.getSessionWithStats(session.id) ?: SessionWithStats(session = session)
            }
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
        database.runInTransaction {
            // Delete all data but keep at least one session
            val sessions = database.sessionDao().getAllSessions().first()
            if (sessions.size > 1) {
                sessions.drop(1).forEach { session ->
                    database.sessionDao().deleteSessionById(session.id)
                }
            }
            
            // Clear the remaining session
            sessions.firstOrNull()?.let { session ->
                clearSession(session.id)
            }
        }
    }
    
    suspend fun cleanupOldData(olderThanDays: Int = 30) {
        val cutoffTime = kotlinx.datetime.Clock.System.now()
            .minus(kotlinx.datetime.DateTimeUnit.DAY * olderThanDays)
        
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
}