package com.example.movieapp.domain.use_cases.get_favorite_movies

import com.example.movieapp.data.room.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface GetFavoriteMoviesUseCase {
    fun favoriteMovies(): Flow<List<MovieEntity>>
}