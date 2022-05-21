package com.example.movieapp.domain.repository

import com.example.movieapp.domain.model.ActorsModel
import com.example.movieapp.domain.model.MovieDetailsModel
import com.example.movieapp.domain.model.MovieTrailer
import com.example.movieapp.domain.utils.Resource

interface MovieNetworkRepository {

    suspend fun getSearchedMovies(searchedText: String): Resource<List<MovieDetailsModel>>

    suspend fun getMovieDetails(id: String): Resource<MovieDetailsModel>

    suspend fun getActorsDetails(actors: String): Resource<List<ActorsModel>>

    suspend fun requestMovieTrailer(imdbId: String): Resource<MovieTrailer>

}