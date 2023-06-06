package com.example.idea.presentation

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
            sortSearchResults(SortBy.LATEST, SortBy.DIFFICULTY_RANDOM)
        }
    }

    fun onEvent(event: UiEvents){

    }
    fun refresh(indicator: Boolean = true){
        viewModelScope.launch {
            isFilterChanging = false
            if(indicator) isRefreshing = true
            state.currentDifficulty = SortBy.DIFFICULTY_RANDOM
            state.currentSortBy = SortBy.LATEST
            state = state.copy(mainList = data.loadAllProjects().toMutableList())
            state = state.copy(tempList = state.mainList)
            if(indicator) isRefreshing = false
            isFilterChanging = true
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

    fun likeClick(projectIdea: ProjectIdea){
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
        }
    }
}