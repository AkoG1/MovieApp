package com.example.movieapp.data.mappers

import com.example.movieapp.data.dto.SearchedItemsResponseDto
import com.example.movieapp.domain.model.SearchedItemsModel

class SearchedItemsModelMapperImpl(private val searchMapperImpl: SearchMapperImpl): Mapper<SearchedItemsResponseDto, SearchedItemsModel> {
    override fun mapModel(model: SearchedItemsResponseDto): SearchedItemsModel {
        with(model) {
            return SearchedItemsModel(
                search = searchMapperImpl.mapToNullableList(search)
            )
        }
    }
}