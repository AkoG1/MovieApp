package com.example.movieapp.repository

import androidx.lifecycle.LiveData
import com.example.movieapp.model.ActorsModel
import com.example.movieapp.model.MovieDetailsModel
import com.example.movieapp.model.SearchedItemsModel
import com.example.movieapp.network.NetworkClient
import com.example.movieapp.room.dao.MoviesDao
import com.example.movieapp.room.entity.MovieEntity
import com.example.movieapp.utils.Resource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class Repository(private val networkClient: NetworkClient, private val moviesDao: MoviesDao) {

    suspend fun getSearchedMovies(searchedText: String): Resource<List<MovieDetailsModel>> {
        return try {
            val response = networkClient.searchedItemsApiService.searchMovies(
                apikey = API_KEY_OMDB,
                searchedText = searchedText
            )
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null && !responseBody.search.isNullOrEmpty()) {
                getDetailedMovies(responseBody.search)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    private suspend fun getDetailedMovies(
        items: List<SearchedItemsModel.Search>
    ): Resource<List<MovieDetailsModel>> {
        val result = items.map { item ->
            coroutineScope {
                async { getMovieDetails(item.imdbID) }
            }
        }.awaitAll()

        val errors = result.filterIsInstance<Resource.Error<MovieDetailsModel>>()

        return if (errors.isNotEmpty()) {
            Resource.Error(errors.first().message)
        } else {
            Resource.Success(
                result.filterIsInstance<Resource.Success<MovieDetailsModel>>()
                    .map { it.data }
            )
        }
    }


    suspend fun getMovieDetails(id: String): Resource<MovieDetailsModel> {
        return try {
            val response = networkClient.searchedItemsApiService.getMovieDetails(
                apikey = API_KEY_OMDB,
                id = id
            )
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                Resource.Success(responseBody)
            } else {
                Resource.Error(response.message(), responseBody)
            }
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    suspend fun getActorsDetails(actors: String): Resource<List<ActorsModel>> {
        val listedNames = actors.split(",").map { it.trim() }
        val result = listedNames.map { actor ->
            coroutineScope {
                async { requestActorsDetails(actor) }
            }
        }.awaitAll()

        val errors = result.filterIsInstance<Resource.Error<ActorsModel>>()

        return if (errors.isNotEmpty()) {
            Resource.Error(errors.first().message)
        } else {
            Resource.Success(
                result.filterIsInstance<Resource.Success<ActorsModel>>()
                    .map { it.data }
            )
        }
    }

    private suspend fun requestActorsDetails(name: String): Resource<ActorsModel> {
        return try {
            val response = networkClient.actorsDetailsApiService.getActorsDetails(
                apikey = API_KEY_TMDB,
                name = name
            )
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                Resource.Success(responseBody)
            } else {
                Resource.Error(response.message(), responseBody)
            }
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    fun favoriteMovies(): LiveData<List<MovieEntity>> = moviesDao.getAll()

    suspend fun insertToSaved(movie: MovieEntity) {
        moviesDao.insertAll(movie)
    }

    suspend fun deleteFromDB(movie: MovieEntity) {
        withContext(IO) {
            moviesDao.delete(movie = movie)
        }
    }

    suspend fun getMovieById(imdbId: String): MovieEntity =
        moviesDao.getMovieById(imdbID = imdbId)

    suspend fun checkMovieInDB(imdbId: String): Boolean {
        return moviesDao.checkMovieInDb(imdbId)
    }

    companion object {
        private const val API_KEY_OMDB = "8abb53bf"
        private const val API_KEY_TMDB = "a0138d03454feb065cf883ee60805f71"
    }
}