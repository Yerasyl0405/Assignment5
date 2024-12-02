package com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SearchSettingsScreen(navController: NavController) {
    var selectedOption by remember { mutableStateOf("Все") }
    var sliderValue by remember { mutableStateOf(1f) }
    var selectedOption1 by remember { mutableStateOf("Дата") }
    var selectedCountry by remember { mutableStateOf("Россия") }
    var selectedGenre by remember { mutableStateOf("Комедия") }
    val selectedFromYear = remember { mutableStateOf(2000) }
    val selectedToYear = remember { mutableStateOf(2024) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Top bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                navController.previousBackStackEntry?.savedStateHandle?.set("selectedOption", selectedOption)
                navController.previousBackStackEntry?.savedStateHandle?.set("selectedCountry", selectedCountry)
                navController.previousBackStackEntry?.savedStateHandle?.set("selectedGenre", selectedGenre)
                navController.previousBackStackEntry?.savedStateHandle?.set("selectedFromYear", selectedFromYear.value)
                navController.previousBackStackEntry?.savedStateHandle?.set("selectedToYear", selectedToYear.value)
                navController.previousBackStackEntry?.savedStateHandle?.set("sliderValue", sliderValue.toInt())
                navController.popBackStack()
            }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Настройки поиска",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            listOf("Все", "FILM", "TV_SERIES").forEach { option ->
                Box(
                    modifier = Modifier
                        .selectable(
                            selected = selectedOption == option,
                            onClick = { selectedOption = option }
                        )
                        .padding(8.dp)
                        .background(
                            if (selectedOption == option) Color.Blue else Color.Transparent,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = option,
                        color = if (selectedOption == option) Color.White else Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Страна

        // Observe savedStateHandle for results
        val currentBackStackEntry = navController.currentBackStackEntry
        val savedStateHandle = currentBackStackEntry?.savedStateHandle

        savedStateHandle?.getStateFlow<Int>("selectedFromYear", -1)?.collectAsState()?.let {
            if (it.value != -1) selectedFromYear.value = it.value
        }

        savedStateHandle?.getStateFlow<Int>("selectedToYear", -1)?.collectAsState()?.let {
            if (it.value != -1) selectedToYear.value = it.value
        }




        // Listen for navigation result
        LaunchedEffect(navController.currentBackStackEntry) {
            navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("country")
                ?.observeForever { result ->
                    selectedCountry = result
                }
        }
        LaunchedEffect(navController.currentBackStackEntry) {
            navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("genre")
                ?.observeForever { result ->
                    selectedGenre = result
                }
        }
        SettingItem("Страна" ,  selectedCountry,navController,selectedCountry)
        SettingItem1("Жанр", selectedGenre,navController,selectedGenre)



        SettingItem2( "Год", "57",navController, selectedFromYear.value.toString(),
            selectedToYear.value.toString())



        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Рейтинг", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Slider(
                value = sliderValue,
                onValueChange = { sliderValue = it },
                valueRange = 1f..10f,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = sliderValue.toInt().toString(),
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Сортировать", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            listOf("Дата", "Популярность", "Рейтинг").forEach { option ->
                Box(
                    modifier = Modifier
                        .selectable(
                            selected = selectedOption1 == option,
                            onClick = { selectedOption1 = option }
                        )
                        .padding(8.dp)
                        .background(
                            if (selectedOption1 == option) Color.Blue else Color.Transparent,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = option,
                        color = if (selectedOption1 == option) Color.White else Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Checkbox for "Не просмотрен"
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = false, onCheckedChange = { /* Handle checkbox */ })
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Не просмотрен")
        }
    }
}

@Composable
fun SettingItem( label: String, value: String, navController: NavController, selectedCountry:String) {
    Row(
        modifier = Modifier
            .fillMaxWidth().clickable {                 navController.navigate("country_selection?selected=$selectedCountry")
            }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(text = value, color = Color.Gray, fontSize = 14.sp)
    }
}

@Composable
fun SettingItem1( label: String, value: String, navController: NavController, selectedCenre:String) {
    Row(
        modifier = Modifier
            .fillMaxWidth().clickable {                 navController.navigate("genre_selection?selected=$selectedCenre")
            }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(text = value, color = Color.Gray, fontSize = 14.sp)
    }
}
@Composable
fun SettingItem2( label: String, value: String, navController: NavController, selectedYear:String, selectedToYear: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth().clickable {                 navController.navigate("year_selection")
            }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(text = "$selectedYear $selectedToYear", color = Color.Gray, fontSize = 14.sp)
    }
}