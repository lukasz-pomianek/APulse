package com.apulse.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlin.time.Instant
import kotlinx.serialization.Serializable

@Entity(
    tableName = "app_logs",
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
        Index("priority"),
        Index("tag"),
        Index("timestamp")
    ]
)
@Serializable
data class AppLog(
    @PrimaryKey
    val id: String,
    val sessionId: String,
    val priority: Int, // Log.VERBOSE, Log.DEBUG, Log.INFO, Log.WARN, Log.ERROR, Log.ASSERT
    val tag: String?,
    val message: String,
    val error: String? = null, // Serialized throwable
    val stackTrace: String? = null,
    val timestamp: Instant,
    val threadName: String? = null,
    val className: String? = null,
    val methodName: String? = null,
    val lineNumber: Int? = null
) {
    companion object {
        // Android Log levels
        const val VERBOSE = 2
        const val DEBUG = 3
        const val INFO = 4
        const val WARN = 5
        const val ERROR = 6
        const val ASSERT = 7
        
        fun getPriorityName(priority: Int): String = when (priority) {
            VERBOSE -> "VERBOSE"
            DEBUG -> "DEBUG"
            INFO -> "INFO"
            WARN -> "WARN"
            ERROR -> "ERROR"
            ASSERT -> "ASSERT"
            else -> "VERBOSE"
        }
        
        fun getPriorityShortName(priority: Int): String = when (priority) {
            VERBOSE -> "V"
            DEBUG -> "D"
            INFO -> "I"
            WARN -> "W"
            ERROR -> "E"
            ASSERT -> "A"
            else -> "V"
        }
        
        fun getPriorityColor(priority: Int): androidx.compose.ui.graphics.Color = when (priority) {
            VERBOSE -> androidx.compose.ui.graphics.Color.Gray
            DEBUG -> androidx.compose.ui.graphics.Color.Blue
            INFO -> androidx.compose.ui.graphics.Color.Green
            WARN -> androidx.compose.ui.graphics.Color(0xFFFF9800) // Orange
            ERROR -> androidx.compose.ui.graphics.Color.Red
            ASSERT -> androidx.compose.ui.graphics.Color.Magenta
            else -> androidx.compose.ui.graphics.Color.Gray
        }
    }
}