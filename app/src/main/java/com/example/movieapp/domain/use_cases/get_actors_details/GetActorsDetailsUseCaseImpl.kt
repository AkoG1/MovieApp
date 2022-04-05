package com.example.movieapp.domain.use_cases.get_actors_details

import com.example.movieapp.domain.model.ActorsModel
import com.example.movieapp.domain.repository.NetworkRepository
import com.example.movieapp.domain.utils.Resource

class GetActorsDetailsUseCaseImpl(private val repository: NetworkRepository): GetActorsDetailsUseCase {
    override suspend fun getActorsDetails(actors: String): Resource<List<ActorsModel>> {
        return repository.getActorsDetails(actors)
    }

}