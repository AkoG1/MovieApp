package com.example.movieapp.data.network

import com.example.movieapp.data.dto.ActorsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ActorsDetailsApiService {

    @GET("/3/search/person")
    suspend fun getActorsDetails(
        @Query("api_key") apikey: String,
        @Query("query") name: String
    ): Response<ActorsResponseDto>

}