package com.example.catapp.data

import com.example.catapp.domain.models.CatBreed
import com.example.catapp.domain.models.CatBreedSearch
import com.example.catapp.domain.models.CatImage
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CatInterface {

    @Headers("x-api-key: ${Endpoints.apiKey}")
    @GET("images/search?")
    suspend fun getCatImages(@Query("limit") limit: Int = 21): List<CatImage>

    @Headers("x-api-key: ${Endpoints.apiKey}")
    @GET("breeds?")
    suspend fun getCatBreeds(@Query("limit") limit: Int = 21): List<CatBreed>

    @Headers("x-api-key: ${Endpoints.apiKey}")
    @GET("breeds/search?")
    suspend fun searchCatBreed(@Query("q") q: String): List<CatBreedSearch>

    @GET("images/search")
    suspend fun getRandomCatImage(): List<CatImage>

    @GET("images/search")
    suspend fun getRandomCatImageList(@Query("limit") limit: Int = 10): List<CatImage>
}
