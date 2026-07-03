package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Landscape
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.data.local.AppDatabase
import com.example.data.repository.UnimarRepository
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.UnimarViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize Room Local Database
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = UnimarRepository(
            database.enrollmentDao(),
            database.academicEventDao()
        )

        // Build ViewModel via Factory
        val viewModel = ViewModelProvider(
            this,
            UnimarViewModel.Factory(repository, applicationContext)
        )[UnimarViewModel::class.java]

        setContent {
            MyApplicationTheme {
                MainAppLayout(viewModel)
            }
        }
    }
}

@Composable
fun MainAppLayout(viewModel: UnimarViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "story"

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            // Immersive constraint: Only show bottom nav when NOT on the Instagram Story starting screen
            if (currentRoute != "story") {
                NavigationBar(
                    containerColor = Color.White,
                    tonalElevation = 8.dp,
                    modifier = Modifier.testTag("app_navigation_bar")
                ) {
                    val items = listOf(
                        NavigationItem(
                            route = "story",
                            label = "Inicio",
                            selectedIcon = Icons.Filled.Home,
                            unselectedIcon = Icons.Outlined.Home
                        ),
                        NavigationItem(
                            route = "enrollment",
                            label = "Inscripción",
                            selectedIcon = Icons.Filled.Assignment,
                            unselectedIcon = Icons.Outlined.Assignment
                        ),
                        NavigationItem(
                            route = "pensum",
                            label = "Pensum",
                            selectedIcon = Icons.Filled.School,
                            unselectedIcon = Icons.Outlined.School
                        ),
                        NavigationItem(
                            route = "gallery",
                            label = "Galería",
                            selectedIcon = Icons.Filled.Landscape,
                            unselectedIcon = Icons.Outlined.Landscape
                        ),
                        NavigationItem(
                            route = "notifications",
                            label = "Noticias",
                            selectedIcon = Icons.Filled.Notifications,
                            unselectedIcon = Icons.Outlined.Notifications
                        )
                    )

                    items.forEach { item ->
                        val isSelected = currentRoute == item.route
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                if (currentRoute != item.route) {
                                    navController.navigate(item.route) {
                                        // Pop up to the start destination to avoid building a huge backstack
                                        popUpTo("story") { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.label
                                )
                            },
                            label = {
                                Text(
                                    text = item.label,
                                    fontSize = 11.sp
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0x0F, 0x2C, 0x59),
                                selectedTextColor = Color(0x0F, 0x2C, 0x59),
                                indicatorColor = Color(0xFF, 0x6F, 0x00).copy(alpha = 0.15f),
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "story",
            modifier = Modifier.padding(innerPadding)
        ) {
            // Screen 1: Story Portada (Landing Page)
            composable("story") {
                MainStoryScreen(
                    onNavigateToEnrollment = {
                        navController.navigate("enrollment") {
                            launchSingleTop = true
                        }
                    },
                    onNavigateToAdmin = {
                        navController.navigate("admin") {
                            launchSingleTop = true
                        }
                    },
                    viewModel = viewModel
                )
            }

            // Screen 2: Interactive enrollment / registration form
            composable("enrollment") {
                EnrollmentScreen(
                    viewModel = viewModel,
                    modifier = Modifier.systemBarsPadding()
                )
            }

            // Screen 3: Academic curriculums (Pensum)
            composable("pensum") {
                PensumScreen(
                    modifier = Modifier.systemBarsPadding()
                )
            }

            // Screen 4: Photo Gallery of buildings/areas
            composable("gallery") {
                GalleryScreen(
                    modifier = Modifier.systemBarsPadding()
                )
            }

            // Screen 5: Simulated notification board & notice center
            composable("notifications") {
                NotificationsScreen(
                    viewModel = viewModel,
                    modifier = Modifier.systemBarsPadding()
                )
            }

            // Screen 6: Administrator Control Panel
            composable("admin") {
                AdminScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() },
                    modifier = Modifier.systemBarsPadding()
                )
            }
        }
    }
}

data class NavigationItem(
    val route: String,
    val label: String,
    val selectedIcon: androidx.compose.ui.graphics.vector.ImageVector,
    val unselectedIcon: androidx.compose.ui.graphics.vector.ImageVector
)
