package com.example.movieapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {

    private const val BASE_URL_OMDB_API = "https://www.omdbapi.com"

    private fun retrofitBuild() = Retrofit.Builder()

    private fun provideGson() = retrofitBuild().addConverterFactory(GsonConverterFactory.create())

    val searchedItemsApiService: SearchedItemsApiService =
        provideGson().baseUrl(BASE_URL_OMDB_API).build().create(SearchedItemsApiService::class.java)

}