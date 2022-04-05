package com.example.movieapp.domain.use_cases.get_movie_details

import com.example.movieapp.domain.model.MovieDetailsModel
import com.example.movieapp.domain.repository.NetworkRepository
import com.example.movieapp.domain.utils.Resource

class GetMovieDetailsUseCaseImpl(private val repository: NetworkRepository): GetMovieDetailsUseCase {
    override suspend fun getMovieDetails(id: String): Resource<MovieDetailsModel> {
        return repository.getMovieDetails(id)
    }
}