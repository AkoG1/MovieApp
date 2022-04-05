package com.example.movieapp.domain.use_cases.save_in_db

import com.example.movieapp.data.room.entity.MovieEntity

interface SaveInDbUseCase {

    suspend fun insertToSaved(movie: MovieEntity)

}