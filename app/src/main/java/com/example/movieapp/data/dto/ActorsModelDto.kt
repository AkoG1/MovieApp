package com.example.movieapp.data.dto

import com.google.gson.annotations.SerializedName

data class ActorsModelDto(
    @SerializedName("results")
    val resultDtos: List<ResultDto>?
)