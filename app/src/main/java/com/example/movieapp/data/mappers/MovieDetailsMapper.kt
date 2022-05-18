package com.example.movieapp.data.mappers

import com.example.movieapp.data.dto.MovieDetailsResponseDto
import com.example.movieapp.domain.model.MovieDetailsModel

class MovieDetailsMapper(): Mapper<MovieDetailsResponseDto, MovieDetailsModel> {
    override fun mapModel(model: MovieDetailsResponseDto): MovieDetailsModel {
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
                type = type,
                year = year
            )
        }
    }
}