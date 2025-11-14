package com.apulse.ui.sessions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Http
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.apulse.data.model.AppLog
import com.apulse.data.model.SessionItem
import com.apulse.ui.APulseViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionDetailScreen(
    sessionId: String,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: SessionDetailViewModel = viewModel(factory = APulseViewModelFactory(LocalContext.current))
) {
    val context = LocalContext.current
    val sessionWithStats by viewModel.sessionWithStats.collectAsState()
    val sessionItems by viewModel.sessionItems.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(sessionId) {
        viewModel.loadSessionDetail(sessionId)
    }
    
    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(
            title = { 
                Text(sessionWithStats?.session?.name ?: "Session Details")
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(
                    onClick = {
                        sessionWithStats?.let { sessionData ->
                            viewModel.exportSessionData(sessionData, context)
                        }
                    },
                    enabled = sessionWithStats != null
                ) {
                    Icon(
                        Icons.Default.Download,
                        contentDescription = "Export session data"
                    )
                }
            }
        )
        
        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            
            error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = error ?: "Unknown error",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.clearError() }) {
                            Text("Retry")
                        }
                    }
                }
            }
            
            sessionWithStats != null -> {
                SessionDetailContent(
                    sessionWithStats = sessionWithStats!!,
                    sessionItems = sessionItems,
                    navController = navController,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun SessionDetailContent(
    sessionWithStats: com.apulse.data.model.SessionWithStats,
    sessionItems: List<SessionItem>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Session stats header
        item {
            SessionStatsCard(sessionWithStats)
        }
        
        // Chronological items list
        item {
            Text(
                text = "Activity Timeline (${sessionItems.size} items)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        
        if (sessionItems.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "No activity in this session yet",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            items(sessionItems) { item ->
                when (item) {
                    is SessionItem.RequestItem -> {
                        RequestItemCard(
                            request = item.request,
                            onClick = {
                                navController.navigate("request_details/${item.request.id}")
                            }
                        )
                    }
                    is SessionItem.LogItem -> {
                        LogItemCard(
                            log = item.log,
                            onClick = {
                                navController.navigate("log_details/${item.log.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SessionStatsCard(
    sessionWithStats: com.apulse.data.model.SessionWithStats,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = sessionWithStats.session.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            sessionWithStats.session.description?.let { description ->
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Data utworzenia sesji
            Text(
                text = "Created: ${formatSessionDate(sessionWithStats.session.createdAt)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Requests: ${sessionWithStats.requestCount}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Logs: ${sessionWithStats.logCount}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                
                Column {
                    Text(
                        text = "Success: ${sessionWithStats.successCount}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Errors: ${sessionWithStats.errorCount}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
private fun RequestItemCard(
    request: com.apulse.data.model.NetworkRequest,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Timestamp na górze - pełna data z sekundami i milisekundami
            Text(
                text = formatDetailedTimestamp(request.startTime),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Typ (Method) i Status Code w jednej linii
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Http,
                    contentDescription = "HTTP Request",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Surface(
                    color = when (request.method.uppercase()) {
                        "GET" -> MaterialTheme.colorScheme.primary
                        "POST" -> MaterialTheme.colorScheme.secondary
                        "PUT", "PATCH" -> MaterialTheme.colorScheme.tertiary
                        "DELETE" -> MaterialTheme.colorScheme.error
                        else -> MaterialTheme.colorScheme.outline
                    },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = request.method.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Text(
                    text = "Status: ${request.statusCode ?: "—"}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = when (request.statusCode) {
                        in 200..299 -> MaterialTheme.colorScheme.primary
                        in 400..599 -> MaterialTheme.colorScheme.error
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    fontWeight = FontWeight.Medium
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // URL na dole
            Text(
                text = request.url,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun LogItemCard(
    log: com.apulse.data.model.AppLog,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Timestamp na górze - pełna data z sekundami i milisekundami
            Text(
                text = formatDetailedTimestamp(log.timestamp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Typ loga (Priority) i Tag w jednej linii
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Log Entry",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(16.dp)
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Surface(
                    color = when (log.priority) {
                        AppLog.ERROR -> MaterialTheme.colorScheme.error
                        AppLog.WARN -> MaterialTheme.colorScheme.tertiary
                        AppLog.INFO -> MaterialTheme.colorScheme.primary
                        AppLog.DEBUG -> MaterialTheme.colorScheme.secondary
                        AppLog.VERBOSE -> MaterialTheme.colorScheme.outline
                        else -> MaterialTheme.colorScheme.outline
                    },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = AppLog.getPriorityName(log.priority),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onError,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                
                if (!log.tag.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Tag: ${log.tag}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Message na dole
            Text(
                text = log.message,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

// Helper function to format detailed timestamp with milliseconds
private fun formatDetailedTimestamp(instant: kotlinx.datetime.Instant): String {
    val date = Date(instant.toEpochMilliseconds())
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
    return formatter.format(date)
}

// Helper function to format session creation date
private fun formatSessionDate(instant: kotlinx.datetime.Instant): String {
    val date = Date(instant.toEpochMilliseconds())
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return formatter.format(date)
}