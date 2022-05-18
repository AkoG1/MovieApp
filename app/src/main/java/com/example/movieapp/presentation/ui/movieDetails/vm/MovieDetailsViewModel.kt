package com.example.movieapp.presentation.ui.movieDetails.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.room.entity.MovieEntity
import com.example.movieapp.domain.model.ActorsModel
import com.example.movieapp.domain.model.MovieDetailsModel
import com.example.movieapp.domain.model.MovieTrailer
import com.example.movieapp.domain.use_cases.check_movie_in_db.CheckMovieInDbUseCase
import com.example.movieapp.domain.use_cases.get_actors_details.GetActorsDetailsUseCase
import com.example.movieapp.domain.use_cases.get_movie_details.GetMovieDetailsUseCase
import com.example.movieapp.domain.use_cases.get_movie_from_db.GetMovieFromDbUseCase
import com.example.movieapp.domain.use_cases.request_movie_trailer.RequestMovieTrailerUseCase
import com.example.movieapp.domain.use_cases.save_in_db.SaveInDbUseCase
import com.example.movieapp.domain.utils.Resource
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val getMovieDetails: GetMovieDetailsUseCase,
    private val saveToDbUseCase: SaveInDbUseCase,
    private val actorsRepository: GetActorsDetailsUseCase,
    private val getMovieFromDb: GetMovieFromDbUseCase,
    private val checkMovieInDb: CheckMovieInDbUseCase,
    private val requestMovieTrailer: RequestMovieTrailerUseCase
) : ViewModel() {

    private val _movieDetails = MutableLiveData<Resource<MovieDetailsModel>>(Resource.Idle)
    val movieDetails: LiveData<Resource<MovieDetailsModel>> get() = _movieDetails

    private val _actorsDetails = MutableLiveData<Resource<List<ActorsModel>>>(Resource.Idle)
    val actorsDetails: LiveData<Resource<List<ActorsModel>>> get() = _actorsDetails

    private val _movieDetailsFromDb = MutableLiveData<MovieDetailsModel>()
    val movieDetailsFromDb: LiveData<MovieDetailsModel> get() = _movieDetailsFromDb

    private val _movieTrailer = MutableLiveData<Resource<MovieTrailer>>()
    val movieTrailer: LiveData<Resource<MovieTrailer>> get() = _movieTrailer

    private val _checkInDb = MutableLiveData<Boolean>()
    val checkInDb: LiveData<Boolean> get() = _checkInDb

    fun getMovieDetails(id: String) {
        viewModelScope.launch {
            _movieDetails.value = Resource.Loading
            _movieDetails.value = getMovieDetails.getMovieDetails(id = id)
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
            type = movie.type
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
            type = movie.type
        )
    }

    fun getMovieById(imdbId: String) {
        viewModelScope.launch {
            _movieDetailsFromDb.value = dataClassTransform(getMovieFromDb.getMovieById(imdbId))
        }
    }

    fun requestMovieTrailer(imdbId: String) {
        viewModelScope.launch {
            _movieTrailer.value = requestMovieTrailer.requestMovieTrailer(imdbId)
        }
    }

    fun checkMovieInDB(imdbId: String) {
        viewModelScope.launch {
            _checkInDb.value = checkMovieInDb.checkMovieInDB(imdbId)
        }
    }
}