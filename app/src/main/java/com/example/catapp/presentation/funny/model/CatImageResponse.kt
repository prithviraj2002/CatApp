package com.example.catapp.presentation.funny.model

import com.example.catapp.domain.models.CatImage

data class CatImageResponse(
    val imageData: List<CatImage> = emptyList(),
    val error: Exception? = null,
    val isLoading: Boolean = false
)
