package com.example.movieapp.di

import com.example.movieapp.data.mappers.*
import com.example.movieapp.data.network.NetworkClient
import com.example.movieapp.data.repository.MovieDbRepositoryImpl
import com.example.movieapp.data.repository.MovieNetworkRepositoryImpl
import com.example.movieapp.domain.use_cases.get_actors_details.GetActorsDetailsUseCaseImpl
import com.example.movieapp.domain.use_cases.get_movie_details.GetMovieDetailsUseCaseImpl
import com.example.movieapp.domain.use_cases.get_searched_movies.GetSearchedMoviesUseCaseImpl
import com.example.movieapp.presentation.ui.favorites.vm.FavoritesViewModel
import com.example.movieapp.presentation.ui.movieDetails.vm.MovieDetailsViewModel
import com.example.movieapp.presentation.ui.movies.vm.MoviesViewModel
import com.example.movieapp.data.room.AppDatabase
import com.example.movieapp.domain.repository.MovieDbRepository
import com.example.movieapp.domain.repository.MovieNetworkRepository
import com.example.movieapp.domain.use_cases.check_movie_in_db.CheckMovieInDbUseCase
import com.example.movieapp.domain.use_cases.check_movie_in_db.CheckMovieInDbUseCaseImpl
import com.example.movieapp.domain.use_cases.delete_favorite_movie.DeleteMovieFromDbUseCase
import com.example.movieapp.domain.use_cases.delete_favorite_movie.DeleteMovieFromDbUseCaseImpl
import com.example.movieapp.domain.use_cases.get_actors_details.GetActorsDetailsUseCase
import com.example.movieapp.domain.use_cases.get_favorite_movies.GetFavoriteMoviesUseCase
import com.example.movieapp.domain.use_cases.get_favorite_movies.GetFavoriteMoviesUseCaseImpl
import com.example.movieapp.domain.use_cases.get_movie_details.GetMovieDetailsUseCase
import com.example.movieapp.domain.use_cases.get_movie_from_db.GetMovieFromDbUseCase
import com.example.movieapp.domain.use_cases.get_movie_from_db.GetMovieFromDbUseCaseImpl
import com.example.movieapp.domain.use_cases.get_searched_movies.GetSearchedMoviesUseCase
import com.example.movieapp.domain.use_cases.request_movie_trailer.RequestMovieTrailerUseCase
import com.example.movieapp.domain.use_cases.request_movie_trailer.RequestMovieTrailerUseCaseImpl
import com.example.movieapp.domain.use_cases.save_in_db.SaveInDbUseCase
import com.example.movieapp.domain.use_cases.save_in_db.SaveInDbUseCaseImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    single { NetworkClient }

    factory<MovieDbRepository> { MovieDbRepositoryImpl(get()) }

    factory<MovieNetworkRepository> { MovieNetworkRepositoryImpl(get(), get(), get(), get(), get(), get()) }
}

val viewModels = module {

    viewModel { MoviesViewModel(get()) }
    viewModel { MovieDetailsViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { FavoritesViewModel(get(), get()) }
}

val useCases = module {

    factory<SaveInDbUseCase> { SaveInDbUseCaseImpl(get()) }
    factory<GetSearchedMoviesUseCase> { GetSearchedMoviesUseCaseImpl(get()) }
    factory<GetActorsDetailsUseCase> { GetActorsDetailsUseCaseImpl(get()) }
    factory<GetMovieDetailsUseCase> { GetMovieDetailsUseCaseImpl(get()) }
    factory<GetMovieFromDbUseCase> { GetMovieFromDbUseCaseImpl(get()) }
    factory<CheckMovieInDbUseCase> { CheckMovieInDbUseCaseImpl(get()) }
    factory<GetFavoriteMoviesUseCase> { GetFavoriteMoviesUseCaseImpl(get()) }
    factory<DeleteMovieFromDbUseCase> { DeleteMovieFromDbUseCaseImpl(get()) }
    factory<RequestMovieTrailerUseCase> { RequestMovieTrailerUseCaseImpl(get()) }

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
    single { MovieExternalIdMapper() }
    single { MovieTrailerResultMapper() }
    single { MovieTrailerMapper(get()) }

}