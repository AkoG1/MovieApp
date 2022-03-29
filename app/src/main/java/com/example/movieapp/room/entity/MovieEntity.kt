package com.example.movieapp.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieDetails")
data class MovieEntity(
    @ColumnInfo(name = "actors") val actors: String?,
    @ColumnInfo(name = "country") val country: String?,
    @ColumnInfo(name = "director") val director: String?,
    @ColumnInfo(name = "genre") val genre: String?,
    @PrimaryKey val imdbID: String,
    @ColumnInfo(name = "imdbRating") val imdbRating: String?,
    @ColumnInfo(name = "imdbVotes") val imdbVotes: String?,
    @ColumnInfo(name = "language") val language: String?,
    @ColumnInfo(name = "poster") val poster: String?,
    @ColumnInfo(name = "plot") val plot: String?,
    @ColumnInfo(name = "rated") val rated: String?,
    @ColumnInfo(name = "released") val released: String?,
    @ColumnInfo(name = "runtime") val runtime: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "year") val year: String?
)