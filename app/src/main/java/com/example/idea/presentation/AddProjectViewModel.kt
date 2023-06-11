package com.example.idea.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.idea.domain.models.Domains
import com.example.idea.presentation.util.SortBy

class AddProjectViewModel: ViewModel() {
    inner class CateBoxState(
        val name: String,
        val selectorIndex:Int
    )
    var name by mutableStateOf("")
    var categoriesFinal = mutableStateListOf<CateBoxState>()
    val categoriesList = Domains().domainlist
    var alertOpen by mutableStateOf(false)

    var difficulty by mutableStateOf(SortBy.DIFFICULTY_BEGINNER)
    var menuExpanded by mutableStateOf(false)

}