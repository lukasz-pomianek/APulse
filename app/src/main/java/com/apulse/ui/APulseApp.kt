package com.apulse.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apulse.ui.sessions.SessionListViewModel
import com.apulse.ui.navigation.APulseDestination
import com.apulse.ui.requests.RequestListScreen
import com.apulse.ui.sessions.SessionListScreen
import com.apulse.ui.settings.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun APulseApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val context = LocalContext.current
    val factory = remember(context) { APulseViewModelFactory(context) }
    val sessionViewModel: SessionListViewModel = viewModel(factory = factory)

    LaunchedEffect(Unit) {
        // Ensure a default session exists to start capturing immediately
        sessionViewModel.createDefaultSession()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (currentDestination?.route) {
                            APulseDestination.Requests.route -> "Network Requests"
                            APulseDestination.Sessions.route -> "Sessions"
                            APulseDestination.Settings.route -> "Settings"
                            else -> "APulse"
                        }
                    )
                }
            )
        },
        bottomBar = {
            NavigationBar {
                APulseDestination.values().forEach { destination ->
                    NavigationBarItem(
                        icon = { Icon(destination.icon, contentDescription = null) },
                        label = { Text(destination.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true,
                        onClick = {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = APulseDestination.Requests.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(APulseDestination.Requests.route) {
                RequestListScreen(navController = navController)
            }
            composable(APulseDestination.Sessions.route) {
                SessionListScreen(navController = navController)
            }
            composable(APulseDestination.Settings.route) {
                SettingsScreen()
            }
        }
    }
}