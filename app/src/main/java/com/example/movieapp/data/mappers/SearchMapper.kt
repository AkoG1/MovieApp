package com.example.movieapp.data.mappers

import com.example.movieapp.data.dto.SearchDto
import com.example.movieapp.domain.model.Search

class SearchMapper: Mapper<SearchDto, Search> {
    override fun mapModel(model: SearchDto): Search {
        with(model) {
            return Search(
                imdbID = imdbID
            )
        }
    }
}