package com.apulse.redaction

import android.content.Context
import android.content.SharedPreferences
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
class SecurityPolicyManager(
    private val context: Context,
    private val redactionEngine: RedactionEngine
) {
    
    companion object {
        private const val PREFS_NAME = "apulse_security_policy"
        private const val KEY_CURRENT_POLICY = "current_policy"
        private const val KEY_POLICY_VERSION = "policy_version"
        private const val KEY_COMPLIANCE_MODE = "compliance_mode"
        
        val DEFAULT_POLICY = SecurityPolicy(
            name = "Default Security Policy",
            version = "1.0",
            enforceHttpsOnly = false,
            blockSensitiveUrls = true,
            maxDataRetentionDays = 30,
            maxRequestBodySize = 10 * 1024 * 1024, // 10MB
            autoRedactionEnabled = true,
            requireExplicitConsent = false,
            allowExternalSharing = true,
            encryptStoredData = false,
            logSecurityEvents = true,
            blockedHosts = emptySet(),
            allowedMimeTypes = emptySet(), // Empty = allow all
            sensitiveUrlPatterns = setOf(
                ".*login.*",
                ".*password.*",
                ".*auth.*",
                ".*oauth.*",
                ".*token.*"
            )
        )
    }
    
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val json = Json { ignoreUnknownKeys = true }
    
    fun getCurrentPolicy(): SecurityPolicy {
        val policyJson = prefs.getString(KEY_CURRENT_POLICY, null)
        return if (policyJson != null) {
            try {
                json.decodeFromString<SecurityPolicy>(policyJson)
            } catch (e: Exception) {
                DEFAULT_POLICY
            }
        } else {
            DEFAULT_POLICY
        }
    }
    
    fun updatePolicy(policy: SecurityPolicy) {
        val policyJson = json.encodeToString(policy)
        prefs.edit()
            .putString(KEY_CURRENT_POLICY, policyJson)
            .putString(KEY_POLICY_VERSION, policy.version)
            .apply()
    }
    
    fun getComplianceMode(): ComplianceMode {
        val mode = prefs.getString(KEY_COMPLIANCE_MODE, ComplianceMode.STANDARD.name)
        return try {
            ComplianceMode.valueOf(mode ?: ComplianceMode.STANDARD.name)
        } catch (e: Exception) {
            ComplianceMode.STANDARD
        }
    }
    
    fun setComplianceMode(mode: ComplianceMode) {
        prefs.edit().putString(KEY_COMPLIANCE_MODE, mode.name).apply()
        applyComplianceMode(mode)
    }
    
    private fun applyComplianceMode(mode: ComplianceMode) {
        val currentPolicy = getCurrentPolicy()
        val newPolicy = when (mode) {
            ComplianceMode.STRICT -> currentPolicy.copy(
                enforceHttpsOnly = true,
                blockSensitiveUrls = true,
                maxDataRetentionDays = 7,
                maxRequestBodySize = 1024 * 1024, // 1MB
                autoRedactionEnabled = true,
                requireExplicitConsent = true,
                allowExternalSharing = false,
                encryptStoredData = true,
                logSecurityEvents = true
            )
            ComplianceMode.GDPR -> currentPolicy.copy(
                maxDataRetentionDays = 30,
                requireExplicitConsent = true,
                autoRedactionEnabled = true,
                allowExternalSharing = false,
                encryptStoredData = true,
                logSecurityEvents = true
            )
            ComplianceMode.HIPAA -> currentPolicy.copy(
                enforceHttpsOnly = true,
                maxDataRetentionDays = 90,
                autoRedactionEnabled = true,
                requireExplicitConsent = true,
                allowExternalSharing = false,
                encryptStoredData = true,
                logSecurityEvents = true
            )
            ComplianceMode.STANDARD -> DEFAULT_POLICY
            ComplianceMode.PERMISSIVE -> currentPolicy.copy(
                enforceHttpsOnly = false,
                blockSensitiveUrls = false,
                maxDataRetentionDays = 365,
                requireExplicitConsent = false,
                allowExternalSharing = true,
                encryptStoredData = false
            )
        }
        updatePolicy(newPolicy)
        
        // Update redaction categories based on compliance mode
        val redactionCategories = when (mode) {
            ComplianceMode.STRICT, ComplianceMode.GDPR, ComplianceMode.HIPAA -> 
                setOf(
                    RedactionCategory.AUTHENTICATION,
                    RedactionCategory.COOKIES,
                    RedactionCategory.PERSONAL_DATA,
                    RedactionCategory.SECURITY_TOKENS
                )
            ComplianceMode.STANDARD -> 
                setOf(
                    RedactionCategory.AUTHENTICATION,
                    RedactionCategory.COOKIES
                )
            ComplianceMode.PERMISSIVE -> emptySet()
        }
        redactionEngine.setEnabledCategories(redactionCategories)
    }
    
    fun shouldCaptureRequest(url: String, method: String, headers: Map<String, String>): SecurityDecision {
        val policy = getCurrentPolicy()
        
        // Check HTTPS enforcement
        if (policy.enforceHttpsOnly && !url.startsWith("https://")) {
            return SecurityDecision(
                allowed = false,
                reason = "HTTPS-only policy violated",
                action = SecurityAction.BLOCK_REQUEST
            )
        }
        
        // Check blocked hosts
        val host = extractHostFromUrl(url)
        if (host in policy.blockedHosts) {
            return SecurityDecision(
                allowed = false,
                reason = "Host is blocked by security policy",
                action = SecurityAction.BLOCK_REQUEST
            )
        }
        
        // Check sensitive URL patterns
        if (policy.blockSensitiveUrls) {
            val isSensitiveUrl = policy.sensitiveUrlPatterns.any { pattern ->
                url.matches(Regex(pattern, RegexOption.IGNORE_CASE))
            }
            if (isSensitiveUrl) {
                return SecurityDecision(
                    allowed = true,
                    reason = "Sensitive URL detected - will apply extra redaction",
                    action = SecurityAction.CAPTURE_WITH_REDACTION,
                    requiresRedaction = true
                )
            }
        }
        
        return SecurityDecision(
            allowed = true,
            reason = "Request allowed by security policy",
            action = SecurityAction.CAPTURE_NORMAL
        )
    }
    
    fun shouldAllowExport(format: String, includesBodies: Boolean): SecurityDecision {
        val policy = getCurrentPolicy()
        
        if (!policy.allowExternalSharing) {
            return SecurityDecision(
                allowed = false,
                reason = "External sharing disabled by security policy",
                action = SecurityAction.BLOCK_EXPORT
            )
        }
        
        if (includesBodies && policy.maxRequestBodySize < 1024 * 1024) { // 1MB threshold
            return SecurityDecision(
                allowed = true,
                reason = "Body export allowed but will be size-limited",
                action = SecurityAction.EXPORT_WITH_LIMITS,
                requiresRedaction = true
            )
        }
        
        return SecurityDecision(
            allowed = true,
            reason = "Export allowed by security policy",
            action = SecurityAction.EXPORT_NORMAL
        )
    }
    
    fun auditSecurityEvent(event: SecurityEvent) {
        if (!getCurrentPolicy().logSecurityEvents) return
        
        // In a real implementation, this would log to a secure audit system
        // For now, we'll use Android's Log system with appropriate security measures
        android.util.Log.i("APulse-Security", "Security Event: ${event.type} - ${event.description}")
    }
    
    fun validateDataRetention(): List<RetentionViolation> {
        val policy = getCurrentPolicy()
        val violations = mutableListOf<RetentionViolation>()
        
        if (policy.maxDataRetentionDays > 0) {
            // This would check the database for old data
            // For now, return empty violations
        }
        
        return violations
    }
    
    fun generateComplianceReport(): ComplianceReport {
        val policy = getCurrentPolicy()
        val complianceMode = getComplianceMode()
        
        return ComplianceReport(
            policyVersion = policy.version,
            complianceMode = complianceMode,
            timestamp = kotlinx.datetime.Clock.System.now(),
            redactionEnabled = policy.autoRedactionEnabled,
            dataRetentionDays = policy.maxDataRetentionDays,
            httpsEnforced = policy.enforceHttpsOnly,
            encryptionEnabled = policy.encryptStoredData,
            auditingEnabled = policy.logSecurityEvents,
            externalSharingAllowed = policy.allowExternalSharing,
            risks = identifyRisks(policy, complianceMode)
        )
    }
    
    private fun identifyRisks(policy: SecurityPolicy, complianceMode: ComplianceMode): List<SecurityRisk> {
        val risks = mutableListOf<SecurityRisk>()
        
        if (!policy.autoRedactionEnabled) {
            risks.add(SecurityRisk(
                level = RiskLevel.HIGH,
                description = "Automatic redaction is disabled - sensitive data may be stored",
                recommendation = "Enable automatic redaction"
            ))
        }
        
        if (!policy.encryptStoredData && complianceMode in setOf(ComplianceMode.STRICT, ComplianceMode.GDPR, ComplianceMode.HIPAA)) {
            risks.add(SecurityRisk(
                level = RiskLevel.MEDIUM,
                description = "Data encryption is disabled in ${complianceMode.displayName} mode",
                recommendation = "Enable data encryption for compliance"
            ))
        }
        
        if (policy.maxDataRetentionDays > 90 && complianceMode == ComplianceMode.GDPR) {
            risks.add(SecurityRisk(
                level = RiskLevel.MEDIUM,
                description = "Data retention period exceeds GDPR recommendations",
                recommendation = "Reduce retention period to 90 days or less"
            ))
        }
        
        return risks
    }
    
    private fun extractHostFromUrl(url: String): String {
        return try {
            java.net.URL(url).host
        } catch (e: Exception) {
            url.substringAfter("://").substringBefore("/").substringBefore("?")
        }
    }
}

