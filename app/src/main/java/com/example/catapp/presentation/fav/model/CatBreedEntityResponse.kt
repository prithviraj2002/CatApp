package com.example.catapp.presentation.fav.model

import com.example.catapp.domain.models.CatBreed
import com.example.catapp.domain.models.DbModels.CatBreedEntity

data class CatBreedEntityResponse(
    val catBreedData: List<CatBreed> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false
)
