package com.example.catapp.presentation.home.model

import com.example.catapp.domain.models.CatImage

data class RandomCatImageListResponse(
    val imagesData: List<CatImage>? = null,
    val error: Exception? = null,
    val isLoading: Boolean = false
)
