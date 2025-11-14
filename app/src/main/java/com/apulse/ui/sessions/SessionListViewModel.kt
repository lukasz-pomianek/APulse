package com.apulse.ui.sessions

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apulse.data.db.APulseDatabase
import com.apulse.data.model.Session
import com.apulse.data.model.SessionWithStats
import com.apulse.data.repository.APulseRepository
import com.apulse.export.ImportService
import com.apulse.service.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class SessionListViewModel(
    private val database: APulseDatabase,
    private val sessionManager: SessionManager,
    private val repository: APulseRepository,
    private val context: Context
) : ViewModel() {
    
    private val importService = ImportService(database, context)
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _importResult = MutableStateFlow<ImportResult?>(null)
    val importResult: StateFlow<ImportResult?> = _importResult.asStateFlow()
    
    sealed class ImportResult {
        object Success : ImportResult()
        data class Error(val message: String) : ImportResult()
    }
    
    val sessions: StateFlow<List<SessionWithStats>> = database.sessionDao().getAllSessions()
        .flatMapLatest { sessionList ->
            flow {
                try {
                    _isLoading.value = true
                    val sessionWithStatsList = sessionList.map { session ->
                        try {
                            repository.getSessionWithBasicStats(session.id) ?: SessionWithStats(
                                session = session,
                                requestCount = 0,
                                logCount = 0,
                                totalSize = 0,
                                successCount = 0,
                                errorCount = 0,
                                distinctHosts = 0,
                                averageDuration = 0
                            )
                        } catch (e: Exception) {
                            android.util.Log.e("SessionListViewModel", "Error loading stats for session ${session.id}", e)
                            SessionWithStats(
                                session = session,
                                requestCount = 0,
                                logCount = 0,
                                totalSize = 0,
                                successCount = 0,
                                errorCount = 0,
                                distinctHosts = 0,
                                averageDuration = 0
                            )
                        }
                    }

                    emit(sessionWithStatsList)
                } catch (e: Exception) {
                    android.util.Log.e("SessionListViewModel", "Error loading sessions", e)
                    emit(emptyList())
                } finally {
                    _isLoading.value = false
                }
            }
        }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    fun createSession(name: String, description: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            sessionManager.createSession(
                name = name,
                description = description,
                activateImmediately = false // Don't auto-activate, let user choose
            )
        }
    }
    
    fun activateSession(sessionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val session = database.sessionDao().getSession(sessionId)
            if (session != null) {
                if (session.isActive) {
                    // Deactivate the session
                    sessionManager.deactivateSession(sessionId)
                } else {
                    // Activate this session
                    sessionManager.activateSession(sessionId)
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
    
    fun getActiveSession(): Flow<Session?> = sessionManager.currentSession
    
    fun createDefaultSession() {
        viewModelScope.launch(Dispatchers.IO) {
            sessionManager.ensureActiveSession()
        }
    }
    
    fun importSession(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoading.value = true
                val result = importService.importFromUri(uri)
                
                if (result.isSuccess) {
                    _importResult.value = ImportResult.Success
                    android.util.Log.d("SessionListViewModel", "Session imported successfully")
                } else {
                    val error = result.exceptionOrNull()?.message ?: "Import failed"
                    _importResult.value = ImportResult.Error(error)
                    android.util.Log.e("SessionListViewModel", "Failed to import session: $error")
                }
            } catch (e: Exception) {
                _importResult.value = ImportResult.Error(e.message ?: "Import failed")
                android.util.Log.e("SessionListViewModel", "Failed to import session", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun clearImportResult() {
        _importResult.value = null
    }
}