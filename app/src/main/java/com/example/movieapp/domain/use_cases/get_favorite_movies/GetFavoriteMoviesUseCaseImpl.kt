package com.example.movieapp.domain.use_cases.get_favorite_movies

import com.example.movieapp.data.room.entity.MovieEntity
import com.example.movieapp.domain.repository.MovieDbRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteMoviesUseCaseImpl(private val repositoryMovie: MovieDbRepository): GetFavoriteMoviesUseCase {
    override fun favoriteMovies(): Flow<List<MovieEntity>> {
        return repositoryMovie.favoriteMovies()
    }

}