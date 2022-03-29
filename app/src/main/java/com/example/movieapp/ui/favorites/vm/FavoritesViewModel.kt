package com.example.movieapp.ui.favorites.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.repository.Repository
import com.example.movieapp.room.entity.MovieEntity
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repository: Repository) : ViewModel() {

    val favoriteMovies = repository.favoriteMovies()

    fun deleteMovieFromFav(movieEntity: MovieEntity) {
        viewModelScope.launch {
            repository.deleteFromDB(movieEntity)
        }
    }

}