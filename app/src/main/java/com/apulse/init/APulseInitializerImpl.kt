package com.apulse.init

import android.content.Context
import com.apulse.core.APulseConfig
import com.apulse.core.APulseInitializer
import com.apulse.data.db.APulseDatabase
import com.apulse.service.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi

class APulseInitializerImpl : APulseInitializer {
    
    @OptIn(InternalSerializationApi::class)
    override fun onInitialize(context: Context, config: APulseConfig) {
        val database = APulseDatabase.getDatabase(context)
        val sessionManager = SessionManager.getInstance(database)
        
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val session = sessionManager.ensureActiveSession()
                android.util.Log.d("APulseInitializerImpl", "APulse initialized with session: ${session.id}")
                sessionManager.cleanupOldSessions(7)
            } catch (e: Exception) {
                android.util.Log.e("APulseInitializerImpl", "Failed to initialize APulse", e)
            }
        }
    }
}


