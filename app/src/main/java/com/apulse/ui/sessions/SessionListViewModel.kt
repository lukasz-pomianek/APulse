package com.apulse.ui.sessions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apulse.data.db.APulseDatabase
import com.apulse.data.model.Session
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import java.util.*

class SessionListViewModel(
    private val database: APulseDatabase
) : ViewModel() {
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    val sessions: StateFlow<List<Session>> = database.sessionDao().getAllSessions()
        .onStart { _isLoading.value = true }
        .onEach { _isLoading.value = false }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    fun createSession(name: String, description: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val now = Clock.System.now()
            val session = Session(
                id = UUID.randomUUID().toString(),
                name = name,
                description = description,
                createdAt = now,
                updatedAt = now,
                isActive = false // Don't auto-activate, let user choose
            )
            database.sessionDao().insertSession(session)
        }
    }
    
    fun activateSession(sessionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val session = database.sessionDao().getSession(sessionId)
            if (session != null) {
                if (session.isActive) {
                    // Deactivate the session
                    val updatedSession = session.copy(isActive = false, updatedAt = Clock.System.now())
                    database.sessionDao().updateSession(updatedSession)
                } else {
                    // Activate this session and deactivate others
                    database.sessionDao().deactivateOtherSessions(sessionId)
                    val updatedSession = session.copy(isActive = true, updatedAt = Clock.System.now())
                    database.sessionDao().updateSession(updatedSession)
                }
            }
        }
    }
    
    fun deleteSession(sessionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            database.sessionDao().deleteSessionById(sessionId)
        }
    }
    
    fun updateSession(session: Session) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedSession = session.copy(updatedAt = Clock.System.now())
            database.sessionDao().updateSession(updatedSession)
        }
    }
    
    fun getActiveSession(): Flow<Session?> = flow {
        emit(database.sessionDao().getActiveSession())
    }.flowOn(Dispatchers.IO)
    
    fun createDefaultSession() {
        viewModelScope.launch(Dispatchers.IO) {
            val existingSessions = database.sessionDao().getAllSessions().first()
            if (existingSessions.isEmpty()) {
                val now = Clock.System.now()
                val defaultSession = Session(
                    id = UUID.randomUUID().toString(),
                    name = "Default Session",
                    description = "Automatically created session",
                    createdAt = now,
                    updatedAt = now,
                    isActive = true,
                    tags = listOf("default")
                )
                database.sessionDao().insertSession(defaultSession)
            }
        }
    }
}