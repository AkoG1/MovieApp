package com.example.movieapp.data.network

import com.example.movieapp.data.dto.MovieDetailsModelDto
import com.example.movieapp.data.dto.SearchedItemsModelDto
import com.example.movieapp.domain.model.MovieDetailsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchedItemsApiService {

    @GET("/")
    suspend fun searchMovies(
        @Query("apikey") apikey: String,
        @Query("s") searchedText: String
    ): Response<SearchedItemsModelDto>

    @GET("/")
    suspend fun getMovieDetails(
        @Query("apikey") apikey: String,
        @Query("i") id: String
    ): Response<MovieDetailsModelDto>

}