package com.example.movieapp.data.room.dao

import androidx.room.*
import com.example.movieapp.data.room.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movieDetails")
    fun getAll(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movieDetails WHERE imdbID = :imdbID")
    suspend fun getMovieById(imdbID: String) : MovieEntity

    @Query("SELECT exists(SELECT * FROM movieDetails WHERE imdbID == :imdbID)")
    suspend fun checkMovieInDb(imdbID: String) : Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg movie: MovieEntity)

    @Delete
    fun delete(movie: MovieEntity)

}