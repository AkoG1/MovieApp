package com.example.movieapp.data.dto

import com.google.gson.annotations.SerializedName

data class MovieExternalIdDto(
    @SerializedName("facebook_id")
    val facebookId: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("instagram_id")
    val instagramId: Any?,
    @SerializedName("twitter_id")
    val twitterId: Any?
)