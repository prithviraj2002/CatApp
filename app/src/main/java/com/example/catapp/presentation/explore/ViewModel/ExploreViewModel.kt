package com.example.catapp.presentation.explore.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapp.domain.models.CatBreed
import com.example.catapp.domain.models.CatBreedSearch
import com.example.catapp.domain.repo.CatBreedRepo
import com.example.catapp.presentation.explore.model.CatBreedResponse
import com.example.catapp.presentation.explore.model.CatBreedSearchResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val catBreedRepo: CatBreedRepo
): ViewModel(){
    val q = mutableStateOf("")
    private val _catSearchResponse = MutableStateFlow(
        CatBreedSearchResponse(
            emptyList(), null, false
        )
    );
    val catSearchResponse = _catSearchResponse.asStateFlow()

    val selectedBreed = mutableStateOf<CatBreed?>(null);
    val selectedSearchBreed = mutableStateOf<CatBreedSearch?>(null);
    val showSheet = mutableStateOf(false)

    private val _catBreedData = MutableStateFlow(CatBreedResponse());
    val catBreedData = _catBreedData.asStateFlow()

    init {
        getCatBreeds()
    }

    fun getCatBreeds() {
        _catBreedData.update {
            it.copy(
                data = emptyList(), e = null, isLoading = true
            )
        }
        viewModelScope.launch {
            try {
                val catBreedList = catBreedRepo.getCatBreeds(limit = 21)
                _catBreedData.update {
                    it.copy(
                        data = catBreedList, e = null, isLoading = false
                    )
                }
            } catch (e: Exception) {
                _catBreedData.update {
                    it.copy(
                        data = emptyList(), e = e, isLoading = false
                    )
                }
            }
        }
    }

    fun searchCatBreed() {
        _catSearchResponse.update {
            it.copy(
                emptyList(), null, true
            )
        }

        viewModelScope.launch {
            try {
                val response = catBreedRepo.searchCatBreed(q.value)

                _catSearchResponse.update {
                    it.copy(
                        response, null, false
                    )
                }
            } catch (e: Exception) {
                _catSearchResponse.update {
                    it.copy(
                        emptyList(), e = e, false
                    )
                }
            }
        }
    }
}