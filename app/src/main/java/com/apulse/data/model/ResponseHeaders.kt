package com.apulse.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(
    tableName = "response_headers",
    foreignKeys = [
        ForeignKey(
            entity = NetworkRequest::class,
            parentColumns = ["id"],
            childColumns = ["requestId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("requestId")]
)
@Serializable
data class ResponseHeaders(
    @PrimaryKey
    val id: String,
    val requestId: String,
    val headers: Map<String, String>,
    val rawHeaders: String? = null // original headers as received
)