package com.example.catapp.presentation.home.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.catapp.domain.models.CatImage
import com.example.catapp.presentation.components.RandomCatImage
import com.example.catapp.presentation.components.RandomCatImageComponent
import com.example.catapp.presentation.home.ViewModel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    viewModel: HomeViewModel
){
    val randomCatImageState by viewModel.randomCatImage.collectAsState()
    val randomCatImageListState by viewModel.randomCatImageList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cats") }
            )
        }
    ) {
            innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
        ) {
            when{
                randomCatImageState.isLoading -> {
                    CircularProgressIndicator()
                }
                randomCatImageState.error != null -> {
                    Text("An error occurred")
                }
                randomCatImageState.imageData != null -> {
                    RandomCatImage(randomCatImageState.imageData!!)
                }
                else -> {
                    Text("Something went wrong!")
                }
            }

            Box(modifier = Modifier.height(20.dp))
            Divider()
            Box(modifier = Modifier.height(20.dp))

            Text("Random cat images for you")
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
                        modifier = Modifier.padding(12.dp)
                    ) {
                        items(randomCatImageListState.imagesData!!.size){ item ->
                            RandomCatImageComponent(randomCatImageListState.imagesData!![item]) { }
                        }
                    }
                }
            }
        }
    }
}
