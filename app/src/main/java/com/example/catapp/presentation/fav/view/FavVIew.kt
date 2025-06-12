package com.example.catapp.presentation.fav.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catapp.presentation.components.CatBreedDialog
import com.example.catapp.presentation.components.CatBreedImage
import com.example.catapp.presentation.components.RandomCatImageComponent
import com.example.catapp.presentation.components.SavedCatBreedDialog
import com.example.catapp.presentation.components.SavedCatImagesDialog
import com.example.catapp.presentation.components.TrendingCatImagesDialog
import com.example.catapp.presentation.fav.ViewModel.FavViewModel

@Composable
fun FavView(
    viewModel: FavViewModel
){
    val savedImageState by viewModel.savedImages.collectAsState()
    val savedBreedsState by viewModel.savedBreeds.collectAsState()

    val showSavedBreed = remember{
        mutableStateOf(false)
    }
    val breedIndex = remember{
        mutableIntStateOf(0)
    }

    val showSavedImage = remember{
        mutableStateOf(false)
    }
    val imageIndex = remember{
        mutableIntStateOf(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(modifier = Modifier.height(20.dp))
        Text("Saved breeds", style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold))
        Box(modifier = Modifier.height(12.dp))
        when{
            savedBreedsState.isLoading -> {
                CircularProgressIndicator()
            }

            savedBreedsState.error != null -> {
                Text("Error: ${savedImageState.error}")
            }

            else -> {
                if(savedBreedsState.catBreedData.isEmpty()){
                    Text("No cat breeds saved yet!")
                }
                else{
                    LazyRow(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(savedBreedsState.catBreedData.size){item ->
                            Box(
                                modifier = Modifier.width(160.dp)
                            ) {
                                CatBreedImage(savedBreedsState.catBreedData.reversed()[item]) {
                                    showSavedBreed.value = true
                                    breedIndex.intValue = item
                                }
                            }
                        }
                    }
                }
            }
        }

        Box(modifier = Modifier.height(20.dp))
        Text("Saved images", style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold))
        Box(modifier = Modifier.height(12.dp))
        when{
            savedImageState.isLoading -> {
                CircularProgressIndicator()
            }

            savedImageState.error != null -> {
                Text("Error: ${savedImageState.error}")
            }

            else -> {
                if(savedImageState.imageData.isEmpty()){
                    Text("No cat images saved yet!")
                }
                else{
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(savedImageState.imageData.size){item ->
                            RandomCatImageComponent(
                                savedImageState.imageData.reversed()[item].url,
                                savedImageState.imageData.reversed()[item].imageId
                            ) {
                                showSavedImage.value = true
                                imageIndex.intValue = item
                            }
                        }
                    }
                }
            }
        }
    }

    if(showSavedBreed.value){
        SavedCatBreedDialog(
            showSavedBreed,
            savedBreedsState,
            index = breedIndex.intValue
        )
    }

    if(showSavedImage.value){
        SavedCatImagesDialog(
            showSavedImage,
            savedImageState,
            index = imageIndex.intValue
        )
    }
}