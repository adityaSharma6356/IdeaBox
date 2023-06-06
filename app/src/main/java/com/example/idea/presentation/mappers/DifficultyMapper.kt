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


fun colorProvider(it: SortBy, secondary: Color): Color {
    when(it){
        SortBy.DIFFICULTY_RANDOM -> {
            return secondary
        }
        SortBy.DIFFICULTY_BEGINNER -> {
            return Color(72, 155, 56, 255)
        }
        SortBy.DIFFICULTY_INTERMEDIATE -> {
            return Color(41, 105, 182, 255)
        }
        SortBy.DIFFICULTY_PRO -> {
            return Color(146, 39, 39, 255)
        }
        else -> {
            return Color(255, 255, 255, 255)
        }
    }
}