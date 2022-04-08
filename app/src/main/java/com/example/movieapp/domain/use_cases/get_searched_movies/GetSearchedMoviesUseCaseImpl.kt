package com.example.movieapp.domain.use_cases.get_searched_movies

import com.example.movieapp.domain.model.MovieDetailsModel
import com.example.movieapp.domain.repository.MovieNetworkRepository
import com.example.movieapp.domain.utils.Resource

class GetSearchedMoviesUseCaseImpl(private val repositoryMovie: MovieNetworkRepository): GetSearchedMoviesUseCase {
    override suspend fun getSearchedMovies(searchedText: String): Resource<List<MovieDetailsModel>> {
        return repositoryMovie.getSearchedMovies(searchedText)
    }
}