package com.apulse.init

import android.content.Context
import com.apulse.core.APulseConfig
import com.apulse.core.APulseInitializer
import com.apulse.data.db.APulseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import java.util.UUID

class APulseInitializerImpl : APulseInitializer {
    override fun onInitialize(context: Context, config: APulseConfig) {
        // Ensure there's at least one active session on app start
        val database = APulseDatabase.getDatabase(context)
        CoroutineScope(Dispatchers.IO).launch {
            val active = database.sessionDao().getActiveSession()
            if (active == null) {
                val now = Clock.System.now()
                val session = com.apulse.data.model.Session(
                    id = UUID.randomUUID().toString(),
                    name = "Default Session",
                    description = "Auto-created on app start",
                    createdAt = now,
                    updatedAt = now,
                    isActive = true,
                    tags = listOf("default")
                )
                database.sessionDao().insertSession(session)
                database.sessionDao().deactivateOtherSessions(session.id)
            }
        }
    }
}


