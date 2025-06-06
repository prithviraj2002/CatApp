package com.example.catapp.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.catapp.domain.models.CatBreedSearch
import coil3.compose.AsyncImage
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun CatSearchTile(catBreed: CatBreedSearch, onClick: () -> Unit){
    Card (
        onClick = {
            onClick()
        },
        modifier = Modifier.padding(start = 12.dp, top = 12.dp, end = 12.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimary)
    ) {
        Row(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
            Spacer(modifier = Modifier.width(5.dp))
            AsyncImage(
                model = catBreed.image.url,
                contentDescription = null,
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp)
                    .padding(16.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = catBreed.name,
                style = TextStyle(
                    fontSize = 20.sp,
                ),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterVertically),
                color = Color.Black, textAlign = TextAlign.Center
            )
        }
    }
}