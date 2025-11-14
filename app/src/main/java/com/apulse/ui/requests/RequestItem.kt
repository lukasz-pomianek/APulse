package com.apulse.ui.requests

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.apulse.data.model.NetworkRequest
import com.apulse.data.model.RequestWithDetails
import com.apulse.ui.theme.MethodDelete
import com.apulse.ui.theme.MethodGet
import com.apulse.ui.theme.MethodPatch
import com.apulse.ui.theme.MethodPost
import com.apulse.ui.theme.MethodPut
import com.apulse.ui.theme.StatusError
import com.apulse.ui.theme.StatusInfo
import com.apulse.ui.theme.StatusSuccess
import com.apulse.ui.theme.StatusWarning
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun RequestItem(
    request: NetworkRequest,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // First row: Method, URL, and Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    // HTTP Method chip
                    Surface(
                        color = getMethodColor(request.method),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier.size(width = 60.dp, height = 24.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = request.method,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.surface,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    // URL
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = request.path,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = request.host,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                
                // Status code and bookmark
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (request.error != null) {
                        Icon(
                            Icons.Default.Error,
                            contentDescription = "Error",
                            tint = StatusError,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        request.statusCode?.let { statusCode ->
                            Surface(
                                color = getStatusColor(statusCode),
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier.size(width = 40.dp, height = 20.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = statusCode.toString(),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.surface,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Icon(
                        imageVector = if (request.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = if (request.isBookmarked) "Bookmarked" else "Not bookmarked",
                        tint = if (request.isBookmarked) StatusWarning else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Second row: Time, Duration, Size
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formatTime(request.startTime),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Row {
                    request.duration?.let { duration ->
                        Text(
                            text = formatDuration(duration),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    
                    val totalSize = request.requestSize + request.responseSize
                    if (totalSize > 0) {
                        Text(
                            text = formatSize(totalSize),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
            // Tags if any
            if (request.tags?.isNotEmpty() == true) {
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    request.tags.take(3).forEach { tag ->
                        Surface(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.padding(end = 4.dp)
                        ) {
                            Text(
                                text = tag,
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }
                    if (request.tags.size > 3) {
                        Text(
                            text = "+${request.tags.size - 3}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun getMethodColor(method: String) = when (method.uppercase()) {
    "GET" -> MethodGet
    "POST" -> MethodPost
    "PUT" -> MethodPut
    "PATCH" -> MethodPatch
    "DELETE" -> MethodDelete
    else -> MaterialTheme.colorScheme.primary
}

@Composable
private fun getStatusColor(statusCode: Int) = when (statusCode) {
    in 200..299 -> StatusSuccess
    in 300..399 -> StatusInfo
    in 400..499 -> StatusWarning
    in 500..599 -> StatusError
    else -> MaterialTheme.colorScheme.onSurface
}

private fun formatTime(instant: kotlinx.datetime.Instant): String {
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${localDateTime.hour.toString().padStart(2, '0')}:${localDateTime.minute.toString().padStart(2, '0')}:${localDateTime.second.toString().padStart(2, '0')}"
}

private fun formatDuration(durationMs: Long): String {
    return when {
        durationMs < 1000 -> "${durationMs}ms"
        durationMs < 60000 -> "${durationMs / 1000}.${(durationMs % 1000) / 100}s"
        else -> "${durationMs / 60000}m ${(durationMs % 60000) / 1000}s"
    }
}

private fun formatSize(bytes: Long): String {
    return when {
        bytes < 1024 -> "${bytes}B"
        bytes < 1024 * 1024 -> "${bytes / 1024}KB"
        bytes < 1024 * 1024 * 1024 -> "${bytes / (1024 * 1024)}MB"
        else -> "${bytes / (1024 * 1024 * 1024)}GB"
    }
}

@Composable
fun RequestItemWithDetails(
    requestWithDetails: RequestWithDetails,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val request = requestWithDetails.request
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Main request info row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Method badge
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = getMethodColor(request.method)
                ) {
                    Text(
                        text = request.method,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                // Status code
                if (request.statusCode != null) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = getStatusColor(request.statusCode)
                    ) {
                        Text(
                            text = request.statusCode.toString(),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                Spacer(modifier = Modifier.weight(1f))
                
                // Bookmark icon
                Icon(
                    imageVector = if (request.isBookmarked) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                    contentDescription = if (request.isBookmarked) "Bookmarked" else "Not bookmarked",
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // URL
            Text(
                text = request.url,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Additional details row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Timing info
                Column {
                    if (request.duration != null) {
                        Text(
                            text = "${request.duration}ms",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = request.startTime.toLocalDateTime(TimeZone.currentSystemDefault())
                            .let { "${it.hour}:${it.minute.toString().padStart(2, '0')}" },
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                // Size info
                Column(horizontalAlignment = Alignment.End) {
                    val requestSize = requestWithDetails.requestBody?.size ?: request.requestSize
                    val responseSize = requestWithDetails.responseBody?.size ?: request.responseSize
                    
                    if (requestSize > 0) {
                        Text(
                            text = "↑ ${formatSize(requestSize)}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    if (responseSize > 0) {
                        Text(
                            text = "↓ ${formatSize(responseSize)}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
            // Headers and body info
            requestWithDetails.requestHeaders?.let { headers ->
                if (headers.headers.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Request Headers: ${headers.headers.size}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            requestWithDetails.requestBody?.let { body ->
                if (body.bodyText?.isNotEmpty() == true || body.body?.isNotEmpty() == true) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Request Body: ${body.contentType ?: "unknown"}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            requestWithDetails.responseHeaders?.let { headers ->
                if (headers.headers.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Response Headers: ${headers.headers.size}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            
            requestWithDetails.responseBody?.let { body ->
                if (body.bodyText?.isNotEmpty() == true || body.body?.isNotEmpty() == true) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Response Body: ${body.contentType ?: "unknown"}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            
            // Error indicator
            if (request.error != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Error,
                        contentDescription = "Error",
                        tint = StatusError,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = request.error,
                        style = MaterialTheme.typography.labelSmall,
                        color = StatusError,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}