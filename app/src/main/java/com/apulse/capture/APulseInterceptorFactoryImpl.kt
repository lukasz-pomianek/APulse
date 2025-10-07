package com.apulse.capture

import android.content.Context
import com.apulse.capture.interceptor.APulseInterceptor
import com.apulse.capture.interceptor.CaptureSettings
import com.apulse.core.APulseConfig
import com.apulse.core.APulseInterceptorFactory
import com.apulse.data.db.APulseDatabase
import com.apulse.redaction.RedactionEngine
import com.apulse.redaction.SecurityPolicyManager
import okhttp3.Interceptor

/**
 * Factory implementation that creates the actual APulseInterceptor
 * This is in the main app module which has access to all dependencies
 */
class APulseInterceptorFactoryImpl : APulseInterceptorFactory {
    
    override fun createInterceptor(context: Context, config: APulseConfig): Interceptor {
        // Initialize database
        val database = APulseDatabase.getInstance(context)
        
        // Create capture settings
        val captureSettings = CaptureSettings(
            redactionEngine = RedactionEngine(),
            securityPolicyManager = SecurityPolicyManager(),
            maxRequestBodySize = config.maxRequestBodySize,
            maxResponseBodySize = config.maxResponseBodySize,
            enableRedaction = config.enableAutoRedaction,
            enableEncryption = config.enableEncryption
        )
        
        // Return the actual interceptor
        return APulseInterceptor(
            context = context,
            database = database,
            captureSettings = captureSettings
        )
    }
}