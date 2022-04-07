package com.example.movieapp.domain.use_cases.get_movie_from_db

import com.example.movieapp.data.room.entity.MovieEntity
import com.example.movieapp.domain.repository.MovieDbRepository

class GetMovieFromDbUseCaseImpl(private val repositoryMovie: MovieDbRepository): GetMovieFromDbUseCase {
    override suspend fun getMovieById(imdbId: String): MovieEntity {
        return repositoryMovie.getMovieById(imdbId)
    }
}