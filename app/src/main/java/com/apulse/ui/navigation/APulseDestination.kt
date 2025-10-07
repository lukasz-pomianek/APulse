package com.apulse.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Storage
import androidx.compose.ui.graphics.vector.ImageVector

enum class APulseDestination(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    Requests("requests", "Requests", Icons.Default.List),
    Sessions("sessions", "Sessions", Icons.Default.Storage),
    Settings("settings", "Settings", Icons.Default.Settings)
}