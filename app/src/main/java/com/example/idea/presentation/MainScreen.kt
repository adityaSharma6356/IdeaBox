package com.example.idea.presentation


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.idea.R
import com.example.idea.presentation.google.GoogleAuthUIClient
import com.example.idea.presentation.util.MultiSelect
import com.example.idea.presentation.util.Screen
import com.example.idea.presentation.util.SortBy
import com.example.idea.presentation.util.UiEvents


@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    mainNavController: NavController,
    googleAuthUiClient: GoogleAuthUIClient
){
    val navController = rememberNavController()
    val config = LocalConfiguration.current
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val addProjectViewModel = viewModel<AddProjectViewModel>()
        val (searchBar, mainContent, bottomNav, floatingButton, bar) = createRefs()
        SearchBox(
            mainViewModel = mainViewModel,
            modifier = Modifier
                .zIndex(1f)
                .constrainAs(searchBar) {
                    top.linkTo(parent.top)
                })
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            modifier = Modifier.constrainAs(bottomNav){
            bottom.linkTo(parent.bottom, margin = (-20).dp)
        }) {
            mainViewModel.navItems.forEach { screen ->
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                NavigationBarItem(
                    label = { Text(text = screen.label) },
                    selected = selected,
                    onClick = {
                        if(screen.route==Screen.MyIdea.route){
                            mainViewModel.onEvent(UiEvents.SetMyIdeaView)
                        }
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
                            modifier = Modifier.size(25.dp)
                        )
                    }
                )
            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height((config.screenHeightDp - 120).dp)
            .constrainAs(mainContent) {
                top.linkTo(parent.top, margin = 100.dp)
                bottom.linkTo(bottomNav.top)
            }){
            if(mainViewModel.showProfileSection){
                ProfileAlertBox(mainViewModel, mainNavController, googleAuthUiClient)
            }
            NavHost(navController = navController, startDestination = Screen.Idea.route){
                composable(Screen.Idea.route){
                    IdeaBoxScreen(mainViewModel, navController, addProjectViewModel)
                }
                composable(Screen.MyIdea.route){
                    MyIdeasScreen(mainViewModel, navController, addProjectViewModel)
                }
                composable(Screen.Profile.route){
                    ProfileScreen(mainViewModel)
                }
                composable(Screen.AddProject.route){
                    AddProjectScreen(mainViewModel, navController, addProjectViewModel)
                }
                composable(Screen.SingleIdea.route){
                    SingleIdeaScreen(mainViewModel, navController)
                }
            }
        }
        val nvbs by navController.currentBackStackEntryAsState()
        val crdt = nvbs?.destination
        val slt = crdt?.hierarchy?.any { it.route == Screen.AddProject.route } == true
        Crossfade(targetState = slt, modifier = Modifier
            .constrainAs(floatingButton) {
                bottom.linkTo(bottomNav.top, margin = 10.dp)
                end.linkTo(parent.end, margin = 10.dp)
            }) {
            if(!it){
//                addProjectViewModel.editEnabled = false
                IconButton(
                    colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary),
                    onClick = {
                        if(mainViewModel.state.user.name.isNotBlank()){
                            addProjectViewModel.editEnabled = false
                            navController.navigate(Screen.AddProject.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        } else {
                            mainViewModel.onEvent(UiEvents.ShowBar("Login Required"))
                        }
                    },
                    modifier = Modifier
                        .width(120.dp)
                        .height(50.dp)) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(modifier = Modifier.size(30.dp),painter = painterResource(id = R.drawable.idea_unselected), contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
                        Text(text = "  idea!! ", maxLines = 2, fontSize = 15.sp, color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            } else {
                if(!addProjectViewModel.editEnabled){
                    mainViewModel.state.floatText = "  Drop  "
                    addProjectViewModel.name = ""
                    addProjectViewModel.description = ""
                    addProjectViewModel.categoriesFinal.clear()
                    addProjectViewModel.difficulty = SortBy.DIFFICULTY_BEGINNER
                    addProjectViewModel.cp.clear()
                    addProjectViewModel.cp =  addProjectViewModel.categoriesList.map { MultiSelect(it, false) }.toMutableStateList()
                }
                IconButton(
                    colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary),
                    onClick = {
                        if(mainViewModel.state.user.name.isNotBlank()){
                            if(addProjectViewModel.editEnabled){
                                addProjectViewModel.editData(mainViewModel, navController, mainViewModel.state.highlightProject)
                                addProjectViewModel.editEnabled = false
                            } else {
                                addProjectViewModel.shareData(mainViewModel, navController)
                            }
                        } else {
                            mainViewModel.onEvent(UiEvents.ShowBar("Login Required"))
                        }
                    },
                    modifier = Modifier
                        .constrainAs(floatingButton) {
                            bottom.linkTo(bottomNav.top, margin = 10.dp)
                            end.linkTo(parent.end, margin = 10.dp)
                        }
                        .width(120.dp)
                        .height(50.dp)) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(modifier = Modifier.size(30.dp),painter = painterResource(id = R.drawable.box_dark), contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
                        Text(text = mainViewModel.state.floatText, maxLines = 2, fontSize = 15.sp, color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }
        AnimatedVisibility(
           enter = fadeIn(TweenSpec(150)),
           exit =  fadeOut(TweenSpec(150)),
            visible = mainViewModel.showBar,
            modifier = Modifier
            .constrainAs(bar) {
                bottom.linkTo(bottomNav.top, margin = 10.dp)
                start.linkTo(parent.start, margin = 10.dp)
                end.linkTo(floatingButton.start, margin = 10.dp)
                width = Dimension.fillToConstraints
            }) {
            IconButton(
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary),
                onClick = {
                    navController.navigate(Screen.AddProject.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = mainViewModel.barMessage, maxLines = 1, fontSize = 15.sp, color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }
}







