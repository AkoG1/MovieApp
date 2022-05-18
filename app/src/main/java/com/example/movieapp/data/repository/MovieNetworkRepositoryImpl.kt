package com.example.movieapp.data.repository

import com.example.movieapp.data.dto.ActorsResponseDto
import com.example.movieapp.data.mappers.*
import com.example.movieapp.data.network.NetworkClient
import com.example.movieapp.domain.model.*
import com.example.movieapp.domain.repository.MovieNetworkRepository
import com.example.movieapp.domain.utils.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class MovieNetworkRepositoryImpl(
    private val networkClient: NetworkClient,
    private val movieDetailsMapper: MovieDetailsMapper,
    private val actorsModelMapper: ActorsModelMapper,
    private val searchedItemsModelMapper: SearchedItemsModelMapper,
    private val movieExternalIdMapper: MovieExternalIdMapper,
    private val movieTrailerMapper: MovieTrailerMapper
) : MovieNetworkRepository {

    override suspend fun getSearchedMovies(searchedText: String): Resource<List<MovieDetailsModel>> {
        return try {
            val response = networkClient.searchedItemsApiService.searchMovies(
                apikey = API_KEY_OMDB,
                searchedText = searchedText
            )
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null && !responseBody.search.isNullOrEmpty()) {
                val search = searchedItemsModelMapper.mapModel(responseBody)
                getDetailedMovies(search.search!!)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    private suspend fun getDetailedMovies(
        itemsDto: List<Search>
    ): Resource<List<MovieDetailsModel>> {
        val result = itemsDto.map { item ->
            coroutineScope {
                async { getMovieDetails(item.imdbID) }
            }
        }.awaitAll()

        val errors = result.filterIsInstance<Resource.Error<MovieDetailsModel>>()

        return if (errors.isNotEmpty()) {
            Resource.Error(errors.first().message)
        } else {
            Resource.Success(
                result.filterIsInstance<Resource.Success<MovieDetailsModel>>()
                    .map { it.data }
            )
        }
    }

    override suspend fun getMovieDetails(id: String): Resource<MovieDetailsModel> {
        return try {
            val response = networkClient.searchedItemsApiService.getMovieDetails(
                apikey = API_KEY_OMDB,
                id = id
            )
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                val movieDetails: MovieDetailsModel = movieDetailsMapper.mapModel(responseBody)
                Resource.Success(movieDetails)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    override suspend fun getActorsDetails(actors: String): Resource<List<ActorsModel>> {
        val listedNames = actors.split(COMMA).map { it.trim() }
        val result = listedNames.map { actor ->
            coroutineScope {
                async { requestActorsDetails(actor) }
            }
        }.awaitAll()

        val errors = result.filterIsInstance<Resource.Error<ActorsResponseDto>>()

        return if (errors.isNotEmpty()) {
            Resource.Error(errors.first().message)
        } else {
            Resource.Success(
                result.filterIsInstance<Resource.Success<ActorsModel>>()
                    .map { it.data }
            )
        }
    }

    private suspend fun requestActorsDetails(name: String): Resource<ActorsModel> {
        return try {
            val response = networkClient.actorsDetailsApiService.getActorsDetails(
                apikey = API_KEY_TMDB,
                name = name
            )
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                val actorsDetails = actorsModelMapper.mapModel(responseBody)
                Resource.Success(actorsDetails)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    private suspend fun getMovieExternalId(imdbId: String): Resource<MovieExternalId> {
        return try {
            val response =
                networkClient.movieExternalIdApiService.getMovieExternalId(
                    imdbId = imdbId,
                    apikey = API_KEY_TMDB
                )
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                val movieExternalId = movieExternalIdMapper.mapModel(responseBody)
                Resource.Success(movieExternalId)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    override suspend fun requestMovieTrailer(imdbId: String): Resource<MovieTrailer> {
        val externalId = coroutineScope {
            async { getMovieExternalId(imdbId) }
        }.await()

        return when (externalId) {
            is Resource.Success -> {
                val movieTrailer = coroutineScope {
                    async { getMovieTrailer(externalId.data.id.toString()) }
                }.await()
                movieTrailer
            }
            is Resource.Error -> {
                Resource.Error(externalId.message)
            }
            else -> {
                Resource.Error(TRAILER_NOT_AVAILABLE)
            }
        }
    }

    private suspend fun getMovieTrailer(imdbId: String): Resource<MovieTrailer> {
        return try {
            val response =
                networkClient.movieTrailerApiService.getMovieTrailer(
                    id = imdbId,
                    apikey = API_KEY_TMDB
                )
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                val movieTrailer = movieTrailerMapper.mapModel(responseBody)
                Resource.Success(movieTrailer)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    companion object {
        private const val API_KEY_OMDB = "8abb53bf"
        private const val API_KEY_TMDB = "a0138d03454feb065cf883ee60805f71"
        private const val COMMA = ","
        private const val TRAILER_NOT_AVAILABLE = "Trailer not available"
    }
}