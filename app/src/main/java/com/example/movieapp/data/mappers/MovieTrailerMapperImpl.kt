package com.example.movieapp.data.mappers

import com.example.movieapp.data.dto.MovieTrailerDto
import com.example.movieapp.domain.model.MovieTrailer

class MovieTrailerMapperImpl(private val movieTrailerResultMapper: MovieTrailerResultMapperImpl): Mapper<MovieTrailerDto, MovieTrailer> {
    override fun mapModel(model: MovieTrailerDto): MovieTrailer {
        return MovieTrailer(
            result = movieTrailerResultMapper.mapToNullableList(model.results)
        )
    }
}