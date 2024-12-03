package com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable


import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.assignment3.model.Movie
import com.example.assignment3.view.FilmViewModel

@Composable
fun YearSelectionScreen(navController: NavController) {
    val currentRangeFrom = remember { mutableStateOf(1998..2009) }
    val selectedYearFrom = remember { mutableStateOf(1998) }

    val currentRangeTo = remember { mutableStateOf(1998..2009) }
    val selectedYearTo = remember { mutableStateOf(2009) }



    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item{
        Row(
            modifier = Modifier
                .fillMaxWidth().height(60.dp)
                ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.material.IconButton(onClick = { navController.popBackStack() }) {
                androidx.compose.material.Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            androidx.compose.material.Text(
                text = "Период",
                style = TextStyle(fontSize = 12.sp, lineHeight = 13.2.sp, fontWeight = FontWeight.W600),
                modifier = Modifier.padding(start = 100.dp)
            )
        }}

        item{
        YearPicker(
            label = "Искать в период с",
            currentRange = currentRangeFrom.value,
            selectedYear = selectedYearFrom.value,
            onYearSelected = { selectedYearFrom.value = it },
            onNavigateRange = { direction ->
                currentRangeFrom.value = updateYearRange(currentRangeFrom.value, direction)
            }, navController
        )

            Spacer(modifier = Modifier.padding(top = 10.dp))
        YearPicker(
            label = "Искать в период до",
            currentRange = currentRangeTo.value,
            selectedYear = selectedYearTo.value,
            onYearSelected = { selectedYearTo.value = it },
            onNavigateRange = { direction ->
                currentRangeTo.value = updateYearRange(currentRangeTo.value, direction)
            }, navController
        )
            Spacer(modifier = Modifier.padding(30.dp))

        Button(
            onClick = {
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    "selectedFromYear",
                    selectedYearFrom.value
                )
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    "selectedToYear",
                    selectedYearTo.value
                )
                navController.popBackStack()
            },
            modifier = Modifier.width(120.dp)
        ) {

            Text(text = "Выбрать")
        }
    }}
}
@Composable
fun YearPicker(
    label: String,
    currentRange: IntRange,
    selectedYear: Int,
    onYearSelected: (Int) -> Unit,
    onNavigateRange: (Int) -> Unit, navController: NavController
) {

    Column() {

        Text(text = label,  fontSize = 14.sp, lineHeight = 15.4.sp, color = Color.Gray)

        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically

        ) {
            Text(text = "${currentRange.first} - ${currentRange.last}",)
            Spacer(modifier = Modifier.padding(start = 150.dp))


            IconButton(onClick = { onNavigateRange(-1) }) {
                Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Previous Range")
            }
            IconButton(onClick = { onNavigateRange(1) }) {
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next Range")
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp).clip(shape = RoundedCornerShape(8.dp) ).border(width = 1.dp , color = Color.Black, shape = RoundedCornerShape(8.dp)),
            horizontalArrangement = Arrangement.Center
        ) {
            items(currentRange.toList()) { year ->
                Text(
                    text = year.toString(),
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onYearSelected(year) }
                        .background(
                            if (year == selectedYear) Color.Blue else Color.Transparent,
                            shape = RoundedCornerShape(4.dp)
                        )                        .padding(start = 12.dp, bottom = 4.dp, top =4.dp ,  ),
                    color = if (year == selectedYear) Color.White else Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

fun updateYearRange(currentRange: IntRange, direction: Int): IntRange {
    val step = 12
    return if (direction < 0) {
        (currentRange.first - step)..(currentRange.last - step)
    } else {
        (currentRange.first + step)..(currentRange.last + step)
    }
}
