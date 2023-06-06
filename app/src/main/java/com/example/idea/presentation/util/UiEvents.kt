package com.example.idea.presentation.util

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import com.example.idea.presentation.google.GoogleAuthUIClient

sealed class UiEvents{
    data class LoginToGoogle(val googleAuthUIClient: GoogleAuthUIClient, val launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>): UiEvents()
    data class LoadData(val states: UiStates) : UiEvents()
    object LikeProject : UiEvents()

}
