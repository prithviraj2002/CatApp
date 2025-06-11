package com.example.catapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.catapp.data.CatInterface
import com.example.catapp.data.Endpoints
import com.example.catapp.data.dao.CatBreedDao
import com.example.catapp.data.dao.CatImageDao
import com.example.catapp.data.db.CatBreedDb
import com.example.catapp.data.db.CatImageDb
import com.example.catapp.domain.models.DbModels.CatBreedEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun getCatImageDb(@ApplicationContext context: Context): CatImageDb{
        return Room.databaseBuilder(
            context,
            CatImageDb::class.java,
            "cat_image_db"
        ).build();
    }

    @Provides
    @Singleton
    fun getCatImageDao(catImageDb: CatImageDb): CatImageDao{
        return catImageDb.getCatImageDao()
    }

    @Provides
    @Singleton
    fun getCatBreedDb(@ApplicationContext context: Context): CatBreedDb{
        return Room.databaseBuilder(
            context,
            CatBreedDb::class.java,
            "cat_breed_db"
        ).build()
    }

    @Provides
    @Singleton
    fun getCatBreedDao(catBreedDb: CatBreedDb): CatBreedDao{
        return catBreedDb.getCatBreedDao()
    }
}