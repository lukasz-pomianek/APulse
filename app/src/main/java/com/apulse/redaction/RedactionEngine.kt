package com.apulse.redaction

import android.content.Context
import android.content.SharedPreferences
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RedactionEngine @Inject constructor(
    private val context: Context
) {
    
    companion object {
        private const val PREFS_NAME = "apulse_redaction_rules"
        private const val KEY_CUSTOM_RULES = "custom_rules"
        private const val KEY_ENABLED_CATEGORIES = "enabled_categories"
        
        // Default redaction patterns
        private val DEFAULT_PATTERNS = mapOf(
            RedactionCategory.AUTHENTICATION to listOf(
                RedactionRule(
                    name = "Authorization Header",
                    pattern = "(?i)authorization",
                    target = RedactionTarget.HEADER_NAME,
                    replacement = "[REDACTED]"
                ),
                RedactionRule(
                    name = "Bearer Token",
                    pattern = "Bearer [A-Za-z0-9._-]+",
                    target = RedactionTarget.HEADER_VALUE,
                    replacement = "Bearer [REDACTED]"
                ),
                RedactionRule(
                    name = "API Key Header",
                    pattern = "(?i)(x-api-key|api-key|apikey)",
                    target = RedactionTarget.HEADER_NAME,
                    replacement = "[REDACTED]"
                ),
                RedactionRule(
                    name = "JWT Token",
                    pattern = "eyJ[A-Za-z0-9._-]*",
                    target = RedactionTarget.BODY_CONTENT,
                    replacement = "[JWT_TOKEN_REDACTED]"
                )
            ),
            
            RedactionCategory.COOKIES to listOf(
                RedactionRule(
                    name = "Cookie Header",
                    pattern = "(?i)cookie",
                    target = RedactionTarget.HEADER_NAME,
                    replacement = "[REDACTED]"
                ),
                RedactionRule(
                    name = "Set-Cookie Header",
                    pattern = "(?i)set-cookie",
                    target = RedactionTarget.HEADER_NAME,
                    replacement = "[REDACTED]"
                ),
                RedactionRule(
                    name = "Session Cookie",
                    pattern = "JSESSIONID=[^;\\s]+",
                    target = RedactionTarget.HEADER_VALUE,
                    replacement = "JSESSIONID=[REDACTED]"
                )
            ),
            
            RedactionCategory.PERSONAL_DATA to listOf(
                RedactionRule(
                    name = "Email Address",
                    pattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}",
                    target = RedactionTarget.BODY_CONTENT,
                    replacement = "[EMAIL_REDACTED]"
                ),
                RedactionRule(
                    name = "Phone Number",
                    pattern = "\\+?[1-9]\\d{1,14}",
                    target = RedactionTarget.BODY_CONTENT,
                    replacement = "[PHONE_REDACTED]"
                ),
                RedactionRule(
                    name = "SSN",
                    pattern = "\\d{3}-\\d{2}-\\d{4}",
                    target = RedactionTarget.BODY_CONTENT,
                    replacement = "[SSN_REDACTED]"
                ),
                RedactionRule(
                    name = "Credit Card",
                    pattern = "\\b(?:\\d{4}[-\\s]?){3}\\d{4}\\b",
                    target = RedactionTarget.BODY_CONTENT,
                    replacement = "[CARD_REDACTED]"
                )
            ),
            
            RedactionCategory.SECURITY_TOKENS to listOf(
                RedactionRule(
                    name = "Access Token",
                    pattern = "\"access_token\"\\s*:\\s*\"[^\"]+\"",
                    target = RedactionTarget.BODY_CONTENT,
                    replacement = "\"access_token\":\"[REDACTED]\""
                ),
                RedactionRule(
                    name = "Refresh Token",
                    pattern = "\"refresh_token\"\\s*:\\s*\"[^\"]+\"",
                    target = RedactionTarget.BODY_CONTENT,
                    replacement = "\"refresh_token\":\"[REDACTED]\""
                ),
                RedactionRule(
                    name = "Password Field",
                    pattern = "\"password\"\\s*:\\s*\"[^\"]+\"",
                    target = RedactionTarget.BODY_CONTENT,
                    replacement = "\"password\":\"[REDACTED]\""
                ),
                RedactionRule(
                    name = "Secret Key",
                    pattern = "\"(secret|key|token)\"\\s*:\\s*\"[^\"]+\"",
                    target = RedactionTarget.BODY_CONTENT,
                    replacement = "\"$1\":\"[REDACTED]\""
                )
            )
        )
    }
    
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val json = Json { ignoreUnknownKeys = true }
    
    fun redactHeaders(headers: Map<String, String>): Map<String, String> {
        val enabledRules = getEnabledRules()
        val headerRules = enabledRules.filter { 
            it.target == RedactionTarget.HEADER_NAME || it.target == RedactionTarget.HEADER_VALUE 
        }
        
        return headers.mapValues { (name, value) ->
            var redactedValue = value
            
            // Check if header name should be redacted
            headerRules.filter { it.target == RedactionTarget.HEADER_NAME }.forEach { rule ->
                if (name.matches(Regex(rule.pattern, RegexOption.IGNORE_CASE))) {
                    return@mapValues rule.replacement
                }
            }
            
            // Check if header value should be redacted
            headerRules.filter { it.target == RedactionTarget.HEADER_VALUE }.forEach { rule ->
                redactedValue = redactedValue.replace(Regex(rule.pattern, RegexOption.IGNORE_CASE), rule.replacement)
            }
            
            redactedValue
        }
    }
    
    fun redactBody(body: String, contentType: String? = null): String {
        val enabledRules = getEnabledRules().filter { it.target == RedactionTarget.BODY_CONTENT }
        var redactedBody = body
        
        enabledRules.forEach { rule ->
            redactedBody = redactedBody.replace(Regex(rule.pattern, RegexOption.IGNORE_CASE), rule.replacement)
        }
        
        return redactedBody
    }
    
    fun redactUrl(url: String): String {
        val enabledRules = getEnabledRules().filter { it.target == RedactionTarget.URL }
        var redactedUrl = url
        
        enabledRules.forEach { rule ->
            redactedUrl = redactedUrl.replace(Regex(rule.pattern, RegexOption.IGNORE_CASE), rule.replacement)
        }
        
        return redactedUrl
    }
    
    fun addCustomRule(rule: RedactionRule) {
        val customRules = getCustomRules().toMutableList()
        customRules.add(rule)
        saveCustomRules(customRules)
    }
    
    fun removeCustomRule(ruleName: String) {
        val customRules = getCustomRules().toMutableList()
        customRules.removeAll { it.name == ruleName }
        saveCustomRules(customRules)
    }
    
    fun getCustomRules(): List<RedactionRule> {
        val rulesJson = prefs.getString(KEY_CUSTOM_RULES, "[]") ?: "[]"
        return try {
            json.decodeFromString<List<RedactionRule>>(rulesJson)
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    fun getEnabledCategories(): Set<RedactionCategory> {
        val categoriesJson = prefs.getString(KEY_ENABLED_CATEGORIES, "[]") ?: "[]"
        return try {
            json.decodeFromString<List<String>>(categoriesJson)
                .mapNotNull { categoryName ->
                    RedactionCategory.values().find { it.name == categoryName }
                }
                .toSet()
        } catch (e: Exception) {
            setOf(RedactionCategory.AUTHENTICATION, RedactionCategory.COOKIES)
        }
    }
    
    fun setEnabledCategories(categories: Set<RedactionCategory>) {
        val categoriesJson = json.encodeToString(categories.map { it.name })
        prefs.edit().putString(KEY_ENABLED_CATEGORIES, categoriesJson).apply()
    }
    
    private fun saveCustomRules(rules: List<RedactionRule>) {
        val rulesJson = json.encodeToString(rules)
        prefs.edit().putString(KEY_CUSTOM_RULES, rulesJson).apply()
    }
    
    private fun getEnabledRules(): List<RedactionRule> {
        val enabledCategories = getEnabledCategories()
        val defaultRules = DEFAULT_PATTERNS
            .filterKeys { it in enabledCategories }
            .values
            .flatten()
        
        return defaultRules + getCustomRules()
    }
    
    fun validateRule(rule: RedactionRule): RedactionValidationResult {
        return try {
            // Test the regex pattern
            Regex(rule.pattern)
            
            // Check for potential issues
            val warnings = mutableListOf<String>()
            
            if (rule.pattern.length < 3) {
                warnings.add("Pattern is very short and may cause excessive redaction")
            }
            
            if (!rule.pattern.contains("\\b") && rule.target == RedactionTarget.BODY_CONTENT) {
                warnings.add("Consider using word boundaries (\\b) to avoid partial matches")
            }
            
            if (rule.replacement.isEmpty()) {
                warnings.add("Empty replacement may cause formatting issues")
            }
            
            RedactionValidationResult(isValid = true, warnings = warnings)
        } catch (e: Exception) {
            RedactionValidationResult(isValid = false, error = "Invalid regex pattern: ${e.message}")
        }
    }
    
    fun testRule(rule: RedactionRule, testInput: String): RedactionTestResult {
        return try {
            val regex = Regex(rule.pattern, RegexOption.IGNORE_CASE)
            val matches = regex.findAll(testInput).map { it.value }.toList()
            val redactedOutput = testInput.replace(regex, rule.replacement)
            
            RedactionTestResult(
                matches = matches,
                redactedOutput = redactedOutput,
                isSuccess = true
            )
        } catch (e: Exception) {
            RedactionTestResult(
                matches = emptyList(),
                redactedOutput = testInput,
                isSuccess = false,
                error = e.message
            )
        }
    }
    
    fun exportRules(): String {
        val allRules = RedactionRulesExport(
            version = "1.0",
            enabledCategories = getEnabledCategories().toList(),
            customRules = getCustomRules()
        )
        return json.encodeToString(allRules)
    }
    
    fun importRules(rulesJson: String): ImportRulesResult {
        return try {
            val rulesExport = json.decodeFromString<RedactionRulesExport>(rulesJson)
            
            // Validate all rules before importing
            val invalidRules = rulesExport.customRules.filter { rule ->
                !validateRule(rule).isValid
            }
            
            if (invalidRules.isNotEmpty()) {
                return ImportRulesResult(
                    success = false,
                    error = "Invalid rules found: ${invalidRules.map { it.name }}"
                )
            }
            
            // Import rules
            setEnabledCategories(rulesExport.enabledCategories.toSet())
            saveCustomRules(rulesExport.customRules)
            
            ImportRulesResult(
                success = true,
                importedCustomRules = rulesExport.customRules.size,
                enabledCategories = rulesExport.enabledCategories.size
            )
        } catch (e: Exception) {
            ImportRulesResult(success = false, error = e.message)
        }
    }
}

@Serializable
data class RedactionRule(
    val name: String,
    val pattern: String,
    val target: RedactionTarget,
    val replacement: String = "[REDACTED]",
    val description: String = "",
    val isEnabled: Boolean = true
)

@Serializable
enum class RedactionTarget {
    HEADER_NAME,
    HEADER_VALUE,
    BODY_CONTENT,
    URL,
    QUERY_PARAM
}

enum class RedactionCategory(val displayName: String) {
    AUTHENTICATION("Authentication"),
    COOKIES("Cookies & Sessions"),
    PERSONAL_DATA("Personal Information"),
    SECURITY_TOKENS("Security Tokens"),
    CUSTOM("Custom Rules")
}

data class RedactionValidationResult(
    val isValid: Boolean,
    val error: String? = null,
    val warnings: List<String> = emptyList()
)

data class RedactionTestResult(
    val matches: List<String>,
    val redactedOutput: String,
    val isSuccess: Boolean,
    val error: String? = null
)

@Serializable
data class RedactionRulesExport(
    val version: String,
    val enabledCategories: List<RedactionCategory>,
    val customRules: List<RedactionRule>
)

data class ImportRulesResult(
    val success: Boolean,
    val error: String? = null,
    val importedCustomRules: Int = 0,
    val enabledCategories: Int = 0
)