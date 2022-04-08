package com.example.movieapp.presentation.ui.favorites.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.room.entity.MovieEntity
import com.example.movieapp.domain.use_cases.delete_favorite_movie.DeleteMovieFromDbUseCase
import com.example.movieapp.domain.use_cases.get_favorite_movies.GetFavoriteMoviesUseCase
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: GetFavoriteMoviesUseCase,
    private val deleteMovieFromDb: DeleteMovieFromDbUseCase
) : ViewModel() {

    val favoriteMovies = repository.favoriteMovies()

    fun deleteMovieFromFav(movieEntity: MovieEntity) {
        viewModelScope.launch {
            deleteMovieFromDb.deleteFromDB(movieEntity)
        }
    }

}