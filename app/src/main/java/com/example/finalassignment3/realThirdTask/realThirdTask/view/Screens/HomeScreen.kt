@file:Suppress("NAME_SHADOWING")

package com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens
import com.example.finalassignment3.R

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter

import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.assignment3.model.Movie


import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.style.TextAlign
import com.example.assignment3.view.MovieViewModel
import com.example.finalassignment3.realThirdTask.realThirdTask.model.UiState
import kotlin.collections.emptyList as emptyList1

@Composable
fun MoviesByCollectionScreen(
    collectionTypes: String,
    viewModel: MovieViewModel,
    collectionType: String,
    navController: NavController
) {
    LaunchedEffect(collectionType) {
        viewModel.fetchMoviesByCollection(collectionType)
    }

    val collectionState = viewModel.moviesByCollectionMap[collectionType] ?: UiState.Loading

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable { navController.navigate("MovieScreen") }
            )
            Text(
                text = collectionTypes,
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W500),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        when (collectionState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Success -> {
                LazyVerticalGrid(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(240.dp)
                        .padding(bottom = 80.dp),
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(collectionState.data) { movie ->
                        MovieItem(movie = movie, navController)
                    }
                }
            }

            is UiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Error: ${collectionState.message}",
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


@Composable
fun MovieItem(movie: Movie, navController: NavController) {
    Column(
        modifier = Modifier
            .clickable { navController.navigate("NewScreen3/${movie.kinopoiskId}")}
            .width(111.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Card(
            modifier = Modifier

                .height(156.dp).fillMaxWidth()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = rememberImagePainter(data = movie.posterUrl),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )

            Box(
                modifier = Modifier
                    .padding(top = 2.dp, end = 4.dp, bottom = 2.dp, start = 4.dp)
                    .background(color = Color(0xFF3D3BFF), shape = RoundedCornerShape(4.dp))
                    .align(Alignment.TopEnd)
            ) {
                Text(
                    text = movie.ratingKinopoisk.toString(),
                    color = Color.White,
                    fontSize = 8.sp,
                    modifier = Modifier.padding(2.dp)
                )
            }
        }}
        Text(
            text = movie.nameRu ?: movie.nameEn ?: "Unknown",
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W500),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()


        )
        Text(text = movie.genres[0].genre, fontSize = 12.sp, color = Color.Gray)
    }
}
@Composable
fun MovieCollectionRow(
    title: String,
    collectionType: String,
    viewModel: MovieViewModel,
    onSeeAllClick: () -> Unit,
    navController: NavController
) {
    val collectionState = viewModel.moviesByCollectionMap[collectionType] ?: UiState.Loading

    LaunchedEffect(collectionType) {
        viewModel.fetchMoviesByCollection(collectionType)
    }

    Column {
        Row(
            modifier = Modifier
                .padding(start = 26.dp, end = 26.dp)
                .height(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.W600),
                modifier = Modifier.clickable { /* Handle title click if needed */ }
            )

            Text(
                text = "Все",
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W500, color = Color.Blue),
                modifier = Modifier.clickable { onSeeAllClick() }
            )
        }

        when (collectionState) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(156.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Success -> {
                LazyRow(
                    modifier = Modifier
                        .padding(start = 26.dp)
                        .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(collectionState.data) { movie ->
                        MovieItem(movie = movie, navController = navController)
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .height(156.dp)
                                .width(111.dp)
                                .clickable { onSeeAllClick() },
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(R.drawable.baseline_arrow_forward_24),
                                contentDescription = "See All"
                            )
                            Text(
                                text = "Показать все",
                                fontSize = 12.sp,
                                lineHeight = 13.2.sp
                            )
                        }
                    }
                }
            }

            is UiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(156.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Ошибка: ${collectionState.message}",
                        color = Color.Red,
                        style = TextStyle(fontSize = 14.sp)
                    )
                }
            }
        }
    }
}
