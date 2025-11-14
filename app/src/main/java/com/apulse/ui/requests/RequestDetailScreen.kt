package com.apulse.ui.requests

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.apulse.data.model.RequestWithDetails
import com.apulse.ui.APulseViewModelFactory
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestDetailScreen(
    requestId: String,
    navController: NavController,
    viewModel: RequestDetailViewModel = viewModel(factory = APulseViewModelFactory(LocalContext.current))
) {
    LaunchedEffect(requestId) {
        viewModel.loadRequest(requestId)
    }
    
    val requestDetails by viewModel.requestDetails.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Request Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (requestDetails != null) {
            RequestDetailContent(
                requestDetails = requestDetails!!,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Request not found")
            }
        }
    }
}

@Composable
private fun RequestDetailContent(
    requestDetails: RequestWithDetails,
    modifier: Modifier = Modifier
) {
    val request = requestDetails.request
    
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Request Info Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Request Information",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                DetailRow("Method", request.method)
                DetailRow("URL", request.url)
                DetailRow("Host", request.host)
                DetailRow("Path", request.path)
                request.query?.let { DetailRow("Query", it) }
                DetailRow("Protocol", request.protocol ?: "Unknown")
                DetailRow("Start Time", request.startTime.toLocalDateTime(TimeZone.currentSystemDefault()).toString())
                request.endTime?.let { 
                    DetailRow("End Time", it.toLocalDateTime(TimeZone.currentSystemDefault()).toString())
                }
                request.duration?.let { DetailRow("Duration", "${it}ms") }
                request.statusCode?.let { DetailRow("Status Code", it.toString()) }
                request.statusMessage?.let { DetailRow("Status Message", it) }
                DetailRow("Request Size", "${request.requestSize} bytes")
                DetailRow("Response Size", "${request.responseSize} bytes")
                request.mimeType?.let { DetailRow("MIME Type", it) }
                request.error?.let { DetailRow("Error", it) }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Request Headers Section
        requestDetails.requestHeaders?.let { headers ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Request Headers (${headers.headers.size})",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    headers.headers.forEach { (name, value) ->
                        DetailRow(name, value)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        // Request Body Section
        requestDetails.requestBody?.let { body ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Header with copy button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Request Body",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        
                        val clipboardManager = LocalClipboardManager.current
                        val context = LocalContext.current
                        IconButton(
                            onClick = {
                                body.bodyText?.let { text ->
                                    clipboardManager.setText(AnnotatedString(text))
                                    Toast.makeText(context, "Request Body copied to clipboard", Toast.LENGTH_SHORT).show()
                                } ?: run {
                                    Toast.makeText(context, "No request body to copy", Toast.LENGTH_SHORT).show()
                                }
                            }
                        ) {
                            Icon(
                                Icons.Filled.ContentCopy,
                                contentDescription = "Copy Request Body",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    body.contentType?.let { DetailRow("Content-Type", it) }
                    body.contentEncoding?.let { DetailRow("Content-Encoding", it) }
                    DetailRow("Size", "${body.size} bytes")
                    
                    body.bodyText?.let { text ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Body Content:",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Text(
                                text = text,
                                modifier = Modifier.padding(12.dp),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        // Response Headers Section
        requestDetails.responseHeaders?.let { headers ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Response Headers (${headers.headers.size})",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    headers.headers.forEach { (name, value) ->
                        DetailRow(name, value)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        // Response Body Section
        requestDetails.responseBody?.let { body ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Header with copy button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Response Body",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        
                        val clipboardManager = LocalClipboardManager.current
                        val context = LocalContext.current
                        IconButton(
                            onClick = {
                                body.bodyText?.let { text ->
                                    clipboardManager.setText(AnnotatedString(text))
                                    Toast.makeText(context, "Response Body copied to clipboard", Toast.LENGTH_SHORT).show()
                                } ?: run {
                                    Toast.makeText(context, "No response body to copy", Toast.LENGTH_SHORT).show()
                                }
                            }
                        ) {
                            Icon(
                                Icons.Filled.ContentCopy,
                                contentDescription = "Copy Response Body",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    body.contentType?.let { DetailRow("Content-Type", it) }
                    body.contentEncoding?.let { DetailRow("Content-Encoding", it) }
                    DetailRow("Size", "${body.size} bytes")
                    if (body.isJson) DetailRow("Format", "JSON")
                    if (body.isXml) DetailRow("Format", "XML")
                    if (body.isImage) DetailRow("Format", "Image")
                    
                    body.bodyText?.let { text ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Body Content:",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Text(
                                text = text,
                                modifier = Modifier.padding(12.dp),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis
        )
    }
}