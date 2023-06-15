package com.example.idea.presentation.util

import android.content.Context
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import com.example.idea.domain.models.ProjectIdea
import com.example.idea.presentation.google.GoogleAuthUIClient

sealed class UiEvents{
    data class LoginToGoogle(val googleAuthUIClient: GoogleAuthUIClient, val launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>): UiEvents()
    data class LoadData(val states: UiStates) : UiEvents()
    data class LikeProject(val thisProject:ProjectIdea, val context: Context) : UiEvents()
    data class UploadIdea(val data: ProjectIdea) : UiEvents()
    data class ShowBar(val message : String) : UiEvents()
    data class Bookmark(val data : ProjectIdea) : UiEvents()
    data class DeleteIdea(val data: ProjectIdea) : UiEvents()
    data class EditIdea(val data: ProjectIdea) : UiEvents()
    object SetBookmarkView : UiEvents()
    object SetMyIdeaView : UiEvents()
}
