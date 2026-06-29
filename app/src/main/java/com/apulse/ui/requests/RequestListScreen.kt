package com.apulse.ui.requests

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.apulse.ui.APulseViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestListScreen(
    navController: NavController,
    viewModel: RequestListViewModel = viewModel(factory = APulseViewModelFactory(LocalContext.current))
) {
    val requests by viewModel.requests.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val filter by viewModel.filter.collectAsState()
    val availableHosts by viewModel.availableHosts.collectAsState()
    val availableCodes by viewModel.availableCodes.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var showSearch by remember { mutableStateOf(false) }
    var showFilterDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Search bar
        if (showSearch) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    viewModel.searchRequests(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search requests...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
            )
        }

        // Action buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(onClick = { showSearch = !showSearch }) {
                Icon(Icons.Default.Search, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Search")
            }

            OutlinedButton(
                onClick = { showFilterDialog = true },
                colors = if (filter.isActive) {
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                } else {
                    ButtonDefaults.outlinedButtonColors()
                }
            ) {
                Icon(Icons.Default.FilterList, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Filter")
            }
        }

        // Active filter chips
        if (filter.isActive) {
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filter.selectedHosts.toList()) { host ->
                    OutlinedButton(
                        onClick = {
                            viewModel.updateFilter(
                                filter.copy(selectedHosts = filter.selectedHosts - host)
                            )
                        },
                        modifier = Modifier.height(32.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        Text(text = "✕ $host", fontSize = 12.sp)
                    }
                }
                items(filter.selectedCodes.toList().sorted()) { code ->
                    OutlinedButton(
                        onClick = {
                            viewModel.updateFilter(
                                filter.copy(selectedCodes = filter.selectedCodes - code)
                            )
                        },
                        modifier = Modifier.height(32.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        Text(text = "✕ $code", fontSize = 12.sp)
                    }
                }
                filter.dateFrom?.let { from ->
                    item {
                        OutlinedButton(
                            onClick = { viewModel.updateFilter(filter.copy(dateFrom = null)) },
                            modifier = Modifier.height(32.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp)
                        ) {
                            Text(text = "✕ From: ${from.toString().take(10)}", fontSize = 12.sp)
                        }
                    }
                }
                filter.dateTo?.let { to ->
                    item {
                        OutlinedButton(
                            onClick = { viewModel.updateFilter(filter.copy(dateTo = null)) },
                            modifier = Modifier.height(32.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp)
                        ) {
                            Text(text = "✕ To: ${to.toString().take(10)}", fontSize = 12.sp)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }

        // Request list
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (requests.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No network requests captured",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Start capturing network traffic to see requests here",
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
                items(requests) { requestWithDetails ->
                    RequestItemWithDetails(
                        requestWithDetails = requestWithDetails,
                        onClick = {
                            navController.navigate("request_details/${requestWithDetails.request.id}")
                        }
                    )
                }
            }
        }
    }

    if (showFilterDialog) {
        RequestFilterDialog(
            availableHosts = availableHosts,
            availableCodes = availableCodes,
            currentFilter = filter,
            onApply = { viewModel.updateFilter(it) },
            onDismiss = { showFilterDialog = false }
        )
    }
}
