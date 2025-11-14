package com.apulse.ui.logs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.apulse.data.db.APulseDatabase
import com.apulse.data.model.AppLog
import com.apulse.service.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for managing application logs display and filtering
 */
class LogListViewModel(
    private val database: APulseDatabase,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _selectedPriorities = MutableStateFlow<Set<Int>>(emptySet()) // Empty set means show all
    val selectedPriorities: StateFlow<Set<Int>> = _selectedPriorities.asStateFlow()

    private val _selectedTags = MutableStateFlow<Set<String>>(emptySet())
    val selectedTags: StateFlow<Set<String>> = _selectedTags.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Reactive filtering using combine to apply all filters
    val logs: StateFlow<List<AppLog>> = combine(
        database.appLogDao().getAllLogs(),
        _searchQuery,
        _selectedPriorities,
        _selectedTags
    ) { allLogs, query, priorities, tags ->
        
        var filtered = allLogs
        
        // Apply search query - filter by message content
        if (query.isNotBlank()) {
            filtered = filtered.filter { log ->
                log.message.contains(query, ignoreCase = true)
            }
        }
        
        // Apply priority filters - only show selected priorities
        if (priorities.isNotEmpty()) {
            filtered = filtered.filter { log ->
                priorities.contains(log.priority)
            }
        }
        
        // Apply tag filters - only show selected tags
        if (tags.isNotEmpty()) {
            filtered = filtered.filter { log ->
                log.tag != null && tags.contains(log.tag)
            }
        }
        filtered
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun filterByPriority(priorities: Set<Int>) {
        _selectedPriorities.value = priorities
    }

    fun filterByTags(tags: Set<String>) {
        _selectedTags.value = tags
    }

    fun searchLogs(query: String) {
        _searchQuery.value = query
    }

    fun clearLogs() {
        viewModelScope.launch {
            try {
                val currentSessionId = sessionManager.getCurrentSessionId()
                database.appLogDao().deleteLogsBySession(currentSessionId)
            } catch (e: Exception) {
                android.util.Log.e("LogListViewModel", "Failed to clear logs", e)
            }
        }
    }

    fun getAvailableTags(): List<String> {
        return logs.value.mapNotNull { it.tag }.distinct().sorted()
    }



    class Factory(
        private val database: APulseDatabase,
        private val sessionManager: SessionManager
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LogListViewModel::class.java)) {
                return LogListViewModel(database, sessionManager) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}