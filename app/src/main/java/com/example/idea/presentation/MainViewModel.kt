package com.example.idea.presentation

import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idea.data.repositories.DataRepositoryImpl
import com.example.idea.domain.models.ProjectIdea
import com.example.idea.domain.models.User
import com.example.idea.presentation.google.GoogleAuthUIClient
import com.example.idea.presentation.util.Screen
import com.example.idea.presentation.util.SortBy
import com.example.idea.presentation.util.UiEvents
import com.example.idea.presentation.util.UiStates
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    var query by mutableStateOf("")
    var active by  mutableStateOf(false)
    var openFilter by  mutableStateOf(false)
    var loginSuccess by mutableStateOf(false)
    var showProfileSection by mutableStateOf(false)
    var showBar by mutableStateOf(false)
    var barMessage by mutableStateOf("")
    var searchSuggestions = mutableStateListOf<String>()
    var searchFilters = mutableStateListOf<String>()
    val navItems = listOf(
        Screen.Idea,
        Screen.MyIdea,
        Screen.Profile
    )
    var isRefreshing by  mutableStateOf(false)
    var isFilterChanging by  mutableStateOf(true)

    var state by mutableStateOf(UiStates())

    val data = DataRepositoryImpl()
    init {
        viewModelScope.launch {
            state = state.copy(mainList = data.loadAllProjects().toMutableList())
            state = state.copy(tempList = state.mainList)
            state.sortByPopularity()
//            sortSearchResults(SortBy.LATEST, SortBy.DIFFICULTY_RANDOM)
        }
    }

    fun onEvent(event: UiEvents){
        when(event){
            is UiEvents.EditIdea -> {
                editIdea(event.data)
            }
            is UiEvents.SetMyIdeaView -> {
                state = state.copy(setView = 0)
                state.setView()
            }
            is UiEvents.SetBookmarkView -> {
                state = state.copy(setView = 1)
                state.setView()
            }
            is UiEvents.DeleteIdea -> {
                deleteIdea(event.data)
            }
            is UiEvents.LikeProject ->{
                viewModelScope.launch {
                    if(state.user.id.isBlank()){
                        Toast.makeText(event.context, "Login required", Toast.LENGTH_SHORT).show()
                    } else {
                        likeClick(event.thisProject)
                    }
                }
            }
            is UiEvents.Bookmark -> {
                likeClick(event.data)
            }
            is UiEvents.UploadIdea -> {
                uploadIdea(event.data)
            }
            is UiEvents.ShowBar -> {
                showBarWithMessage(event.message)
            }
            else -> Unit
        }
    }

    private fun editIdea(project: ProjectIdea){
        likeClick(project)
        showBarWithMessage("Idea Updated")
    }
    private fun deleteIdea(project: ProjectIdea){
        viewModelScope.launch {
            if(data.deleteProject(project)){
                showBarWithMessage("Idea Deleted")
                refresh(false)
            } else {
                showBarWithMessage("Something went wrong :(")
            }
        }
    }
    fun refresh(indicator: Boolean = true){
        viewModelScope.launch {
            if(indicator) isRefreshing = true
            state.currentDifficulty = SortBy.DIFFICULTY_RANDOM
            state.currentSortBy = SortBy.LATEST
            state = state.copy(mainList = data.loadAllProjects().toMutableList())
            state = state.copy(tempList = state.mainList)
            state.sortByPopularity()
            if(indicator) isRefreshing = false
        }
    }
    private fun showBarWithMessage(message: String){
        viewModelScope.launch {
            barMessage = message
            showBar = true
            delay(1500)
            showBar = false
            barMessage = ""
        }
    }
    private fun uploadIdea(project: ProjectIdea){
        viewModelScope.launch {
            if(data.uploadNewProject(project)){
                showBarWithMessage("Idea Uploaded :D")
            } else {
                showBarWithMessage("Something went wrong :(")
            }
        }
    }

    fun sortSearchResults(sortBY: SortBy, difficulty: SortBy = SortBy.DIFFICULTY_RANDOM){
        viewModelScope.launch {
            isFilterChanging = false
            state = state.copy(currentSortBy = sortBY)
            state = state.copy(currentDifficulty = difficulty)
            delay(150L)
            state.sortByPopularity()
            isFilterChanging = true
        }
    }

    private fun likeClick(projectIdea: ProjectIdea){
        viewModelScope.launch {
            data.likeProject(projectIdea)
            refresh(false)
        }
    }

    fun loadSuggestions(query: String){
        viewModelScope.launch {
            searchSuggestions = data.getSearchSuggestion(query).toMutableStateList()
        }
    }

    fun getProjectsByName(query: String){
        viewModelScope.launch {
            state = state.copy(tempSearchList = state.mainList.filter { it.name.uppercase().contains(query.uppercase()) }.toMutableList())
        }
    }

    fun loginToGoogle(
        googleAuthUIClient: GoogleAuthUIClient,
        launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>
    ){
        viewModelScope.launch {
            val signInIntentSender = googleAuthUIClient.signIn()
            launcher.launch(
                IntentSenderRequest.Builder(
                    signInIntentSender ?: return@launch
                ).build()
            )
        }
    }

    fun logout(
        googleAuthUIClient: GoogleAuthUIClient
    ){
        viewModelScope.launch {
            googleAuthUIClient.signOut()
            state = state.copy(user = User())
            showProfileSection = false
            if(state.user.id.isBlank()){
                showBarWithMessage("Signed out successfully")
            }
        }
    }
}