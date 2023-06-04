package com.example.idea.domain.models

import com.example.idea.util.SortBy
import com.google.firebase.Timestamp

data class ProjectIdea(
    val projectId: String = "",
    val name: String = "",
    val categories: List<String> = listOf(),
    val stars : Int = 0,
    val description: String = "",
    val author: String = "",
    val dateCreated: Timestamp = Timestamp(0L, 0),
    val difficulty: SortBy = SortBy.DIFFICULTY_RANDOM
)
