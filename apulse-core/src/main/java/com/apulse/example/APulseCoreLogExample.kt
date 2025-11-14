package com.apulse.example

import android.content.Context
import com.apulse.core.APulse

/**
 * Przykład użycia systemu logowania APulse przez API w apulse-core
 */
class APulseCoreLogExample {

    fun demonstrateLogging(context: Context) {
        // Inicjalizacja APulse (jeśli jeszcze nie została wykonana)
        APulse.initialize(context) {
            enableNotifications = true
            maxStorageSize = 100 * 1024 * 1024 // 100MB
        }
        
        // Utworzenie log interceptora przez API core
        val logger = APulse.createLogInterceptor(context)
        
        // Przykłady różnych poziomów logowania
        
        // Debug
        logger.d("CoreExample", "APulse logging system initialized")
        
        // Info
        logger.i("CoreExample", "Processing user request")
        
        // Warning  
        logger.w("CoreExample", "Network connection unstable")
        
        // Error z wyjątkiem
        try {
            val result = 10 / 0
        } catch (e: Exception) {
            logger.e("CoreExample", "Math operation failed", e)
        }
        
        // Bezpośrednie użycie log() z priorytetem
        logger.log(android.util.Log.INFO, "CoreExample", "Custom priority logging", null)
        
        // Logowanie z niestandardowym tagiem
        logger.i("MyModule", "Feature XYZ completed successfully")
        logger.e("NetworkLayer", "Connection timeout", RuntimeException("Timeout after 30s"))
    }
    
    fun logUserActions(context: Context) {
        val logger = APulse.createLogInterceptor(context)
        
        // Logowanie akcji użytkownika
        logger.i("UserAction", "User opened main screen")
        logger.i("UserAction", "User clicked on item: 123")
        logger.w("UserAction", "User attempted invalid operation")
        
        // Logowanie danych biznesowych
        logger.d("BusinessLogic", "Processing order: ORDER_123")
        logger.i("BusinessLogic", "Payment processed successfully")
        
        // Error handling
        try {
            // Symulacja błędu
            throw IllegalStateException("Invalid state in business logic")
        } catch (e: Exception) {
            logger.e("BusinessLogic", "Critical business error occurred", e)
        }
    }
}