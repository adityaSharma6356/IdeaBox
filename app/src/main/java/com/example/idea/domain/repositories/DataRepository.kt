package com.example.idea.domain.repositories

import com.example.idea.domain.models.ProjectIdea

interface DataRepository {
    suspend fun loadAllProjects() :List<ProjectIdea>
    suspend fun uploadNewProject(
        data: ProjectIdea
    ) : Boolean

    suspend fun likeProject(
        projectIdea: ProjectIdea
    )

    suspend fun getSearchSuggestion(
        query: String
    ) : List<String>

    suspend fun deleteProject(
        data: ProjectIdea
    ) : Boolean
}