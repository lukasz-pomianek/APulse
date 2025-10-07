package com.apulse.export.model

import com.apulse.data.model.*
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

/**
 * APulse native export format that preserves all data
 */

@Serializable
data class APulseExport(
    val version: String = "1.0",
    val exportedAt: Instant,
    val creator: APulseCreator = APulseCreator(),
    val sessions: List<APulseSessionExport>,
    val metadata: APulseExportMetadata
)

@Serializable
data class APulseCreator(
    val name: String = "APulse",
    val version: String = "1.0",
    val platform: String = "Android"
)

@Serializable
data class APulseExportMetadata(
    val totalSessions: Int,
    val totalRequests: Int,
    val totalSize: Long,
    val dateRange: APulseDateRange?,
    val includesBodies: Boolean = true,
    val redacted: Boolean = false,
    val tags: List<String> = emptyList()
)

@Serializable
data class APulseDateRange(
    val from: Instant,
    val to: Instant
)

@Serializable
data class APulseSessionExport(
    val session: Session,
    val requests: List<APulseRequestExport>,
    val stats: APulseSessionStats
)

@Serializable
data class APulseSessionStats(
    val requestCount: Int,
    val totalSize: Long,
    val successCount: Int,
    val errorCount: Int,
    val distinctHosts: Int,
    val averageDuration: Long,
    val methodBreakdown: Map<String, Int>,
    val statusBreakdown: Map<String, Int>,
    val hostBreakdown: Map<String, Int>
)

@Serializable
data class APulseRequestExport(
    val request: NetworkRequest,
    val requestHeaders: RequestHeaders? = null,
    val responseHeaders: ResponseHeaders? = null,
    val requestBody: RequestBody? = null,
    val responseBody: ResponseBody? = null,
    val appMetadata: AppMetadata? = null,
    val timing: APulseTimingData? = null
)

@Serializable
data class APulseTimingData(
    val dnsLookup: Long? = null,
    val tcpConnect: Long? = null,
    val sslHandshake: Long? = null,
    val requestSent: Long? = null,
    val waitingTime: Long? = null,
    val contentDownload: Long? = null,
    val totalTime: Long
)

// Simplified export formats for sharing
@Serializable
data class APulseLightExport(
    val version: String = "1.0",
    val exportedAt: Instant,
    val sessions: List<APulseLightSession>
)

@Serializable
data class APulseLightSession(
    val id: String,
    val name: String,
    val createdAt: Instant,
    val requests: List<APulseLightRequest>
)

@Serializable
data class APulseLightRequest(
    val id: String,
    val method: String,
    val url: String,
    val statusCode: Int?,
    val startTime: Instant,
    val duration: Long?,
    val requestSize: Long,
    val responseSize: Long,
    val error: String? = null,
    val tags: List<String> = emptyList(),
    val isBookmarked: Boolean = false
)

// Export options
@Serializable
data class ExportOptions(
    val format: ExportFormat,
    val includeBodies: Boolean = true,
    val includeHeaders: Boolean = true,
    val includeMetadata: Boolean = true,
    val redactSensitiveData: Boolean = true,
    val sessionIds: List<String>? = null, // null = all sessions
    val dateRange: APulseDateRange? = null,
    val maxBodySize: Long? = null,
    val tags: List<String>? = null // filter by tags
)

enum class ExportFormat {
    APULSE_FULL,    // Complete APulse format with all data
    APULSE_LIGHT,   // Simplified format for sharing
    HAR,            // HTTP Archive format
    JSON,           // Raw JSON export
    CSV             // CSV for analysis
}