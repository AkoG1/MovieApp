package com.example.movieapp.domain.repository

import com.example.movieapp.data.room.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieDbRepository {

    fun favoriteMovies(): Flow<List<MovieEntity>>

    suspend fun insertToSaved(movie: MovieEntity)

    suspend fun deleteFromDB(movie: MovieEntity)

    suspend fun getMovieById(imdbId: String): MovieEntity

    suspend fun checkMovieInDB(imdbId: String): Boolean

}