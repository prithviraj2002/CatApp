package com.example.catapp.di

import com.example.catapp.data.CatInterface
import com.example.catapp.data.Endpoints
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun getCatInterface(): CatInterface{
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(
            Endpoints.baseUrl).build().create(CatInterface::class.java)
    }
}