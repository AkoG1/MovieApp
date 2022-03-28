package com.example.movieapp.model


import com.google.gson.annotations.SerializedName

data class MovieDetailsModel(
    @SerializedName("Actors")
    val actors: String?,
    @SerializedName("Country")
    val country: String?,
    @SerializedName("Director")
    val director: String?,
    @SerializedName("Genre")
    val genre: String?,
    @SerializedName("imdbID")
    val imdbID: String,
    @SerializedName("imdbRating")
    val imdbRating: String?,
    @SerializedName("imdbVotes")
    val imdbVotes: String?,
    @SerializedName("Language")
    val language: String?,
    @SerializedName("Poster")
    val poster: String?,
    @SerializedName("Plot")
    val plot: String?,
    @SerializedName("Rated")
    val rated: String?,
    @SerializedName("Ratings")
    val ratings: List<Rating>?,
    @SerializedName("Released")
    val released: String?,
    @SerializedName("Runtime")
    val runtime: String?,
    @SerializedName("Title")
    val title: String?,
    @SerializedName("Type")
    val type: String?,
    @SerializedName("Year")
    val year: String?
) {
    data class Rating(
        @SerializedName("Source")
        val source: String?,
        @SerializedName("Value")
        val value: String?
    )
}