package com.example.catapp.domain.repo

import android.util.Log
import com.example.catapp.data.CatInterface
import com.example.catapp.data.dao.CatBreedDao
import com.example.catapp.domain.models.CatBreed
import com.example.catapp.domain.models.CatBreedSearch
import com.example.catapp.domain.models.DbModels.CatBreedEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatBreedRepo @Inject constructor(
    private val catBreedDao: CatBreedDao,
    private val catService: CatInterface
) {
    fun getSavedBreeds(): Flow<List<CatBreedEntity>> = catBreedDao.getSavedBreeds()

    suspend fun saveBreed(catBreedEntity: CatBreedEntity) {
        catBreedDao.saveCatBreed(catBreedEntity)
        Log.e("Saved breed:", catBreedEntity.breedId)
    }

    suspend fun removeBreed(catBreedId: String) {
        catBreedDao.delCatBreed(catBreedId)
    }

    fun isBreedSaved(breedId: String): Flow<Boolean>{
        return catBreedDao.isBreedSaved(breedId)
    }

    suspend fun getCatBreeds(limit: Int): List<CatBreed>{
        return catService.getCatBreeds(limit)
    }

    suspend fun searchCatBreed(q: String): List<CatBreedSearch>{
        return catService.searchCatBreed(q)
    }

    suspend fun getBreedDetails(breedId: String = "aege"): CatBreed{
        return catService.getBreedDetails(breedId)
    }
}