package com.example.catapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil3.compose.AsyncImage
import com.example.catapp.presentation.home.model.RandomCatImageListResponse
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.catapp.domain.models.CatBreed
import com.example.catapp.presentation.explore.model.CatBreedResponse
import com.example.catapp.presentation.fav.ViewModel.FavViewModel
import com.example.catapp.presentation.funny.model.CatImageResponse
import com.example.catapp.presentation.home.model.RandomCatImageResponse
import java.net.URI

@Composable
fun RandomCatImageDialog(
    showDialog: MutableState<Boolean>,
    randomCatImageState: RandomCatImageResponse
){
    Dialog(
        onDismissRequest = {
            showDialog.value = false
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ){
            Column(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End) {
                    IconButton(
                        onClick = {
                            showDialog.value = false
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Close button",
                        )
                    }
                }
                AsyncImage(
                    model = randomCatImageState.imageData!!.url,
                    contentDescription = "Random cat image",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun TrendingCatImagesDialog(
    showDialog: MutableState<Boolean>,
    trendingImageList: RandomCatImageListResponse,
    index: Int = 0
){

    val pagerState = rememberPagerState(
        initialPage = index,
        pageCount = {
            trendingImageList.imagesData!!.size
        }
    )

    Dialog(
        onDismissRequest = {
            showDialog.value = false
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ){
            Column(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End) {
                    IconButton(
                        onClick = {
                            showDialog.value = false
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Close button",
                        )
                    }
                }
            }
            HorizontalPager(
                state = pagerState
            ) { page ->
                AsyncImage(
                    model = trendingImageList.imagesData!![page].url,
                    contentDescription = "Random cat image",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}


@Composable
fun FunnyCatImagesDialog(
    showDialog: MutableState<Boolean>,
    funnyImageList: CatImageResponse,
    index: Int = 0
){

    val favViewModel = hiltViewModel<FavViewModel>()

    val pagerState = rememberPagerState(
        initialPage = index,
        pageCount = {
            funnyImageList.imageData.size
        }
    )

    Dialog(
        onDismissRequest = {
            showDialog.value = false
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ){
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End) {
                IconButton(
                    onClick = {
                        showDialog.value = false
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Close button",
                    )
                }
            }
            HorizontalPager(
                state = pagerState
            ) { page ->
                Column {
                    Row {
                        IconButton(
                            onClick = {
                                favViewModel.saveImage(funnyImageList.imageData[page])
                            }
                        ) {
                            Icon(Icons.Default.FavoriteBorder, contentDescription = "")
                        }
                    }
                    AsyncImage(
                        model = funnyImageList.imageData[page].url,
                        contentDescription = "Random cat image",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}


@Composable
fun CatBreedDialog(
    showDialog: MutableState<Boolean>,
    catBreedList: CatBreedResponse,
    index: Int = 0
){

    val pagerState = rememberPagerState(
        initialPage = index,
        pageCount = {
            catBreedList.data.size
        }
    )

    Dialog(
        onDismissRequest = {
            showDialog.value = false
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ){
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End) {
                IconButton(
                    onClick = {
                        showDialog.value = false
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Close button",
                    )
                }
            }
            HorizontalPager(
                state = pagerState
            ) { page ->
                CatDetailPage(catBreedList.data[page])
            }
        }
    }
}


@Composable
fun CatDetailPage(
    catBreed: CatBreed
){
    val uriHandler = LocalUriHandler.current
    val favViewModel = hiltViewModel<FavViewModel>()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.height(50.dp))
        AsyncImage(
            model = getCatImageUrl(catBreed.referenceImageId),
            contentDescription = "Random cat image",
            modifier = Modifier.height(
                240.dp
            )
                .width(360.dp)
        )
        Box(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(catBreed.name, style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            ))
            Box(modifier = Modifier.width(12.dp))
            IconButton(
                onClick = {
                    favViewModel.saveBreed(catBreed)
                }
            ) {
                Icon(Icons.Default.FavoriteBorder, contentDescription = "")
            }
        }
        Box(modifier = Modifier.height(16.dp))
        Divider()
        Box(modifier = Modifier.height(16.dp))
        Text(catBreed.description, style = TextStyle(fontSize = 20.sp))
        Box(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = {
                uriHandler.openUri(catBreed.wikipediaUrl)
            }
        ) {
            Text("Wikipedia", style = TextStyle(
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic,
                color = Color.Blue)
            )
        }
    }
}


