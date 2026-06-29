package com.apulse.ui.requests

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestFilterDialog(
    availableHosts: List<String>,
    availableCodes: List<Int>,
    currentFilter: RequestFilter,
    onApply: (RequestFilter) -> Unit,
    onDismiss: () -> Unit
) {
    var tempHosts by remember { mutableStateOf(currentFilter.selectedHosts) }
    var tempCodes by remember { mutableStateOf(currentFilter.selectedCodes) }
    var tempDateFrom by remember { mutableStateOf(currentFilter.dateFrom) }
    var tempDateTo by remember { mutableStateOf(currentFilter.dateTo) }

    var showFromPicker by remember { mutableStateOf(false) }
    var showToPicker by remember { mutableStateOf(false) }

    if (showFromPicker) {
        val state = rememberDatePickerState(
            initialSelectedDateMillis = tempDateFrom?.toEpochMilliseconds()
        )
        DatePickerDialog(
            onDismissRequest = { showFromPicker = false },
            confirmButton = {
                TextButton(onClick = {
                    state.selectedDateMillis?.let { tempDateFrom = Instant.fromEpochMilliseconds(it) }
                    showFromPicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showFromPicker = false }) { Text("Anuluj") }
            }
        ) {
            DatePicker(state = state)
        }
    }

    if (showToPicker) {
        val state = rememberDatePickerState(
            initialSelectedDateMillis = tempDateTo?.toEpochMilliseconds()
        )
        DatePickerDialog(
            onDismissRequest = { showToPicker = false },
            confirmButton = {
                TextButton(onClick = {
                    state.selectedDateMillis?.let { tempDateTo = Instant.fromEpochMilliseconds(it) }
                    showToPicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showToPicker = false }) { Text("Anuluj") }
            }
        ) {
            DatePicker(state = state)
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Filter Requests",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // ── HOSTS ──────────────────────────────────────────
                Text(
                    text = "Hosts",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (availableHosts.isEmpty()) {
                    Text(
                        text = "No hosts recorded yet",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    availableHosts.forEach { host ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Checkbox(
                                checked = host in tempHosts,
                                onCheckedChange = { checked ->
                                    tempHosts = if (checked) tempHosts + host else tempHosts - host
                                }
                            )
                            Text(
                                text = host,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .padding(start = 4.dp)
                                    .weight(1f)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))

                // ── STATUS CODES ───────────────────────────────────
                Text(
                    text = "Status Codes",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (availableCodes.isEmpty()) {
                    Text(
                        text = "No status codes recorded yet",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    availableCodes.forEach { code ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Checkbox(
                                checked = code in tempCodes,
                                onCheckedChange = { checked ->
                                    tempCodes = if (checked) tempCodes + code else tempCodes - code
                                }
                            )
                            Surface(
                                color = statusCodeColor(code),
                                modifier = Modifier.padding(start = 4.dp)
                            ) {
                                Text(
                                    text = "$code",
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = statusCodeLabel(code),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))

                // ── DATE RANGE ─────────────────────────────────────
                Text(
                    text = "Date Range",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))

                // From date
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "From",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.width(44.dp)
                    )
                    OutlinedButton(
                        onClick = { showFromPicker = true },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 6.dp)
                        )
                        Text(
                            text = tempDateFrom?.formatDate() ?: "Select date",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    if (tempDateFrom != null) {
                        IconButton(onClick = { tempDateFrom = null }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear from date")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // To date
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "To",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.width(44.dp)
                    )
                    OutlinedButton(
                        onClick = { showToPicker = true },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 6.dp)
                        )
                        Text(
                            text = tempDateTo?.formatDate() ?: "Select date",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    if (tempDateTo != null) {
                        IconButton(onClick = { tempDateTo = null }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear to date")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ── ACTION BUTTONS ─────────────────────────────────
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        tempHosts = emptySet()
                        tempCodes = emptySet()
                        tempDateFrom = null
                        tempDateTo = null
                    }) {
                        Text("Reset")
                    }
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Button(onClick = {
                        onApply(
                            RequestFilter(
                                selectedHosts = tempHosts,
                                selectedCodes = tempCodes,
                                dateFrom = tempDateFrom,
                                dateTo = tempDateTo
                            )
                        )
                        onDismiss()
                    }) {
                        Text("Apply")
                    }
                }
            }
        }
    }
}

private fun Instant.formatDate(): String {
    // ISO string is "yyyy-MM-ddTHH:mm:ssZ" — take first 10 chars
    return toString().take(10)
}

private fun statusCodeColor(code: Int): Color = when {
    code in 100..199 -> Color(0xFF9E9E9E)
    code in 200..299 -> Color(0xFF4CAF50)
    code in 300..399 -> Color(0xFF2196F3)
    code in 400..499 -> Color(0xFFFF9800)
    code in 500..599 -> Color(0xFFF44336)
    else -> Color(0xFF9E9E9E)
}

private fun statusCodeLabel(code: Int): String = when (code) {
    200 -> "OK"
    201 -> "Created"
    204 -> "No Content"
    301 -> "Moved Permanently"
    302 -> "Found"
    304 -> "Not Modified"
    400 -> "Bad Request"
    401 -> "Unauthorized"
    403 -> "Forbidden"
    404 -> "Not Found"
    408 -> "Request Timeout"
    409 -> "Conflict"
    422 -> "Unprocessable Entity"
    429 -> "Too Many Requests"
    500 -> "Internal Server Error"
    502 -> "Bad Gateway"
    503 -> "Service Unavailable"
    504 -> "Gateway Timeout"
    else -> when {
        code in 100..199 -> "Informational"
        code in 200..299 -> "Success"
        code in 300..399 -> "Redirection"
        code in 400..499 -> "Client Error"
        code in 500..599 -> "Server Error"
        else -> ""
    }
}
