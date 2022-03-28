package com.example.movieapp.ui.movieDetails.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.ActorsModel
import com.example.movieapp.model.MovieDetailsModel
import com.example.movieapp.repository.Repository
import com.example.movieapp.room.entity.MovieEntity
import com.example.movieapp.utils.Resource
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val repository: Repository) : ViewModel() {

    private val _movieDetails = MutableLiveData<Resource<MovieDetailsModel>>(Resource.Idle)
    val movieDetails: LiveData<Resource<MovieDetailsModel>> get() = _movieDetails

    private val _actorsDetails = MutableLiveData<Resource<List<ActorsModel>>>(Resource.Idle)
    val actorsDetails: LiveData<Resource<List<ActorsModel>>> get() = _actorsDetails

    private val _movieDetailsFromDb = MutableLiveData<MovieDetailsModel>()
    val movieDetailsFromDb: LiveData<MovieDetailsModel> get() = _movieDetailsFromDb

    private val _checkInDb = MutableLiveData<Boolean>()
    val checkInDb: LiveData<Boolean> get() = _checkInDb

    fun getMovieDetails(id: String) {
        viewModelScope.launch {
            _movieDetails.value = Resource.Loading
            _movieDetails.value = repository.getMovieDetails(id = id)
        }
    }

    fun getActorsDetails(actors: String) {
        viewModelScope.launch {
            _actorsDetails.value = Resource.Loading
            _actorsDetails.value = repository.getActorsDetails(actors = actors)
        }
    }

    fun saveMovie(movie: MovieDetailsModel) {
        viewModelScope.launch {
            repository.insertToSaved(movie = dataClassTransform(movie))
        }
    }

    private fun dataClassTransform(movie: MovieDetailsModel): MovieEntity {
        return MovieEntity(
            actors = movie.actors,
            country = movie.country,
            director = movie.director,
            genre = movie.genre,
            imdbID = movie.imdbID,
            imdbRating = movie.imdbRating,
            imdbVotes = movie.imdbVotes,
            language = movie.language,
            poster = movie.poster,
            plot = movie.plot,
            rated = movie.rated,
            released = movie.released,
            runtime = movie.runtime,
            title = movie.title,
            type = movie.type,
            year = movie.year
        )
    }

    private fun dataClassTransform(movie: MovieEntity): MovieDetailsModel {
        return MovieDetailsModel(
            actors = movie.actors,
            country = movie.country,
            director = movie.director,
            genre = movie.genre,
            imdbID = movie.imdbID,
            imdbRating = movie.imdbRating,
            imdbVotes = movie.imdbVotes,
            language = movie.language,
            poster = movie.poster,
            plot = movie.plot,
            rated = movie.rated,
            released = movie.released,
            runtime = movie.runtime,
            title = movie.title,
            type = movie.type,
            year = movie.year,
            ratings = null
        )
    }

    fun getMovieById(imdbId: String) {
        viewModelScope.launch {
            _movieDetailsFromDb.value = dataClassTransform(repository.getMovieById(imdbId))
        }
    }

    fun checkMovieInDB(imdbId: String) {
        viewModelScope.launch {
            _checkInDb.value = repository.checkMovieInDB(imdbId)
        }
    }
}