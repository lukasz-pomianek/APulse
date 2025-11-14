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
import androidx.navigation.navArgument
import androidx.navigation.NavType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apulse.ui.sessions.SessionListViewModel
import com.apulse.ui.navigation.APulseDestination
import com.apulse.ui.requests.RequestListScreen
import com.apulse.ui.requests.RequestDetailScreen
import com.apulse.ui.logs.LogListScreen
import com.apulse.ui.logs.LogDetailScreen
import com.apulse.ui.sessions.SessionListScreen
import com.apulse.ui.sessions.SessionDetailScreen
import com.apulse.ui.settings.SettingsScreen
import com.apulse.capture.log.APulseLogFactory



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
        // Initialize log interceptor with optional excluded tags
        // Example: exclude noisy system tags
        val excludedTags = listOf("System", "SystemServer", "WindowManager", "InputMethodManager")
        APulseLogFactory.initialize(context, excludedTags)
        
        // Ensure a default session exists to start capturing immediately
        sessionViewModel.createDefaultSession()
        
        // All sample data creation removed - using only real intercepted data
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when {
                            currentDestination?.route == APulseDestination.Requests.route -> "Network Requests"
                            currentDestination?.route == APulseDestination.Logs.route -> "Application Logs"
                            currentDestination?.route == APulseDestination.Sessions.route -> "Sessions"
                            currentDestination?.route == APulseDestination.Settings.route -> "Settings"
                            currentDestination?.route?.startsWith("request_details/") == true -> "Request Details"
                            currentDestination?.route?.startsWith("log_details/") == true -> "Log Details"
                            currentDestination?.route?.startsWith("session_detail/") == true -> "Session Details"
                            else -> "APulse"
                        }
                    )
                }
            )
        },
        bottomBar = {
            NavigationBar {
                APulseDestination.values().forEach { destination ->
                    // Hide Settings from navigation
                    if (destination != APulseDestination.Settings) {
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
            composable(
                route = "request_details/{requestId}",
                arguments = listOf(
                    navArgument("requestId") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val requestId = backStackEntry.arguments?.getString("requestId") ?: ""
                RequestDetailScreen(
                    requestId = requestId,
                    navController = navController
                )
            }
            composable(APulseDestination.Logs.route) {
                LogListScreen(
                    onNavigateToLogDetail = { logId ->
                        navController.navigate("log_details/$logId")
                    },
                    database = factory.database,
                    sessionManager = factory.sessionManager
                )
            }
            composable(
                route = "log_details/{logId}",
                arguments = listOf(
                    navArgument("logId") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val logId = backStackEntry.arguments?.getString("logId") ?: ""
                LogDetailScreen(
                    logId = logId,
                    onNavigateBack = { navController.popBackStack() },
                    database = factory.database
                )
            }
            composable(APulseDestination.Sessions.route) {
                SessionListScreen(navController = navController)
            }
            composable(
                route = "session_detail/{sessionId}",
                arguments = listOf(
                    navArgument("sessionId") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val sessionId = backStackEntry.arguments?.getString("sessionId") ?: ""
                com.apulse.ui.sessions.SessionDetailScreen(
                    sessionId = sessionId,
                    navController = navController
                )
            }
            composable(APulseDestination.Settings.route) {
                SettingsScreen()
            }
        }
    }
}