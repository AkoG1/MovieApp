package com.example.movieapp.data.network

import com.example.movieapp.data.dto.MovieTrailerDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieTrailerApiService {

    @GET("/3/movie/{movie_id}/videos")
    suspend fun getMovieTrailer(
        @Path("movie_id") id: String,
        @Query("api_key") apikey: String
    ): Response<MovieTrailerDto>

}