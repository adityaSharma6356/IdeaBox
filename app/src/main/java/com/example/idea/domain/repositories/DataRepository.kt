package com.example.idea.domain.repositories

import com.example.idea.domain.models.ProjectIdea

interface DataRepository {
    suspend fun loadProjects()
    suspend fun uploadNewProject(
        data: ProjectIdea
    )

    suspend fun getProjectsByName(
        query: String
    ): List<ProjectIdea>


    suspend fun getSearchSuggestion(
        query: String
    ) : List<String>
}