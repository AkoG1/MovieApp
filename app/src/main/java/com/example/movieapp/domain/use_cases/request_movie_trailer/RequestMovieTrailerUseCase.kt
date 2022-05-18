package com.example.movieapp.domain.use_cases.request_movie_trailer

import com.example.movieapp.domain.model.MovieTrailer
import com.example.movieapp.domain.utils.Resource

interface RequestMovieTrailerUseCase {
    suspend fun requestMovieTrailer(imdbId: String): Resource<MovieTrailer>
}