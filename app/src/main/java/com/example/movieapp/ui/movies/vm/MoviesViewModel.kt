package com.example.movieapp.ui.movies.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.SearchedItemsModel
import com.example.movieapp.repository.Repository
import com.example.movieapp.utils.Resource
import kotlinx.coroutines.launch

class MoviesViewModel(private val repository: Repository) : ViewModel() {

    private val _searchedMovies = MutableLiveData<Resource<SearchedItemsModel>>(Resource.Idle)
    val searchedMovies: LiveData<Resource<SearchedItemsModel>> get() = _searchedMovies

    fun getSearchedMovies(searchedText: String) {
        viewModelScope.launch {
            _searchedMovies.value = Resource.Loading
            _searchedMovies.value = repository.getSearchedMovies(searchedText = searchedText)
        }
    }

    fun swipeRefresh(lastText: String) {
        getSearchedMovies(lastText)
    }
}