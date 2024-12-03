package com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SearchSettingsScreen(navController: NavController) {
    var selectedOption by remember { mutableStateOf("VSE") }
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
            .padding(top = 16.dp, end = 16.dp)
    ) {
        // Top bar
        Row(
            modifier = Modifier.fillMaxWidth().height(50.dp).padding(start = 16.dp),
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
            }, modifier = Modifier) {
                Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Back")
            }
            Text(
                text = "Настройки поиска",
                style = TextStyle(fontSize =12.sp , lineHeight = 13.2.sp , fontWeight = FontWeight.W600),
                modifier = Modifier.padding(start = 60.dp)
            )
        }
        Text(text = "Показывать" , modifier = Modifier.padding(start = 26.dp)  , fontWeight = FontWeight.W400, lineHeight = 13.2.sp , fontSize = 12.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(26.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(33.dp).padding(start = 25.dp)

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(77.dp)
                    .selectable(
                        selected = selectedOption == "VSE",
                        onClick = { selectedOption = "VSE" }
                    )
                    .clip(RoundedCornerShape(topStart = 56.dp, bottomStart = 56.dp)).border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(bottomStart = 56.dp, topStart = 56.dp))
                    .background(
                        if (selectedOption == "VSE") Color.Blue else Color.Transparent,
                        shape = MaterialTheme.shapes.small
                    )
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "VSE",
                    color = if (selectedOption == "VSE") Color.White else Color.Black,
                    fontWeight = FontWeight.W500
                )
            }


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(131.dp).border(width = 1.dp, color = Color.Black)
                    .selectable(
                        selected = selectedOption == "FILM",
                        onClick = { selectedOption = "FILM" }
                    )
                    .background(
                        if (selectedOption == "FILM") Color.Blue else Color.Transparent,
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "FILM",
                    color = if (selectedOption == "FILM") Color.White else Color.Black,
                    fontWeight = FontWeight.W500
                )
            }


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(100.dp)
                    .selectable(
                        selected = selectedOption == "TV_SERIES",
                        onClick = { selectedOption = "TV_SERIES" }
                    )
                    .clip(RoundedCornerShape(topEnd = 56.dp, bottomEnd = 56.dp)).border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(bottomEnd = 56.dp, topEnd = 56.dp))
                    .background(
                        if (selectedOption == "TV_SERIES") Color.Blue else Color.Transparent,
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "TV_SERIES",
                    color = if (selectedOption == "TV_SERIES") Color.White else Color.Black,
                    fontWeight = FontWeight.W500
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        val currentBackStackEntry = navController.currentBackStackEntry
        val savedStateHandle = currentBackStackEntry?.savedStateHandle

        savedStateHandle?.getStateFlow<Int>("selectedFromYear", -1)?.collectAsState()?.let {
            if (it.value != -1) selectedFromYear.value = it.value
        }

        savedStateHandle?.getStateFlow<Int>("selectedToYear", -1)?.collectAsState()?.let {
            if (it.value != -1) selectedToYear.value = it.value
        }




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
        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))

        SettingItem1("Жанр", selectedGenre,navController,selectedGenre)
        Spacer(modifier = Modifier.height(8.dp))

HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))


        SettingItem2( "Год", "57",navController, selectedFromYear.value.toString(),
            selectedToYear.value.toString())
        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider()


        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Рейтинг", fontWeight = FontWeight.W400, fontSize = 16.sp, lineHeight = 17.6.sp, modifier = Modifier.padding(start = 16.dp))
        Column (
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp).height(70.dp),
            verticalArrangement = Arrangement.Center) {
            Slider(
                value = sliderValue,
                onValueChange = { sliderValue = it },
                valueRange = 1f..10f,
                modifier = Modifier.weight(1f).background(Color.White)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Row(            modifier = Modifier.fillMaxWidth().padding(end = 5.dp), horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {

                Text(text = "1")
            Text(
                text = sliderValue.toInt().toString(),
                fontWeight = FontWeight.W300
            )
        }}

        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))


        Text(text = "Сортировать", fontWeight = FontWeight.W400, fontSize = 16.sp, lineHeight = 17.6.sp, modifier = Modifier.padding(start = 16.dp, bottom = 16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(33.dp).padding(start = 25.dp)
        ) {
                Box(
                    modifier = Modifier.width(77.dp)
                        .selectable(
                            selected = selectedOption1 == "Дата",
                            onClick = { selectedOption1 = "Дата" }
                        )
                        .clip(RoundedCornerShape(topStart = 56.dp, bottomStart = 56.dp)).border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(bottomStart = 56.dp, topStart = 56.dp))
                        .background(
                            if (selectedOption1 == "Дата") Color.Blue else Color.Transparent,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Дата",
                        color = if (selectedOption1 == "Дата") Color.White else Color.Black,
                        fontWeight = FontWeight.W500
                    )

            }
            Box(
                modifier = Modifier.width(131.dp)
                    .selectable(
                        selected = selectedOption1 == "Популярность",
                        onClick = { selectedOption1 = "Популярность" }
                    )
                    .border(width = 1.dp, color = Color.Black, )
                    .background(
                        if (selectedOption1 == "Популярность") Color.Blue else Color.Transparent,
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Популярность",
                    color = if (selectedOption1 == "Популярность") Color.White else Color.Black,
                    fontWeight = FontWeight.W500
                )

            }
            Box(
                modifier = Modifier.width(100.dp)
                    .selectable(
                        selected = selectedOption1 == "Рейтинг",
                        onClick = { selectedOption1 = "Рейтинг" }
                    )
                    .clip(RoundedCornerShape(topEnd = 56.dp, bottomEnd = 56.dp)).border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(bottomEnd = 56.dp, topEnd = 56.dp))
                    .background(
                        if (selectedOption1 == "Рейтинг") Color.Blue else Color.Transparent,
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Рейтинг",
                    color = if (selectedOption1 == "Рейтинг") Color.White else Color.Black,
                    fontWeight = FontWeight.W500
                )

            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = false, onCheckedChange = { })
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
            .padding(vertical = 8.dp, ).padding(start = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontWeight = FontWeight.W400, fontSize = 16.sp, lineHeight = 17.6.sp)
        Text(text = value, color = Color.Gray, fontSize = 14.sp, lineHeight = 15.4.sp , fontWeight = FontWeight.W400)
    }
}

@Composable
fun SettingItem1( label: String, value: String, navController: NavController, selectedCenre:String) {
    Row(
        modifier = Modifier
            .fillMaxWidth().clickable {                 navController.navigate("genre_selection?selected=$selectedCenre")
            }
            .padding(vertical = 8.dp).padding(start = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontWeight = FontWeight.W400, fontSize = 16.sp, lineHeight = 17.6.sp)
        Text(text = value, color = Color.Gray, fontSize = 14.sp, lineHeight = 15.4.sp , fontWeight = FontWeight.W400)
    }
}
@Composable
fun SettingItem2( label: String, value: String, navController: NavController, selectedYear:String, selectedToYear: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth().clickable {                 navController.navigate("year_selection")
            }
            .padding(vertical = 8.dp).padding(start = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontWeight = FontWeight.W400, fontSize = 16.sp, lineHeight = 17.6.sp)
        Text(text = value, color = Color.Gray, fontSize = 14.sp, lineHeight = 15.4.sp , fontWeight = FontWeight.W400)
    }
}