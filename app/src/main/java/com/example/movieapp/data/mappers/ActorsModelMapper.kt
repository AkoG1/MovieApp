package com.example.movieapp.data.mappers

import com.example.movieapp.data.dto.ActorsResponseDto
import com.example.movieapp.domain.model.ActorsModel

class ActorsModelMapper(private val resultMapper: ResultMapper) :
    Mapper<ActorsResponseDto, ActorsModel> {
    override fun mapModel(model: ActorsResponseDto): ActorsModel {
        return ActorsModel(
            resultMapper.mapToNullableList(model.resultDtos)
        )
    }
}