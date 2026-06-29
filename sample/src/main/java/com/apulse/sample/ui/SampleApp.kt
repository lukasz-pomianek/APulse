@file:OptIn(
    kotlinx.serialization.InternalSerializationApi::class,
    kotlin.time.ExperimentalTime::class
)

package com.apulse.sample.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.apulse.data.db.APulseDatabase
import com.apulse.data.model.AppLog
import com.apulse.data.model.NetworkRequest
import com.apulse.service.SessionManager
import com.apulse.ui.APulseApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Instant.Companion.fromEpochMilliseconds

@Composable
fun SampleApp() {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            seedSampleDataIfNeeded(context)
        }
    }

    APulseApp()
}

private suspend fun seedSampleDataIfNeeded(context: Context) {
    val prefs = context.getSharedPreferences("apulse_sample_prefs", Context.MODE_PRIVATE)
    if (prefs.getBoolean("data_seeded", false)) return

    val database = APulseDatabase.getDatabase(context)
    val sessionManager = SessionManager.getInstance(database)

    val demoSession = sessionManager.createSession(
        name = "Demo Session",
        description = "Sample data showcasing APulse features",
        tags = listOf("demo", "sample"),
        activateImmediately = false
    )
    val sessionId = demoSession.id
    val now = Clock.System.now()
    val nowMs = System.currentTimeMillis()

    // ── Network Requests ─────────────────────────────────────────
    val requests = listOf(
        buildRequest(
            sessionId, "GET",
            "https://api.example.com/users", "api.example.com", "/users",
            statusCode = 200, startTime = now - 4.hours, durationMs = 312L, responseSize = 4_820L
        ),
        buildRequest(
            sessionId, "POST",
            "https://api.example.com/users", "api.example.com", "/users",
            statusCode = 201, startTime = now - 3.hours - 20.minutes, durationMs = 187L, responseSize = 640L,
            requestSize = 320L
        ),
        buildRequest(
            sessionId, "GET",
            "https://api.example.com/products?page=1&limit=20", "api.example.com", "/products",
            query = "page=1&limit=20",
            statusCode = 200, startTime = now - 2.hours - 45.minutes, durationMs = 524L, responseSize = 12_340L
        ),
        buildRequest(
            sessionId, "PUT",
            "https://api.example.com/users/42", "api.example.com", "/users/42",
            statusCode = 200, startTime = now - 2.hours, durationMs = 98L, responseSize = 430L,
            requestSize = 256L
        ),
        buildRequest(
            sessionId, "DELETE",
            "https://api.example.com/users/15", "api.example.com", "/users/15",
            statusCode = 204, startTime = now - 1.hours - 30.minutes, durationMs = 78L, responseSize = 0L
        ),
        buildRequest(
            sessionId, "POST",
            "https://auth.example.com/oauth/token", "auth.example.com", "/oauth/token",
            statusCode = 401, startTime = now - 1.hours, durationMs = 145L, responseSize = 220L,
            requestSize = 128L
        ),
        buildRequest(
            sessionId, "GET",
            "https://cdn.example.com/images/profile.jpg", "cdn.example.com", "/images/profile.jpg",
            statusCode = 200, startTime = now - 40.minutes, durationMs = 654L, responseSize = 89_320L
        ),
        buildRequest(
            sessionId, "POST",
            "https://api.example.com/orders", "api.example.com", "/orders",
            statusCode = 500, startTime = now - 10.minutes, durationMs = 2_103L, responseSize = 180L,
            requestSize = 512L,
            error = "InternalServerError: Unexpected condition at orders service"
        ),
    )
    database.networkRequestDao().let { dao ->
        requests.forEach { dao.insertRequestSuspend(it) }
    }

    // ── App Logs ─────────────────────────────────────────────────
    val logs = listOf(
        buildLog(sessionId, AppLog.INFO,  "MainActivity",       "Application started successfully",                              nowMs - 4 * 3_600_000L),
        buildLog(sessionId, AppLog.DEBUG, "NetworkInterceptor", "Outgoing request: GET https://api.example.com/users",          nowMs - 4 * 3_600_000L + 500L),
        buildLog(sessionId, AppLog.DEBUG, "NetworkInterceptor", "Response 200 received from api.example.com in 312ms",          nowMs - 4 * 3_600_000L + 900L),
        buildLog(sessionId, AppLog.INFO,  "UserRepository",     "Loaded 10 users from api.example.com",                        nowMs - 3 * 3_600_000L),
        buildLog(sessionId, AppLog.DEBUG, "CacheManager",       "Cache hit: /products?page=1&limit=20 (TTL remaining: 4m 32s)", nowMs - 2 * 3_600_000L),
        buildLog(sessionId, AppLog.WARN,  "SessionManager",     "Session will expire in 5 minutes, refreshing token",           nowMs - 3_600_000L),
        buildLog(sessionId, AppLog.ERROR, "ApiService",         "Request failed: POST /orders → 500 Internal Server Error",     nowMs - 600_000L,
                 error = "InternalServerException: Unexpected condition at orders service"),
        buildLog(sessionId, AppLog.WARN,  "NetworkInterceptor", "Slow response detected: 2103ms for POST /orders (threshold: 1000ms)", nowMs - 590_000L),
        buildLog(sessionId, AppLog.INFO,  "AnalyticsTracker",   "Screen view tracked: ProductListScreen",                      nowMs - 300_000L),
        buildLog(sessionId, AppLog.DEBUG, "NetworkInterceptor", "Outgoing request: GET https://cdn.example.com/images/profile.jpg", nowMs - 120_000L),
    )
    database.appLogDao().insertLogs(logs)

    prefs.edit().putBoolean("data_seeded", true).apply()
}

private fun buildRequest(
    sessionId: String,
    method: String,
    url: String,
    host: String,
    path: String,
    statusCode: Int,
    startTime: kotlin.time.Instant,
    durationMs: Long,
    responseSize: Long,
    requestSize: Long = 0L,
    query: String? = null,
    error: String? = null
): NetworkRequest = NetworkRequest(
    id = UUID.randomUUID().toString(),
    sessionId = sessionId,
    url = url,
    method = method,
    host = host,
    path = path,
    query = query,
    startTime = startTime,
    endTime = startTime + durationMs.milliseconds,
    duration = durationMs,
    statusCode = statusCode,
    requestSize = requestSize,
    responseSize = responseSize,
    error = error
)

private fun buildLog(
    sessionId: String,
    priority: Int,
    tag: String,
    message: String,
    timestampMs: Long,
    error: String? = null
): AppLog = AppLog(
    id = UUID.randomUUID().toString(),
    sessionId = sessionId,
    priority = priority,
    tag = tag,
    message = message,
    error = error,
    timestamp = fromEpochMilliseconds(timestampMs),
    threadName = when (priority) {
        AppLog.ERROR, AppLog.WARN -> "main"
        else -> "OkHttp Dispatcher"
    }
)
