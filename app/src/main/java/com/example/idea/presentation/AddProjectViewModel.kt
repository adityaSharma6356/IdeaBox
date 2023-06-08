package com.example.idea.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.idea.domain.models.Domains

class AddProjectViewModel: ViewModel() {
    var name by mutableStateOf("")
    var categories = mutableStateListOf<String>()
    var categoriesFinal = mutableStateListOf<String>("cat 1", "nshoisaidoas")
    var currentCategory by mutableStateOf("")
    private val categoriesList = Domains().domainlist
    var alertOpen by mutableStateOf(false)

    fun filterList(){
        categories.clear()
        categories.addAll(categoriesList.filter { it.uppercase().contains(currentCategory.uppercase()) }.toMutableStateList())
    }

}