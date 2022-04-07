package com.example.movieapp.data.network

import com.example.movieapp.data.dto.MovieDetailsResponseDto
import com.example.movieapp.data.dto.SearchedItemsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchedItemsApiService {

    @GET("/")
    suspend fun searchMovies(
        @Query("apikey") apikey: String,
        @Query("s") searchedText: String
    ): Response<SearchedItemsResponseDto>

    @GET("/")
    suspend fun getMovieDetails(
        @Query("apikey") apikey: String,
        @Query("i") id: String
    ): Response<MovieDetailsResponseDto>

}