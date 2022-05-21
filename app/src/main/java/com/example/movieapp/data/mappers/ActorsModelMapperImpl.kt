package com.example.movieapp.data.mappers

import com.example.movieapp.data.dto.ActorsResponseDto
import com.example.movieapp.domain.model.ActorsModel

class ActorsModelMapperImpl(private val resultMapperImpl: ResultMapperImpl) :
    Mapper<ActorsResponseDto, ActorsModel> {
    override fun mapModel(model: ActorsResponseDto): ActorsModel {
        return ActorsModel(
            resultMapperImpl.mapToNullableList(model.resultDtos)
        )
    }
}