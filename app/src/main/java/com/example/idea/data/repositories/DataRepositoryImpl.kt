package com.example.idea.data.repositories

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.example.idea.data.remote.ProjectsApi
import com.example.idea.domain.models.Domains
import com.example.idea.domain.models.ProjectIdea
import com.example.idea.domain.repositories.DataRepository

class DataRepositoryImpl: DataRepository {

    private val domains: Domains = Domains()
    private val api = ProjectsApi()
    override suspend fun getProjectsByName(query: String): SnapshotStateList<ProjectIdea> {
        if(query.isBlank()){
            return mutableStateListOf()
        }
        val data: List<ProjectIdea> = api.getProjects()
        val finalData: MutableList<ProjectIdea>
        finalData = data.filter { it.name.uppercase().contains(query.uppercase()) }.toMutableStateList()
        return finalData
    }

    override suspend fun getSearchSuggestion(query: String): List<String> {
        val data = mutableListOf<String>()
        if(query.isBlank()){
            return data
        }
        var limit = 0
        for ((domain, subdomains) in domains.domainsWithSubdomains) {
            if(domain.uppercase().contains(query.uppercase())){
                data.add(domain)
                limit++
            }
            for (subdomain in subdomains) {
                if(subdomain.uppercase().contains(query.uppercase())){
                    data.add(subdomain)
                    limit++
                }
                if(limit>=3){
                    return data
                }
            }
        }
        return data
    }
}