package com.apulse.capture.interceptor

import android.content.Context
import android.content.SharedPreferences
import com.apulse.redaction.RedactionEngine
import com.apulse.redaction.SecurityPolicyManager
class CaptureSettings(
    private val context: Context,
    private val redactionEngine: RedactionEngine,
    private val securityPolicyManager: SecurityPolicyManager
) {
    
    companion object {
        private const val PREFS_NAME = "apulse_capture_settings"
        private const val KEY_CAPTURE_ENABLED = "capture_enabled"
        private const val KEY_MAX_BODY_SIZE = "max_body_size"
        private const val KEY_MAX_REQUESTS_PER_SESSION = "max_requests_per_session"
        private const val KEY_AUTO_REDACT_AUTH = "auto_redact_auth"
        private const val KEY_AUTO_REDACT_COOKIES = "auto_redact_cookies"
        private const val KEY_BLOCKED_HOSTS = "blocked_hosts"
        private const val KEY_ALLOWED_HOSTS = "allowed_hosts"
        private const val KEY_CUSTOM_REDACTION_RULES = "custom_redaction_rules"
        
        private const val DEFAULT_MAX_BODY_SIZE = 10 * 1024 * 1024L // 10MB
        private const val DEFAULT_MAX_REQUESTS = 10000
        
        // Common sensitive headers
        private val SENSITIVE_HEADERS = setOf(
            "authorization",
            "cookie",
            "set-cookie",
            "x-api-key",
            "x-auth-token",
            "x-access-token",
            "bearer",
            "token"
        )
    }
    
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    fun isCaptureEnabled(): Boolean {
        return prefs.getBoolean(KEY_CAPTURE_ENABLED, true)
    }
    
    fun setCaptureEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_CAPTURE_ENABLED, enabled).apply()
    }
    
    val maxBodySize: Long
        get() = prefs.getLong(KEY_MAX_BODY_SIZE, DEFAULT_MAX_BODY_SIZE)
    
    fun setMaxBodySize(size: Long) {
        prefs.edit().putLong(KEY_MAX_BODY_SIZE, size).apply()
    }
    
    val maxRequestsPerSession: Int
        get() = prefs.getInt(KEY_MAX_REQUESTS_PER_SESSION, DEFAULT_MAX_REQUESTS)
    
    fun setMaxRequestsPerSession(count: Int) {
        prefs.edit().putInt(KEY_MAX_REQUESTS_PER_SESSION, count).apply()
    }
    
    val autoRedactAuth: Boolean
        get() = prefs.getBoolean(KEY_AUTO_REDACT_AUTH, true)
    
    fun setAutoRedactAuth(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_AUTO_REDACT_AUTH, enabled).apply()
    }
    
    val autoRedactCookies: Boolean
        get() = prefs.getBoolean(KEY_AUTO_REDACT_COOKIES, true)
    
    fun setAutoRedactCookies(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_AUTO_REDACT_COOKIES, enabled).apply()
    }
    
    fun getBlockedHosts(): Set<String> {
        val hostsString = prefs.getString(KEY_BLOCKED_HOSTS, "") ?: ""
        return if (hostsString.isEmpty()) emptySet() 
               else hostsString.split(",").map { it.trim() }.toSet()
    }
    
    fun setBlockedHosts(hosts: Set<String>) {
        prefs.edit().putString(KEY_BLOCKED_HOSTS, hosts.joinToString(",")).apply()
    }
    
    fun getAllowedHosts(): Set<String> {
        val hostsString = prefs.getString(KEY_ALLOWED_HOSTS, "") ?: ""
        return if (hostsString.isEmpty()) emptySet() 
               else hostsString.split(",").map { it.trim() }.toSet()
    }
    
    fun setAllowedHosts(hosts: Set<String>) {
        prefs.edit().putString(KEY_ALLOWED_HOSTS, hosts.joinToString(",")).apply()
    }
    
    fun getCustomRedactionRules(): List<String> {
        val rulesString = prefs.getString(KEY_CUSTOM_REDACTION_RULES, "") ?: ""
        return if (rulesString.isEmpty()) emptyList() 
               else rulesString.split("\n").filter { it.isNotBlank() }
    }
    
    fun setCustomRedactionRules(rules: List<String>) {
        prefs.edit().putString(KEY_CUSTOM_REDACTION_RULES, rules.joinToString("\n")).apply()
    }
    
    fun shouldCaptureUrl(url: String): Boolean {
        // First check security policy
        val securityDecision = securityPolicyManager.shouldCaptureRequest(url, "GET", emptyMap())
        if (!securityDecision.allowed) {
            return false
        }
        
        try {
            val host = java.net.URL(url).host.lowercase()
            
            // Check blocked hosts first
            val blockedHosts = getBlockedHosts()
            if (blockedHosts.isNotEmpty() && blockedHosts.any { host.contains(it.lowercase()) }) {
                return false
            }
            
            // If allowed hosts are specified, only capture those
            val allowedHosts = getAllowedHosts()
            if (allowedHosts.isNotEmpty()) {
                return allowedHosts.any { host.contains(it.lowercase()) }
            }
            
            return true
        } catch (e: Exception) {
            // If URL parsing fails, capture it
            return true
        }
    }
    
    fun redactHeaderIfNeeded(name: String, value: String): String {
        // Use the advanced redaction engine
        val headers = mapOf(name to value)
        val redactedHeaders = redactionEngine.redactHeaders(headers)
        return redactedHeaders[name] ?: value
    }
    
    fun redactBodyIfNeeded(body: String): String {
        // Use the advanced redaction engine
        return redactionEngine.redactBody(body)
    }
}