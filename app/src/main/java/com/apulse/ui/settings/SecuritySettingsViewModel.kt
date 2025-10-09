package com.apulse.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apulse.redaction.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SecuritySettingsViewModel(
    private val securityPolicyManager: SecurityPolicyManager,
    private val redactionEngine: RedactionEngine,
    private val dataEncryptionService: DataEncryptionService
) : ViewModel() {
    
    private val _complianceMode = MutableStateFlow(ComplianceMode.STANDARD)
    val complianceMode: StateFlow<ComplianceMode> = _complianceMode.asStateFlow()
    
    private val _enabledRedactionCategories = MutableStateFlow<Set<RedactionCategory>>(emptySet())
    val enabledRedactionCategories: StateFlow<Set<RedactionCategory>> = _enabledRedactionCategories.asStateFlow()
    
    private val _customRedactionRules = MutableStateFlow<List<RedactionRule>>(emptyList())
    val customRedactionRules: StateFlow<List<RedactionRule>> = _customRedactionRules.asStateFlow()
    
    private val _securityRisks = MutableStateFlow<List<SecurityRisk>>(emptyList())
    val securityRisks: StateFlow<List<SecurityRisk>> = _securityRisks.asStateFlow()
    
    private val _encryptionInfo = MutableStateFlow(
        EncryptionInfo(
            isAvailable = false,
            algorithm = "N/A",
            keyLocation = "N/A",
            hardwareBacked = false
        )
    )
    val encryptionInfo: StateFlow<EncryptionInfo> = _encryptionInfo.asStateFlow()
    
    init {
        loadSecuritySettings()
    }
    
    private fun loadSecuritySettings() {
        viewModelScope.launch {
            // Load compliance mode
            _complianceMode.value = securityPolicyManager.getComplianceMode()
            
            // Load redaction settings
            _enabledRedactionCategories.value = redactionEngine.getEnabledCategories()
            _customRedactionRules.value = redactionEngine.getCustomRules()
            
            // Load encryption info
            _encryptionInfo.value = dataEncryptionService.getEncryptionInfo()
            
            // Generate compliance report and extract risks
            val complianceReport = securityPolicyManager.generateComplianceReport()
            _securityRisks.value = complianceReport.risks
        }
    }
    
    fun setComplianceMode(mode: ComplianceMode) {
        viewModelScope.launch {
            securityPolicyManager.setComplianceMode(mode)
            _complianceMode.value = mode
            
            // Reload settings as compliance mode changes other settings
            loadSecuritySettings()
        }
    }
    
    fun toggleRedactionCategory(category: RedactionCategory, enabled: Boolean) {
        viewModelScope.launch {
            val currentCategories = _enabledRedactionCategories.value.toMutableSet()
            if (enabled) {
                currentCategories.add(category)
            } else {
                currentCategories.remove(category)
            }
            
            redactionEngine.setEnabledCategories(currentCategories)
            _enabledRedactionCategories.value = currentCategories
            
            // Update security risks after change
            refreshSecurityRisks()
        }
    }
    
    fun addCustomRule(rule: RedactionRule) {
        viewModelScope.launch {
            val validationResult = redactionEngine.validateRule(rule)
            if (validationResult.isValid) {
                redactionEngine.addCustomRule(rule)
                _customRedactionRules.value = redactionEngine.getCustomRules()
            }
            // In a real app, you might want to show validation errors to the user
        }
    }
    
    fun removeCustomRule(ruleName: String) {
        viewModelScope.launch {
            redactionEngine.removeCustomRule(ruleName)
            _customRedactionRules.value = redactionEngine.getCustomRules()
        }
    }
    
    fun testRedactionRule(rule: RedactionRule, testInput: String): RedactionTestResult {
        return redactionEngine.testRule(rule, testInput)
    }
    
    fun exportRedactionRules(): String {
        return redactionEngine.exportRules()
    }
    
    fun importRedactionRules(rulesJson: String): ImportRulesResult {
        val result = redactionEngine.importRules(rulesJson)
        if (result.success) {
            viewModelScope.launch {
                loadSecuritySettings()
            }
        }
        return result
    }
    
    fun generateComplianceReport(): ComplianceReport {
        return securityPolicyManager.generateComplianceReport()
    }
    
    fun auditSecurityEvent(eventType: SecurityEventType, description: String) {
        val event = SecurityEvent(
            type = eventType,
            description = description
        )
        securityPolicyManager.auditSecurityEvent(event)
    }
    
    private fun refreshSecurityRisks() {
        viewModelScope.launch {
            val complianceReport = securityPolicyManager.generateComplianceReport()
            _securityRisks.value = complianceReport.risks
        }
    }
    
    fun enableDataEncryption(enabled: Boolean) {
        viewModelScope.launch {
            val currentPolicy = securityPolicyManager.getCurrentPolicy()
            val updatedPolicy = currentPolicy.copy(encryptStoredData = enabled)
            securityPolicyManager.updatePolicy(updatedPolicy)
            
            refreshSecurityRisks()
        }
    }
    
    fun setDataRetentionDays(days: Int) {
        viewModelScope.launch {
            val currentPolicy = securityPolicyManager.getCurrentPolicy()
            val updatedPolicy = currentPolicy.copy(maxDataRetentionDays = days)
            securityPolicyManager.updatePolicy(updatedPolicy)
            
            refreshSecurityRisks()
        }
    }
    
    fun setMaxBodySize(sizeBytes: Long) {
        viewModelScope.launch {
            val currentPolicy = securityPolicyManager.getCurrentPolicy()
            val updatedPolicy = currentPolicy.copy(maxRequestBodySize = sizeBytes)
            securityPolicyManager.updatePolicy(updatedPolicy)
        }
    }
    
    fun toggleHttpsOnlyMode(enabled: Boolean) {
        viewModelScope.launch {
            val currentPolicy = securityPolicyManager.getCurrentPolicy()
            val updatedPolicy = currentPolicy.copy(enforceHttpsOnly = enabled)
            securityPolicyManager.updatePolicy(updatedPolicy)
            
            refreshSecurityRisks()
        }
    }
    
    fun addBlockedHost(host: String) {
        viewModelScope.launch {
            val currentPolicy = securityPolicyManager.getCurrentPolicy()
            val updatedHosts = currentPolicy.blockedHosts + host
            val updatedPolicy = currentPolicy.copy(blockedHosts = updatedHosts)
            securityPolicyManager.updatePolicy(updatedPolicy)
        }
    }
    
    fun removeBlockedHost(host: String) {
        viewModelScope.launch {
            val currentPolicy = securityPolicyManager.getCurrentPolicy()
            val updatedHosts = currentPolicy.blockedHosts - host
            val updatedPolicy = currentPolicy.copy(blockedHosts = updatedHosts)
            securityPolicyManager.updatePolicy(updatedPolicy)
        }
    }
    
    fun getSecurityPolicySummary(): SecurityPolicySummary {
        val policy = securityPolicyManager.getCurrentPolicy()
        val complianceMode = securityPolicyManager.getComplianceMode()
        
        return SecurityPolicySummary(
            complianceMode = complianceMode,
            httpsOnlyEnabled = policy.enforceHttpsOnly,
            encryptionEnabled = policy.encryptStoredData,
            dataRetentionDays = policy.maxDataRetentionDays,
            redactionCategoriesCount = _enabledRedactionCategories.value.size,
            customRulesCount = _customRedactionRules.value.size,
            blockedHostsCount = policy.blockedHosts.size,
            riskLevel = when {
                _securityRisks.value.any { it.level == RiskLevel.CRITICAL } -> RiskLevel.CRITICAL
                _securityRisks.value.any { it.level == RiskLevel.HIGH } -> RiskLevel.HIGH
                _securityRisks.value.any { it.level == RiskLevel.MEDIUM } -> RiskLevel.MEDIUM
                _securityRisks.value.any { it.level == RiskLevel.LOW } -> RiskLevel.LOW
                else -> RiskLevel.LOW
            }
        )
    }
}

data class SecurityPolicySummary(
    val complianceMode: ComplianceMode,
    val httpsOnlyEnabled: Boolean,
    val encryptionEnabled: Boolean,
    val dataRetentionDays: Int,
    val redactionCategoriesCount: Int,
    val customRulesCount: Int,
    val blockedHostsCount: Int,
    val riskLevel: RiskLevel
)