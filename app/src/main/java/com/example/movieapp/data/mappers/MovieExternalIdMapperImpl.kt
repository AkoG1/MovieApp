package com.example.movieapp.data.mappers

import com.example.movieapp.data.dto.MovieExternalIdDto
import com.example.movieapp.domain.model.MovieExternalId

class MovieExternalIdMapperImpl :
    Mapper<MovieExternalIdDto, MovieExternalId> {
    override fun mapModel(model: MovieExternalIdDto): MovieExternalId {
        return MovieExternalId(model.id)
    }
}