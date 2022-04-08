package com.example.movieapp.domain.use_cases.get_searched_movies

import com.example.movieapp.domain.model.MovieDetailsModel
import com.example.movieapp.domain.utils.Resource

interface GetSearchedMoviesUseCase {
    suspend fun getSearchedMovies(searchedText: String): Resource<List<MovieDetailsModel>>
}