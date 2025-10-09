package com.apulse.ui.requests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apulse.data.db.APulseDatabase
import com.apulse.data.model.NetworkRequest
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

class RequestListViewModel(
    private val database: APulseDatabase
) : ViewModel() {
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _selectedSessionId = MutableStateFlow<String?>(null)
    
    val requests: StateFlow<List<NetworkRequest>> = combine(
        _searchQuery,
        _selectedSessionId
    ) { query, sessionId ->
        Pair(query, sessionId)
    }.flatMapLatest { (query, sessionId) ->
        _isLoading.value = true
        
        val flow = when {
            sessionId != null && query.isNotEmpty() -> {
                database.networkRequestDao().searchRequestsInSession(sessionId, query)
            }
            sessionId != null -> {
                database.networkRequestDao().getRequestsForSession(sessionId)
            }
            query.isNotEmpty() -> {
                // Global search across all sessions without blocking the main thread
                database.networkRequestDao().getAllRequests()
                    .map { allRequests ->
                        allRequests.filter { request ->
                            request.url.contains(query, ignoreCase = true) ||
                            request.host.contains(query, ignoreCase = true) ||
                            request.method.contains(query, ignoreCase = true)
                        }
                    }
                    .flowOn(Dispatchers.Default)
            }
            else -> {
                database.networkRequestDao().getAllRequests()
            }
        }
        
        flow.onEach { _isLoading.value = false }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    
    init {
        // Load the active session by default
        viewModelScope.launch(Dispatchers.IO) {
            val activeSession = database.sessionDao().getActiveSession()
            _selectedSessionId.value = activeSession?.id
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
                val newTags = (it.tags + tag).distinct()
                database.networkRequestDao().updateTags(requestId, newTags)
            }
        }
    }
}