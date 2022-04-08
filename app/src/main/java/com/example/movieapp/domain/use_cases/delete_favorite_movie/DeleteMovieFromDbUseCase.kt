package com.example.movieapp.domain.use_cases.delete_favorite_movie

import com.example.movieapp.data.room.entity.MovieEntity

interface DeleteMovieFromDbUseCase {
    suspend fun deleteFromDB(movie: MovieEntity)
}