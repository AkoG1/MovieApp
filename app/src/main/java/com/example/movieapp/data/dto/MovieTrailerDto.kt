package com.example.movieapp.data.dto

import com.google.gson.annotations.SerializedName

data class MovieTrailerDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("results")
    val results: List<MovieTrailerResultDto>?
)