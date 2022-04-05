package com.example.movieapp.data.mappers

import com.example.movieapp.data.dto.ActorsModelDto
import com.example.movieapp.domain.model.ActorsModel

class ActorsModelMapper(private val resultMapper: ResultMapper) : Mapper<ActorsModelDto, ActorsModel> {
    override fun mapModel(model: ActorsModelDto): ActorsModel {
        with(model) {
            return ActorsModel(
                resultMapper.mapToNullableList(resultDtos)
            )
        }
    }
}