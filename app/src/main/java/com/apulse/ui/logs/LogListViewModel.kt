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
import kotlinx.datetime.Instant

/**
 * ViewModel for managing application logs display and filtering
 */
class LogListViewModel(
    private val database: APulseDatabase,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _selectedPriorities = MutableStateFlow<Set<Int>>(emptySet())
    val selectedPriorities: StateFlow<Set<Int>> = _selectedPriorities.asStateFlow()

    private val _selectedTags = MutableStateFlow<Set<String>>(emptySet())
    val selectedTags: StateFlow<Set<String>> = _selectedTags.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _dateFrom = MutableStateFlow<Instant?>(null)
    val dateFrom: StateFlow<Instant?> = _dateFrom.asStateFlow()

    private val _dateTo = MutableStateFlow<Instant?>(null)
    val dateTo: StateFlow<Instant?> = _dateTo.asStateFlow()

    val logs: StateFlow<List<AppLog>> = combine(
        combine(
            database.appLogDao().getAllLogs(),
            _searchQuery,
            _selectedPriorities,
            _selectedTags
        ) { allLogs, query, priorities, tags ->
            var filtered = allLogs
            if (query.isNotBlank()) {
                filtered = filtered.filter { it.message.contains(query, ignoreCase = true) }
            }
            if (priorities.isNotEmpty()) {
                filtered = filtered.filter { priorities.contains(it.priority) }
            }
            if (tags.isNotEmpty()) {
                filtered = filtered.filter { it.tag != null && tags.contains(it.tag) }
            }
            filtered
        },
        _dateFrom,
        _dateTo
    ) { filtered, dateFrom, dateTo ->
        var result = filtered
        dateFrom?.let { from ->
            result = result.filter { it.timestamp >= from }
        }
        dateTo?.let { to ->
            val endOfDay = Instant.fromEpochMilliseconds(to.toEpochMilliseconds() + 86_400_000L)
            result = result.filter { it.timestamp <= endOfDay }
        }
        result
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

    fun filterByDateFrom(date: Instant?) {
        _dateFrom.value = date
    }

    fun filterByDateTo(date: Instant?) {
        _dateTo.value = date
    }

    fun searchLogs(query: String) {
        _searchQuery.value = query
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
