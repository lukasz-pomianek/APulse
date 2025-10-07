package com.apulse.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Entity(
    tableName = "network_requests",
    foreignKeys = [
        ForeignKey(
            entity = Session::class,
            parentColumns = ["id"],
            childColumns = ["sessionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("sessionId"),
        Index("url"),
        Index("method"),
        Index("statusCode"),
        Index("startTime")
    ]
)
@Serializable
data class NetworkRequest(
    @PrimaryKey
    val id: String,
    val sessionId: String,
    val url: String,
    val method: String,
    val protocol: String? = null,
    val host: String,
    val path: String,
    val query: String? = null,
    val startTime: Instant,
    val endTime: Instant? = null,
    val duration: Long? = null, // in milliseconds
    val statusCode: Int? = null,
    val statusMessage: String? = null,
    val requestSize: Long = 0, // in bytes
    val responseSize: Long = 0, // in bytes
    val mimeType: String? = null,
    val isBookmarked: Boolean = false,
    val tags: List<String> = emptyList(),
    val notes: String? = null,
    val error: String? = null
)