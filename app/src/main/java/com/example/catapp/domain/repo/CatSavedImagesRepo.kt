package com.example.catapp.domain.repo

import com.example.catapp.data.CatInterface
import com.example.catapp.data.dao.CatImageDao
import com.example.catapp.domain.models.CatImage
import com.example.catapp.domain.models.DbModels.CatImageEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatSavedImagesRepo @Inject constructor(
    private val catImageDao: CatImageDao,
    private val catService: CatInterface
){

    fun getSavedImages(): Flow<List<CatImageEntity>> = catImageDao.getCatImages()

    suspend fun saveImage(catImageEntity: CatImageEntity) {
        catImageDao.saveImage(catImageEntity)
    }

    suspend fun removeImage(catImageId: String) {
        catImageDao.removeFromFav(catImageId)
    }

    fun isImageSaved(catImageId: String): Flow<Boolean>{
        return catImageDao.isImageSaved(catImageId)
    }

    suspend fun getCatImages(limit: Int): List<CatImage>{
        return catService.getCatImages(limit)
    }

    suspend fun getRandomCatImage(): List<CatImage>{
        return catService.getRandomCatImage()
    }

    suspend fun getRandomCatImageList(limit: Int): List<CatImage>{
        return catService.getRandomCatImageList(limit)
    }
}