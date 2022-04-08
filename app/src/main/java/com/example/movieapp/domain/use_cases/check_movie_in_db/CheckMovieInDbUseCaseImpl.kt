package com.example.movieapp.domain.use_cases.check_movie_in_db

import com.example.movieapp.domain.repository.MovieDbRepository

class CheckMovieInDbUseCaseImpl(private val repositoryMovie: MovieDbRepository): CheckMovieInDbUseCase {
    override suspend fun checkMovieInDB(imdbId: String): Boolean {
        return repositoryMovie.checkMovieInDB(imdbId)
    }
}