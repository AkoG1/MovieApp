package com.example.movieapp.domain.use_cases.save_in_db

import com.example.movieapp.data.room.entity.MovieEntity
import com.example.movieapp.domain.repository.MovieDbRepository

class SaveInDbUseCaseImpl(private val repositoryMovieImplImpl: MovieDbRepository): SaveInDbUseCase {
    override suspend fun insertToSaved(movie: MovieEntity) {
        repositoryMovieImplImpl.insertToSaved(movie)
    }
}