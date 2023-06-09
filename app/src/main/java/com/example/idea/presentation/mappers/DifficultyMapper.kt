package com.example.idea.presentation.mappers

import androidx.compose.ui.graphics.Color
import com.example.idea.presentation.util.SortBy

fun toName(it: SortBy): String {
    when(it){
        SortBy.DIFFICULTY_RANDOM -> {
            return "Dependant"
        }
        SortBy.DIFFICULTY_BEGINNER -> {
            return "Beginner"
        }
        SortBy.DIFFICULTY_INTERMEDIATE -> {
            return "Intermediate"
        }
        SortBy.DIFFICULTY_PRO -> {
            return "Pro"
        }
        else -> {
            return ""
        }
    }
}


fun colorProvider(it: SortBy): Color {
    when(it){
        SortBy.DIFFICULTY_RANDOM -> {
            return Color.White
        }
        SortBy.DIFFICULTY_BEGINNER -> {
            return Color(72, 155, 56, 255)
        }
        SortBy.DIFFICULTY_INTERMEDIATE -> {
            return Color(52, 128, 221, 255)
        }
        SortBy.DIFFICULTY_PRO -> {
            return Color(204, 55, 55, 255)
        }
        else -> {
            return Color(255, 255, 255, 255)
        }
    }
}