package com.example.movieapp.domain.use_cases.get_movie_from_db

import com.example.movieapp.data.room.entity.MovieEntity

interface GetMovieFromDbUseCase {

    suspend fun getMovieById(imdbId: String): MovieEntity

}