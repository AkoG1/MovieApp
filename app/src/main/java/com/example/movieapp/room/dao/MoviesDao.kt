package com.example.movieapp.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.movieapp.room.entity.MovieEntity

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movieDetails")
    fun getAll(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM movieDetails WHERE imdbID = :imdbID")
    suspend fun getMovieById(imdbID: String) : MovieEntity

    @Query("SELECT exists(SELECT * FROM movieDetails WHERE imdbID == :imdbID)")
    suspend fun checkMovieInDb(imdbID: String) : Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg movie: MovieEntity)

    @Delete
    fun delete(movie: MovieEntity)

}