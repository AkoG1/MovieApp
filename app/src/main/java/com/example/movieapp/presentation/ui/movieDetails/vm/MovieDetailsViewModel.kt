package com.example.movieapp.presentation.ui.movieDetails.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.model.ActorsModel
import com.example.movieapp.domain.model.MovieDetailsModel
import com.example.movieapp.domain.use_cases.get_movie_details.GetMovieDetailsUseCaseImpl
import com.example.movieapp.domain.use_cases.get_actors_details.GetActorsDetailsUseCaseImpl
import com.example.movieapp.domain.utils.Resource
import com.example.movieapp.data.room.entity.MovieEntity
import com.example.movieapp.domain.use_cases.check_movie_in_db.CheckMovieInDbUseCaseImpl
import com.example.movieapp.domain.use_cases.get_movie_from_db.GetMovieFromDbUseCaseImpl
import com.example.movieapp.domain.use_cases.save_in_db.SaveInDbUseCaseImpl
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val repository: GetMovieDetailsUseCaseImpl,
    private val saveToDbUseCase: SaveInDbUseCaseImpl,
    private val actorsRepository: GetActorsDetailsUseCaseImpl,
    private val getMovieFromDb: GetMovieFromDbUseCaseImpl,
    private val checkMovieInDb: CheckMovieInDbUseCaseImpl
) : ViewModel() {

    private val _movieDetails = MutableLiveData<Resource<MovieDetailsModel>>(Resource.Idle)
    val movieDetails: LiveData<Resource<MovieDetailsModel>> get() = _movieDetails

    private val _actorsDetails = MutableLiveData<Resource<List<ActorsModel>>>(Resource.Idle)
    val actorsDetailsDto: LiveData<Resource<List<ActorsModel>>> get() = _actorsDetails

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
            _actorsDetails.value = actorsRepository.getActorsDetails(actors = actors)
        }
    }

    fun saveMovie(movie: MovieDetailsModel) {
        viewModelScope.launch {
            saveToDbUseCase.insertToSaved(movie = dataClassTransform(movie))
        }
    }

    private fun dataClassTransform(movie: MovieDetailsModel): MovieEntity {
        return MovieEntity(
            actors = movie.actors,
            genre = movie.genre,
            imdbID = movie.imdbID,
            imdbRating = movie.imdbRating,
            language = movie.language,
            poster = movie.poster,
            plot = movie.plot,
            rated = movie.rated,
            released = movie.released,
            runtime = movie.runtime,
            title = movie.title,
            year = movie.year,
            country = null,
            director = null,
            imdbVotes = null,
            type = null
        )
    }

    private fun dataClassTransform(movie: MovieEntity): MovieDetailsModel {
        return MovieDetailsModel(
            actors = movie.actors,
            genre = movie.genre,
            imdbID = movie.imdbID,
            imdbRating = movie.imdbRating,
            language = movie.language,
            poster = movie.poster,
            plot = movie.plot,
            rated = movie.rated,
            released = movie.released,
            runtime = movie.runtime,
            title = movie.title,
            year = movie.year,
        )
    }

    fun getMovieById(imdbId: String) {
        viewModelScope.launch {
            _movieDetailsFromDb.value = dataClassTransform(getMovieFromDb.getMovieById(imdbId))
        }
    }

    fun checkMovieInDB(imdbId: String) {
        viewModelScope.launch {
            _checkInDb.value = checkMovieInDb.checkMovieInDB(imdbId)
        }
    }
}