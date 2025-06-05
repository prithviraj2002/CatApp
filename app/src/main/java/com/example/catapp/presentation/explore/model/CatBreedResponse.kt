package com.example.catapp.presentation.explore.model

import com.example.catapp.domain.models.CatBreed

data class CatBreedResponse(
    val data: List<CatBreed> = emptyList(),
    val e: Exception? = null,
    val isLoading: Boolean = false
)
