package com.example.catapp.presentation.fav.model

import com.example.catapp.domain.models.DbModels.CatImageEntity

data class CatImageEntityResponse(
    val imageData: List<CatImageEntity> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false
)
