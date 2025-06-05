package com.example.catapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.catapp.domain.models.CatImage
import com.example.catapp.presentation.home.model.RandomCatImageResponse

@Composable
fun RandomCatImage(
    catImage: CatImage
){
    Card(
        modifier = Modifier.height(300.dp).width(500.dp)
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = catImage.url,
            contentDescription = "Random cat image"
        )
    }
}

@Composable
fun RandomCatImageComponent(catImage: CatImage, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(300.dp)
            .height(300.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onClick()
            }
    ) {
        AsyncImage(
            model = catImage.url,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
