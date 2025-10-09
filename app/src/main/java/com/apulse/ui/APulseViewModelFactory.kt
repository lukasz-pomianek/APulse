package com.apulse.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apulse.capture.interceptor.CaptureSettings
import com.apulse.data.db.APulseDatabase
import com.apulse.redaction.DataEncryptionService
import com.apulse.redaction.RedactionEngine
import com.apulse.redaction.SecurityPolicyManager
import com.apulse.ui.requests.RequestListViewModel
import com.apulse.ui.sessions.SessionListViewModel
import com.apulse.ui.settings.SecuritySettingsViewModel
import com.apulse.ui.settings.SettingsViewModel

class APulseViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    
    private val database: APulseDatabase by lazy { APulseDatabase.getDatabase(context) }
    private val redactionEngine: RedactionEngine by lazy { RedactionEngine(context) }
    private val securityPolicyManager: SecurityPolicyManager by lazy { SecurityPolicyManager(context, redactionEngine) }
    private val dataEncryptionService: DataEncryptionService by lazy { DataEncryptionService(context) }
    private val captureSettings: CaptureSettings by lazy { CaptureSettings(context) }
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            RequestListViewModel::class.java -> {
                RequestListViewModel(database) as T
            }
            SessionListViewModel::class.java -> {
                SessionListViewModel(database) as T
            }
            SettingsViewModel::class.java -> {
                SettingsViewModel(captureSettings, database) as T
            }
            SecuritySettingsViewModel::class.java -> {
                SecuritySettingsViewModel(securityPolicyManager, redactionEngine, dataEncryptionService) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}