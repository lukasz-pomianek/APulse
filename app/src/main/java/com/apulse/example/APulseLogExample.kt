package com.apulse.example

import com.apulse.capture.log.APulseLogFactory
import com.apulse.data.model.AppLog

/**
 * Example usage of APulse Log Interceptor
 * 
 * This demonstrates how to use the APulse logging system to capture
 * application logs for debugging purposes.
 */
object APulseLogExample {
    
    private const val TAG = "APulseLogExample"
    
    fun demonstrateLogging() {
        android.util.Log.d("APulseLogExample", "Starting demonstrateLogging()")
        
        try {
            // Basic logging - matches the interface you requested
            APulseLogFactory.log(
                priority = AppLog.INFO,
                tag = TAG,
                message = "Application started successfully"
            )
            android.util.Log.d("APulseLogExample", "First log call completed")
        } catch (e: Exception) {
            android.util.Log.e("APulseLogExample", "Failed to log basic message", e)
        }
        
        // Logging with throwable
        try {
            // Simulate some operation that might fail
            val result = 10 / 0
        } catch (e: Exception) {
            try {
                APulseLogFactory.log(
                    priority = AppLog.ERROR,
                    tag = TAG,
                    message = "Division by zero occurred",
                    t = e
                )
                android.util.Log.d("APulseLogExample", "Error log call completed")
            } catch (logException: Exception) {
                android.util.Log.e("APulseLogExample", "Failed to log error message", logException)
            }
        }
        
        // Convenience methods that match Android Log interface
        APulseLogFactory.v(TAG, "Verbose log message")
        APulseLogFactory.d(TAG, "Debug information")
        APulseLogFactory.i(TAG, "Important information")
        APulseLogFactory.w(TAG, "Warning message")
        APulseLogFactory.e(TAG, "Error message")
        
        // Logging with throwable using convenience methods
        val exception = RuntimeException("Something went wrong")
        APulseLogFactory.e(TAG, "Error occurred", exception)
        
        // Different priority levels
        APulseLogFactory.log(AppLog.VERBOSE, "Network", "HTTP request started")
        APulseLogFactory.log(AppLog.DEBUG, "Database", "Query executed in 15ms")
        APulseLogFactory.log(AppLog.INFO, "Auth", "User logged in successfully")
        APulseLogFactory.log(AppLog.WARN, "Memory", "Memory usage at 80%")
        APulseLogFactory.log(AppLog.ERROR, "Crash", "Unhandled exception caught")
        APulseLogFactory.log(AppLog.ASSERT, "Critical", "Critical system error")
    }
    
    fun logNetworkOperation() {
        APulseLogFactory.i("Network", "Starting API call to /users/profile")
        
        try {
            // Simulate network call
            Thread.sleep(100)
            APulseLogFactory.d("Network", "Received 200 OK response")
            APulseLogFactory.i("Network", "API call completed successfully")
        } catch (e: Exception) {
            APulseLogFactory.e("Network", "Network request failed", e)
        }
    }
    
    fun logDatabaseOperation() {
        APulseLogFactory.d("Database", "Opening database connection")
        
        try {
            // Simulate database operation
            APulseLogFactory.v("Database", "Executing SELECT query")
            APulseLogFactory.d("Database", "Query returned 5 results")
        } catch (e: Exception) {
            APulseLogFactory.e("Database", "Database operation failed", e)
        } finally {
            APulseLogFactory.d("Database", "Closing database connection")
        }
    }
    
    fun logUserInteraction() {
        APulseLogFactory.i("UI", "User clicked login button")
        APulseLogFactory.d("UI", "Validating user input")
        APulseLogFactory.v("UI", "Email format is valid")
        APulseLogFactory.v("UI", "Password length is sufficient")
        APulseLogFactory.i("UI", "Starting authentication process")
    }
}