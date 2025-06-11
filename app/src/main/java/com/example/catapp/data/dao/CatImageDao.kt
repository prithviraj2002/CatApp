package com.example.catapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.catapp.domain.models.DbModels.CatImageEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@Dao
interface CatImageDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveImage(catImage: CatImageEntity)

    @Delete
    suspend fun removeFromFav(catImage: CatImageEntity)

    @Query("SELECT * FROM catImages")
    fun getCatImages(): Flow<List<CatImageEntity>>
}