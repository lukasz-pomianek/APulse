package com.apulse.service

import com.apulse.data.db.APulseDatabase
import com.apulse.data.model.Session
import com.apulse.data.model.SessionWithStats
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val database: APulseDatabase
) {
    
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    private val _currentSessionId = MutableStateFlow<String?>(null)
    val currentSessionId: StateFlow<String?> = _currentSessionId.asStateFlow()
    
    val currentSession: StateFlow<Session?> = currentSessionId
        .flatMapLatest { sessionId ->
            if (sessionId != null) {
                flow { emit(database.sessionDao().getSession(sessionId)) }
            } else {
                flowOf(null)
            }
        }
        .stateIn(scope, SharingStarted.Eagerly, null)
    
    init {
        // Load the active session on init
        scope.launch {
            val activeSession = database.sessionDao().getActiveSession()
            _currentSessionId.value = activeSession?.id
            
            // Create a default session if none exist
            if (activeSession == null) {
                createDefaultSession()
            }
        }
    }
    
    suspend fun createSession(
        name: String,
        description: String? = null,
        tags: List<String> = emptyList(),
        activateImmediately: Boolean = false
    ): Session {
        val now = Clock.System.now()
        val session = Session(
            id = UUID.randomUUID().toString(),
            name = name,
            description = description,
            createdAt = now,
            updatedAt = now,
            isActive = activateImmediately,
            tags = tags
        )
        
        database.sessionDao().insertSession(session)
        
        if (activateImmediately) {
            activateSession(session.id)
        }
        
        return session
    }
    
    suspend fun activateSession(sessionId: String): Boolean {
        val session = database.sessionDao().getSession(sessionId) ?: return false
        
        // Deactivate all other sessions
        database.sessionDao().deactivateOtherSessions(sessionId)
        
        // Activate this session
        val updatedSession = session.copy(isActive = true, updatedAt = Clock.System.now())
        database.sessionDao().updateSession(updatedSession)
        
        _currentSessionId.value = sessionId
        return true
    }
    
    suspend fun deactivateSession(sessionId: String): Boolean {
        val session = database.sessionDao().getSession(sessionId) ?: return false
        
        val updatedSession = session.copy(isActive = false, updatedAt = Clock.System.now())
        database.sessionDao().updateSession(updatedSession)
        
        // If this was the current session, clear it
        if (_currentSessionId.value == sessionId) {
            _currentSessionId.value = null
        }
        
        return true
    }
    
    suspend fun deleteSession(sessionId: String): Boolean {
        // Don't delete if it's the only session
        val sessionCount = database.sessionDao().getSessionCount()
        if (sessionCount <= 1) {
            return false
        }
        
        // If deleting the active session, activate another one
        if (_currentSessionId.value == sessionId) {
            val allSessions = database.sessionDao().getAllSessions().first()
            val nextSession = allSessions.find { it.id != sessionId }
            nextSession?.let { activateSession(it.id) }
        }
        
        database.sessionDao().deleteSessionById(sessionId)
        return true
    }
    
    suspend fun updateSessionMetadata(
        sessionId: String,
        name: String? = null,
        description: String? = null,
        tags: List<String>? = null
    ): Boolean {
        val session = database.sessionDao().getSession(sessionId) ?: return false
        
        val updatedSession = session.copy(
            name = name ?: session.name,
            description = description ?: session.description,
            tags = tags ?: session.tags,
            updatedAt = Clock.System.now()
        )
        
        database.sessionDao().updateSession(updatedSession)
        return true
    }
    
    suspend fun getSessionWithStats(sessionId: String): SessionWithStats? {
        val session = database.sessionDao().getSession(sessionId) ?: return null
        val requests = database.networkRequestDao().getRequestsForSession(sessionId).first()
        
        val successCount = requests.count { it.statusCode in 200..299 }
        val errorCount = requests.count { (it.statusCode ?: 0) >= 400 || it.error != null }
        val distinctHosts = requests.map { it.host }.distinct().size
        val averageDuration = if (requests.isNotEmpty()) {
            requests.mapNotNull { it.duration }.average().toLong()
        } else 0L
        
        return SessionWithStats(
            session = session,
            requestCount = requests.size,
            totalSize = requests.sumOf { it.requestSize + it.responseSize },
            successCount = successCount,
            errorCount = errorCount,
            distinctHosts = distinctHosts,
            averageDuration = averageDuration
        )
    }
    
    suspend fun mergeSessionsInto(targetSessionId: String, sourceSessionIds: List<String>): Boolean {
        val targetSession = database.sessionDao().getSession(targetSessionId) ?: return false
        
        database.runInTransaction {
            sourceSessionIds.forEach { sourceId ->
                // Move all requests from source to target
                val sourceRequests = database.networkRequestDao().getRequestsForSession(sourceId).first()
                sourceRequests.forEach { request ->
                    val updatedRequest = request.copy(sessionId = targetSessionId)
                    database.networkRequestDao().updateRequest(updatedRequest)
                }
                
                // Delete the source session
                database.sessionDao().deleteSessionById(sourceId)
            }
            
            // Update target session stats
            updateSessionStatsInternal(targetSessionId)
        }
        
        return true
    }
    
    suspend fun cleanupOldSessions(olderThanDays: Int = 30) {
        val cutoffTime = Clock.System.now().minus(kotlinx.datetime.DateTimeUnit.DAY * olderThanDays)
        
        // Don't delete the active session
        val activeSessionId = _currentSessionId.value
        val oldSessions = database.sessionDao().getAllSessions().first()
            .filter { it.updatedAt < cutoffTime && it.id != activeSessionId }
        
        oldSessions.forEach { session ->
            database.sessionDao().deleteSessionById(session.id)
        }
    }
    
    suspend fun getOrCreateCurrentSession(): Session {
        _currentSessionId.value?.let { sessionId ->
            database.sessionDao().getSession(sessionId)?.let { return it }
        }
        
        // No active session, create a default one
        return createDefaultSession()
    }
    
    private suspend fun createDefaultSession(): Session {
        val now = Clock.System.now()
        val defaultSession = Session(
            id = UUID.randomUUID().toString(),
            name = "Default Session - ${formatDateTime(now)}",
            description = "Automatically created session",
            createdAt = now,
            updatedAt = now,
            isActive = true,
            tags = listOf("default", "auto-created")
        )
        
        database.sessionDao().insertSession(defaultSession)
        _currentSessionId.value = defaultSession.id
        
        return defaultSession
    }
    
    private suspend fun updateSessionStatsInternal(sessionId: String) {
        val requestCount = database.networkRequestDao().getRequestCountForSession(sessionId)
        val totalSize = database.networkRequestDao().getTotalSizeForSession(sessionId) ?: 0L
        database.sessionDao().updateSessionStats(sessionId, requestCount, totalSize, Clock.System.now())
    }
    
    private fun formatDateTime(instant: Instant): String {
        val localDateTime = instant.toString() // Simple formatting for now
        return localDateTime.substring(0, 19).replace('T', ' ')
    }
}