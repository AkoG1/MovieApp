package com.example.movieapp.di

import com.example.movieapp.network.NetworkClient
import com.example.movieapp.repository.Repository
import com.example.movieapp.room.AppDatabase
import com.example.movieapp.ui.favorites.vm.FavoritesViewModel
import com.example.movieapp.ui.movieDetails.vm.MovieDetailsViewModel
import com.example.movieapp.ui.movies.vm.MoviesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    single { NetworkClient }

    factory { Repository(get(), get()) }
}

val viewModels = module {
    viewModel { MoviesViewModel(get()) }
    viewModel { MovieDetailsViewModel(get()) }
    viewModel { FavoritesViewModel(get()) }
}

val database = module {

    single { AppDatabase.getInstance(androidContext()).moviesDao }

}