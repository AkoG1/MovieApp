package com.example.movieapp.domain.use_cases.request_movie_trailer

import com.example.movieapp.domain.model.MovieTrailer
import com.example.movieapp.domain.repository.MovieNetworkRepository
import com.example.movieapp.domain.utils.Resource

class RequestMovieTrailerUseCaseImpl(private val repositoryMovie: MovieNetworkRepository): RequestMovieTrailerUseCase {
    override suspend fun requestMovieTrailer(imdbId: String): Resource<MovieTrailer> {
        return repositoryMovie.requestMovieTrailer(imdbId = imdbId)
    }

}