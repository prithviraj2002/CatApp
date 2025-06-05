package com.example.catapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.catapp.domain.models.CatBreed

@Composable
fun CatBreedImage(refId: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onClick()
            }
    ) {
        AsyncImage(
            model = getCatImageUrl(refId),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

fun getCatImageUrl(id: String): String {
    return "https://cdn2.thecatapi.com/images/$id.jpg";
}

@Composable
fun CatDetailSheet(breed: CatBreed) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = getCatImageUrl(breed.referenceImageId),
            contentDescription = "Cat image"
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.width(4.dp))
            Text(breed.name)
            Box(modifier = Modifier.width(4.dp))
            Text(breed.origin)
            Box(modifier = Modifier.width(4.dp))
            Text(breed.countryCode)
            Box(modifier = Modifier.width(4.dp))
        }
        Box(modifier = Modifier.height(12.dp))
        Text(breed.description)
        Text(breed.wikipediaUrl)
    }
}
