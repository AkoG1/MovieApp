package com.example.movieapp.network

import com.example.movieapp.model.ActorsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ActorsDetailsApiService {

    @GET("t/p/original/")
    suspend fun getActorsDetails(
        @Query("api_key") apikey: String,
        @Query("query") name: String
    ): Response<ActorsModel>

}