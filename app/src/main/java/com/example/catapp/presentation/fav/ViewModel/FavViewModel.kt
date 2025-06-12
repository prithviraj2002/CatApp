package com.example.catapp.presentation.fav.ViewModel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapp.data.CatInterface
import com.example.catapp.domain.models.CatBreed
import com.example.catapp.domain.models.CatImage
import com.example.catapp.domain.models.DbModels.CatBreedEntity
import com.example.catapp.domain.models.DbModels.CatImageEntity
import com.example.catapp.domain.repo.CatBreedRepo
import com.example.catapp.domain.repo.CatSavedImagesRepo
import com.example.catapp.presentation.fav.model.CatBreedEntityResponse
import com.example.catapp.presentation.fav.model.CatImageEntityResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavViewModel @Inject constructor(
    private val catImageRepo: CatSavedImagesRepo,
    private val catBreedRepo: CatBreedRepo,
    private val catService: CatInterface
): ViewModel(){

    private val _savedImages = MutableStateFlow(CatImageEntityResponse())
    val savedImages = _savedImages.asStateFlow()

    private val _savedBreeds = MutableStateFlow(CatBreedEntityResponse())
    val savedBreeds = _savedBreeds.asStateFlow()

    init {
        getSavedImages()
        getSavedBreeds()
    }

    fun getSavedImages(){
        viewModelScope.launch {
            catImageRepo.getSavedImages()
                .onStart {
                    _savedImages.update { it.copy(emptyList(), null, true) }
                }
                .catch { e ->
                    _savedImages.update { it.copy(emptyList(), e.message, false) }
                }
                .collect { list ->
                    Log.e("Total saved images", list.size.toString())
                    _savedImages.update {
                        it.copy(list, null, false)
                    }
                }
        }
    }

    fun getSavedBreeds(){
        viewModelScope.launch {
            catBreedRepo.getSavedBreeds()
                .onStart {
                    _savedBreeds.update { it.copy(emptyList(), null, true) }
                }
                .catch { e ->
                    _savedBreeds.update { it.copy(emptyList(), e.message, false) }
                }
                .collect { list ->
                    Log.e("LoadedBreeds", list.size.toString())
                    _savedBreeds.update {
                        val breeds = mutableListOf<CatBreed>();

                        for(entity in list){
                            breeds.add(catService.getBreedDetails(entity.breedId))
                        }
                        it.copy(breeds.toList(), null, false)
                    }
                }
        }
    }

    fun saveImage(catImageId: String, catImageUrl: String) {
        viewModelScope.launch {
            try {
                catImageRepo.saveImage(
                    CatImageEntity(
                        imageId = catImageId,
                        url = catImageUrl
                    )
                )
            } catch (e: Exception) {
                Log.e("FavViewModel", "Save error", e)
            }
        }
    }

    fun isImageSaved(catImageId: String): Flow<Boolean> {
        return catImageRepo.isImageSaved(catImageId)
    }

    fun isBreedSaved(breedId: String): Flow<Boolean>{
        return catBreedRepo.isBreedSaved(breedId)
    }

    fun saveBreed(catBreed: CatBreed){
        viewModelScope.launch {
            try {
                catBreedRepo.saveBreed(
                    CatBreedEntity(
                        breedId = catBreed.id,
                    )
                )
            } catch (e: Exception) {
                Log.e("FavViewModel", "Save error", e)
            }
        }
    }

    fun removeBreed(catBreedId: String){
        viewModelScope.launch {
            try {
                catBreedRepo.removeBreed(catBreedId)
            } catch (e: Exception) {
                Log.e("FavViewModel", "Remove error", e)
            }
        }
    }

    fun removeImage(catImageId: String) {
        viewModelScope.launch {
            try {
                catImageRepo.removeImage(catImageId)
            } catch (e: Exception) {
                Log.e("FavViewModel", "Remove error", e)
            }
        }
    }
}
