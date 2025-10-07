package com.apulse.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(
    tableName = "app_metadata",
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
data class AppMetadata(
    @PrimaryKey
    val id: String,
    val requestId: String,
    val screenName: String? = null,
    val activityName: String? = null,
    val fragmentName: String? = null,
    val userId: String? = null,
    val userAgent: String? = null,
    val buildVersion: String? = null,
    val buildType: String? = null, // debug, release
    val deviceInfo: String? = null,
    val osVersion: String? = null,
    val appVersion: String? = null,
    val threadName: String? = null,
    val customData: Map<String, String> = emptyMap()
)