@Serializable
data class SecurityPolicy(
    val name: String,
    val version: String,
    val enforceHttpsOnly: Boolean,
    val blockSensitiveUrls: Boolean,
    val maxDataRetentionDays: Int,
    val maxRequestBodySize: Long,
    val autoRedactionEnabled: Boolean,
    val requireExplicitConsent: Boolean,
    val allowExternalSharing: Boolean,
    val encryptStoredData: Boolean,
    val logSecurityEvents: Boolean,
    val blockedHosts: Set<String>,
    val allowedMimeTypes: Set<String>,
    val sensitiveUrlPatterns: Set<String>
)

data class SecurityDecision(
    val allowed: Boolean,
    val reason: String,
    val action: SecurityAction,
    val requiresRedaction: Boolean = false
)

enum class SecurityAction {
    CAPTURE_NORMAL,
    CAPTURE_WITH_REDACTION,
    BLOCK_REQUEST,
    EXPORT_NORMAL,
    EXPORT_WITH_LIMITS,
    BLOCK_EXPORT
}

@Serializable
enum class ComplianceMode(val displayName: String) {
    PERMISSIVE("Permissive"),
    STANDARD("Standard"),
    STRICT("Strict"),
    GDPR("GDPR Compliance"),
    HIPAA("HIPAA Compliance")
}

