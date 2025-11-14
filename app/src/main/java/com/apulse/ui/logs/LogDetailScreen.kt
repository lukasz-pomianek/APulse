package com.apulse.ui.logs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import com.apulse.data.model.AppLog
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apulse.data.db.APulseDatabase
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogDetailScreen(
    logId: String,
    onNavigateBack: () -> Unit,
    database: APulseDatabase
) {
    val viewModel: LogDetailViewModel = viewModel(
        factory = LogDetailViewModel.Factory(database)
    )
    
    val log by viewModel.log.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    
    val clipboardManager = LocalClipboardManager.current
    
    LaunchedEffect(logId) {
        viewModel.loadLog(logId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Log Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            log?.let { logEntry ->
                                val logText = buildString {
                                    append("Priority: ${AppLog.getPriorityName(logEntry.priority)}\n")
                                    append("Tag: ${logEntry.tag ?: "N/A"}\n")
                                    append("Message: ${logEntry.message}\n")
                                    append("Timestamp: ${logEntry.timestamp.toLocalDateTime(TimeZone.currentSystemDefault())}\n")
                                    append("Thread: ${logEntry.threadName}\n")
                                    logEntry.className?.let { className -> append("Class: $className\n") }
                                    logEntry.methodName?.let { methodName -> append("Method: $methodName\n") }
                                    logEntry.lineNumber?.let { lineNumber -> append("Line: $lineNumber\n") }
                                    logEntry.error?.let { error -> append("Error: $error\n") }
                                    logEntry.stackTrace?.let { stackTrace -> append("Stack Trace:\n$stackTrace\n") }
                                }
                                clipboardManager.setText(AnnotatedString(logText))
                            }
                        }
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "Copy")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                log != null -> {
                    LogDetailContent(
                        log = log!!,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    )
                }
                else -> {
                    Text(
                        text = "Log not found",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
fun LogDetailContent(
    log: com.apulse.data.model.AppLog,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Priority and Tag
        Card {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Priority & Tag",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = AppLog.getPriorityColor(log.priority)
                    ) {
                        Text(
                            text = AppLog.getPriorityName(log.priority),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    log.tag?.let { tag ->
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = tag,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }
        }
        
        // Message
        Card {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Message",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                SelectionContainer {
                    Text(
                        text = log.message,
                        style = MaterialTheme.typography.bodyMedium,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
        
        // Error (if present)
        log.error?.let { error ->
            Card {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Error",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    SelectionContainer {
                        Text(
                            text = error,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }
        
        // Stack Trace (if present)
        log.stackTrace?.let { stackTrace ->
            Card {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Stack Trace",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    SelectionContainer {
                        Text(
                            text = stackTrace,
                            style = MaterialTheme.typography.bodySmall,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }
        
        // Metadata
        Card {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Metadata",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                DetailRow(
                    label = "Timestamp",
                    value = log.timestamp.toLocalDateTime(TimeZone.currentSystemDefault()).toString()
                )
                
                DetailRow(
                    label = "Thread",
                    value = log.threadName ?: "N/A"
                )
                
                log.className?.let { className ->
                    DetailRow(
                        label = "Class",
                        value = className
                    )
                }
                
                log.methodName?.let { methodName ->
                    DetailRow(
                        label = "Method",
                        value = methodName
                    )
                }
                
                log.lineNumber?.let { lineNumber ->
                    DetailRow(
                        label = "Line Number",
                        value = lineNumber.toString()
                    )
                }
                
                DetailRow(
                    label = "Session ID",
                    value = log.sessionId
                )
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        SelectionContainer {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.weight(2f)
            )
        }
    }
}

