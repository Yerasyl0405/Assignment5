package com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
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
fun SearchPage(viewModel: SearchViewModel = viewModel() , navController: NavController) {
    val moviesMap by viewModel.moviesBySearch.collectAsState()

    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle

    val selectedCountry = savedStateHandle?.getStateFlow("selectedCountry", "Россия")
        ?.collectAsState()?.value ?: "Россия"
    val selectedGenre = savedStateHandle?.getStateFlow("selectedGenre", "Комедия")
        ?.collectAsState()?.value ?: "Комедия"
    val selectedType = savedStateHandle?.getStateFlow("selectedOption", "Все")
    ?.collectAsState()?.value ?: " Все"

    val selectedFromYear = savedStateHandle?.getStateFlow("selectedFromYear", 2000)
        ?.collectAsState()?.value ?: 2000
    val selectedToYear = savedStateHandle?.getStateFlow("selectedToYear", 2024)
        ?.collectAsState()?.value ?: 2024
    val sliderValue = savedStateHandle?.getStateFlow("sliderValue", 1f)
        ?.collectAsState()?.value?.toInt() ?: 1
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(searchText) {
        if (searchText.isNotEmpty()) {
            viewModel.fetchMoviesBySearch(searchText)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.padding(start = 26.dp, end = 26.dp).height(56.dp).clip(shape = RoundedCornerShape(56.dp)), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp) // Adjust the icon size
            )
            TextField(
                value = searchText,
                onValueChange = { newText -> searchText = newText },

                placeholder = {
                    Text(
                        text = "Фильмы, актёры, режиссёры", // Placeholder text
                        fontSize = 14.sp, // Adjust font size for better readability
                        color = Color.Gray // Placeholder text color
                    )
                },
                singleLine = true, // Ensure single-line text input
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent, // Make the TextField's container transparent
                    focusedIndicatorColor = Color.Transparent, // Remove focus indicator
                    unfocusedIndicatorColor = Color.Transparent, // Remove unfocused indicator
                ),

            )
            Box(modifier = Modifier.height(40.dp).width(40.dp).clickable { navController.navigate("search_settings")}){
            Image(
              painter = painterResource(R.drawable.filter), contentDescription = "",
                 // Adjust the icon size
                modifier = Modifier.fillMaxSize()
            )
        }}
        var era = 0.0;
        var nur = false;

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            val movies = moviesMap[searchText]
                if (movies != null && movies.isNotEmpty()) {
                    items(movies) { movie ->
                        if(movie.rating=="null"){
                            era = 0.0
                        }else{
                            era = movie.rating.toDouble()
                        }
                        movie.genres.forEach(){genre->
                            if(genre.genre==selectedGenre.toLowerCase()){
                                nur=true


                            }


                        }



                            if (selectedToYear > movie.year.toInt() && selectedFromYear < movie.year.toInt() && nur==true && sliderValue.toDouble()<era && selectedType.toString().toLowerCase()==movie.type.toLowerCase()){
                                MovieItem(movie = movie)

                        }
                    }
            } else {
                item {
                    Text("No movies found.", modifier = Modifier.padding(16.dp))
                }
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
