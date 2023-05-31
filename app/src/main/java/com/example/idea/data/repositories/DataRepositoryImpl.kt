package com.example.idea.data.repositories

import com.example.idea.domain.models.Domains
import com.example.idea.domain.repositories.DataRepository

class DataRepositoryImpl: DataRepository {

    private val domains: Domains = Domains()

    override suspend fun getSearchSuggestion(query: String): List<String> {
        val data = mutableListOf<String>()
        if(query.isBlank()){
            return data
        }
        for ((domain, subdomains) in domains.domainsWithSubdomains) {
            if(domain.uppercase().contains(query.uppercase())){
                data.add(domain)
            }
            for (subdomain in subdomains) {
                if(subdomain.uppercase().contains(query.uppercase())){
                    data.add(subdomain)
                }
            }
        }
        return data
    }
}