package com.example.movieapp.network

import com.example.movieapp.model.SearchedItemsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchedItemsApiService {

    @GET("/")
    suspend fun searchMovies(
        @Query("apikey") apikey: String,
        @Query("s") searchedText: String
    ): Response<SearchedItemsModel>

}