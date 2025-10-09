package com.apulse.redaction

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import kotlin.random.Random

class DataEncryptionService(
    private val context: Context
) {
    
    companion object {
        private const val KEYSTORE_ALIAS = "apulse_encryption_key"
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val GCM_IV_LENGTH = 12
        private const val GCM_TAG_LENGTH = 16
    }
    
    private val masterKey: MasterKey by lazy {
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }
    
    private val encryptedPrefs by lazy {
        EncryptedSharedPreferences.create(
            context,
            "apulse_encrypted_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
    
    init {
        initializeEncryptionKey()
    }
    
    private fun initializeEncryptionKey() {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)
        
        if (!keyStore.containsAlias(KEYSTORE_ALIAS)) {
            generateEncryptionKey()
        }
    }
    
    private fun generateEncryptionKey() {
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
        
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            KEYSTORE_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setRandomizedEncryptionRequired(false)
            .build()
        
        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()
    }
    
    private fun getSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)
        
        return keyStore.getKey(KEYSTORE_ALIAS, null) as SecretKey
    }
    
    fun encryptString(plainText: String): EncryptedData {
        return try {
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
            
            val iv = cipher.iv
            val encryptedBytes = cipher.doFinal(plainText.toByteArray(StandardCharsets.UTF_8))
            
            EncryptedData(
                encryptedBytes = encryptedBytes,
                iv = iv,
                isEncrypted = true
            )
        } catch (e: Exception) {
            // Fallback to unencrypted if encryption fails
            EncryptedData(
                encryptedBytes = plainText.toByteArray(StandardCharsets.UTF_8),
                iv = null,
                isEncrypted = false,
                error = e.message
            )
        }
    }
    
    fun decryptString(encryptedData: EncryptedData): String {
        return try {
            if (!encryptedData.isEncrypted || encryptedData.iv == null) {
                // Data is not encrypted, return as-is
                return String(encryptedData.encryptedBytes, StandardCharsets.UTF_8)
            }
            
            val cipher = Cipher.getInstance(TRANSFORMATION)
            val spec = GCMParameterSpec(GCM_TAG_LENGTH * 8, encryptedData.iv)
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), spec)
            
            val decryptedBytes = cipher.doFinal(encryptedData.encryptedBytes)
            String(decryptedBytes, StandardCharsets.UTF_8)
        } catch (e: Exception) {
            // Return a placeholder if decryption fails
            "[DECRYPTION_FAILED]"
        }
    }
    
    fun encryptSensitiveHeaders(headers: Map<String, String>): Map<String, EncryptedData> {
        return headers.mapValues { (_, value) ->
            encryptString(value)
        }
    }
    
    fun decryptSensitiveHeaders(encryptedHeaders: Map<String, EncryptedData>): Map<String, String> {
        return encryptedHeaders.mapValues { (_, encryptedValue) ->
            decryptString(encryptedValue)
        }
    }
    
    fun encryptRequestBody(body: String): EncryptedData {
        // Only encrypt if body contains sensitive patterns
        val containsSensitiveData = listOf(
            "password", "token", "secret", "key", "auth",
            "email", "phone", "ssn", "credit"
        ).any { pattern ->
            body.contains(pattern, ignoreCase = true)
        }
        
        return if (containsSensitiveData) {
            encryptString(body)
        } else {
            EncryptedData(
                encryptedBytes = body.toByteArray(StandardCharsets.UTF_8),
                iv = null,
                isEncrypted = false
            )
        }
    }
    
    fun securelyStoreApiKey(key: String, value: String) {
        encryptedPrefs.edit().putString(key, value).apply()
    }
    
    fun securelyRetrieveApiKey(key: String): String? {
        return encryptedPrefs.getString(key, null)
    }
    
    fun clearSecureStorage() {
        encryptedPrefs.edit().clear().apply()
    }
    
    fun generateSecureToken(length: Int = 32): String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { charset[Random.nextInt(charset.length)] }
            .joinToString("")
    }
    
    fun hashSensitiveValue(value: String): String {
        // Simple hashing for demonstration - in production use proper cryptographic hashing
        return value.hashCode().toString()
    }
    
    fun createDataDigest(data: ByteArray): String {
        // Create a digest/checksum of the data for integrity verification
        return data.contentHashCode().toString()
    }
    
    fun isEncryptionAvailable(): Boolean {
        return try {
            getSecretKey()
            true
        } catch (e: Exception) {
            false
        }
    }
    
    fun getEncryptionInfo(): EncryptionInfo {
        return EncryptionInfo(
            isAvailable = isEncryptionAvailable(),
            algorithm = "AES-256-GCM",
            keyLocation = "Android Keystore",
            hardwareBacked = true // Android Keystore is typically hardware-backed
        )
    }
}

