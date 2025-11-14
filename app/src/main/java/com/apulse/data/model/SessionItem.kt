package com.apulse.data.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
sealed class SessionItem {
    abstract val timestamp: Instant
    abstract val sessionId: String
    
    @Serializable
    data class RequestItem(
        val request: NetworkRequest,
        override val timestamp: Instant = request.startTime,
        override val sessionId: String = request.sessionId
    ) : SessionItem()
    
    @Serializable 
    data class LogItem(
        val log: AppLog,
        override val timestamp: Instant = log.timestamp,
        override val sessionId: String = log.sessionId
    ) : SessionItem()
}