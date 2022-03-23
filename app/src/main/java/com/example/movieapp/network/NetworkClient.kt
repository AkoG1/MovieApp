package com.example.movieapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {

    private const val BASE_URL_OMDB_API = "https://www.omdbapi.com"

    private const val BASE_URL_TMDB_API = "https://image.tmdb.org/"

    private fun retrofitBuild() = Retrofit.Builder()

    private fun provideGson() = retrofitBuild().addConverterFactory(GsonConverterFactory.create())

    val searchedItemsApiService: SearchedItemsApiService =
        provideGson().baseUrl(BASE_URL_OMDB_API).build().create(SearchedItemsApiService::class.java)

    val movieDetailsApiService: MovieDetailsApiService =
        provideGson().baseUrl(BASE_URL_OMDB_API).build().create(MovieDetailsApiService::class.java)

    val actorsDetailsApiService: ActorsDetailsApiService =
        provideGson().baseUrl(BASE_URL_TMDB_API).build().create(ActorsDetailsApiService::class.java)

}