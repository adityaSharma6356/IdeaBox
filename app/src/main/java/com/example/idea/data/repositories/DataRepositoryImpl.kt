package com.example.idea.data.repositories

import com.example.idea.data.remote.ProjectsApi
import com.example.idea.domain.models.Domains
import com.example.idea.domain.models.ProjectIdea
import com.example.idea.domain.repositories.DataRepository

class DataRepositoryImpl: DataRepository {

    private val domains: Domains = Domains()
    private val api = ProjectsApi()

    override suspend fun deleteProject(data: ProjectIdea): Boolean {
        return api.deleteProject(data)
    }

    override suspend fun loadAllProjects(): List<ProjectIdea> {
        return api.getProjects()
    }

    override suspend fun likeProject(projectIdea: ProjectIdea) {
        api.likeProject(projectIdea)
    }

    override suspend fun uploadNewProject(data: ProjectIdea): Boolean {
        return api.uploadNewProject(data)
    }

    override suspend fun getSearchSuggestion(query: String): List<String> {
        val data = mutableListOf<String>()
        if(query.isBlank()){
            return data
        }
        var limit = 0
        for (thisdata in domains.domainlist) {
            if(thisdata.uppercase().contains(query.uppercase())){
                data.add(thisdata)
                limit++
                if(limit>=3) return data
            }
        }
        return data
    }
}