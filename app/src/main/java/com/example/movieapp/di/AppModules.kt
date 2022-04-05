package com.example.movieapp.di

import com.example.movieapp.data.mappers.*
import com.example.movieapp.data.network.NetworkClient
import com.example.movieapp.domain.repository.DbRepository
import com.example.movieapp.domain.repository.NetworkRepository
import com.example.movieapp.domain.use_cases.get_actors_details.GetActorsDetailsUseCaseImpl
import com.example.movieapp.domain.use_cases.get_movie_details.GetMovieDetailsUseCaseImpl
import com.example.movieapp.domain.use_cases.get_searched_movies.GetSearchedMoviesUseCaseImpl
import com.example.movieapp.presentation.ui.favorites.vm.FavoritesViewModel
import com.example.movieapp.presentation.ui.movieDetails.vm.MovieDetailsViewModel
import com.example.movieapp.presentation.ui.movies.vm.MoviesViewModel
import com.example.movieapp.data.room.AppDatabase
import com.example.movieapp.domain.use_cases.check_movie_in_db.CheckMovieInDbUseCaseImpl
import com.example.movieapp.domain.use_cases.delete_favorite_movie.DeleteMovieFromDbUseCaseImpl
import com.example.movieapp.domain.use_cases.get_favorite_movies.GetFavoriteMoviesUseCaseImpl
import com.example.movieapp.domain.use_cases.get_movie_from_db.GetMovieFromDbUseCaseImpl
import com.example.movieapp.domain.use_cases.save_in_db.SaveInDbUseCaseImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    single { NetworkClient }

    factory { DbRepository(get()) }

    factory { NetworkRepository(get(), get(), get(), get()) }
}


val viewModels = module {
    viewModel { MoviesViewModel(get()) }
    viewModel { MovieDetailsViewModel(get(), get(), get(), get(), get()) }
    viewModel { FavoritesViewModel(get(), get()) }
}

val useCases = module {
    factory { SaveInDbUseCaseImpl(get()) }
    factory { GetSearchedMoviesUseCaseImpl(get()) }
    factory { GetActorsDetailsUseCaseImpl(get()) }
    factory { GetMovieDetailsUseCaseImpl(get()) }
    factory { GetMovieFromDbUseCaseImpl(get()) }
    factory { CheckMovieInDbUseCaseImpl(get()) }
    factory { GetFavoriteMoviesUseCaseImpl(get()) }
    factory { DeleteMovieFromDbUseCaseImpl(get()) }
}

val database = module {
    single { AppDatabase.getInstance(androidContext()).moviesDao }
}

val mappers = module {

    single { ResultMapper() }
    single { ActorsModelMapper(get()) }
    single { MovieDetailsMapper() }
    single { SearchedItemsModelMapper(get()) }
    single { SearchMapper() }
}