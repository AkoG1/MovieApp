package com.example.movieapp.domain.use_cases.get_movie_from_db

import com.example.movieapp.data.room.entity.MovieEntity
import com.example.movieapp.domain.repository.DbRepository

class GetMovieFromDbUseCaseImpl(private val repository: DbRepository): GetMovieFromDbUseCase {
    override suspend fun getMovieById(imdbId: String): MovieEntity {
        return repository.getMovieById(imdbId)
    }
}