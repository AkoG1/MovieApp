package com.example.movieapp.ui.movieDetails.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.MovieDetailsModel
import com.example.movieapp.repository.Repository
import com.example.movieapp.utils.Resource
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val repository: Repository) : ViewModel() {

    private val _movieDetails = MutableLiveData<Resource<MovieDetailsModel>>(Resource.Idle)
    val movieDetails: LiveData<Resource<MovieDetailsModel>> get() = _movieDetails


    fun getMovieDetails(id: String) {
        viewModelScope.launch {
            _movieDetails.value = Resource.Loading
            _movieDetails.value = repository.getMovieDetails(id = id)
        }
    }
}