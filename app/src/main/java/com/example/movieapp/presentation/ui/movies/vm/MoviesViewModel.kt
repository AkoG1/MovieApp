package com.example.movieapp.presentation.ui.movies.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.model.MovieDetailsModel
import com.example.movieapp.domain.use_cases.get_searched_movies.GetSearchedMoviesUseCase
import com.example.movieapp.domain.utils.Resource
import kotlinx.coroutines.launch

class MoviesViewModel(private val getSearchedMovies: GetSearchedMoviesUseCase) : ViewModel() {

    private val _searchedMovies = MutableLiveData<Resource<List<MovieDetailsModel>>>(Resource.Idle)
    val searchedMovies: LiveData<Resource<List<MovieDetailsModel>>> get() = _searchedMovies

    fun getSearchedMovies(searchedText: String) {
        viewModelScope.launch {
            _searchedMovies.value = Resource.Loading
            _searchedMovies.value = getSearchedMovies.getSearchedMovies(searchedText = searchedText)
        }
    }

    fun swipeRefresh(lastText: String) {
        getSearchedMovies(lastText)
    }

    fun clearLiveData() {
        _searchedMovies.value = Resource.Idle
    }

}