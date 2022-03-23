package com.example.movieapp.ui.movieDetails.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.ActorsModel
import com.example.movieapp.model.MovieDetailsModel
import com.example.movieapp.repository.Repository
import com.example.movieapp.utils.Resource
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val repository: Repository) : ViewModel() {

    private val _movieDetails = MutableLiveData<Resource<MovieDetailsModel>>(Resource.Idle)
    val movieDetails: LiveData<Resource<MovieDetailsModel>> get() = _movieDetails

    private val _actorsDetails = MutableLiveData<Resource<ActorsModel>>(Resource.Idle)
    val actorsDetails: LiveData<Resource<ActorsModel>> get() = _actorsDetails

    fun getMovieDetails(id: String) {
        viewModelScope.launch {
            _movieDetails.value = Resource.Loading
            _movieDetails.value = repository.getMovieDetails(id = id)
        }
    }

    fun getActorsDetails(name: String) {
        viewModelScope.launch {
            _actorsDetails.value = Resource.Loading
            _actorsDetails.value = repository.getActorsDetails(name = name)
        }
    }

    fun swipeRefreshListener(id: String) {
        getMovieDetails(id = id)
    }

}