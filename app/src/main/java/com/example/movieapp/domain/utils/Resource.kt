package com.example.movieapp.domain.utils

sealed class Resource<out T> {

    class Success<T>(val data: T) : Resource<T>()

    class Error<T>(
        val message: String? = null,
        val data: T? = null
    ) : Resource<T>()

    object Loading : Resource<Nothing>()

    object Idle : Resource<Nothing>()

    class Null<T>() : Resource<T>()

}