package com.example.movieapp.data.mappers

interface Mapper<in MODEL_A, out MODEL_B> {

    fun mapModel(model: MODEL_A): MODEL_B

    fun mapToList(modelList: List<MODEL_A>): List<MODEL_B> {
        return modelList.map { mapModel(it) }
    }

    fun mapToNullableList(modelList: List<MODEL_A>?): List<MODEL_B>? {
        return modelList?.let {
            it.map { model -> mapModel(model) }
        }
    }

}