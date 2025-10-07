package com.apulse.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(
    tableName = "request_bodies",
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
data class RequestBody(
    @PrimaryKey
    val id: String,
    val requestId: String,
    val body: ByteArray? = null,
    val bodyText: String? = null, // for text content
    val contentType: String? = null,
    val contentEncoding: String? = null,
    val size: Long = 0,
    val isRedacted: Boolean = false,
    val filePath: String? = null // path for large files stored externally
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RequestBody

        if (id != other.id) return false
        if (requestId != other.requestId) return false
        if (body != null) {
            if (other.body == null) return false
            if (!body.contentEquals(other.body)) return false
        } else if (other.body != null) return false
        if (bodyText != other.bodyText) return false
        if (contentType != other.contentType) return false
        if (contentEncoding != other.contentEncoding) return false
        if (size != other.size) return false
        if (isRedacted != other.isRedacted) return false
        if (filePath != other.filePath) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + requestId.hashCode()
        result = 31 * result + (body?.contentHashCode() ?: 0)
        result = 31 * result + (bodyText?.hashCode() ?: 0)
        result = 31 * result + (contentType?.hashCode() ?: 0)
        result = 31 * result + (contentEncoding?.hashCode() ?: 0)
        result = 31 * result + size.hashCode()
        result = 31 * result + isRedacted.hashCode()
        result = 31 * result + (filePath?.hashCode() ?: 0)
        return result
    }
}