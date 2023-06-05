package com.example.idea.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProjectEntity(
    @PrimaryKey val key: Int? = null,
    val projectId:String,
    val name: String ,
    val categories: String,
    val stars : Int,
    val description: String ,
    val author: String ,
    val dateCreated: Long ,
    val difficulty: Int,
    val likedByUserId: String
)