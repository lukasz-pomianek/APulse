package com.apulse.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.apulse.R
import com.apulse.capture.interceptor.CaptureSettings
import com.apulse.data.db.APulseDatabase
// MainActivity removed - service is now library-compatible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class NetworkCaptureService : Service() {
    
    companion object {
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "apulse_capture_channel"
        private const val CHANNEL_NAME = "APulse Network Capture"
        
        const val ACTION_START_CAPTURE = "com.apulse.START_CAPTURE"
        const val ACTION_STOP_CAPTURE = "com.apulse.STOP_CAPTURE"
        const val ACTION_TOGGLE_CAPTURE = "com.apulse.TOGGLE_CAPTURE"
    }
    
    private lateinit var database: APulseDatabase
    private lateinit var captureSettings: CaptureSettings
    private lateinit var sessionManager: SessionManager
    
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var statsJob: Job? = null
    
    private var requestCount = 0
    private var isCapturing = false
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize dependencies manually
        database = APulseDatabase.getDatabase(this)
        captureSettings = CaptureSettings(this)
        sessionManager = SessionManager(database)
        
        createNotificationChannel()
        startMonitoring()
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START_CAPTURE -> startCapture()
            ACTION_STOP_CAPTURE -> stopCapture()
            ACTION_TOGGLE_CAPTURE -> toggleCapture()
            else -> {
                // Default behavior - start if capture is enabled
                if (captureSettings.isCaptureEnabled()) {
                    startCapture()
                } else {
                    stopSelf()
                }
            }
        }
        
        return START_STICKY
    }
    
    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        statsJob?.cancel()
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Shows network capture status"
                setShowBadge(false)
            }
            
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    private fun startCapture() {
        isCapturing = true
        captureSettings.setCaptureEnabled(true)
        startForeground(NOTIFICATION_ID, createNotification())
        
        // Ensure there's an active session
        serviceScope.launch {
            sessionManager.getOrCreateCurrentSession()
        }
    }
    
    private fun stopCapture() {
        isCapturing = false
        captureSettings.setCaptureEnabled(false)
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }
    
    private fun toggleCapture() {
        if (isCapturing) {
            stopCapture()
        } else {
            startCapture()
        }
    }
    
    private fun startMonitoring() {
        // Monitor session changes and update notification
        statsJob = serviceScope.launch {
            combine(
                sessionManager.currentSession,
                sessionManager.currentSessionId
            ) { session, sessionId ->
                session to sessionId
            }.collect { (session, sessionId) ->
                if (session != null && sessionId != null) {
                    // Update request count for the current session
                    val count = database.networkRequestDao().getRequestCountForSession(sessionId)
                    requestCount = count
                    
                    // Update notification if service is running
                    if (isCapturing) {
                        val notification = createNotification()
                        val notificationManager = getSystemService(NotificationManager::class.java)
                        notificationManager.notify(NOTIFICATION_ID, notification)
                    }
                }
            }
        }
    }
    
    private fun createNotification(): Notification {
        // Note: Library doesn't specify what activity to open
        // Consuming apps should configure their own notification behavior
        
        val toggleIntent = Intent(this, NetworkCaptureService::class.java).apply {
            action = ACTION_TOGGLE_CAPTURE
        }
        val togglePendingIntent = PendingIntent.getService(
            this, 1, toggleIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val currentSessionName = sessionManager.currentSession.value?.name ?: "Unknown Session"
        
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification) // We'd need to create this icon
            .setContentTitle("APulse - Network Capture")
            .setContentText("Captured $requestCount requests in $currentSessionName")
            .addAction(
                R.drawable.ic_stop, // We'd need this icon too
                if (isCapturing) "Stop" else "Start",
                togglePendingIntent
            )
            .setOngoing(true)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setShowWhen(false)
            .build()
    }
}

// Extension functions to start/stop the service
fun Context.startNetworkCapture() {
    val intent = Intent(this, NetworkCaptureService::class.java).apply {
        action = NetworkCaptureService.ACTION_START_CAPTURE
    }
    startService(intent)
}

fun Context.stopNetworkCapture() {
    val intent = Intent(this, NetworkCaptureService::class.java).apply {
        action = NetworkCaptureService.ACTION_STOP_CAPTURE
    }
    startService(intent)
}

fun Context.toggleNetworkCapture() {
    val intent = Intent(this, NetworkCaptureService::class.java).apply {
        action = NetworkCaptureService.ACTION_TOGGLE_CAPTURE
    }
    startService(intent)
}