package com.example.movieapp.data.repository

import com.example.movieapp.domain.model.ActorsModel
import com.example.movieapp.domain.model.MovieDetailsModel
import com.example.movieapp.domain.utils.Resource

interface NetworkRepositoryImpl {

    suspend fun getSearchedMovies(searchedText: String): Resource<List<MovieDetailsModel>>

    suspend fun getMovieDetails(id: String): Resource<MovieDetailsModel>

    suspend fun getActorsDetails(actors: String): Resource<List<ActorsModel>>

}