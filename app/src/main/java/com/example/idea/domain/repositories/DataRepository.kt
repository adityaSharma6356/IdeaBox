package com.example.idea.domain.repositories

interface DataRepository {

    suspend fun getSearchSuggestion(
        query: String
    ) : List<String>
}