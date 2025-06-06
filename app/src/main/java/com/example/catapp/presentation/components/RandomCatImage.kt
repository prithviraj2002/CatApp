package com.example.catapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.catapp.domain.models.CatImage
import com.example.catapp.presentation.home.model.RandomCatImageResponse

@Composable
fun RandomCatImage(
    catImage: CatImage
){
    Box(
        modifier = Modifier.height(240.dp).width(360.dp)
            .clip(RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = catImage.url,
            contentDescription = "Random cat image",
            modifier = Modifier.height(240.dp),
            contentScale = ContentScale.FillHeight
        )
    }
}

@Composable
fun RandomCatImageComponent(catImage: CatImage, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(120.dp)
            .height(240.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onClick()
            }
    ) {
        AsyncImage(
            model = catImage.url,
            contentDescription = null,
            modifier = Modifier.height(200.dp),
            contentScale = ContentScale.Crop
        )
    }
}
