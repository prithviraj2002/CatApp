package com.example.catapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.stylusHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.catapp.domain.models.CatBreed
import com.example.catapp.domain.models.CatBreedSearch
import com.example.catapp.presentation.fav.ViewModel.FavViewModel

@Composable
fun CatBreedImage(catBreed: CatBreed, onClick: () -> Unit) {

    val favViewModel = hiltViewModel<FavViewModel>()
    val isLiked by favViewModel.isBreedSaved(catBreed.id).collectAsState(initial = false)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onClick()
            }
    ) {
        AsyncImage(
            model = getCatImageUrl(catBreed.referenceImageId),
            contentDescription = null,
            modifier = Modifier.height(200.dp),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(shape = CircleShape)
                    .background(color = Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        if (!isLiked) {
                            favViewModel.saveBreed(catBreed.id)
                        } else {
                            favViewModel.removeBreed(catBreed.id)
                        }
                    }
                ) {
                    if (isLiked) {
                        Icon(Icons.Default.Favorite, tint = Color.Red, contentDescription = "")
                    } else {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "")
                    }
                }
            }
        }
    }
}

fun getCatImageUrl(id: String): String {
    return "https://cdn2.thecatapi.com/images/$id.jpg";
}

@Composable
fun CatDetailSheet(breed: CatBreed) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(breed.name, style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
        Box(modifier = Modifier.height(12.dp))
        Divider()
        Box(modifier = Modifier.height(12.dp))
        AsyncImage(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            model = getCatImageUrl(breed.referenceImageId),
            contentDescription = "Cat image"
        )
        Box(modifier = Modifier.height(12.dp))
        Text(breed.description)
        Text(breed.wikipediaUrl)
    }
}

@Composable
fun CatDetailSearchSheet(breed: CatBreedSearch) {

    val uriHandler = LocalUriHandler.current

    val viewModel = hiltViewModel<FavViewModel>()
    val isLiked by viewModel.isBreedSaved(breed.id).collectAsState(initial = false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
            ) {
            Text(breed.name, style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold))
            IconButton(
                onClick = {
                    if (isLiked) {
                        viewModel.removeBreed(breed.id)
                    } else {
                        viewModel.saveBreed(breed.id)
                    }
                }
            ) {
                if (isLiked) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = "Liked icon",
                        tint = Color.Red
                    )
                } else {
                    Icon(
                        Icons.Default.FavoriteBorder,
                        contentDescription = "Like icon",
                        tint = Color.Red
                    )
                }
            }
        }
        Box(modifier = Modifier.height(16.dp))
        Divider()
        Box(modifier = Modifier.height(16.dp))
        AsyncImage(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            model = breed.image.url,
            contentDescription = "Cat image"
        )
        Box(modifier = Modifier.height(16.dp))
        Text(breed.description, style = TextStyle(fontSize = 20.sp))
        Box(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = {
                uriHandler.openUri(breed.wikipediaUrl)
            }
        ) {
            Text(
                "Wikipedia", style = TextStyle(
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.Blue
                )
            )
        }
    }
}

