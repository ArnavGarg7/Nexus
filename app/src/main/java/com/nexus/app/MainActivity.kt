package com.nexus.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nexus.app.presentation.navigation.NexusNavHost
import com.nexus.app.presentation.navigation.Screen
import com.nexus.app.presentation.theme.NexusRed
import com.nexus.app.presentation.theme.NexusTheme
import dagger.hilt.android.AndroidEntryPoint

data class BottomNavItem(val label: String, val icon: ImageVector, val route: String)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NexusTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val bottomNavItems = listOf(
                    BottomNavItem("Home", Icons.Default.Home, Screen.Home.route),
                    BottomNavItem("Search", Icons.Default.Search, Screen.Search.route),
                    BottomNavItem("Quizzes", Icons.Default.Quiz, Screen.QuizHub.route),
                    BottomNavItem("Profile", Icons.Default.Person, Screen.Profile.route)
                )
                val showBottomBar = currentRoute in bottomNavItems.map { it.route }

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            NavigationBar {
                                bottomNavItems.forEach { item ->
                                    NavigationBarItem(
                                        selected = currentRoute == item.route,
                                        onClick = {
                                            if (currentRoute != item.route) {
                                                navController.navigate(item.route) {
                                                    popUpTo(Screen.Home.route) { saveState = true }
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            }
                                        },
                                        icon = { Icon(item.icon, contentDescription = item.label) },
                                        label = { Text(item.label, fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = NexusRed,
                                            selectedTextColor = NexusRed,
                                            indicatorColor = MaterialTheme.colorScheme.surface
                                        )
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    Surface(modifier = Modifier.padding(innerPadding)) {
                        NexusNavHost(navController = navController)
                    }
                }
            }
        }
    }
}
