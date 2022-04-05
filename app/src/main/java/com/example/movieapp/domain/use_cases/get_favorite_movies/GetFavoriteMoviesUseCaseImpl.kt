package com.example.movieapp.domain.use_cases.get_favorite_movies

import androidx.lifecycle.LiveData
import com.example.movieapp.data.room.entity.MovieEntity
import com.example.movieapp.domain.repository.DbRepository

class GetFavoriteMoviesUseCaseImpl(private val repository: DbRepository): GetFavoriteMoviesUseCase {
    override fun favoriteMovies(): LiveData<List<MovieEntity>> {
        return repository.favoriteMovies()
    }

}