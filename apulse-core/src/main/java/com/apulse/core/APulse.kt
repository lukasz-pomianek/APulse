package com.apulse.core

import android.content.Context
import android.content.Intent
import okhttp3.Interceptor

/**
 * APulse - Network debugging library for Android
 * 
 * Main entry point for integrating APulse into your Android application.
 * Provides network capture, session management, and debugging UI.
 */
object APulse {
    
    private var isInitialized = false
    private lateinit var appContext: Context
    private var config = APulseConfig()
    
    /**
     * Initialize APulse with application context and optional configuration
     * 
     * @param context Application context (not activity context)
     * @param configure Optional configuration block
     */
    fun initialize(context: Context, configure: APulseConfig.() -> Unit = {}) {
        if (isInitialized) return
        
        appContext = context.applicationContext
        config.apply(configure)
        isInitialized = true
    }
    
    /**
     * Create OkHttp interceptor for network capture
     * 
     * @param context Application context
     * @return Interceptor that captures all HTTP traffic
     */
    fun createInterceptor(context: Context): Interceptor {
        if (!isInitialized) {
            initialize(context)
        }
        
        // This would return the actual APulseInterceptor from the main app module
        // For now, we'll create a factory method that the main app implements
        return APulseInterceptorFactory.create(context, config)
    }
    
    /**
     * Launch APulse main UI
     * 
     * @param context Context to launch from (Activity context preferred)
     */
    fun launch(context: Context) {
        val intent = Intent().apply {
            setClassName(context.packageName, "com.apulse.ui.MainActivity")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
    
    /**
     * Launch APulse directly to current session
     * 
     * @param context Context to launch from
     */
    fun launchCurrentSession(context: Context) {
        val intent = Intent().apply {
            setClassName(context.packageName, "com.apulse.ui.MainActivity")
            putExtra("openCurrentSession", true)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
    
    /**
     * Check if APulse is available (debug builds only typically)
     */
    fun isAvailable(): Boolean {
        return try {
            Class.forName("com.apulse.ui.MainActivity")
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }
    
    /**
     * Get current configuration
     */
    fun getConfig(): APulseConfig = config
}

/**
 * Configuration class for APulse
 */
data class APulseConfig(
    var enableNotifications: Boolean = true,
    var maxStorageSize: Long = 100 * 1024 * 1024, // 100MB
    var enableAutoRedaction: Boolean = true,
    var maxRequestBodySize: Long = 1024 * 1024, // 1MB
    var maxResponseBodySize: Long = 1024 * 1024, // 1MB
    var retentionDays: Int = 7,
    var enableEncryption: Boolean = true
)

/**
 * Factory interface that the main app module implements
 * This allows the core module to remain lightweight
 */
interface APulseInterceptorFactory {
    companion object {
        fun create(context: Context, config: APulseConfig): Interceptor {
            return try {
                val factoryClass = Class.forName("com.apulse.capture.APulseInterceptorFactoryImpl")
                val factory = factoryClass.getDeclaredConstructor().newInstance() as APulseInterceptorFactory
                factory.createInterceptor(context, config)
            } catch (e: Exception) {
                // Fallback for when full APulse is not available
                NoOpInterceptor()
            }
        }
    }
    
    fun createInterceptor(context: Context, config: APulseConfig): Interceptor
}

/**
 * No-op interceptor for when APulse UI is not included
 */
private class NoOpInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        return chain.proceed(chain.request())
    }
}