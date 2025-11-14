package com.apulse.ui.requests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apulse.data.db.APulseDatabase
import com.apulse.data.model.NetworkRequest
import com.apulse.data.model.RequestWithDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RequestListViewModel(
    private val database: APulseDatabase,
    private val sessionManager: com.apulse.service.SessionManager
) : ViewModel() {
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _selectedSessionId = MutableStateFlow<String?>(null)
    
    val requests: StateFlow<List<RequestWithDetails>> = combine(
        _searchQuery,
        _selectedSessionId
    ) { query, sessionId ->
        Pair(query, sessionId)
    }.flatMapLatest { (query, sessionId) ->
        _isLoading.value = true

        val flow = when {
            sessionId != null && query.isNotEmpty() -> {
                database.networkRequestDao().searchRequestsInSession(sessionId, query).map { requests ->
                    // Convert basic requests to RequestWithDetails without loading bodies
                    requests.map { request ->
                        RequestWithDetails(
                            request = request,
                            requestHeaders = null, // Will load on demand
                            requestBody = null,    // Will load on demand  
                            responseHeaders = null, // Will load on demand
                            responseBody = null    // Will load on demand
                        )
                    }
                }
            }
            sessionId != null -> {
                database.networkRequestDao().getRequestsForSession(sessionId).map { requests ->
                    // Convert basic requests to RequestWithDetails without loading bodies
                    requests.map { request ->
                        RequestWithDetails(
                            request = request,
                            requestHeaders = null, // Will load on demand
                            requestBody = null,    // Will load on demand
                            responseHeaders = null, // Will load on demand
                            responseBody = null    // Will load on demand
                        )
                    }
                }
            }
            query.isNotEmpty() -> {
                // Global search across all sessions without blocking the main thread
                database.networkRequestDao().getAllRequests()
                    .map { allRequests ->
                        allRequests.filter { request ->
                            request.url.contains(query, ignoreCase = true)
                        }.map { request ->
                            RequestWithDetails(
                                request = request,
                                requestHeaders = null, // Will load on demand
                                requestBody = null,    // Will load on demand  
                                responseHeaders = null, // Will load on demand
                                responseBody = null    // Will load on demand
                            )
                        }
                    }
                    .flowOn(Dispatchers.Default)
            }
            else -> {
                database.networkRequestDao().getAllRequests().map { requests ->
                    // Convert basic requests to RequestWithDetails without loading bodies
                    requests.map { request ->
                        RequestWithDetails(
                            request = request,
                            requestHeaders = null, // Will load on demand
                            requestBody = null,    // Will load on demand  
                            responseHeaders = null, // Will load on demand
                            responseBody = null    // Will load on demand
                        )
                    }
                }
            }
        }
        
        flow.onEach { requests ->
            _isLoading.value = false
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    
    init {
        // Load the active session by default
        viewModelScope.launch(Dispatchers.IO) {
            // Get or create active session ID using SessionManager
            _selectedSessionId.value = sessionManager.getCurrentSessionId()
        }
    }
    
    fun searchRequests(query: String) {
        _searchQuery.value = query
    }
    
    fun selectSession(sessionId: String?) {
        _selectedSessionId.value = sessionId
    }
    
    fun clearAll() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentSessionId = _selectedSessionId.value
            if (currentSessionId != null) {
                database.networkRequestDao().deleteRequestsForSession(currentSessionId)
            } else {
                // Clear all requests across all sessions
                database.runInTransaction {
                    // This would need a custom query to delete all requests
                    // For now, we'll clear the current session
                }
            }
        }
    }
    
    fun toggleBookmark(requestId: String, isBookmarked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            database.networkRequestDao().updateBookmarkStatus(requestId, isBookmarked)
        }
    }
    
    fun addTag(requestId: String, tag: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val request = database.networkRequestDao().getRequest(requestId)
            request?.let {
                val newTags = ((it.tags ?: emptyList()) + tag).distinct()
                database.networkRequestDao().updateTags(requestId, newTags)
            }
        }
    }
}