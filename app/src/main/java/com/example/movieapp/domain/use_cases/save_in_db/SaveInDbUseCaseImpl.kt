package com.example.movieapp.domain.use_cases.save_in_db

import com.example.movieapp.domain.repository.DbRepository
import com.example.movieapp.data.room.entity.MovieEntity

class SaveInDbUseCaseImpl(private val repository: DbRepository): SaveInDbUseCase {
    override suspend fun insertToSaved(movie: MovieEntity) {
        repository.insertToSaved(movie)
    }
}