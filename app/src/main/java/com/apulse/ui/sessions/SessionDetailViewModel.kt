package com.apulse.ui.sessions

import android.content.Context
import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apulse.data.model.SessionItem
import com.apulse.data.model.SessionWithStats
import com.apulse.data.repository.APulseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class SessionDetailViewModel(
    private val repository: APulseRepository,
    private val shareService: com.apulse.export.ShareService
) : ViewModel() {
    
    private val _sessionWithStats = MutableStateFlow<SessionWithStats?>(null)
    val sessionWithStats: StateFlow<SessionWithStats?> = _sessionWithStats.asStateFlow()
    
    private val _sessionItems = MutableStateFlow<List<SessionItem>>(emptyList())
    val sessionItems: StateFlow<List<SessionItem>> = _sessionItems.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    fun loadSessionDetail(sessionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            _error.value = null
            
            try {

                val sessionStats = repository.getSessionWithStats(sessionId)
                _sessionWithStats.value = sessionStats
                
                if (sessionStats == null) {
                    _error.value = "Session not found"
                    return@launch
                }

                // Try to load requests first
                val requests = try {
                    repository.getRequestsForSession(sessionId).first()
                } catch (e: Exception) {
                    android.util.Log.e("SessionDetailViewModel", "Error loading requests", e)
                    emptyList()
                }

                // Try to load logs
                val logs = try {
                    repository.getLogsForSession(sessionId).first()
                } catch (e: Exception) {
                    emptyList()
                }
                val requestItems = requests.map { request ->
                    SessionItem.RequestItem(request)
                }
                
                val logItems = logs.map { log ->
                    SessionItem.LogItem(log)
                }
                
                // Combine and sort by timestamp (newest first)
                val allItems = (requestItems + logItems).sortedByDescending { it.timestamp }

                // Always emit items, even if empty
                _sessionItems.value = allItems

            } catch (e: Exception) {
                android.util.Log.e("SessionDetailViewModel", "Error loading session details", e)
                _error.value = "Failed to load session details: ${e.message}"
            } finally {
                _isLoading.value = false
                android.util.Log.d("SessionDetailViewModel", "Loading finished, isLoading = false")
            }
        }
    }
    
    fun clearError() {
        _error.value = null
    }
    
    fun exportSessionData(sessionWithStats: SessionWithStats, context: android.content.Context) {
        viewModelScope.launch {
            try {
                val shareIntent = shareService.shareSessionWithOptions(
                    sessionIds = listOf(sessionWithStats.session.id),
                    format = com.apulse.export.model.ExportFormat.APULSE_FULL,
                    includeHeaders = true,
                    includeBodies = true,
                    maxBodySize = 1024 * 1024
                )

                shareIntent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(shareIntent)

                
            } catch (e: android.database.sqlite.SQLiteBlobTooBigException) {
                exportWithSmallerLimit(sessionWithStats, context, 512 * 1024)
            } catch (e: Exception) {
                android.util.Log.e("SessionDetailViewModel", "Export failed", e)
                _error.value = "Export failed: ${e.message}"
            }
        }
    }
    
    private suspend fun exportWithSmallerLimit(sessionWithStats: SessionWithStats, context: android.content.Context, maxSize: Long) {
        try {
             val shareIntent = shareService.shareSessionWithOptions(
                sessionIds = listOf(sessionWithStats.session.id),
                format = com.apulse.export.model.ExportFormat.APULSE_FULL,
                includeHeaders = true,
                includeBodies = true,
                maxBodySize = maxSize
            )
            
            shareIntent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(shareIntent)

        } catch (e: Exception) {
            android.util.Log.e("SessionDetailViewModel", "Export failed even with smaller limit", e)
            _error.value = "Export failed - response bodies too large: ${e.message}"
        }
    }
}