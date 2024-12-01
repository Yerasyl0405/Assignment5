package com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.assignment3.network.RetrofitInstance.api
import com.example.assignment3.view.FilmImagesViewModel

val filmImagesViewModel = FilmImagesViewModel(api)

@Composable
fun FilmImagesScreen(
    filmId: Int,
    navController: NavController
) {
    val images by filmImagesViewModel.images.collectAsState(emptyList())

    LaunchedEffect(Unit) {
        filmImagesViewModel.fetchImages(filmId)
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .padding(start = 26.dp, end = 26.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Галерея",
                fontSize = 18.sp,
                lineHeight = 19.8.sp,
                fontWeight = FontWeight.W600
            )
            Text(
                text = "${images.size}",
                fontSize = 14.sp,
                lineHeight = 15.4.sp,
                fontWeight = FontWeight.W500,
                color = Color(0xFF3D3BFF),
                modifier = Modifier.clickable { navController.navigate("full_gallery/${filmId}") })
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(images.take(3)) { imageUrl ->
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .height(108.dp)
                        .width(192.dp)

                ) {
                    AsyncImage(
                        model = imageUrl.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}


@Composable
fun FullGalleryScreen(filmId: Int, navController: NavController) {
    val imagess by filmImagesViewModel.images.collectAsState(emptyList())

    LaunchedEffect(Unit) {
        filmImagesViewModel.fetchImages(filmId)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(painter = painterResource(id = com.example.finalassignment3.R.drawable.icon),
                contentDescription = null,
                modifier = Modifier
                    .align(
                        Alignment.CenterVertically
                    )
                    .clickable { navController.popBackStack() })
            Text(
                text = "Галерея",
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W500),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(imagess.chunked(3)) { _, chunk ->
                when (chunk.size) {
                    3 -> {
                        GalleryRow(chunk[0].imageUrl, chunk[1].imageUrl, chunk[2].imageUrl)
                    }

                    2 -> {
                        TwoImagesRow(chunk[0].imageUrl, chunk[1].imageUrl)
                    }

                    1 -> {
                        FullWidthImage(chunk[0].imageUrl)
                    }
                }
            }
        }
    }
}

@Composable
fun GalleryRow(image1: String, image2: String, largeImage: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ImageItem(image1, Modifier.weight(1f))
            ImageItem(image2, Modifier.weight(1f))
        }
        FullWidthImage(largeImage)
    }
}

@Composable
fun TwoImagesRow(image1: String, image2: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ImageItem(image1, Modifier.weight(1f))
        ImageItem(image2, Modifier.weight(1f))
    }
}

@Composable
fun FullWidthImage(image: String) {
    ImageItem(
        image = image,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}

@Composable
fun ImageItem(image: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .aspectRatio(16 / 9f)
            .background(Color.Gray)
            .clip(RoundedCornerShape(8.dp))
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}