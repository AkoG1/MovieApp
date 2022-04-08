package com.example.movieapp.domain.use_cases.check_movie_in_db

interface CheckMovieInDbUseCase {
    suspend fun checkMovieInDB(imdbId: String): Boolean
}