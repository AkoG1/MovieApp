package com.example.movieapp.network

import com.example.movieapp.model.MovieDetailsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDetailsApiService {

    @GET("/")
    suspend fun getMovieDetails(
        @Query("apikey") apikey: String,
        @Query("i") id: String
    ): Response<MovieDetailsModel>

}