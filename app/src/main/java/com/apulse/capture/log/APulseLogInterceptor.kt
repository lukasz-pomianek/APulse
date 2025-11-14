package com.apulse.capture.log

import android.content.Context
import android.util.Log
import com.apulse.core.APulseLogInterface
import com.apulse.data.db.APulseDatabase
import com.apulse.data.model.AppLog
import com.apulse.service.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import java.io.PrintWriter
import java.io.StringWriter
import java.util.UUID

class APulseLogInterceptor private constructor(
    private val database: APulseDatabase,
    private val sessionManager: SessionManager,
    private val excludedTags: List<String> = emptyList()
) : APulseLogInterface {
    
    private val scope = CoroutineScope(Dispatchers.IO)
    
    companion object {
        @Volatile
        private var INSTANCE: APulseLogInterceptor? = null
        
        // Length limits to prevent SQLite cursor window overflow
        private const val MAX_MESSAGE_LENGTH = 500
        private const val MAX_ERROR_LENGTH = 1000
        private const val MAX_STACK_TRACE_LENGTH = 2000
        
        fun getInstance(context: Context, excludedTags: List<String> = emptyList()): APulseLogInterceptor {
            return INSTANCE ?: synchronized(this) {
                val database = APulseDatabase.getDatabase(context)
                val sessionManager = SessionManager.getInstance(database)
                val instance = APulseLogInterceptor(database, sessionManager, excludedTags)
                INSTANCE = instance
                instance
            }
        }
        
        fun log(
            priority: Int,
            tag: String?,
            message: String,
            t: Throwable? = null
        ) {
            // This will be set up by the factory
            INSTANCE?.captureLog(priority, tag, message, t)
        }
    }
    
    fun captureLog(
        priority: Int,
        tag: String?,
        message: String,
        t: Throwable?
    ) {
        // Check if this tag should be excluded from logging
        if (tag != null && excludedTags.contains(tag)) {
            return
        }
        if (sessionManager.currentSession.value == null) {
            return
        }

        scope.launch {
            try {
                val currentSessionId = sessionManager.getCurrentSessionId()
                
                val appLog = createAppLog(
                    sessionId = currentSessionId,
                    priority = priority,
                    tag = tag,
                    message = message,
                    throwable = t
                )
                
                database.appLogDao().insertLog(appLog)
            } catch (e: Exception) {
                // Fallback to system log if our logging fails
                Log.e("APulseLogInterceptor", "Failed to capture log", e)
                t?.printStackTrace()
            }
        }
    }
    
    private fun createAppLog(
        sessionId: String,
        priority: Int,
        tag: String?,
        message: String,
        throwable: Throwable?
    ): AppLog {
        val rawStackTrace = throwable?.let { getStackTraceString(it) }
        val errorMessage = throwable?.message
        
        // Limit field lengths to prevent SQLite cursor window overflow
        val limitedMessage = if (message.length > MAX_MESSAGE_LENGTH) {
            message.take(MAX_MESSAGE_LENGTH) + "..."
        } else {
            message
        }
        
        val limitedError = errorMessage?.let { error ->
            if (error.length > MAX_ERROR_LENGTH) {
                error.take(MAX_ERROR_LENGTH) + "..."
            } else {
                error
            }
        }
        
        val limitedStackTrace = rawStackTrace?.let { stackTrace ->
            if (stackTrace.length > MAX_STACK_TRACE_LENGTH) {
                stackTrace.take(MAX_STACK_TRACE_LENGTH) + "..."
            } else {
                stackTrace
            }
        }
        
        // Get caller information from stack trace
        val callerInfo = getCallerInfo()
        
        return AppLog(
            id = UUID.randomUUID().toString(),
            sessionId = sessionId,
            priority = priority,
            tag = tag,
            message = limitedMessage,
            error = limitedError,
            stackTrace = limitedStackTrace,
            timestamp = Clock.System.now(),
            threadName = Thread.currentThread().name,
            className = callerInfo.className,
            methodName = callerInfo.methodName,
            lineNumber = callerInfo.lineNumber
        )
    }
    
    private fun getStackTraceString(throwable: Throwable): String {
        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)
        throwable.printStackTrace(printWriter)
        return stringWriter.toString()
    }
    
    private fun getCallerInfo(): CallerInfo {
        val stackTrace = Thread.currentThread().stackTrace
        
        // Find the first stack trace element that's not part of our logging infrastructure
        for (element in stackTrace) {
            val className = element.className
            if (!className.startsWith("com.apulse.capture.log") &&
                !className.startsWith("android.util.Log") &&
                !className.contains("Method.invoke") &&
                !className.contains("$") &&
                className != "java.lang.Thread"
            ) {
                return CallerInfo(
                    className = className.substringAfterLast('.'),
                    methodName = element.methodName,
                    lineNumber = if (element.lineNumber > 0) element.lineNumber else null
                )
            }
        }
        
        return CallerInfo(null, null, null)
    }
    
    private data class CallerInfo(
        val className: String?,
        val methodName: String?,
        val lineNumber: Int?
    )
    
    // APulseLogInterface implementation
    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        captureLog(priority, tag, message, throwable)
    }
    
    override fun d(tag: String?, message: String) {
        captureLog(Log.DEBUG, tag, message, null)
    }
    
    override fun i(tag: String?, message: String) {
        captureLog(Log.INFO, tag, message, null)
    }
    
    override fun w(tag: String?, message: String) {
        captureLog(Log.WARN, tag, message, null)
    }
    
    override fun e(tag: String?, message: String, throwable: Throwable?) {
        captureLog(Log.ERROR, tag, message, throwable)
    }
}

