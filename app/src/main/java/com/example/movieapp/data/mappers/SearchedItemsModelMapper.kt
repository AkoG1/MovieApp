package com.example.movieapp.data.mappers

import com.example.movieapp.data.dto.SearchedItemsModelDto
import com.example.movieapp.domain.model.SearchedItemsModel

class SearchedItemsModelMapper(private val searchMapper: SearchMapper): Mapper<SearchedItemsModelDto, SearchedItemsModel> {
    override fun mapModel(model: SearchedItemsModelDto): SearchedItemsModel {
        with(model) {
            return SearchedItemsModel(
                search = searchMapper.mapToNullableList(search)
            )
        }
    }
}