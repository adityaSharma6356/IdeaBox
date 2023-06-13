package com.example.idea.presentation.util

import androidx.compose.runtime.mutableStateOf
import com.example.idea.domain.models.ProjectIdea
import com.example.idea.domain.models.User

data class UiStates(
    var user: User = User(),
    var highlightProject:ProjectIdea = ProjectIdea(),
    var tempList: MutableList<ProjectIdea> = mutableListOf(),
    var mainList: MutableList<ProjectIdea> = mutableListOf(),
    var tempSearchList: MutableList<ProjectIdea> = mutableListOf(),
    var secondList: MutableList<ProjectIdea> = mutableListOf(),
    var currentSortBy: SortBy = SortBy.LATEST,
    var currentDifficulty: SortBy = SortBy.DIFFICULTY_RANDOM,
    var trendingTopic:List<String> = listOf(
        "Mobile App Development",
        "Web Development",
        "Fitness and Exercise",
        "Education",
        "Language Learning",
        "Music and Audio",
        "E-commerce",
        "Marketing and Advertising",
        "Robotics",
        "Social Media and Communication",
    ),
    var showErrorCard: Boolean = false,
    var draftSaved :Boolean = false,
    var currentQuery : String = "",
    var setView: Int = 0
){
    fun setView(){
        secondList = if(setView==1){
            mainList.filter { it.bookMarkedByUsers.contains(user.id) }.toMutableList()
        } else {
            mainList.filter { it.author == user.id }.toMutableList()
        }
    }
    fun search(){
        if(currentQuery.isNotBlank()){
            tempList = mainList.filter {
                it.categories.contains(currentQuery) || it.name.uppercase().contains(currentQuery.uppercase())
            }.toMutableList()
        }
    }
    fun sortByPopularity(){
        tempList = if(currentDifficulty==SortBy.DIFFICULTY_RANDOM){
            mainList
        } else {
            mainList.filter { it.difficulty==currentDifficulty }.toMutableList()
        }
        if(currentSortBy==SortBy.POPULAR) {
            tempList.sortedByDescending { it.stars }
        } else {
            tempList.sortBy { it.dateCreated }
        }
    }
}
