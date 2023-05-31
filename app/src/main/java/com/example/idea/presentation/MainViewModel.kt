package com.example.idea.presentation

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idea.domain.models.User
import com.example.idea.presentation.google.GoogleAuthUIClient
import com.example.idea.util.Screen
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    var user by mutableStateOf(User())
    var loginSuccess by mutableStateOf(false)
    val Navitems = listOf(
        Screen.Idea,
        Screen.MyIdea,
        Screen.Profile
    )

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
}