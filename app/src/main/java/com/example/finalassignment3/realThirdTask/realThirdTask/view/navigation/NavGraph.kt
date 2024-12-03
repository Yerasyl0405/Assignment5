package com.example.finalassignment3.realThirdTask.realThirdTask.view.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.assignment3.model.Movie
import com.example.assignment3.model.MovieCollectionResponse
import com.example.assignment3.model.Type
import com.example.assignment3.network.RetrofitInstance
import com.example.assignment3.network.RetrofitInstance.api
import com.example.assignment3.view.MovieViewModel
import com.example.finalassignment3.realThirdTask.realThirdTask.model.UiState
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.CountrySelectionScreen
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.FavouritesPage

import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.FilmScreen
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.FullGalleryScreen
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.GenreselectionPage
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.LoadingPage
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.MovieCollectionRow
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.MoviesByCollectionScreen
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.SearchPage
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.SearchSettingsScreen
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.WantToWatchPage
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.YearSelectionScreen
import com.example.finalassignment3.realThirdTask.realThirdTask.view.viewmodel.ActorScreen
import com.example.finalassignment3.realThirdTask.realThirdTask.view.viewmodel.ActorViewModel
import com.example.finalassignment3.realThirdTask.realThirdTask.view.viewmodel.SearchViewModel
import retrofit2.Response

@Composable
fun NavGraph(
    navHostController: NavHostController,viewModell: ActorViewModel,viewModel1: SearchViewModel
) {
    val viewModel: MovieViewModel = remember { MovieViewModel(RetrofitInstance.api) }
    NavHost(navController = navHostController, startDestination = "MovieScreen"){
        composable("MovieScreen") { MovieScreen(viewModel, navHostController) }
        composable("MoviesByCollectionScreen/{collectionType}/{collectionTypes}") { backStackEntry ->
            val collectionType = backStackEntry.arguments?.getString("collectionType") ?: ""
            val collectionTypes = backStackEntry.arguments?.getString("collectionTypes") ?: ""
            MoviesByCollectionScreen(collectionTypes, viewModel, collectionType, navHostController)
        }
        composable("screen_2") {
            SearchPage(viewModel1,navController = navHostController)
        }
        composable("search_settings"){
            SearchSettingsScreen(navController = navHostController)
        }




        composable("full_gallery/{filmId}"){ backStackEntry ->
            val filmId  = backStackEntry.arguments?.getString("filmId")?.toIntOrNull()
            if (filmId != null){
                FullGalleryScreen(filmId= filmId, navController = navHostController)
            }

        }
        composable("actor_screen/{staffId}/{person}") { backStackEntry ->
            val person = backStackEntry.arguments?.getString("person") ?: ""
            val staffId = backStackEntry.arguments?.getString("staffId")?.toIntOrNull()
            if (staffId != null) {
                ActorScreen(viewModell, Id = staffId, navController = navHostController, person)}
        }
        composable(
            route = "NewScreen3/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0

            FilmScreen(kinopoiskId = id, navController = navHostController)
        }
        composable("ActorPage/{staffId}") { backStackEntry ->
            val staffId = backStackEntry.arguments?.getString("staffId")?.toIntOrNull()
            if (staffId != null) {
                com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.ActorPage(
                    staffId = staffId, navController = navHostController
                )
            }
        }
        composable(
                route = "country_selection",
        arguments = listOf(
            navArgument("selectedCountry") {
                type = NavType.StringType
                defaultValue = ""
            }
        )
        ) { backStackEntry ->
        val selectedCountry = backStackEntry.arguments?.getString("selectedCountry") ?: ""
        CountrySelectionScreen(navHostController, selectedCountry)
    }
        composable(
            route = "genre_selection",
            arguments = listOf(
                navArgument("selectedGenre") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val selectedCenre = backStackEntry.arguments?.getString("selectedGenre") ?: ""
            GenreselectionPage(navHostController, selectedCenre)
        }

        composable("FullStaffList"){
            NewPage(navController = navHostController)
        }
        composable("year_selection") {
            YearSelectionScreen(navHostController)
        }
    }
}
@Composable
fun MovieScreen(viewModel: MovieViewModel, navController: NavController) {
    val collectionTypes = listOf(
        Type(type = "TOP_POPULAR_ALL"),
        Type(type = "TOP_POPULAR_MOVIES"),
        Type(type = "TOP_250_MOVIES"),
        Type(type = "TOP_250_TV_SHOWS"),
        Type(type = "VAMPIRE_THEME"),
        Type(type = "COMICS_THEME")
    )
    val collectionTypess = listOf(
        Type(type = "Популярное"),
        Type(type = "Популярные фильмы"),
        Type(type = "Топ 250 фильмов"),
        Type(type = "Топ 250 сериалов"),
        Type(type = "Фильмы про вампиров"),
        Type(type = "Фильмы про комиксы")
    )
    val scrollState = rememberLazyListState(
        initialFirstVisibleItemIndex = viewModel.scrollPositionIndex,
        initialFirstVisibleItemScrollOffset = viewModel.scrollPositionOffset
    )
    val collectionState = viewModel.moviesByCollectionMap[collectionTypes[0].type] ?: UiState.Loading
    LaunchedEffect(collectionTypes[0].type) {
        viewModel.fetchMoviesByCollection(collectionTypes[0].type)
    }
    LaunchedEffect(collectionTypes[1].type) {
        viewModel.fetchMoviesByCollection(collectionTypes[1].type)
    }
    LaunchedEffect(collectionTypes[2].type) {
        viewModel.fetchMoviesByCollection(collectionTypes[2].type)
    }
    LaunchedEffect(collectionTypes[3].type) {
        viewModel.fetchMoviesByCollection(collectionTypes[3].type)
    }
    LaunchedEffect(collectionTypes[4].type) {
        viewModel.fetchMoviesByCollection(collectionTypes[4].type)
    }
    LaunchedEffect(collectionTypes[5].type) {
        viewModel.fetchMoviesByCollection(collectionTypes[5].type)
    }



    val era = true
    when (collectionState) {
        is UiState.Loading -> {
LoadingPage()        }

        is UiState.Success -> {


            LazyColumn(
                state = scrollState,
                verticalArrangement = Arrangement.spacedBy(36.12.dp),
                modifier = Modifier.fillMaxSize().padding(bottom = 80.dp),
                contentPadding = PaddingValues(top = 97.dp)
            ) {

                item {
                    Image(
                        painter = painterResource(id = com.example.finalassignment3.R.drawable.vector__1_),
                        contentDescription = null,
                        modifier = Modifier.padding(start = 26.dp)
                    )
                }
                for (index in collectionTypes.indices) {


                    item() {
                        MovieCollectionRow(
                            title = collectionTypess[index].type,
                            collectionType = collectionTypes[index].type,
                            viewModel = viewModel,
                            onSeeAllClick = { navController.navigate("MoviesByCollectionScreen/" + collectionTypes[index].type + "/" + collectionTypess[index].type) },
                            navController
                        )

                    }
                }
            }
        }

        is UiState.Error ->     throw NotImplementedError("MovieScreen functionality is not implemented yet")



    }
}



@Composable
fun Screen2(){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Text(text="Screen 2")}}
@Composable
fun Screen3(){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Text(text="Screen 3")}}



