package com.example.catapp.presentation.home.ViewModel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapp.data.CatInterface
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
    private val catService: CatInterface
): ViewModel(){

    val _randomCatImage = MutableStateFlow(RandomCatImageResponse())
    val randomCatImage: StateFlow<RandomCatImageResponse> = _randomCatImage.asStateFlow()

    val _randomCatImageList = MutableStateFlow(RandomCatImageListResponse())
    val randomCatImageList = _randomCatImageList.asStateFlow()

    init{
        getRandomCatImage()
        getRandomCatImageList()
    }

    fun getRandomCatImage(){
        _randomCatImage.update {
            it.copy(
                imageData = null,
                error = null,
                isLoading = true
            )
        }

        viewModelScope.launch {
            try{
                val response = catService.getRandomCatImage()

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

    fun getRandomCatImageList(){
        _randomCatImageList.update {
            it.copy(
                imagesData = null,
                error = null,
                isLoading = true
            )
        }

        viewModelScope.launch {
            try{
                val response = catService.getRandomCatImageList()

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
