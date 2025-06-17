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
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.catapp.presentation.fav.model.CatBreedEntityResponse
import com.example.catapp.presentation.fav.model.CatImageEntityResponse
import com.example.catapp.presentation.funny.model.CatImageResponse
import com.example.catapp.presentation.home.model.RandomCatImageResponse

@Composable
fun RandomCatImageDialog(
    showDialog: MutableState<Boolean>,
    randomCatImageState: RandomCatImageResponse
){
    val favViewModel = hiltViewModel<FavViewModel>()
    val isLiked by favViewModel.isImageSaved(randomCatImageState.imageData!!.id).collectAsState(initial = false)

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
                    modifier = Modifier.fillMaxWidth().height(500.dp)
                )
                Box(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = {
                            if(isLiked){
                                favViewModel.removeImage(randomCatImageState.imageData.id)
                            }
                            else{
                                favViewModel.saveImage(randomCatImageState.imageData.id, randomCatImageState.imageData.url)
                            }
                        }
                    ) {
                        if(isLiked){
                            Icon(Icons.Default.Favorite, contentDescription = "", tint = Color.Red)
                        }
                        else{
                            Icon(Icons.Default.FavoriteBorder, contentDescription = "")
                        }
                    }
                }
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

    val favViewModel = hiltViewModel<FavViewModel>()

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
            HorizontalPager(
                state = pagerState
            ) { page ->
                val isLiked by favViewModel.isImageSaved(trendingImageList.imagesData!![page].id).collectAsState(initial = false)

                Box(
                    modifier = Modifier.fillMaxWidth().height(500.dp)
                ){
                    AsyncImage(
                        model = trendingImageList.imagesData!![page].url,
                        contentDescription = "Random cat image",
                        modifier = Modifier.height(490.dp).fillMaxWidth(),
                    )
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom
                    ){
                        IconButton(
                            onClick = {
                                if(isLiked){
                                    favViewModel.removeImage(trendingImageList.imagesData[page].id)
                                }
                                else{
                                    favViewModel.saveImage(trendingImageList.imagesData[page].id, trendingImageList.imagesData[page].url)
                                }
                            }
                        ) {
                            if(isLiked){
                                Icon(Icons.Default.Favorite, contentDescription = "", tint = Color.Red)
                            }
                            else{
                                Icon(Icons.Default.FavoriteBorder, contentDescription = "")
                            }
                        }
                    }

                    Row(modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Top
                        ) {
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
            HorizontalPager(
                state = pagerState
            ) { page ->
                val isLiked by favViewModel.isImageSaved(funnyImageList.imageData[page].id).collectAsState(initial = false)

                Box(
                    modifier = Modifier.fillMaxWidth().height(500.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = funnyImageList.imageData[page].url,
                        contentDescription = "Random cat image",
                        modifier = Modifier.fillMaxWidth().height(490.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        IconButton(
                            onClick = {
                                if(isLiked){
                                    favViewModel.removeImage(funnyImageList.imageData[page].id)
                                }
                                else{
                                    favViewModel.saveImage(funnyImageList.imageData[page].id, funnyImageList.imageData[page].url)
                                }
                            }
                        ) {
                            if(isLiked){
                                Icon(Icons.Default.Favorite, contentDescription = "", tint = Color.Red)
                            }
                            else{
                                Icon(Icons.Default.FavoriteBorder, contentDescription = "")
                            }
                        }
                    }
                    Row(modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Top
                        ) {
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
            HorizontalPager(
                state = pagerState
            ) { page ->
                CatDetailPage(catBreedList.data[page], showDialog)
            }
        }
    }
}

@Composable
fun SavedCatBreedDialog(
    showDialog: MutableState<Boolean>,
    catBreedList: CatBreedEntityResponse,
    index: Int = 0
){
    val pagerState = rememberPagerState(
        initialPage = index,
        pageCount = {
            catBreedList.catBreedData.size
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
            HorizontalPager(
                state = pagerState
            ) { page ->
                CatDetailPage(catBreedList.catBreedData.reversed()[page], showDialog)
            }
        }
    }
}

@Composable
fun SavedCatImagesDialog(
    showDialog: MutableState<Boolean>,
    catImageList: CatImageEntityResponse,
    index: Int = 0
){

    val favViewModel = hiltViewModel<FavViewModel>()

    val pagerState = rememberPagerState(
        initialPage = index,
        pageCount = {
            catImageList.imageData.size
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
            HorizontalPager(
                state = pagerState
            ) { page ->
                val isLiked by favViewModel.isImageSaved(catImageList.imageData.reversed()[page].imageId).collectAsState(initial = false)

                Box(
                    modifier = Modifier.fillMaxWidth().height(500.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = catImageList.imageData.reversed()[page].url,
                        contentDescription = "Random cat image",
                        modifier = Modifier.fillMaxWidth().height(490.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        IconButton(
                            onClick = {
                                if(isLiked){
                                    favViewModel.removeImage(catImageList.imageData.reversed()[page].imageId)
                                }
                                else{
                                    favViewModel.saveImage(catImageList.imageData.reversed()[page].imageId, catImageList.imageData.reversed()[page].url)
                                }
                            }
                        ) {
                            if(isLiked){
                                Icon(Icons.Default.Favorite, contentDescription = "", tint = Color.Red)
                            }
                            else{
                                Icon(Icons.Default.FavoriteBorder, contentDescription = "")
                            }
                        }
                    }
                    Row(modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Top
                    ) {
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
            }
        }
    }
}

@Composable
fun CatDetailPage(
    catBreed: CatBreed,
    showDialog: MutableState<Boolean>
){
    val uriHandler = LocalUriHandler.current
    val favViewModel = hiltViewModel<FavViewModel>()
    val isLiked by favViewModel.isBreedSaved(catBreed.id).collectAsState(initial = false)

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.height(50.dp))
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
            Box(modifier = Modifier.width(4.dp))
            IconButton(
                onClick = {
                    if(isLiked){
                        favViewModel.removeBreed(catBreed.id)
                    }
                    else{
                        favViewModel.saveBreed(catBreed.id)
                    }
                }
            ) {
                if(isLiked){
                    Icon(Icons.Default.Favorite, contentDescription = "", tint = Color.Red)
                }
                else{
                    Icon(Icons.Default.FavoriteBorder, contentDescription = "")
                }
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


