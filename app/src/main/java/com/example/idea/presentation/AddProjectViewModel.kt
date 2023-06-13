package com.example.idea.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.idea.domain.models.Domains
import com.example.idea.domain.models.ProjectIdea
import com.example.idea.presentation.util.Screen
import com.example.idea.presentation.util.SortBy
import com.example.idea.presentation.util.UiEvents
import com.google.firebase.Timestamp

class AddProjectViewModel: ViewModel() {
    inner class CateBoxState(
        val name: String,
        val selectorIndex:Int
    )
    var name by mutableStateOf("")
    var categoriesFinal = mutableStateListOf<CateBoxState>()
    val categoriesList = Domains().domainlist
    var alertOpen by mutableStateOf(false)
    var description by mutableStateOf("")

    var difficulty by mutableStateOf(SortBy.DIFFICULTY_BEGINNER)
    var menuExpanded by mutableStateOf(false)

    fun shareData(mainViewModel: MainViewModel, navController: NavController){
        if(name.isNotBlank() && categoriesFinal.isNotEmpty() && description.isNotBlank()){
            val temp = ProjectIdea(
                name = name,
                categories = categoriesFinal.map { it.name }.toMutableList(),
                difficulty = difficulty,
                description = description,
                author = mainViewModel.state.user.id,
                dateCreated = Timestamp(System.currentTimeMillis()/1000, 0)
            )
            mainViewModel.onEvent(UiEvents.UploadIdea(temp))
            navController.navigate(Screen.Idea.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        } else {
            mainViewModel.onEvent(UiEvents.ShowBar("All fields required"))
        }
    }
}