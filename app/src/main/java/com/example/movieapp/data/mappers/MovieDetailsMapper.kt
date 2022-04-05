package com.example.movieapp.data.mappers

import com.example.movieapp.data.dto.MovieDetailsModelDto
import com.example.movieapp.domain.model.MovieDetailsModel

class MovieDetailsMapper(): Mapper<MovieDetailsModelDto, MovieDetailsModel> {
    override fun mapModel(model: MovieDetailsModelDto): MovieDetailsModel {
        with(model) {
            return MovieDetailsModel(
                actors = actors,
                genre = genre,
                imdbID = imdbID,
                imdbRating = imdbRating,
                language = language,
                plot = plot,
                poster = poster,
                rated = rated,
                released = released,
                runtime = runtime,
                title = title,
                year = year
            )
        }
    }
}