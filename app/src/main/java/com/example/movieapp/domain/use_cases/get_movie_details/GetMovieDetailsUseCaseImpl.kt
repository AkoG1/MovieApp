package com.example.movieapp.domain.use_cases.get_movie_details

import com.example.movieapp.domain.model.MovieDetailsModel
import com.example.movieapp.domain.repository.MovieNetworkRepository
import com.example.movieapp.domain.utils.Resource

class GetMovieDetailsUseCaseImpl(private val repositoryMovie: MovieNetworkRepository): GetMovieDetailsUseCase {
    override suspend fun getMovieDetails(id: String): Resource<MovieDetailsModel> {
        return repositoryMovie.getMovieDetails(id)
    }
}