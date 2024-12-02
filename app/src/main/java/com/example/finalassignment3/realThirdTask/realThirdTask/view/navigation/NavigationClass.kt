package com.example.finalassignment3.realThirdTask.realThirdTask.view.navigation


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.finalassignment3.R
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.ActorPage
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.ScreenPager
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.Main_Screen
import com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens.FilmScreen
import com.example.finalassignment3.realThirdTask.realThirdTask.view.viewmodel.ActorViewModel
import com.example.finalassignment3.realThirdTask.realThirdTask.view.viewmodel.SearchViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavigationClass(viewModell: ActorViewModel,viewModel: SearchViewModel) {

    val navController = rememberNavController()
    
    androidx.compose.material3.Scaffold(
        content = {
            NavHost(
                navController = navController,
                startDestination = "pagerScreen"
            ) {
                composable("pagerScreen") {
                    ScreenPager(navController)
                }
                composable("newScreen") {
                    Main_Screen(viewModell,viewModel)
                }
                composable(
                    route = "NewScreen3/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getInt("id") ?: 0

                    FilmScreen(kinopoiskId = id,navController= navController)
                }
                composable("ActorPage/{staffId}") { backStackEntry ->
                    val staffId = backStackEntry.arguments?.getString("staffId")?.toIntOrNull()
                    if (staffId != null) {
                        ActorPage(staffId = staffId, navController = navController)
                    }

                }
                composable("FullStaffList"){
                  NewPage(navController)
                }
            }
        }
    )
}

  @Composable
fun NewPage(navController: NavController){
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
    Box(modifier = Modifier.width(50.dp).height(50.dp)) {
        Image(
            painter = painterResource(R.drawable.back),
            contentDescription = null,
            modifier = Modifier.fillMaxSize().clickable { navController.popBackStack() })
    }

    }

}