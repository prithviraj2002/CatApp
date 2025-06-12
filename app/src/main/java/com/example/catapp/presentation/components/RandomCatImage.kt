package com.example.catapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.catapp.domain.models.CatImage
import com.example.catapp.presentation.fav.ViewModel.FavViewModel

@Composable
fun RandomCatImage(
    catImage: CatImage,
    onClick: () -> Unit
){
    val favViewModel = hiltViewModel<FavViewModel>()
    val isLiked by favViewModel.isImageSaved(catImage.id).collectAsState(initial = false)

    Box(
        modifier = Modifier
            .height(260.dp)
            .width(360.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = catImage.url,
            contentDescription = "Random cat image",
            modifier = Modifier.height(240.dp),
            contentScale = ContentScale.FillHeight
        )
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
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
                            favViewModel.saveImage(
                                catImage.id,
                                catImage.url
                            )
                        } else {
                            favViewModel.removeImage(
                                catImage.id
                            )
                        }
                    }
                ) {
                    if (isLiked) {
                        Icon(Icons.Default.Favorite, contentDescription = "Liked", tint = Color.Red)
                    } else {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "Not Liked")
                    }
                }
            }
        }
    }
}

@Composable
fun RandomCatImageComponent(catImage: String, catImageId: String, onClick: () -> Unit) {

    val favViewModel = hiltViewModel<FavViewModel>()
    val isLiked by favViewModel.isImageSaved(catImageId).collectAsState(initial = false)

    Box(
        modifier = Modifier
            .width(120.dp)
            .height(220.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onClick()
            }
    ) {
        AsyncImage(
            model = catImage,
            contentDescription = null,
            modifier = Modifier.height(200.dp),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
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
                            favViewModel.saveImage(
                                catImageId,
                                catImage
                            )
                        } else {
                            favViewModel.removeImage(
                                catImageId
                            )
                        }
                    }
                ) {
                    if (isLiked) {
                        Icon(Icons.Default.Favorite, contentDescription = "Liked", tint = Color.Red)
                    } else {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "Not Liked")
                    }
                }
            }
        }
    }
}
