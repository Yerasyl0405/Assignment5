package com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.assignment3.view.KinoViewModel
import com.example.assignment3.view.MovieViewModel
import com.example.finalassignment3.R
import com.example.finalassignment3.realThirdTask.realThirdTask.model.UiState

@Composable
fun WantToWatchPage(
    collectionTypes: String,
    viewModel: MovieViewModel,
    navController: NavController,
    viewModel2: KinoViewModel
) {
    val wantToWatchMovies by viewModel2.wantToWatchMovies.collectAsState()




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
            IconButton(onClick = { viewModel2.clearWantToWatchMovies() }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Clear Watched Movies",
                    tint = Color.Black
                )
            }
        }
        LazyVerticalGrid(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(240.dp)
                .padding(bottom = 80.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(wantToWatchMovies) { film ->
                MovieCard(
                    film
                )
            }

        }



    }
}