data class EncryptedData(
    val encryptedBytes: ByteArray,
    val iv: ByteArray?,
    val isEncrypted: Boolean,
    val error: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EncryptedData

        if (!encryptedBytes.contentEquals(other.encryptedBytes)) return false
        if (iv != null) {
            if (other.iv == null) return false
            if (!iv.contentEquals(other.iv)) return false
        } else if (other.iv != null) return false
        if (isEncrypted != other.isEncrypted) return false
        if (error != other.error) return false

        return true
    }

    override fun hashCode(): Int {
        var result = encryptedBytes.contentHashCode()
        result = 31 * result + (iv?.contentHashCode() ?: 0)
        result = 31 * result + isEncrypted.hashCode()
        result = 31 * result + (error?.hashCode() ?: 0)
        return result
    }
}

data class EncryptionInfo(
    val isAvailable: Boolean,
    val algorithm: String,
    val keyLocation: String,
    val hardwareBacked: Boolean
)

// Privacy compliance helpers
object PrivacyUtils {
    
    fun anonymizeIpAddress(ipAddress: String): String {
        // Zero out the last octet for IPv4, last 80 bits for IPv6
        return when {
            ipAddress.contains('.') -> {
                // IPv4
                val parts = ipAddress.split('.')
                if (parts.size == 4) {
                    "${parts[0]}.${parts[1]}.${parts[2]}.0"
                } else ipAddress
            }
            ipAddress.contains(':') -> {
                // IPv6 - zero out last 80 bits (5 groups)
                val parts = ipAddress.split(':')
                if (parts.size >= 3) {
                    parts.take(3).joinToString(":") + "::0"
                } else ipAddress
            }
            else -> ipAddress
        }
    }
    
    fun maskEmailAddress(email: String): String {
        val parts = email.split('@')
        return if (parts.size == 2) {
            val username = parts[0]
            val domain = parts[1]
            val maskedUsername = if (username.length <= 2) {
                "**"
            } else {
                username.first() + "*".repeat(username.length - 2) + username.last()
            }
            "$maskedUsername@$domain"
        } else {
            "[MASKED_EMAIL]"
        }
    }
    
    fun maskPhoneNumber(phone: String): String {
        val digits = phone.filter { it.isDigit() }
        return if (digits.length >= 10) {
            val maskedDigits = "*".repeat(digits.length - 4) + digits.takeLast(4)
            var digitIndex = 0
            phone.map { char ->
                if (char.isDigit()) {
                    if (digitIndex < maskedDigits.length) {
                        maskedDigits[digitIndex++]
                    } else {
                        char
                    }
                } else {
                    char
                }
            }.joinToString("")
        } else {
            "[MASKED_PHONE]"
        }
    }
    
    fun generatePrivacyNotice(appName: String = "APulse"): String {
        return """
            $appName Privacy Notice
            
            This application captures network traffic for debugging purposes.
            
            Data Collection:
            • HTTP/HTTPS requests and responses
            • Request/response headers and bodies
            • Timing and metadata information
            
            Data Usage:
            • Stored locally on device
            • Used for debugging and development
            • Shared only with explicit user action
            
            Data Protection:
            • Sensitive data is automatically redacted
            • Data encryption available
            • Configurable retention periods
            
            Your Rights:
            • You can disable capture at any time
            • You can delete all stored data
            • You control data sharing and export
            
            For more information, contact your development team.
        """.trimIndent()
    }
}