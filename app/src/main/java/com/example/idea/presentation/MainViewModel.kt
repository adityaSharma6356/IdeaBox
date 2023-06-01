package com.example.idea.presentation

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idea.data.repositories.DataRepositoryImpl
import com.example.idea.domain.models.User
import com.example.idea.presentation.google.GoogleAuthUIClient
import com.example.idea.util.Screen
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    var user by mutableStateOf(User())
    var loginSuccess by mutableStateOf(false)
    var searchResult = mutableStateMapOf<String, List<String>>()
    var showProfileSection by mutableStateOf(false)
    var searchSuggestions = mutableStateListOf<String>()
    val navItems = listOf(
        Screen.Idea,
        Screen.MyIdea,
        Screen.Profile
    )
    var trendingTopic = listOf(
        "Mobile App Development",
        "Web Development",
        "Fitness and Exercise",
        "Education",
        "Language Learning",
        "Music and Audio",
        "E-commerce",
        "Marketing and Advertising",
        "Robotics",
        "Social Media and Communication",
        )

    val data = DataRepositoryImpl()

    fun loadSuggestions(query: String){
        viewModelScope.launch {
            searchSuggestions = data.getSearchSuggestion(query).toMutableStateList()
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
            user = User()
            showProfileSection = false
        }
    }
}