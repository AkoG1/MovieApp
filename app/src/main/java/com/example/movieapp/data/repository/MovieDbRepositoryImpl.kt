package com.example.movieapp.data.repository

import com.example.movieapp.data.room.dao.MoviesDao
import com.example.movieapp.data.room.entity.MovieEntity
import com.example.movieapp.domain.repository.MovieDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MovieDbRepositoryImpl(private val moviesDao: MoviesDao) :
    MovieDbRepository {

    override fun favoriteMovies(): Flow<List<MovieEntity>> = moviesDao.getAll()

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