package com.example.movieapp.domain.use_cases.delete_favorite_movie

import com.example.movieapp.data.room.entity.MovieEntity
import com.example.movieapp.domain.repository.DbRepository

class DeleteMovieFromDbUseCaseImpl(private val repository: DbRepository): DeleteMovieFromDbUseCase {

    override suspend fun deleteFromDB(movie: MovieEntity) {
        repository.deleteFromDB(movie)
    }

}