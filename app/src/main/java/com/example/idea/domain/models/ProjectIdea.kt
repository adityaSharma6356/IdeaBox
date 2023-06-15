package com.example.idea.domain.models

import com.example.idea.presentation.util.SortBy
import com.google.firebase.Timestamp

data class ProjectIdea(
    val projectId: String = "",
    var name: String = "",
    var categories: MutableList<String> = mutableListOf(),
    var stars : Int = 0,
    var description: String = "",
    var author: String = "",
    var authorName : String = "",
    var dateCreated: Timestamp = Timestamp(0L, 0),
    var difficulty: SortBy = SortBy.DIFFICULTY_RANDOM,
    var likedByUserId: MutableList<String> = mutableListOf(),
    var bookMarkedByUsers: MutableList<String> = mutableListOf()
)
