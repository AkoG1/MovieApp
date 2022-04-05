package com.example.movieapp.domain.use_cases.get_actors_details

import com.example.movieapp.domain.model.ActorsModel
import com.example.movieapp.domain.utils.Resource

interface GetActorsDetailsUseCase {
    suspend fun getActorsDetails(actors: String): Resource<List<ActorsModel>>
}