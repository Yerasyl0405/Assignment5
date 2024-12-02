package com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.assignment3.model.Movie
import com.example.assignment3.view.PersonViewModel
import com.example.assignment3.view.StaffFilmViewModel
import com.example.finalassignment3.R
import com.example.finalassignment3.realThirdTask.realThirdTask.model.StaffDetail

@Composable
fun ActorPage(
    viewModel: PersonViewModel = viewModel(),
    viewModel1: StaffFilmViewModel = viewModel(),
    staffId: Int, navController: NavController
) {
    LaunchedEffect(staffId) {
        viewModel.fetchPersonDetails(staffId)
        viewModel.fetchPersonFilms(staffId)
    }

    val personFilms by viewModel.personFilms.collectAsState()
    val person by viewModel.personDetails.collectAsState()

    val filmList = remember { mutableStateListOf<Movie>() }
    LaunchedEffect(personFilms) {
        personFilms.take(10).forEach { film ->
            film?.filmId?.let { filmId ->
                viewModel1.fetchFilm(filmId)
            }
        }
    }

    val filmsState by viewModel1.filmState.collectAsState()

    LaunchedEffect(filmsState) {
        filmsState?.let {
            if (!filmList.contains(it)) {
                filmList.add(it)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        BackButton(navController)

        ActorInfoSection(person)

        Spacer(modifier = Modifier.height(16.dp))

        BestMoviesSection(filmList)

        Spacer(modifier = Modifier.height(16.dp))

        FilmographySection(navController, staffId, person)

        Spacer(modifier = Modifier.weight(1f))

    }
}

@Composable
fun BackButton(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(start = 26.dp, top = 16.dp)
                .height(24.dp)
                .width(24.dp)
                .clickable { navController.popBackStack() }) {
            Image(
                painter = painterResource(R.drawable.back),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .height(9.5.dp)
                    .width(5.5.dp)
            )
        }
    }
}

@Composable
fun ActorInfoSection(person: StaffDetail?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 26.dp),
    ) {
        Box(
            modifier = Modifier
                .height(201.dp)
                .width(146.dp)
                .background(Color.Gray, shape = RoundedCornerShape(4.dp))
        ) {
            AsyncImage(
                model = person?.posterUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = person?.nameRu ?: "dfdfdf",
                fontWeight = FontWeight.Bold
            )

            Text(
                text = person?.profession ?: "dfdf",
                color = Color.Gray
            )
        }
    }
}

@Composable
fun BestMoviesSection(personFilms: SnapshotStateList<Movie>) {
    Column(modifier = Modifier.padding(start = 35.dp, end = 26.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = "Лучшее",
                fontSize = 18.sp,
                fontWeight = FontWeight.W600
            )
            Text(
                text = "Все",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
    Column() {

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            item { Spacer(modifier = Modifier.padding(start = 19.dp)) }
            items(personFilms) { film ->
                MovieCard(
                    imageUrl = film.posterUrl ?: "",
                    title = film.nameRu ?: "Untitled",
                    genre = film.nameRu ?: "Unknown",
                    rating = ""
                )
            }
        }
    }
}


@Composable
fun MovieCard(imageUrl: String, title: String, genre: String, rating: String) {
    Column(
        modifier = Modifier
            .width(111.dp)
            .height(194.dp)
            .padding(start = 8.dp)
            .clip(RoundedCornerShape(4.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(154.dp)
                .background(Color.Gray)

        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxSize()
            )
        }

        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.W400,
            lineHeight = 15.4.sp,
            maxLines = 1,
        )
        Text(
            text = genre,
            fontWeight = FontWeight.W400,
            fontSize = 12.sp,
            lineHeight = 13.2.sp,
            maxLines = 1,
            color = Color.Gray
        )
    }
}

@Composable
fun FilmographySection(navController: NavController, staffId: Int, person: StaffDetail?) {
    Column(modifier = Modifier.padding(horizontal = 26.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Фильмография",
                fontSize = 18.sp,
                lineHeight = 19.8.sp,

                fontWeight = FontWeight.Bold
            )
            Text(
                text = "К списку",
                modifier = Modifier.clickable { navController.navigate(
                    "actor_screen/" + staffId + "/" + person?.nameRu) })

        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "44 фильма",
            color = Color.Gray
        )
    }
}
