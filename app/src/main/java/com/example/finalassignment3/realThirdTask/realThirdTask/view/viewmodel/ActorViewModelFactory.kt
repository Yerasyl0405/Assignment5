package com.example.finalassignment3.realThirdTask.realThirdTask.view.viewmodel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.finalassignment3.realThirdTask.realThirdTask.model.Actor
import com.example.finalassignment3.realThirdTask.realThirdTask.model.Film
import com.example.finalassignment3.realThirdTask.realThirdTask.model.Filmm
import com.example.finalassignment3.realThirdTask.realThirdTask.network.ActorRepository
import com.example.finalassignment3.realThirdTask.realThirdTask.network.FilmRepository



class ActorViewModelFactory(private val repository: ActorRepository, private val filmRepository: FilmRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ActorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ActorViewModel(repository, filmRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
@Composable
fun CategorySelector(
    categories: List<String>,
    selectedCategory: String?,
    onCategorySelected: (String) -> Unit
) {
    LazyRow(modifier = Modifier.padding(8.dp)) {
        items(categories) { category ->
            Button(
                onClick = { onCategorySelected(category) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (category == selectedCategory) MaterialTheme.colors.primary
                    else MaterialTheme.colors.surface
                ),
                modifier = Modifier.padding(horizontal = 4.dp),
                shape = RoundedCornerShape(50.dp)
            ) {
                Text(text = category, color = if (category == selectedCategory) Color.White else Color.Black)
            }
        }
    }
}
@Composable
fun ActorScreen(viewModel: ActorViewModel, Id: Int, navController: NavController, person: String) {
    val actor by viewModel.actor.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Id) {
        viewModel.fetchActorDetails(Id)
    }

    when {
        isLoading -> {
            CircularProgressIndicator()
        }
        error != null -> {
            Text(text = "Error: $error", color = Color.Red, modifier = Modifier.fillMaxSize())
        }
        actor != null -> {
            CategorizedFilmScreen(films = actor!!.films, navController = navController, person = person)
        }
    }
}

fun categorizeFilms(films: List<Filmm>): Map<String, List<Filmm>> {
    return films.groupBy { film ->
        film.professionKey ?: "Неизвестная категория"
    }
}
@Composable
fun CategorizedFilmScreen(films: List<Filmm>, navController: NavController, person: String?) {
    val categorizedFilms = categorizeFilms(films)
    val categories = categorizedFilms.keys.toList()

    var selectedCategory by remember { mutableStateOf(categories.firstOrNull()) }

    Column(modifier = Modifier.fillMaxSize()){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween){
            Image(painter = painterResource(id = com.example.finalassignment3.R.drawable.icon), contentDescription = null, modifier = Modifier.align(
                Alignment.CenterVertically).clickable { navController.popBackStack()} )
            androidx.compose.material3.Text(
                text = "Фильмография",
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W500),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis


            )
        }

        CategorySelector(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = { category ->
                selectedCategory = category
            }
        )

        // Список фильмов для выбранной категории
        val selectedFilms = selectedCategory?.let { category ->
            categorizedFilms[category].orEmpty()
        }.orEmpty()

        LazyColumn {
            items(selectedFilms) { film ->
                FilmCard(film = film)
            }
        }
    }
}





@Composable
fun FilmCard(film: Filmm) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            film.posterUrl?.let { posterUrl ->
                AsyncImage(
                    model = posterUrl,
                    contentDescription = film.nameRu ?: film.nameEn,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colors.surface)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = film.nameRu ?: film.nameEn ?: "Без названия",
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = film.description ?: "Описание отсутствует",
                    style = MaterialTheme.typography.body2
                )
            }
            Text(
                text = film.rating ?: "Нет рейтинга",
                modifier = Modifier.align(Alignment.CenterVertically),
                style = MaterialTheme.typography.caption
            )
        }
    }
}