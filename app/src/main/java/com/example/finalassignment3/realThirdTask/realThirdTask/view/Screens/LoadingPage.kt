package com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens

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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.finalassignment3.R
import org.jetbrains.annotations.Contract

@Composable

 fun LoadingPage() {
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

        }

        Column(modifier = Modifier.padding(start = 181.dp, top  = 300.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            CircularProgressIndicator()

        }




            Image(
                painter = painterResource(id = R.drawable.layer5),
                contentDescription = "", modifier = Modifier.padding(start = 40.dp , end = 40.dp, top = 70.dp).fillMaxSize()

                )

        }

    }

