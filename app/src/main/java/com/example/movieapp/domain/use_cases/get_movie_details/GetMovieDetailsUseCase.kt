package com.example.movieapp.domain.use_cases.get_movie_details

import com.example.movieapp.domain.model.MovieDetailsModel
import com.example.movieapp.domain.utils.Resource

interface GetMovieDetailsUseCase {
    suspend fun getMovieDetails(id: String): Resource<MovieDetailsModel>
}