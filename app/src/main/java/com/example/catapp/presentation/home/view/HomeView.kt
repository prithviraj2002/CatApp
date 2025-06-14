package com.example.catapp.presentation.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catapp.presentation.components.CatBreedDialog
import com.example.catapp.presentation.components.CatBreedImage
import com.example.catapp.presentation.components.RandomCatImage
import com.example.catapp.presentation.components.RandomCatImageComponent
import com.example.catapp.presentation.components.RandomCatImageDialog
import com.example.catapp.presentation.components.TrendingCatImagesDialog
import com.example.catapp.presentation.explore.ViewModel.ExploreViewModel
import com.example.catapp.presentation.home.ViewModel.HomeViewModel

@Composable
fun HomeView(
    viewModel: HomeViewModel,
    exploreViewModel: ExploreViewModel,
){
    val catBreedState by exploreViewModel.catBreedData.collectAsState()
    val randomCatImageState by viewModel.randomCatImage.collectAsState()
    val randomCatImageListState by viewModel.randomCatImageList.collectAsState()

    val showDialog = remember{
        mutableStateOf(false)
    }

    val showTrendingImageDialog = remember{
        mutableStateOf(false)
    }
    val imageIndex = remember{
        mutableIntStateOf(0)
    }

    val showCatBreeds = remember{
        mutableStateOf(false)
    }
    val breedIndex = remember{
        mutableIntStateOf(0)
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {

        Text("Random cat for you", style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold))
        Box(modifier = Modifier.height(20.dp))
        when{
            randomCatImageState.isLoading -> {
                CircularProgressIndicator()
            }
            randomCatImageState.error != null -> {
                Text("An error occurred")
            }
            randomCatImageState.imageData != null -> {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RandomCatImage(randomCatImageState.imageData!!){
                        showDialog.value = true
                    }
                }
            }
            else -> {
                Text("Something went wrong!")
            }
        }

        Box(modifier = Modifier.height(20.dp))

        Text("Cat breeds", style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold))
        Box(modifier = Modifier.height(20.dp))
        when{
            catBreedState.isLoading -> {
                CircularProgressIndicator()
            }
            catBreedState.e != null ->{
                Text("An error occurred")
            }
            catBreedState.data.isNotEmpty() ->{
                LazyRow(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(10){ item ->
                        Box(
                            modifier = Modifier.width(160.dp)
                        ) {
                            CatBreedImage(catBreedState.data[item]) {
                                showCatBreeds.value = true
                                breedIndex.intValue = item
                            }
                        }
                    }
                }
            }
        }

        Box(modifier = Modifier.height(20.dp))

        Text("Trending cat images", style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold))
        Box(modifier = Modifier.height(20.dp))

        when{
            randomCatImageListState.isLoading -> {
                CircularProgressIndicator()
            }
            randomCatImageListState.error != null ->{
                Text("An error occurred")
            }
            randomCatImageListState.imagesData != null ->{
                LazyRow(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(randomCatImageListState.imagesData!!.size){ item ->
                        RandomCatImageComponent(
                            randomCatImageListState.imagesData!![item].url,
                            randomCatImageListState.imagesData!![item].id
                        ){
                            showTrendingImageDialog.value = true
                            imageIndex.intValue = item
                        }
                    }
                }
            }
        }

        if(showDialog.value){
            RandomCatImageDialog(
                showDialog,
                randomCatImageState
            )
        }

        if(showTrendingImageDialog.value){
            TrendingCatImagesDialog(
                showTrendingImageDialog,
                randomCatImageListState,
                imageIndex.intValue
            )
        }

        if(showCatBreeds.value){
            CatBreedDialog(
                showCatBreeds,
                catBreedState,
                breedIndex.intValue
            )
        }
    }
}
