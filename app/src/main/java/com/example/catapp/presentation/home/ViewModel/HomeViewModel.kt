package com.example.catapp.presentation.home.ViewModel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapp.data.CatInterface
import com.example.catapp.domain.repo.CatSavedImagesRepo
import com.example.catapp.presentation.home.model.RandomCatImageListResponse
import com.example.catapp.presentation.home.model.RandomCatImageResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val catImageRepo: CatSavedImagesRepo
): ViewModel(){

    private val _randomCatImage = MutableStateFlow(RandomCatImageResponse())
    val randomCatImage: StateFlow<RandomCatImageResponse> = _randomCatImage.asStateFlow()

    private val _randomCatImageList = MutableStateFlow(RandomCatImageListResponse())
    val randomCatImageList = _randomCatImageList.asStateFlow()

    init{
        getRandomCatImage()
        getRandomCatImageList()
    }

    private fun getRandomCatImage(){
        _randomCatImage.update {
            it.copy(
                imageData = null,
                error = null,
                isLoading = true
            )
        }

        viewModelScope.launch {
            try{
                val response = catImageRepo.getRandomCatImage()

                _randomCatImage.update {
                    it.copy(
                        imageData = response.first(),
                        error = null,
                        isLoading = false
                    )
                }
            } catch(e: Exception){
                Log.e("Random cat image error:", e.toString())
                _randomCatImage.update {
                    it.copy(
                        imageData = null,
                        error = e,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun getRandomCatImageList(){
        _randomCatImageList.update {
            it.copy(
                imagesData = null,
                error = null,
                isLoading = true
            )
        }

        viewModelScope.launch {
            try{
                val response = catImageRepo.getRandomCatImageList(limit = 10)

                _randomCatImageList.update {
                    it.copy(
                        imagesData = response,
                        error = null,
                        isLoading = false
                    )
                }
            } catch(e: Exception){
                Log.e("Random cat image list error:", e.toString())
                _randomCatImageList.update {
                    it.copy(
                        imagesData = null,
                        error = e,
                        isLoading = false
                    )
                }
            }
        }
    }
}
