package com.example.catapp.presentation.funny.view

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.catapp.presentation.components.ErrorState
import com.example.catapp.presentation.components.FunnyCatImagesDialog
import com.example.catapp.presentation.components.LoadingState
import com.example.catapp.presentation.components.TrendingCatImagesDialog
import com.example.catapp.presentation.funny.ViewModel.FunnyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FunnyView(
    viewModel: FunnyViewModel
){
    val imageState by viewModel.catImageState.collectAsState()

    val isRefreshing = remember {
        mutableStateOf(false)
    }

    val showDialog = remember{
        mutableStateOf(false)
    }
    val imageIndex = remember{
        mutableIntStateOf(0)
    }

    when{
        imageState.isLoading -> {
            LoadingState()
        }
        imageState.error != null -> {
            ErrorState("Something went wrong")
            Log.e("funny image error", imageState.error.toString())
        }
        else -> {
            if(imageState.imageData.isEmpty()){
                ErrorState("No images here")
            }
            else{
                PullToRefreshBox(
                    isRefreshing = isRefreshing.value,
                    onRefresh = {
                        isRefreshing.value = true
                        viewModel.getCatImages()
                        isRefreshing.value = false
                    }
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(imageState.imageData.size) { item ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable {
                                        showDialog.value = true
                                        imageIndex.intValue = item
                                    }
                            ) {
                                AsyncImage(
                                    model = imageState.imageData[item].url,
                                    contentDescription = null,
                                    modifier = Modifier.height(200.dp),
                                    contentScale = ContentScale.FillHeight
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if(showDialog.value){
        FunnyCatImagesDialog(
            showDialog,
            imageState,
            imageIndex.intValue
        )
    }
}