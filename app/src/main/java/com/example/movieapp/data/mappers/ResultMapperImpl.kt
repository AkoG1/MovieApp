package com.example.movieapp.data.mappers

import com.example.movieapp.data.dto.ResultDto
import com.example.movieapp.domain.model.Result

class ResultMapperImpl(): Mapper<ResultDto, Result> {
    override fun mapModel(model: ResultDto): Result {
        return Result(
            name = model.name,
            profilePath = model.profilePath
        )
    }
}