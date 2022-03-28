package com.example.movieapp.model

import com.google.gson.annotations.SerializedName

data class ActorsModel(
    @SerializedName("results")
    val results: List<Result>?
) {
    data class Result(
        @SerializedName("adult")
        val adult: Boolean?,
        @SerializedName("gender")
        val gender: Int?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("known_for_department")
        val knownForDepartment: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("popularity")
        val popularity: Double?,
        @SerializedName("profile_path")
        val profilePath: String?
    )
}