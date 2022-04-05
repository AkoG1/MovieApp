package com.example.movieapp.data.repository

import androidx.lifecycle.LiveData
import com.example.movieapp.data.room.entity.MovieEntity

interface DbRepositoryImpl {

    fun favoriteMovies(): LiveData<List<MovieEntity>>

    suspend fun insertToSaved(movie: MovieEntity)

    suspend fun deleteFromDB(movie: MovieEntity)

    suspend fun getMovieById(imdbId: String): MovieEntity

    suspend fun checkMovieInDB(imdbId: String): Boolean

}