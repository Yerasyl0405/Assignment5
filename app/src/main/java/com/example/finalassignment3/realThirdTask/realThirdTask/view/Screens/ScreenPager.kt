package com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finalassignment3.MainScreen
import com.example.finalassignment3.realThirdTask.realThirdTask.model.datapro
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScreenPager(navController: NavController) {



    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        HorizontalPager(
            count = 3,
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->

            when (page) {
                0 -> MainScreen(navController, datapro[page].comments, datapro[page].ima, datapro[page].des)
                1 -> MainScreen(navController, datapro[page].comments, datapro[page].ima, datapro[page].des)
                2 -> MainScreen(navController, datapro[page].comments, datapro[page].ima, datapro[page].des)
            }
        }

        Row(modifier = Modifier.padding(start = 26.dp , bottom = 48.dp),
        ) {
            repeat(3) { index ->
                val color = if (pagerState.currentPage == index) Color.Black else Color.Gray
                Box(
                    modifier = Modifier.size(8.dp).padding(2.dp).background(color, CircleShape)
                )
            }
        }
    }
}
