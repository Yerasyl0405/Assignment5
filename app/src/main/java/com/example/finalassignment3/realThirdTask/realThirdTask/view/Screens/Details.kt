package com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable


import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.produceState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.assignment3.model.Movie
import com.example.assignment3.view.FilmViewModel

import com.example.finalassignment3.R

@Composable
fun FilmScreen(
    viewModel: FilmViewModel = viewModel(),
    kinopoiskId: Int,
    navController: NavController
) {
    val scrollState = rememberLazyListState(
        initialFirstVisibleItemIndex = viewModel.scrollPositionIndex,
        initialFirstVisibleItemScrollOffset = viewModel.scrollPositionOffset
    )
    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.firstVisibleItemIndex to scrollState.firstVisibleItemScrollOffset }
            .collect { (index, offset) ->
                viewModel.scrollPositionIndex = index
                viewModel.scrollPositionOffset = offset
            }
    }
    LaunchedEffect(kinopoiskId) {
        viewModel.fetchFilm(kinopoiskId)
    }

    val filmState by viewModel.filmState.collectAsState()

    when (filmState) {
        is FilmScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is FilmScreenState.Success -> {
            val film = (filmState as FilmScreenState.Success<Movie>).data
            MainScreenLazy(film, kinopoiskId, navController)
        }

        is FilmScreenState.Error -> {
            val errorMessage = (filmState as FilmScreenState.Error).message
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = errorMessage, color = Color.Red)
            }
        }
    }
}


sealed class FilmScreenState<out T> {
    object Loading : FilmScreenState<Nothing>()
    data class Success<T>(val data: T) : FilmScreenState<T>()
    data class Error(val message: String) : FilmScreenState<Nothing>()
}

@Composable
fun MainScreenLazy(film: Movie?, kinopoiskId: Int, navController: NavController) {

    LazyColumn(
        modifier = Modifier.padding(bottom = 70.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        item {
            Head(film = film, navController)
        }
        item {
            descrip(film)
        }
        item {
            StaffListScreen(kinopoiskid = kinopoiskId, navController = navController)
        }
        item { FullStaffList(kinopoiskid = kinopoiskId, navController = navController) }

        item {
            FilmImagesScreen(filmId = kinopoiskId, navController)
        }
        item {
            SimilarFilmScreen(filmId = kinopoiskId, navController)
        }


    }
}

@Composable
fun Head(film: Movie?, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0x001B1B1B),
                        Color(0xFF1B1B1B),

                        )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        film?.let {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .padding(top = 30.dp, start = 26.dp)
                        .width(24.dp)
                        .clickable { navController.navigate("NewScreen") }
                        .height(24.dp)
                        .clickable { navController.popBackStack() }
                ) {
                    Image(
                        painter = painterResource(R.drawable.back),
                        contentDescription = "",
                        modifier = Modifier
                            .width(5.5.dp)
                            .height(9.5.dp)
                    )
                }
            }
            Image(
                painter = rememberImagePainter(it.webUrl),
                contentDescription = "",
                modifier = Modifier
                    .padding(top = 186.dp)
                    .height(40.dp)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "${it.ratingKinopoisk}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W500,
                    lineHeight = 13.2.sp,
                    color = Color(0xFFB5B5C9)
                )
                Text(
                    text = "${it.nameRu}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W500,
                    lineHeight = 13.2.sp,
                    color = Color(0xFFB5B5C9)
                )
            }
            Spacer(modifier = Modifier.padding(2.dp))


            Row {
                Text(
                    text = "${it.year}, ",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W500,
                    lineHeight = 13.2.sp,
                    color = Color(0xFFB5B5C9)
                )
                Text(
                    text = "${it.genres[0].genre}, ",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400,
                    lineHeight = 13.2.sp,
                    color = Color(0xFFB5B5C9)
                )
                Text(
                    text = "${it.genres[1].genre} ",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400,
                    lineHeight = 13.2.sp,
                    color = Color(0xFFB5B5C9)
                )

            }
            Spacer(modifier = Modifier.padding(2.dp))

            Row {
                val hoour = it.filmLength % 60
                val min = it.filmLength / 60
                Text(
                    text = "${it.countries[0].country}, ",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400,
                    lineHeight = 13.2.sp,
                    color = Color(0xFFB5B5C9)
                )
                Text(
                    text = "${min} ч ${hoour} мин, ",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400,
                    lineHeight = 13.2.sp,
                    color = Color(0xFFB5B5C9)
                )
                Text(
                    text = "${it.ratingAgeLimits.removeRange(0, 3)}+",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400,
                    lineHeight = 13.2.sp,
                    color = Color(0xFFB5B5C9)
                )
            }
            Spacer(modifier = Modifier.padding(2.dp))



            Row(modifier = Modifier.padding(top = 10.dp)) {
                Image(
                    painter = painterResource(R.drawable.heart),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                        .height(18.dp)
                        .width(18.dp)
                )
                Spacer(modifier = Modifier.padding(6.dp))
                Image(
                    painter = painterResource(R.drawable.saved),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                        .height(18.dp)
                        .width(18.dp)
                )
                Spacer(modifier = Modifier.padding(6.dp))

                Image(
                    painter = painterResource(R.drawable.eye),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                        .height(18.dp)
                        .width(18.dp)
                )
                Spacer(modifier = Modifier.padding(6.dp))

                Image(
                    painter = painterResource(R.drawable.share),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                        .height(18.dp)
                        .width(18.dp)
                )
                Spacer(modifier = Modifier.padding(6.dp))

                Image(
                    painter = painterResource(R.drawable.all),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                        .height(18.dp)
                        .width(18.dp)
                )
            }
        }
    }
}


@Composable
fun descrip(film: Movie?) {
    film?.let {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(start = 26.dp, end = 26.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "${film.shortDescription}",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W700,
                    lineHeight = 22.sp
                ),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.padding(10.dp))
            Text(
                text = "${it.description}",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    lineHeight = 22.sp
                ),
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

