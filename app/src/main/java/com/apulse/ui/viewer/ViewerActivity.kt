package com.apulse.ui.viewer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.apulse.ui.theme.APulseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewerActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val fileUri = intent.data
        
        setContent {
            APulseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (fileUri != null) {
                        ViewerScreen(fileUri = fileUri)
                    } else {
                        ErrorScreen()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ViewerScreen(fileUri: Uri) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("APulse Viewer") }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(
                    text = "Viewing APulse file:",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = fileUri.toString(),
                    style = MaterialTheme.typography.bodyMedium
                )
                // TODO: Implement actual file parsing and display
                // This would load and display the shared APulse file
            }
        }
    }
}

@Composable
private fun ErrorScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No file to display",
            style = MaterialTheme.typography.titleMedium
        )
    }
}