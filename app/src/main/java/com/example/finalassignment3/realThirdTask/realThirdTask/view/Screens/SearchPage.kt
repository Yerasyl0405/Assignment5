package com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.finalassignment3.R
import com.example.finalassignment3.realThirdTask.realThirdTask.model.FilmXX
import com.example.finalassignment3.realThirdTask.realThirdTask.model.Genre
import com.example.finalassignment3.realThirdTask.realThirdTask.view.viewmodel.SearchViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPage(viewModel: SearchViewModel = viewModel(), navController: NavController) {
    val moviesMap by viewModel.moviesBySearch.collectAsState()

    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle

    val selectedCountry = savedStateHandle?.getStateFlow("selectedCountry", "Россия")
        ?.collectAsState()?.value ?: "Россия"
    val selectedGenre = savedStateHandle?.getStateFlow("selectedGenre", "Комедия")
        ?.collectAsState()?.value ?: "Комедия"
    val selectedType = savedStateHandle?.getStateFlow("selectedOption", "VSE")
        ?.collectAsState()?.value ?: " Все"

    val selectedFromYear = savedStateHandle?.getStateFlow("selectedFromYear", 2000)
        ?.collectAsState()?.value ?: 2000
    val selectedToYear = savedStateHandle?.getStateFlow("selectedToYear", 2024)
        ?.collectAsState()?.value ?: 2024
    val sliderValue = savedStateHandle?.getStateFlow("sliderValue", 1f)
        ?.collectAsState()?.value?.toInt() ?: 1
    var searchText by remember { mutableStateOf("") }
    var str = "К сожалению, по вашему запросу"
    var str2 = "ничего не найдено"

    LaunchedEffect(searchText) {
        if (searchText.isNotEmpty()) {
            viewModel.fetchMoviesBySearch(searchText)
        }
    }

    var type= 0;

    Column(modifier = Modifier.fillMaxSize().padding(top= 20.dp)) {
        Row(
            modifier = Modifier
                .padding(start = 26.dp)
                .height(56.dp)
                .clip(shape = RoundedCornerShape(56.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            TextField(
                value = searchText,
                onValueChange = { newText -> searchText = newText },
                placeholder = {
                    Text(
                        text = "Фильмы, актёры, режиссёры",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
            )
            Spacer(modifier = Modifier.padding(start = 4.dp))
            Icon(
                imageVector = Icons.Default.Build,
                contentDescription = "Search",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp).clickable { navController.navigate("search_settings")}
            )
            Spacer(modifier = Modifier.padding(start = 16.dp))


        }
        var noFound = false;
        if(moviesMap.isEmpty()){
            noFound = false;
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            val movies = moviesMap[searchText]
            if (movies != null && movies.isNotEmpty()) {

                items(movies) { movie ->
                    var nur = false
                    var era = 0.0
                    if (movie.rating == "null") {
                        era = 0.0
                    } else {
                        era = movie.rating.toDouble()
                    }


                    if(selectedType=="VSE"){
                        type = 1;

                    }

                    movie.genres.forEach { genre ->
                        if (genre.genre == selectedGenre.toLowerCase()) {
                            nur = true
                        }
                    }

                    if (selectedToYear > movie.year.toInt() && selectedFromYear < movie.year.toInt() &&
                        nur && sliderValue.toDouble() < era && (selectedType.toString().toLowerCase() == movie.type.toLowerCase() || type==1)
                    ) {
str = ""
str2 = ""

                        MovieItem(movie = movie)
                    }
                }

                if (noFound==false ) {
                    item {
                        Column(modifier = Modifier.padding(start = 52.dp, top  = 50.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(str, fontWeight = FontWeight.W500, fontSize = 16.sp, lineHeight = 17.6.sp)
                            Text(str2, fontWeight = FontWeight.W500, fontSize = 16.sp, lineHeight = 17.6.sp)

                        }}
                }
            } else {

            }
        }
    }
}




@Composable
fun MovieItem(movie: FilmXX) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 26.dp)
        , verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier

                .height(132.dp).width(96.dp).clip(shape = RoundedCornerShape(4.dp))
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model  = movie.posterUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .padding(top = 2.dp, end = 4.dp, bottom = 2.dp, start = 4.dp).height(10.dp).width(17.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(4.dp))
                        .align(Alignment.TopStart)
                ) {
                    Text(
                        text = movie.rating.toString(),
                        color = Color.Black,
                        fontSize = 6.sp,
                        modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                    )
                }
            }}

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = movie.nameRu ?: "Unknown Title", fontSize = 14.sp, lineHeight = 15.4.sp, fontWeight = FontWeight.W600)

            Text(text = "${movie.year ?: "Unknown Year"} , ${movie.genres.firstOrNull()?.genre ?: "Unknown Genre"}", fontWeight = FontWeight.W400, fontSize = 12.sp , lineHeight = 13.2.sp, color = Color.Gray)

        }
    }
}