data class SecurityEvent(
    val type: SecurityEventType,
    val description: String,
    val timestamp: kotlinx.datetime.Instant = kotlinx.datetime.Clock.System.now(),
    val url: String? = null,
    val action: String? = null
)

enum class SecurityEventType {
    REQUEST_BLOCKED,
    SENSITIVE_DATA_DETECTED,
    REDACTION_APPLIED,
    EXPORT_BLOCKED,
    POLICY_VIOLATION,
    DATA_RETENTION_CLEANUP
}

data class RetentionViolation(
    val sessionId: String,
    val createdAt: kotlinx.datetime.Instant,
    val daysOld: Int,
    val policyLimit: Int
)

@Serializable
data class ComplianceReport(
    val policyVersion: String,
    val complianceMode: ComplianceMode,
    val timestamp: kotlinx.datetime.Instant,
    val redactionEnabled: Boolean,
    val dataRetentionDays: Int,
    val httpsEnforced: Boolean,
    val encryptionEnabled: Boolean,
    val auditingEnabled: Boolean,
    val externalSharingAllowed: Boolean,
    val risks: List<SecurityRisk>
)

@Serializable
data class SecurityRisk(
    val level: RiskLevel,
    val description: String,
    val recommendation: String
)

@Serializable
enum class RiskLevel {
    LOW, MEDIUM, HIGH, CRITICAL
}