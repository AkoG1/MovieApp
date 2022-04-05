package com.example.movieapp.domain.use_cases.check_movie_in_db

import com.example.movieapp.domain.repository.DbRepository

class CheckMovieInDbUseCaseImpl(private val repository: DbRepository): CheckMovieInDbUseCase {
    override suspend fun checkMovieInDB(imdbId: String): Boolean {
        return repository.checkMovieInDB(imdbId)
    }
}