package com.apulse.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Entity(tableName = "sessions")
@Serializable
data class Session(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String? = null,
    val createdAt: Instant,
    val updatedAt: Instant,
    val isActive: Boolean = true,
    val totalRequests: Int = 0,
    val totalSize: Long = 0, // in bytes
    val tags: List<String> = emptyList(),
    val metadata: Map<String, String> = emptyMap()
)