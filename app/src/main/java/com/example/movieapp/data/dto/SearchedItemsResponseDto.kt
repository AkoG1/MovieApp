package com.example.movieapp.data.dto

import com.google.gson.annotations.SerializedName

data class SearchedItemsResponseDto(
    @SerializedName("Response")
    val response: String?,
    @SerializedName("Search")
    val search: List<SearchDto>?,
    @SerializedName("totalResults")
    val totalResults: String?
)