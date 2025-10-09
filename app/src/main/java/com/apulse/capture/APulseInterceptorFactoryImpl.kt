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
        val database = APulseDatabase.getDatabase(context)
        
        // Create dependencies manually (no DI)
        val captureSettings = CaptureSettings(context)
        
        // Return the actual interceptor
        return APulseInterceptor(
            context = context,
            database = database,
            captureSettings = captureSettings
        )
    }
}