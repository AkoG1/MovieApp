package com.example.movieapp.repository

import com.example.movieapp.model.MovieDetailsModel
import com.example.movieapp.model.SearchedItemsModel
import com.example.movieapp.network.NetworkClient
import com.example.movieapp.utils.Resource

class Repository(private val networkClient: NetworkClient) {

    suspend fun getSearchedMovies(searchedText: String): Resource<SearchedItemsModel> {
        return try {
            val response = networkClient.searchedItemsApiService.searchMovies(
                apikey = API_KEY,
                searchedText = searchedText
            )
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                Resource.Success(responseBody)
            } else {
                Resource.Error(response.message(), responseBody)
            }
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    suspend fun getMovieDetails(id: String): Resource<MovieDetailsModel> {
        return try {
            val response = networkClient.movieDetailsApiService.getMovieDetails(
                apikey = API_KEY,
                id = id
            )
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                Resource.Success(responseBody)
            } else {
                Resource.Error(response.message(), responseBody)
            }
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    companion object {
        private const val API_KEY = "8abb53bf"
    }

}