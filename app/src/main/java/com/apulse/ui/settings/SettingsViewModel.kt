package com.apulse.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apulse.capture.interceptor.CaptureSettings
import com.apulse.data.db.APulseDatabase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val captureSettings: CaptureSettings,
    private val database: APulseDatabase
) : ViewModel() {
    
    val captureEnabled: StateFlow<Boolean> = flow {
        emit(captureSettings.isCaptureEnabled())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = captureSettings.isCaptureEnabled()
    )
    
    val maxBodySize: StateFlow<Long> = flow {
        emit(captureSettings.maxBodySize)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = captureSettings.maxBodySize
    )
    
    val autoRedactAuth: StateFlow<Boolean> = flow {
        emit(captureSettings.autoRedactAuth)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = captureSettings.autoRedactAuth
    )
    
    val autoRedactCookies: StateFlow<Boolean> = flow {
        emit(captureSettings.autoRedactCookies)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = captureSettings.autoRedactCookies
    )
    
    fun setCaptureEnabled(enabled: Boolean) {
        captureSettings.setCaptureEnabled(enabled)
    }
    
    fun setMaxBodySize(size: Long) {
        captureSettings.setMaxBodySize(size)
    }
    
    fun setAutoRedactAuth(enabled: Boolean) {
        captureSettings.setAutoRedactAuth(enabled)
    }
    
    fun setAutoRedactCookies(enabled: Boolean) {
        captureSettings.setAutoRedactCookies(enabled)
    }
    
    fun clearAllData() {
        viewModelScope.launch {
            // This is a destructive operation, so we might want to add confirmation
            database.runInTransaction {
                // Clear all tables
                // Note: This would need proper cascade delete queries
                // For now, we'll implement basic clearing
            }
        }
    }
    
    fun exportAllSessions() {
        viewModelScope.launch {
            // This would implement export functionality
            // We'll implement this in the export/import todo
        }
    }
}