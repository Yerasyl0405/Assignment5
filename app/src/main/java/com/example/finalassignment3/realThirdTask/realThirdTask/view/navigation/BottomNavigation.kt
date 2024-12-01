package com.example.finalassignment3.realThirdTask.realThirdTask.view.navigation


import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.finalassignment3.realThirdTask.realThirdTask.model.BottomItem

@Composable
fun BottomNavigation(

    navController: NavController
) {

    val listItems = listOf(
        BottomItem.Screen1,
        BottomItem.Screen2,
        BottomItem.Screen3
    )

    androidx.compose.material.BottomNavigation(
        backgroundColor = Color.White,


    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        listItems.forEach { item ->

            BottomNavigationItem(
                modifier = androidx.compose.ui.Modifier.align(Alignment.CenterVertically).height(80.dp),
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
                },

                unselectedContentColor = Color.Gray,
                selectedContentColor = Color.Blue,
                icon = {

                    Icon(painter = painterResource(id = item.iconId), modifier = androidx.compose.ui.Modifier.size(30.dp), contentDescription = "Icon" , )
                }



            )

        }
    }
}