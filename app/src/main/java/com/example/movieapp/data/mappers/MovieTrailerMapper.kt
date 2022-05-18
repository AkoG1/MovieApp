package com.example.movieapp.data.mappers

import com.example.movieapp.data.dto.MovieTrailerDto
import com.example.movieapp.domain.model.MovieTrailer

class MovieTrailerMapper(private val movieTrailerResultMapper: MovieTrailerResultMapper): Mapper<MovieTrailerDto, MovieTrailer> {
    override fun mapModel(model: MovieTrailerDto): MovieTrailer {
        return MovieTrailer(
            result = movieTrailerResultMapper.mapToNullableList(model.results)
        )
    }
}