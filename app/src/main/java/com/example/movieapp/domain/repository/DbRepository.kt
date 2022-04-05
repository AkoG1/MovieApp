package com.example.movieapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.movieapp.data.repository.DbRepositoryImpl
import com.example.movieapp.data.room.dao.MoviesDao
import com.example.movieapp.data.room.entity.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DbRepository(private val moviesDao: MoviesDao) : DbRepositoryImpl{

    override fun favoriteMovies(): LiveData<List<MovieEntity>> = moviesDao.getAll()

    override suspend fun insertToSaved(movie: MovieEntity) {
        moviesDao.insertAll(movie)
    }

    override suspend fun deleteFromDB(movie: MovieEntity) {
        withContext(Dispatchers.IO) {
            moviesDao.delete(movie = movie)
        }
    }

    override suspend fun getMovieById(imdbId: String): MovieEntity =
        moviesDao.getMovieById(imdbID = imdbId)

    override suspend fun checkMovieInDB(imdbId: String): Boolean {
        return moviesDao.checkMovieInDb(imdbId)
    }

}