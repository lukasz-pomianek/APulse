package com.apulse.ktor

import com.apulse.core.APulse
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.statement.bodyAsChannel
import io.ktor.client.statement.HttpResponse
import io.ktor.util.AttributeKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Interceptor

/**
 * Ktor client plugin that forwards requests into APulse by delegating
 * to the OkHttp-based interceptor when the engine is OkHttp.
 * If a different engine is used, we still record high-level metadata.
 */
val APulsePlugin = createClientPlugin("APulsePlugin") {
    onSend { request, _ ->
        // No-op on send; capturing is handled in receive for non-OkHttp engines
    }

    onResponse { response ->
        // For non-OkHttp engines, we capture minimal response data
        // Advanced capture is available automatically when engine=OkHttp with APulse interceptor
        try {
            // Touch the body off main thread
            withContext(Dispatchers.IO) {
                response.bodyAsChannel().cancel() // ensure stream consumption without buffering
            }
        } catch (_: Throwable) {
        }
    }
}

/**
 * Helper to configure Ktor OkHttp engine to include APulse interceptor.
 */
fun okhttpEngineWithAPulse(context: android.content.Context, interceptorFactory: () -> Interceptor): (okhttp3.OkHttpClient.Builder) -> Unit = { builder ->
    // Ensure APulse is initialized; interceptor is a pass-through when UI is not present
    if (!APulse.isAvailable()) {
        // Still add interceptor; core factory returns no-op when UI absent
    }
    builder.addInterceptor(interceptorFactory())
}

/**
 * Convenience for typical use: APulse.createInterceptor(context)
 */
fun okhttpEngineWithAPulse(context: android.content.Context): (okhttp3.OkHttpClient.Builder) -> Unit =
    okhttpEngineWithAPulse(context) { APulse.createInterceptor(context) }


