package com.example.movieapp.data.network

import com.example.movieapp.data.dto.MovieExternalIdDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieExternalIdApiService {

    @GET("/3/movie/{movie_id}/external_ids")
    suspend fun getMovieExternalId(
        @Path("movie_id") imdbId: String,
        @Query("api_key") apikey: String
    ): Response<MovieExternalIdDto>

}