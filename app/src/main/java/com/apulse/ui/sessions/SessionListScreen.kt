package com.apulse.ui.sessions

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import com.apulse.ui.APulseViewModelFactory

@Composable
fun SessionListScreen(
    navController: NavController,
    viewModel: SessionListViewModel = viewModel(factory = APulseViewModelFactory(LocalContext.current))
) {
    val sessions by viewModel.sessions.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val importResult by viewModel.importResult.collectAsState()
    var showNewSessionDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    
    // File picker launcher for import
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.importSession(it) }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
        Column(modifier = Modifier.fillMaxSize()) {
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (sessions.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No sessions yet",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Create a session to start organizing your network requests",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(sessions) { sessionWithStats ->
                        SessionItem(
                            sessionWithStats = sessionWithStats,
                            onActivate = { viewModel.activateSession(sessionWithStats.session.id) },
                            onDelete = { viewModel.deleteSession(sessionWithStats.session.id) },
                            onClick = { 
                                navController.navigate("session_detail/${sessionWithStats.session.id}")
                            }
                        )
                    }
                }
            }
        }

        // Floating Action Button
        FloatingActionButton(
            onClick = { showNewSessionDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Create Session")
        }
    }

    // New Session Dialog
    if (showNewSessionDialog) {
        NewSessionDialog(
            onDismiss = { showNewSessionDialog = false },
            onCreateSession = { sessionName ->
                viewModel.createSession(sessionName)
                showNewSessionDialog = false
            },
            onImportSession = {
                filePickerLauncher.launch("application/zip")
                showNewSessionDialog = false
            }
        )
    }
    
    // Handle import results
    importResult?.let { result ->
        LaunchedEffect(result) {
            when (result) {
                is SessionListViewModel.ImportResult.Success -> {
                    snackbarHostState.showSnackbar(
                        message = "Session imported successfully",
                        duration = SnackbarDuration.Short
                    )
                }
                is SessionListViewModel.ImportResult.Error -> {
                    snackbarHostState.showSnackbar(
                        message = "Import failed: ${result.message}",
                        duration = SnackbarDuration.Long
                    )
                }
            }
            // Clear the result after handling
            viewModel.clearImportResult()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewSessionDialog(
    onDismiss: () -> Unit,
    onCreateSession: (String) -> Unit,
    onImportSession: () -> Unit
) {
    var sessionName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Session Options") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Create new session section
                Text(
                    text = "Create New Session",
                    style = MaterialTheme.typography.titleSmall
                )
                OutlinedTextField(
                    value = sessionName,
                    onValueChange = { sessionName = it },
                    label = { Text("Session Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                
                HorizontalDivider()
                
                // Import session section
                Text(
                    text = "Or Import Session",
                    style = MaterialTheme.typography.titleSmall
                )
                OutlinedButton(
                    onClick = onImportSession,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Upload,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Import from ZIP file")
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { 
                    if (sessionName.isNotBlank()) {
                        onCreateSession(sessionName.trim())
                    }
                },
                enabled = sessionName.isNotBlank()
            ) {
                Text("Create")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}