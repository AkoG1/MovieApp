package com.example.movieapp.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {

    private const val BASE_URL_OMDB_API = "https://www.omdbapi.com"

    private const val BASE_URL_TMDB_API = "https://api.themoviedb.org"

    private fun retrofitBuild() = Retrofit.Builder()

    private fun provideGson() = retrofitBuild().addConverterFactory(GsonConverterFactory.create())

    val searchedItemsApiService: SearchedItemsApiService =
        provideGson().baseUrl(BASE_URL_OMDB_API).build().create(SearchedItemsApiService::class.java)

    val actorsDetailsApiService: ActorsDetailsApiService =
        provideGson().baseUrl(BASE_URL_TMDB_API).build().create(ActorsDetailsApiService::class.java)

    val movieExternalIdApiService: MovieExternalIdApiService =
        provideGson().baseUrl(BASE_URL_TMDB_API).build().create(MovieExternalIdApiService:: class.java)

    val movieTrailerApiService: MovieTrailerApiService =
        provideGson().baseUrl(BASE_URL_TMDB_API).build().create(MovieTrailerApiService:: class.java)

}