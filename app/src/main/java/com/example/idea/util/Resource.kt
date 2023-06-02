package com.example.idea.util

sealed class Resource<out T>(val data: T?, val message : String?) {
    class Success<T>(data : T?, message: String?) : Resource<T>(data, null)
    class Error<T>(message: String?) : Resource<T>( null, message)
    class Loading<T>(isLoading: Boolean = true) : Resource<T>( null, null)
}
