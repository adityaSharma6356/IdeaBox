package com.example.idea.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.idea.domain.models.Domains
import com.example.idea.domain.models.ProjectIdea
import com.example.idea.presentation.util.Screen
import com.example.idea.presentation.util.SortBy
import com.example.idea.presentation.util.UiEvents
import com.google.firebase.Timestamp
import kotlinx.coroutines.launch

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
    var editEnabled by mutableStateOf(false)

    fun setEditData(projectIdea: ProjectIdea){
        name = projectIdea.name
        description = projectIdea.description
        categoriesFinal.clear()
        categoriesFinal.addAll(
            projectIdea.categories.map {
                CateBoxState(
                    it,
                    categoriesList.indexOf(it)
                )
            }
        )
    }
    fun editData(mainViewModel: MainViewModel, navController: NavController, projectIdea: ProjectIdea){
        viewModelScope.launch {
            if(name.isNotBlank() && categoriesFinal.isNotEmpty() && description.isNotBlank()){
                if(false){
                    mainViewModel.onEvent(UiEvents.ShowBar("No changes made"))
                } else {
                    var temp = projectIdea
                    temp = temp.copy(
                        name = name,
                        categories = categoriesFinal.map { it.name }.toMutableList(),
                        difficulty = difficulty,
                        description = description,
                        author = mainViewModel.state.user.id,
                        dateCreated = Timestamp(System.currentTimeMillis()/1000, 0),
                        authorName = mainViewModel.state.user.name
                    )
                    navController.navigate(Screen.Idea.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    mainViewModel.onEvent(UiEvents.EditIdea(temp))
                    mainViewModel.refresh(false)
                    name = ""
                    description = ""
                    categoriesFinal.clear()
                    difficulty = SortBy.DIFFICULTY_BEGINNER
                    editEnabled = false
                    mainViewModel.state.floatText = "  Drop  "
                }
            } else {
                mainViewModel.onEvent(UiEvents.ShowBar("All fields required"))
            }
        }
    }
    fun shareData(mainViewModel: MainViewModel, navController: NavController){
        viewModelScope.launch {
            if(name.isNotBlank() && categoriesFinal.isNotEmpty() && description.isNotBlank()){
                val temp = ProjectIdea(
                    name = name,
                    categories = categoriesFinal.map { it.name }.toMutableList(),
                    difficulty = difficulty,
                    description = description,
                    author = mainViewModel.state.user.id,
                    dateCreated = Timestamp(System.currentTimeMillis()/1000, 0),
                    authorName = mainViewModel.state.user.name
                )
                navController.navigate(Screen.Idea.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                mainViewModel.onEvent(UiEvents.UploadIdea(temp))
                mainViewModel.refresh(false)
                name = ""
                description = ""
                categoriesFinal.clear()
                difficulty = SortBy.DIFFICULTY_BEGINNER
            } else {
                mainViewModel.onEvent(UiEvents.ShowBar("All fields required"))
            }
        }
    }
}