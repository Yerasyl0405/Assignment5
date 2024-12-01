package com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.assignment3.model.Genre


@Composable
fun GenreselectionPage(navController: NavController, genre: String) {
    val genre = listOf("Комедия", "Мелодрама", "Боевик", "Вестерн", "Драма")
    var selectedGenre by remember { mutableStateOf("Комедия") }
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Жанр",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
            )
        }


        // Search Bar
        BasicTextField(
            value = searchText,
            onValueChange = { searchText = it },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color(0xFFF5F5F5), shape = MaterialTheme.shapes.small)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            decorationBox = { innerTextField ->
                if (searchText.isEmpty()) {
                    Text(
                        text = "Введите жанр",
                        color = Color.Gray
                    )
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Country List
        Column(modifier = Modifier.fillMaxSize()) {
            genre.forEach { country ->
                GenreListItem (
                    country = country,navController = navController,
                    isSelected = country == selectedGenre,
                    onClick = { selectedGenre = country },
                )
            }
        }
    }


}

@Composable
fun GenreListItem(country: String, navController: NavController, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {             navController.previousBackStackEntry?.savedStateHandle?.set(
                "genre",
                country)
                navController.popBackStack()

            }
            .background(if (isSelected) Color(0xFFE0E0E0) else Color.White)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = country,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}