// Factory/Builder pattern for easy setup
class APulseLogFactory private constructor() {
    
    companion object {
        private var initialized = false
        
        fun initialize(context: Context, excludedTags: List<String> = emptyList()): APulseLogInterceptor {
            val interceptor = APulseLogInterceptor.getInstance(context, excludedTags)
            initialized = true
            return interceptor
        }
        
        fun isInitialized(): Boolean = initialized
        
        // Convenience methods that match standard Android Log interface
        fun v(tag: String?, message: String): Int {
            APulseLogInterceptor.log(AppLog.VERBOSE, tag, message)
            return 0
        }
        
        fun v(tag: String?, message: String, tr: Throwable?): Int {
            APulseLogInterceptor.log(AppLog.VERBOSE, tag, message, tr)
            return 0
        }
        
        fun d(tag: String?, message: String): Int {
            APulseLogInterceptor.log(AppLog.DEBUG, tag, message)
            return 0
        }
        
        fun d(tag: String?, message: String, tr: Throwable?): Int {
            APulseLogInterceptor.log(AppLog.DEBUG, tag, message, tr)
            return 0
        }
        
        fun i(tag: String?, message: String): Int {
            APulseLogInterceptor.log(AppLog.INFO, tag, message)
            return 0
        }
        
        fun i(tag: String?, message: String, tr: Throwable?): Int {
            APulseLogInterceptor.log(AppLog.INFO, tag, message, tr)
            return 0
        }
        
        fun w(tag: String?, message: String): Int {
            APulseLogInterceptor.log(AppLog.WARN, tag, message)
            return 0
        }
        
        fun w(tag: String?, message: String, tr: Throwable?): Int {
            APulseLogInterceptor.log(AppLog.WARN, tag, message, tr)
            return 0
        }
        
        fun e(tag: String?, message: String): Int {
            APulseLogInterceptor.log(AppLog.ERROR, tag, message)
            return 0
        }
        
        fun e(tag: String?, message: String, tr: Throwable?): Int {
            APulseLogInterceptor.log(AppLog.ERROR, tag, message, tr)
            return 0
        }
        
        fun wtf(tag: String?, message: String): Int {
            APulseLogInterceptor.log(AppLog.ASSERT, tag, message)
            return 0
        }
        
        fun wtf(tag: String?, message: String, tr: Throwable?): Int {
            APulseLogInterceptor.log(AppLog.ASSERT, tag, message, tr)
            return 0
        }
        
        // Main log function that handles the interface you requested
        fun log(
            priority: Int,
            tag: String?,
            message: String,
            t: Throwable? = null
        ) {
            APulseLogInterceptor.log(priority, tag, message, t)
        }
    }
}