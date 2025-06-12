package com.example.catapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.catapp.domain.models.DbModels.CatBreedEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@Dao
interface CatBreedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCatBreed(catBreed: CatBreedEntity)

    @Query("DELETE FROM CATBREEDS WHERE CATBREEDID = :catBreedId")
    suspend fun delCatBreed(catBreedId: String)

    @Query("SELECT * FROM catBreeds")
    fun getSavedBreeds(): Flow<List<CatBreedEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM catBreeds WHERE CATBREEDID = :breedId)")
    fun isBreedSaved(breedId: String): Flow<Boolean>
}