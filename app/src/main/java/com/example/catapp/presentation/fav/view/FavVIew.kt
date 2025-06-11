package com.example.catapp.presentation.fav.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catapp.presentation.components.CatBreedImage
import com.example.catapp.presentation.components.RandomCatImageComponent
import com.example.catapp.presentation.fav.ViewModel.FavViewModel

@Composable
fun FavView(
    viewModel: FavViewModel
){
    val savedImageState by viewModel.savedImages.collectAsState()
    val savedBreedsState by viewModel.savedBreeds.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Box(modifier = Modifier.height(20.dp))
        Text("Saved images", style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold))
        Box(modifier = Modifier.height(12.dp))
        when{
            savedImageState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator()
                }
            }

            savedImageState.error != null -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text("Error: ${savedImageState.error}")
                }
            }

            else -> {
                if(savedImageState.imageData.isEmpty()){
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text("No cat images saved yet!")
                    }
                }
                else{
                    LazyRow(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(savedImageState.imageData.size){item ->
                            RandomCatImageComponent(savedImageState.imageData[item].url) { }
                        }
                    }
                }
            }
        }

        Box(modifier = Modifier.height(20.dp))
        Text("Saved breeds", style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold))
        Box(modifier = Modifier.height(12.dp))
        when{
            savedBreedsState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator()
                }
            }

            savedBreedsState.error != null -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text("Error: ${savedImageState.error}")
                }
            }

            else -> {
                if(savedBreedsState.catBreedData.isEmpty()){
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text("No cat images saved yet!")
                    }
                }
                else{
                    LazyRow(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(savedBreedsState.catBreedData.size){item ->
                            CatBreedImage(savedBreedsState.catBreedData[item]){}
                        }
                    }
                }
            }
        }
    }
}