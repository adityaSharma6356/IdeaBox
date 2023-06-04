package com.example.idea.data.remote

import android.util.Log
import com.example.idea.data.local.ProjectEntity
import com.example.idea.domain.models.Domains
import com.example.idea.domain.models.ProjectIdea
import com.example.idea.util.Resource
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import retrofit2.HttpException
import java.io.IOException

class ProjectsApi {
    private val fireStore = FirebaseFirestore.getInstance()
    private val projectCollection = fireStore.collection("ProjectIdeas")
    private val categories = fireStore.collection("categories")

    suspend fun getCategories(): List<String>?{
        return try {
            val data: ProjectCategories = categories.document("1").get().await().toObject(ProjectCategories::class.java)!!
            data.categories
        } catch (e: IOException){
            return null
        } catch (e: HttpException){
            return null
        } catch (e: FirebaseTooManyRequestsException){
            return null
        }
    }
    suspend fun uploadNewProject(data: ProjectIdea){
        projectCollection
            .add(data)
            .addOnSuccessListener {
                projectCollection.document(it.id).update("projectId", it.id).addOnFailureListener { exc ->
                    Log.d("firebaselog", "Id Update Failed!! ,Reason : $exc")
                }
            }
            .addOnFailureListener {
                Log.d("firebaselog", "upload Failed!! ,Reason : $it")
            }
    }
    suspend fun getProjectsByCategories(queryList: List<String>): List<ProjectIdea>?{
        return try {
            projectCollection.whereArrayContainsAny("categories", queryList).get().await().toObjects(ProjectIdea::class.java)
        } catch (e: IOException){
            return null
        } catch (e: HttpException){
            return null
        } catch (e: FirebaseTooManyRequestsException){
            return null
        }
    }
    suspend fun getProjects():List<ProjectIdea>{
        return try {
            projectCollection.get().await().toObjects(ProjectIdea::class.java)
        } catch (e: IOException){
            return listOf()
        } catch (e: HttpException){
            return listOf()
        } catch (e: FirebaseTooManyRequestsException){
            return listOf()
        }
    }
}