package com.apulse.service

import com.apulse.data.db.APulseDatabase
import com.apulse.data.model.NetworkRequest
import com.apulse.data.model.Session
import com.apulse.data.model.SessionWithStats
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.plus
import java.util.UUID
import kotlin.time.Duration.Companion.days

class SessionManager private constructor(
    private val database: APulseDatabase
) {
    
    companion object {
        @Volatile
        private var INSTANCE: SessionManager? = null
        private val instanceLock = Any()
        
        fun getInstance(database: APulseDatabase): SessionManager {
            return INSTANCE ?: synchronized(instanceLock) {
                INSTANCE ?: SessionManager(database).also {
                    INSTANCE = it 
                }
            }
        }
    }
    
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val initMutex = Mutex()
    private var isInitialized = false
    
    private val _currentSessionId = MutableStateFlow<String?>(null)
    val currentSessionId: StateFlow<String?> = _currentSessionId.asStateFlow()
    
    val currentSession: StateFlow<Session?> = currentSessionId
        .flatMapLatest { sessionId ->
            if (sessionId != null) {
                flow { emit(database.sessionDao().getSession(sessionId)) }
            } else {
                flowOf<Session?>(null)
            }
        }
        .stateIn(scope, SharingStarted.Eagerly, null)
    
    init {
        // Load the active session on init
        scope.launch {
            ensureActiveSession()
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
    
    /**
     * Ensures there is always an active session available.
     * Thread-safe method that can be called multiple times during initialization.
     */
    suspend fun ensureActiveSession(): Session {
        return initMutex.withLock {
            if (isInitialized) {
                // Return current active session
                return@withLock database.sessionDao().getActiveSessionSuspend()
                    ?: createDefaultSession()
            }

            val activeSession = database.sessionDao().getActiveSessionSuspend()
            val result = if (activeSession == null || activeSession.createdAt.plus(30, DateTimeUnit.SECOND) < Clock.System.now()) {
                android.util.Log.d("SessionManager", "No active session found, creating default")
                createDefaultSession()
            } else {
                android.util.Log.d("SessionManager", "Found active session: ${activeSession.id}")
                _currentSessionId.value = activeSession.id
                activeSession
            }
            
            isInitialized = true
            result
        }
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
        val logCount = database.sessionDao().getLogCountForSession(sessionId)
        
        val successCount = requests.count { it.statusCode in 200..299 }
        val errorCount = requests.count { (it.statusCode ?: 0) >= 400 || it.error != null }
        val distinctHosts = requests.map { it.host }.distinct().size
        val averageDuration = if (requests.isNotEmpty()) {
            requests.mapNotNull { it.duration }.average().toLong()
        } else 0L
        
        return SessionWithStats(
            session = session,
            requestCount = requests.size,
            logCount = logCount,
            totalSize = requests.sumOf { it.requestSize + it.responseSize },
            successCount = successCount,
            errorCount = errorCount,
            distinctHosts = distinctHosts,
            averageDuration = averageDuration
        )
    }
    
    suspend fun mergeSessionsInto(targetSessionId: String, sourceSessionIds: List<String>): Boolean {
        // Verify target session exists
        database.sessionDao().getSession(targetSessionId) ?: return false
        
        // Get all source requests outside of transaction since runInTransaction is not suspend
        val allSourceRequests = mutableListOf<Pair<String, List<NetworkRequest>>>()
        sourceSessionIds.forEach { sourceId ->
            val sourceRequests = database.networkRequestDao().getRequestsForSession(sourceId).first()
            allSourceRequests.add(sourceId to sourceRequests)
        }
        
        database.runInTransaction {
            allSourceRequests.forEach { (sourceId, sourceRequests) ->
                // Move all requests from source to target
                sourceRequests.forEach { request ->
                    val updatedRequest = request.copy(sessionId = targetSessionId)
                    database.networkRequestDao().updateRequest(updatedRequest)
                }
                
                // Delete the source session
                database.sessionDao().deleteSessionById(sourceId)
            }
        }
        
        // Update target session stats outside transaction
        updateSessionStatsInternal(targetSessionId)
        
        return true
    }
    
    suspend fun cleanupOldSessions(olderThanDays: Int = 30) {
        val cutoffTime = Clock.System.now().minus(olderThanDays.days)
        
        // Don't delete the active session
        val activeSessionId = _currentSessionId.value
        val oldSessions = database.sessionDao().getAllSessions().first()
            .filter { it.updatedAt < cutoffTime && it.id != activeSessionId }
        
        oldSessions.forEach { session ->
            database.sessionDao().deleteSessionById(session.id)
        }
    }
    
    /**
     * Gets current session ID, creating a new session if none exists.
     * More efficient than getOrCreateCurrentSession() when only ID is needed.
     */
    suspend fun getCurrentSessionId(): String {
        _currentSessionId.value?.let { sessionId ->
            // Quick check if session still exists
            if (database.sessionDao().getSessionSuspend(sessionId) != null) {
                return sessionId
            }
        }
        
        // Create new session if none exists or current is invalid
        return getOrCreateCurrentSession().id
    }
    
    suspend fun getOrCreateCurrentSession(): Session {
        _currentSessionId.value?.let { sessionId ->
            database.sessionDao().getSessionSuspend(sessionId)?.let {
                return it 
            }
        }

        android.util.Log.d("SessionManager", "No active session found, creating default one")
        val newSession = createDefaultSession()
        return newSession
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
        
        database.sessionDao().insertSessionSuspend(defaultSession)
        database.sessionDao().deactivateOtherSessions(defaultSession.id)
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