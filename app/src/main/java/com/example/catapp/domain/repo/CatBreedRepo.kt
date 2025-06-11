package com.example.catapp.domain.repo

import android.util.Log
import com.example.catapp.data.dao.CatBreedDao
import com.example.catapp.domain.models.DbModels.CatBreedEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatBreedRepo @Inject constructor(private val catBreedDao: CatBreedDao) {
    fun getSavedBreeds(): Flow<List<CatBreedEntity>> = catBreedDao.getSavedBreeds()

    suspend fun saveBreed(catBreedEntity: CatBreedEntity) {
        catBreedDao.saveCatBreed(catBreedEntity)
        Log.e("Saved breed:", catBreedEntity.breedId)
    }

    suspend fun removeBreed(catBreedEntity: CatBreedEntity) {
        catBreedDao.delCatBreed(catBreedEntity)
    }
}