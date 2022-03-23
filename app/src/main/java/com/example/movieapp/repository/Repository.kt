package com.example.movieapp.repository

import com.example.movieapp.model.ActorsModel
import com.example.movieapp.model.MovieDetailsModel
import com.example.movieapp.network.NetworkClient
import com.example.movieapp.utils.Resource

class Repository(private val networkClient: NetworkClient) {

    suspend fun getSearchedMovies(searchedText: String): Resource<MutableList<MovieDetailsModel>> {
        return try {
            val response = networkClient.searchedItemsApiService.searchMovies(
                apikey = API_KEY_OMDB,
                searchedText = searchedText
            )
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null && !responseBody.search.isNullOrEmpty()) {
                val searchedItemsModel = mutableListOf<MovieDetailsModel>()
                var i = 0
                while (responseBody.search.size >= i + 1) {
                    when (val detailedResponse = getMovieDetails(responseBody.search[i].imdbID!!)) {
                        is Resource.Success -> {
                            searchedItemsModel.add(detailedResponse.data)
                        }
                        is Resource.Error -> {
                            Resource.Error(detailedResponse.message, detailedResponse.data)
                        }
                    }
                    i += 1
                }
                Resource.Success(searchedItemsModel)

            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    suspend fun getMovieDetails(id: String): Resource<MovieDetailsModel> {
        return try {
            val response = networkClient.movieDetailsApiService.getMovieDetails(
                apikey = API_KEY_OMDB,
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

    suspend fun getActorsDetails(name: String): Resource<ActorsModel> {
        return try {
            val response = networkClient.actorsDetailsApiService.getActorsDetails(
                apikey = API_KEY_TMDB,
                name = name
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
        private const val API_KEY_OMDB = "8abb53bf"
        private const val API_KEY_TMDB = "a0138d03454feb065cf883ee60805f71"
    }

}