package com.example.movieapp.domain.use_cases.get_favorite_movies

import androidx.lifecycle.LiveData
import com.example.movieapp.data.room.entity.MovieEntity

interface GetFavoriteMoviesUseCase {
    fun favoriteMovies(): LiveData<List<MovieEntity>>
}