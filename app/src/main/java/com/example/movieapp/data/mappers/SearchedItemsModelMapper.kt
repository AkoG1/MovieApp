package com.example.movieapp.data.mappers

import com.example.movieapp.data.dto.SearchedItemsResponseDto
import com.example.movieapp.domain.model.SearchedItemsModel

class SearchedItemsModelMapper(private val searchMapper: SearchMapper): Mapper<SearchedItemsResponseDto, SearchedItemsModel> {
    override fun mapModel(model: SearchedItemsResponseDto): SearchedItemsModel {
        with(model) {
            return SearchedItemsModel(
                search = searchMapper.mapToNullableList(search)
            )
        }
    }
}