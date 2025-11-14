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
    
    @Volatile
    private var isInitialized = false
    private lateinit var appContext: Context
    private var config = APulseConfig()
    private val initLock = Any()
    
    /**
     * Initialize APulse with application context and optional configuration
     * 
     * @param context Application context (not activity context)
     * @param configure Optional configuration block
     */
    fun initialize(context: Context, configure: APulseConfig.() -> Unit = {}) {
        if (isInitialized) return
        
        synchronized(initLock) {
            // Double-checked locking pattern
            if (isInitialized) return
            
            appContext = context.applicationContext
            config.apply(configure)
            
            // Allow the UI/app module to perform additional initialization (e.g., ensure session exists)
            try {
                APulseInitializer.initialize(appContext, config)
            } catch (_: Exception) {
                // Safe to ignore when UI module is not present
            }
            
            isInitialized = true
        }
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
    
    /**
     * Create APulse log interceptor for application logging
     * 
     * @param context Application context
     * @param excludedTags List of tags to exclude from logging (default: empty)
     * @return Log interceptor that captures and stores application logs
     */
    fun createLogInterceptor(context: Context, excludedTags: List<String> = emptyList()): APulseLogInterface {
        if (!isInitialized) {
            initialize(context)
        }
        
        return APulseLogFactory.create(context, config, excludedTags)
    }
    
    fun logEvent(eventName: String, data: Map<String, Any>) {
        try {
            android.util.Log.d("APulse", "logEvent $eventName $data")
        } catch (_: Exception) {
            // Safe to ignore when UI module is not present
        }
    }
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
 * Interface for APulse logging functionality
 */
interface APulseLogInterface {
    fun log(priority: Int, tag: String?, message: String, throwable: Throwable? = null)
    fun d(tag: String?, message: String)
    fun i(tag: String?, message: String)
    fun w(tag: String?, message: String)
    fun e(tag: String?, message: String, throwable: Throwable? = null)
}

/**
 * Factory interface for creating APulse log interceptors
 */
interface APulseLogFactory {
    companion object {
        fun create(context: Context, config: APulseConfig, excludedTags: List<String> = emptyList()): APulseLogInterface {
            return try {
                val factoryClass = Class.forName("com.apulse.logging.APulseLogFactoryImpl")
                val factory = factoryClass.getDeclaredConstructor().newInstance() as APulseLogFactory
                factory.createLogInterceptor(context, config, excludedTags)
            } catch (e: Exception) {
                // Fallback for when full APulse is not available
                NoOpLogInterceptor()
            }
        }
    }
    
    fun createLogInterceptor(context: Context, config: APulseConfig, excludedTags: List<String> = emptyList()): APulseLogInterface
}

/**
 * Optional initializer hook implemented in the UI module to perform
 * additional setup at app start (e.g., create a default active session).
 */
interface APulseInitializer {
    companion object {
        fun initialize(context: Context, config: APulseConfig) {
            try {
                val clazz = Class.forName("com.apulse.init.APulseInitializerImpl")
                val impl = clazz.getDeclaredConstructor().newInstance() as APulseInitializer
                impl.onInitialize(context, config)
            } catch (_: Exception) {
                // No UI module present or initializer failed – capture still works with no-op
            }
        }
    }

    fun onInitialize(context: Context, config: APulseConfig)
}

/**
 * No-op interceptor for when APulse UI is not included
 */
private class NoOpInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        return chain.proceed(chain.request())
    }
}

/**
 * No-op log interceptor for when APulse UI is not included
 */
private class NoOpLogInterceptor : APulseLogInterface {
    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        // No-op - could optionally delegate to Android Log
    }
    
    override fun d(tag: String?, message: String) {
        // No-op
    }
    
    override fun i(tag: String?, message: String) {
        // No-op  
    }
    
    override fun w(tag: String?, message: String) {
        // No-op
    }
    
    override fun e(tag: String?, message: String, throwable: Throwable?) {
        // No-op
    }
}