package com.example.movieapp.data.mappers

import com.example.movieapp.data.dto.MovieTrailerResultDto
import com.example.movieapp.domain.model.MovieTrailerResult

class MovieTrailerResultMapperImpl : Mapper<MovieTrailerResultDto, MovieTrailerResult> {
    override fun mapModel(model: MovieTrailerResultDto): MovieTrailerResult {
        return MovieTrailerResult(model.key, model.type)
    }
}