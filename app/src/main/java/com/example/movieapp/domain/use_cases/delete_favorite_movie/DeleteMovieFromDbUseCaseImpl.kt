package com.example.movieapp.domain.use_cases.delete_favorite_movie

import com.example.movieapp.data.room.entity.MovieEntity
import com.example.movieapp.domain.repository.MovieDbRepository

class DeleteMovieFromDbUseCaseImpl(private val repositoryMovie: MovieDbRepository): DeleteMovieFromDbUseCase {

    override suspend fun deleteFromDB(movie: MovieEntity) {
        repositoryMovie.deleteFromDB(movie)
    }

}