package com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens



import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.finalassignment3.realThirdTask.realThirdTask.view.navigation.BottomNavigation
import com.example.finalassignment3.realThirdTask.realThirdTask.view.navigation.NavGraph
import com.example.finalassignment3.realThirdTask.realThirdTask.view.viewmodel.ActorViewModel
import com.example.finalassignment3.realThirdTask.realThirdTask.view.viewmodel.SearchViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Main_Screen(viewModell:ActorViewModel,viewModel: SearchViewModel) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {
        NavGraph(navHostController = navController, viewModell ,viewModel)
    }

}
