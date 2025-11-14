package com.apulse.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apulse.capture.interceptor.CaptureSettings
import com.apulse.data.db.APulseDatabase
import com.apulse.data.repository.APulseRepository
import com.apulse.export.ExportService
import com.apulse.export.ShareService
import com.apulse.redaction.DataEncryptionService
import com.apulse.redaction.RedactionEngine
import com.apulse.redaction.SecurityPolicyManager
import com.apulse.ui.requests.RequestListViewModel
import com.apulse.ui.requests.RequestDetailViewModel
import com.apulse.ui.sessions.SessionListViewModel
import com.apulse.ui.sessions.SessionDetailViewModel
import com.apulse.ui.settings.SecuritySettingsViewModel
import com.apulse.ui.settings.SettingsViewModel

class APulseViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    
    val database: APulseDatabase by lazy { APulseDatabase.getDatabase(context) }
    val sessionManager: com.apulse.service.SessionManager by lazy { 
        com.apulse.service.SessionManager.getInstance(database) 
    }
    val repository: APulseRepository by lazy {
        APulseRepository(database, sessionManager)
    }
    private val redactionEngine: RedactionEngine by lazy { RedactionEngine(context) }
    private val securityPolicyManager: SecurityPolicyManager by lazy { SecurityPolicyManager(context, redactionEngine) }
    private val dataEncryptionService: DataEncryptionService by lazy { DataEncryptionService(context) }
    private val captureSettings: CaptureSettings by lazy { CaptureSettings(context) }
    private val exportService: ExportService by lazy { ExportService(database, context) }
    private val shareService: ShareService by lazy { ShareService(context, exportService) }
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            RequestListViewModel::class.java -> {
                RequestListViewModel(database, sessionManager) as T
            }
            RequestDetailViewModel::class.java -> {
                RequestDetailViewModel(repository) as T
            }
            SessionListViewModel::class.java -> {
                SessionListViewModel(database, sessionManager, repository, context) as T
            }
            SessionDetailViewModel::class.java -> {
                SessionDetailViewModel(repository, shareService) as T
            }
            SettingsViewModel::class.java -> {
                SettingsViewModel(captureSettings, database, shareService) as T
            }
            SecuritySettingsViewModel::class.java -> {
                SecuritySettingsViewModel(securityPolicyManager, redactionEngine, dataEncryptionService) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}