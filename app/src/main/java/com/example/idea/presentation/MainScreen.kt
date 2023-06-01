package com.example.idea.presentation


import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.idea.presentation.google.GoogleAuthUIClient
import com.example.idea.util.Screen


@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    mainNavController: NavController,
    googleAuthUiClient: GoogleAuthUIClient
){
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            IdeaBoxScreen(mainViewModel)
        },
        containerColor = Color.Transparent,
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                mainViewModel.navItems.forEach { screen ->
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                    NavigationBarItem(
                        alwaysShowLabel = false,
                        label = { Text(text = screen.label) },
                        selected = selected,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = if(selected) screen.icon else screen.offIcon),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    )
                }
            }
                    },
        content = { innerPadding ->
            if(mainViewModel.showProfileSection){
                ProfileAlertBox(mainViewModel, innerPadding, mainNavController, googleAuthUiClient)
            }
            NavHost(navController = navController, startDestination = Screen.Idea.route, modifier = Modifier.padding(innerPadding)){
                composable(Screen.Idea.route){
                    MyIdeasScreen(mainViewModel)
                }
                composable(Screen.MyIdea.route){
                    MyIdeasScreen(mainViewModel)
                }
                composable(Screen.Profile.route){
                    ProfileScreen(mainViewModel)
                }
            }
        }
    )
}





