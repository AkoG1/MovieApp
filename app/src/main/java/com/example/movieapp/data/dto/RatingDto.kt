package com.example.movieapp.data.dto


import com.google.gson.annotations.SerializedName

data class RatingDto(
    @SerializedName("Source")
    val source: String?,
    @SerializedName("Value")
    val value: String?
)