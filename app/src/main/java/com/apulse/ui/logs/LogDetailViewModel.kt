package com.apulse.ui.logs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.apulse.data.db.APulseDatabase
import com.apulse.data.model.AppLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for displaying detailed information about a single log entry
 */
class LogDetailViewModel(
    private val database: APulseDatabase
) : ViewModel() {

    private val _log = MutableStateFlow<AppLog?>(null)
    val log: StateFlow<AppLog?> = _log.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadLog(logId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val logEntry = database.appLogDao().getLogById(logId)
                _log.value = logEntry
            } catch (e: Exception) {
                _log.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }

    class Factory(
        private val database: APulseDatabase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LogDetailViewModel::class.java)) {
                return LogDetailViewModel(database) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}