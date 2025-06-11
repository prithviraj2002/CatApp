package com.example.catapp.domain.repo

import android.util.Log
import androidx.compose.runtime.MutableState
import com.example.catapp.data.dao.CatImageDao
import com.example.catapp.domain.models.DbModels.CatImageEntity
import com.example.catapp.presentation.fav.model.CatBreedEntityResponse
import com.example.catapp.presentation.fav.model.CatImageEntityResponse
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class CatSavedImagesRepo @Inject constructor(private val catImageDao: CatImageDao){

    fun getSavedImages(): Flow<List<CatImageEntity>> = catImageDao.getCatImages()

    suspend fun saveImage(catImageEntity: CatImageEntity) {
        catImageDao.saveImage(catImageEntity)
    }

    suspend fun removeImage(catImageEntity: CatImageEntity) {
        catImageDao.removeFromFav(catImageEntity)
    }
}