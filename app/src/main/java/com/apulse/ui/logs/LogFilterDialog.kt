package com.apulse.ui.logs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.apulse.data.model.AppLog

@Composable
fun LogFilterDialog(
    selectedPriorities: Set<Int>,
    availableTags: List<String>,
    onPrioritiesChanged: (Set<Int>) -> Unit,
    onTagsChanged: (Set<String>) -> Unit,
    onDismiss: () -> Unit
) {
    var tempPriorities by remember { mutableStateOf(selectedPriorities) }
    var tempTags by remember { mutableStateOf<Set<String>>(emptySet()) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Filter Logs",
                    style = MaterialTheme.typography.headlineSmall
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Priority filters
                Text(
                    text = "Priority Levels",
                    style = MaterialTheme.typography.titleMedium
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Column {
                    listOf(2, 3, 4, 5, 6).forEach { priority ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Checkbox(
                                checked = priority in tempPriorities,
                                onCheckedChange = { checked ->
                                    tempPriorities = if (checked) {
                                        tempPriorities + priority
                                    } else {
                                        tempPriorities - priority
                                    }
                                }
                            )
                            
                            Surface(
                                color = AppLog.getPriorityColor(priority),
                                modifier = Modifier.padding(start = 8.dp)
                            ) {
                                Text(
                                    text = AppLog.getPriorityName(priority),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    color = androidx.compose.ui.graphics.Color.White,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Tag filters
                if (availableTags.isNotEmpty()) {
                    Text(
                        text = "Tags",
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        items(availableTags) { tag ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Checkbox(
                                    checked = tag in tempTags,
                                    onCheckedChange = { checked ->
                                        tempTags = if (checked) {
                                            tempTags + tag
                                        } else {
                                            tempTags - tag
                                        }
                                    }
                                )
                                
                                Text(
                                    text = tag,
                                    modifier = Modifier.padding(start = 8.dp),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = {
                            // Reset filters
                            tempPriorities = setOf(2, 3, 4, 5, 6)
                            tempTags = emptySet()
                            onPrioritiesChanged(tempPriorities)
                            onTagsChanged(tempTags)
                        }
                    ) {
                        Text("Reset")
                    }
                    
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    
                    Button(
                        onClick = {
                            onPrioritiesChanged(tempPriorities)
                            onTagsChanged(tempTags)
                            onDismiss()
                        }
                    ) {
                        Text("Apply")
                    }
                }
            }
        }
    }
}