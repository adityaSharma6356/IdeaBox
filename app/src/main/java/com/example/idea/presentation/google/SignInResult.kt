package com.example.idea.presentation.google

import com.example.idea.domain.models.User

data class SignInResult(
    val data: User?,
    val errorMessage: String?
)

data class UserData(
    val userId: String = "",
    val username: String? = "",
    val profilePictureUrl: String? = ""
)