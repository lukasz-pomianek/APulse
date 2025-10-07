package com.apulse.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SessionWithStats(
    val session: Session,
    val requestCount: Int = 0,
    val totalSize: Long = 0,
    val successCount: Int = 0,
    val errorCount: Int = 0,
    val distinctHosts: Int = 0,
    val averageDuration: Long = 0
)