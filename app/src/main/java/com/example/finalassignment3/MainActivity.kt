package com.example.finalassignment3
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.assignment3.network.RetrofitInstance
import com.example.assignment3.network.RetrofitInstance.api
import com.example.assignment3.view.FilmImagesViewModel
import com.example.finalassignment3.realThirdTask.realThirdTask.network.ActorRepository
import com.example.finalassignment3.realThirdTask.realThirdTask.network.FilmRepository
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.ActorPage
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.CountryListItem
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.CountrySelectionScreen
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.FilmScreen
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.FullStaffList
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.LoadingPage
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.ProfileScreen
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.SearchPage
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.SearchSettingsScreen
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.SimilarFilmScreen
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.StaffListScreen
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.YearSelectionScreen
import com.example.finalassignment3.realThirdTask.realThirdTask.view.navigation.NavigationClass
import com.example.finalassignment3.realThirdTask.realThirdTask.view.viewmodel.ActorViewModel
import com.example.finalassignment3.realThirdTask.realThirdTask.view.viewmodel.ActorViewModelFactory
import com.example.finalassignment3.realThirdTask.realThirdTask.view.viewmodel.SearchViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel =  SearchViewModel(RetrofitInstance.api)
        val actorRepository = ActorRepository()
        val filmRepository = FilmRepository(RetrofitInstance.apii)
        val viewModell: ActorViewModel = ViewModelProvider(
            this,
            ActorViewModelFactory(actorRepository, filmRepository)
        ).get(ActorViewModel::class.java)

        setContent {
           // CountrySelectionScreen()
           // SearchSettingsScreen()
           // SearchPage()
            //ProfileScreen()
           // LoadingPage()...
          // ActorPage(staffId = 659)
           // FullStaffList(kinopoiskid = 963)
            //SimilarFilmScreen(filmId = 734)
          //FilmScreen(kinopoiskId = 658)
            NavigationClass(viewModell,viewModel)

        }
    }
}

@Composable
fun MainScreen(navController: NavController, doText: Int, imagename: Int, des: String) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 80.dp, start = 26.dp, end = 26.dp)

                .height(18.24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .width(120.dp)
                    .height(18.24.dp)
                    .fillMaxSize(), horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(R.drawable.vector__1_),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize()
                )

            }
            Image(
                painter = painterResource(id = R.drawable.frame_7299),
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 99.dp)
                    .fillMaxSize()
                    .clickable {
                        navController.navigate("newScreen")
                    }
            )
        }

        Row(
            modifier = Modifier
                .padding(top = 166.76.dp)
                .fillMaxWidth()
                .height(270.dp)
        ) {
            Image(
                painter = painterResource(id = imagename),
                contentDescription = "",
                modifier = Modifier.fillMaxSize()
            )


        }
        Row(
            modifier = Modifier
                .padding(top = 67.24.dp, start = 26.dp)
                .width(201.dp)
                .height(70.dp)
        ) {
            Image(
                painter = painterResource(id = doText),
                contentDescription = "",
                modifier = Modifier.fillMaxSize()
            )

        }

    }

}



