package com.example.catapp.presentation.explore.model

import com.example.catapp.domain.models.CatBreedSearch

data class CatBreedSearchResponse(
    val data: List<CatBreedSearch> = emptyList(),
    val e: Exception? = null,
    val isLoading : Boolean = false
)
