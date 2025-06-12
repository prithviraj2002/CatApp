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

    @Query("DELETE FROM catImages WHERE CATIMAGEID = :imageId")
    suspend fun removeFromFav(imageId: String)

    @Query("SELECT * FROM catImages")
    fun getCatImages(): Flow<List<CatImageEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM catImages WHERE CATIMAGEID = :imageId)")
    fun isImageSaved(imageId: String): Flow<Boolean>
}