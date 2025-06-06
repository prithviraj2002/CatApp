package com.example.catapp.presentation.funny.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapp.data.CatInterface
import com.example.catapp.presentation.funny.model.CatImageResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FunnyViewModel @Inject constructor(
    private val catService: CatInterface
): ViewModel() {

    private val _catImageState = MutableStateFlow(CatImageResponse())
    val catImageState = _catImageState.asStateFlow()

    init{
        getCatImages()
    }

    fun getCatImages(){
        _catImageState.update { it.copy(
            emptyList(), null, true
        ) }

        viewModelScope.launch {
            try{
                val response = catService.getCatImages()

                _catImageState.update { it.copy(
                    response, null, false
                ) }
            } catch(e: Exception){
                Log.e("cat image error", e.toString())
                _catImageState.update { it.copy(
                    emptyList(), e, false
                ) }
            }
        }
    }
}