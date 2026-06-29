package com.apulse.ui.requests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apulse.data.db.APulseDatabase
import com.apulse.data.model.RequestWithDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.days
import kotlin.time.Instant

data class RequestFilter(
    val selectedHosts: Set<String> = emptySet(),
    val selectedCodes: Set<Int> = emptySet(),
    val dateFrom: Instant? = null,
    val dateTo: Instant? = null
) {
    val isActive: Boolean
        get() = selectedHosts.isNotEmpty() || selectedCodes.isNotEmpty() || dateFrom != null || dateTo != null
}

class RequestListViewModel(
    private val database: APulseDatabase,
    private val sessionManager: com.apulse.service.SessionManager
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _filter = MutableStateFlow(RequestFilter())
    val filter: StateFlow<RequestFilter> = _filter.asStateFlow()

    private val allRequestsFlow = database.networkRequestDao().getAllRequests()

    val availableHosts: StateFlow<List<String>> = allRequestsFlow
        .map { requests -> requests.map { it.host }.distinct().sorted() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val availableCodes: StateFlow<List<Int>> = allRequestsFlow
        .map { requests -> requests.mapNotNull { it.statusCode }.distinct().sorted() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val requests: StateFlow<List<RequestWithDetails>> = combine(
        allRequestsFlow,
        _searchQuery,
        _filter
    ) { allRequests, query, filter ->
        var filtered = allRequests

        if (query.isNotEmpty()) {
            filtered = filtered.filter {
                it.url.contains(query, ignoreCase = true) ||
                it.host.contains(query, ignoreCase = true)
            }
        }
        if (filter.selectedHosts.isNotEmpty()) {
            filtered = filtered.filter { it.host in filter.selectedHosts }
        }
        if (filter.selectedCodes.isNotEmpty()) {
            filtered = filtered.filter { it.statusCode != null && it.statusCode in filter.selectedCodes }
        }
        filter.dateFrom?.let { from ->
            filtered = filtered.filter { it.startTime >= from }
        }
        filter.dateTo?.let { to ->
            // inclusive: include the full selected day
            filtered = filtered.filter { it.startTime < to + 1.days }
        }

        filtered.map { request ->
            RequestWithDetails(
                request = request,
                requestHeaders = null,
                requestBody = null,
                responseHeaders = null,
                responseBody = null
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun searchRequests(query: String) {
        _searchQuery.value = query
    }

    fun updateFilter(filter: RequestFilter) {
        _filter.value = filter
    }

    fun resetFilter() {
        _filter.value = RequestFilter()
    }

    fun selectSession(sessionId: String?) {
        // Reserved for future session-based filtering
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
