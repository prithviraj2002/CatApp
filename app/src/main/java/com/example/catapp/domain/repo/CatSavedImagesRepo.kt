package com.example.catapp.domain.repo

import android.util.Log
import com.example.catapp.data.dao.CatImageDao
import com.example.catapp.domain.models.DbModels.CatImageEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatSavedImagesRepo @Inject constructor(private val catImageDao: CatImageDao){

    fun getSavedImages(): Flow<List<CatImageEntity>> = catImageDao.getCatImages()

    suspend fun saveImage(catImageEntity: CatImageEntity) {
        catImageDao.saveImage(catImageEntity)
    }

    suspend fun removeImage(catImageId: String) {
        Log.e("Removing image", catImageId)
        catImageDao.removeFromFav(catImageId)
    }

    fun isImageSaved(catImageId: String): Flow<Boolean>{
        return catImageDao.isImageSaved(catImageId)
    }
}