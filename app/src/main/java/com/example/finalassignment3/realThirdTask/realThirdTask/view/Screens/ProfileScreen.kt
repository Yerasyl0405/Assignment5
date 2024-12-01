package com.example.finalassignment3.realThirdTask.realThirdTask.view.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalassignment3.R

@Composable
fun ProfileScreen(){
LazyColumn(modifier = Modifier.background(color = Color.White)) {
    item {
        Spacer(modifier = Modifier.padding(top = 90.dp))
    }
    item { Posmotreno() }
    item {        Spacer(modifier = Modifier.padding(top = 24.dp))
    }

    item {
        CollectionSection()
    }
    item {
        Spacer(modifier = Modifier.padding(top = 30.dp))

    }
    item {
        Interesno()
    }
}


}

@Composable
fun Posmotreno() {
    Column(modifier = Modifier.padding(start = 26.dp, end = 26.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = "Посмотрено",
                fontSize = 18.sp,
                fontWeight = FontWeight.W600
            )
            Text(
                text = "Все",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
    Column() {

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            item { Spacer(modifier = Modifier.padding(start = 10.dp)) }
            items(6) { film ->
                MovieCard(

                )
            }
        }
    }
}

@Composable
fun MovieCard() {
    Column(
        modifier = Modifier
            .width(111.dp).height(194.dp)
            .padding(start = 8.dp)
            .clip(RoundedCornerShape(4.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth().height(154.dp)
                .background(Color.Gray)

        ) {

        }


        Text(
            text = "Близкие",
            fontSize = 14.sp,
            fontWeight = FontWeight.W400,
            lineHeight = 15.4.sp,
            maxLines = 1,
        )
        Text(
            text = "драма",
            fontWeight = FontWeight.W400,
            fontSize = 12.sp,
            lineHeight = 13.2.sp,
            maxLines = 1,
            color = Color.Gray
        )
    }
}



@Composable
fun CollectionSection(){

    Column (modifier = Modifier.padding(start = 26.dp, end = 26.dp)){

        Text(text  = "Коллекции" , fontSize = 18.sp, lineHeight = 19.8.sp, fontWeight = FontWeight.W600)
        Spacer(modifier = Modifier.padding(top = 16.dp))
          Row(modifier = Modifier.padding(start = 7.dp, end = 26.dp).height(40.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
               Text(text = "+", )

              Text(text = "Создать свою коллекцию", fontSize = 16.sp, lineHeight = 17.8.sp, fontWeight = FontWeight.W400, modifier = Modifier.padding(start = 20.dp))
          }
                     Spacer(modifier = Modifier.padding(top = 16.dp))

        Row {
            Column (modifier = Modifier.height( 146.dp).width(146.dp).background(color = Color.White).border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp)), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Box(modifier = Modifier.height(24.dp).width(24.dp)) {
                    Image(
                        painter = painterResource(R.drawable.blackheart),
                        contentDescription = "",
                        modifier = Modifier.padding(start = 3.25.dp,  top = 5.25.dp).fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.padding(top = 8.dp))

                Text(text = "Любимые", fontWeight = FontWeight.W400, fontSize = 12.sp , lineHeight = 13.2.sp)
                Spacer(modifier = Modifier.padding(top = 8.dp))

                Row(modifier = Modifier.height(13.91.dp).width(26.79.dp).clip(shape = RoundedCornerShape(16.dp)).background(color = Color(0xFF3D3BFF)), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "105", fontSize = 8.sp, lineHeight = 8.8.sp, fontWeight = FontWeight.W500, color = Color.White )
                }

            }
            Spacer(modifier = Modifier.padding(start = 16.dp))
            Column (modifier = Modifier.height( 146.dp).width(146.dp).background(color = Color.White).border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp)), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Box(modifier = Modifier.height(24.dp).width(24.dp)) {
                Image(
                    painter = painterResource(R.drawable.blackheart),
                    contentDescription = "",
                    modifier = Modifier.padding(start = 3.25.dp,  top = 5.25.dp).fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.padding(top = 8.dp))

            Text(text = "Любимые", fontWeight = FontWeight.W400, fontSize = 12.sp , lineHeight = 13.2.sp)
            Spacer(modifier = Modifier.padding(top = 8.dp))

            Row(modifier = Modifier.height(13.91.dp).width(26.79.dp).clip(shape = RoundedCornerShape(16.dp)).background(color = Color(0xFF3D3BFF)), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "105", fontSize = 8.sp, lineHeight = 8.8.sp, fontWeight = FontWeight.W500, color = Color.White )
            }

        }}

        Spacer(modifier = Modifier.padding(top = 16.dp))
        Row {   Column (modifier = Modifier.height( 146.dp).width(146.dp).background(color = Color.White).border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp)), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Box(modifier = Modifier.height(24.dp).width(24.dp)) {
                Image(
                    painter = painterResource(R.drawable.blackheart),
                    contentDescription = "",
                    modifier = Modifier.padding(start = 3.25.dp,  top = 5.25.dp).fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.padding(top = 8.dp))

            Text(text = "Любимые", fontWeight = FontWeight.W400, fontSize = 12.sp , lineHeight = 13.2.sp)
            Spacer(modifier = Modifier.padding(top = 8.dp))

            Row(modifier = Modifier.height(13.91.dp).width(26.79.dp).clip(shape = RoundedCornerShape(16.dp)).background(color = Color(0xFF3D3BFF)), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "105", fontSize = 8.sp, lineHeight = 8.8.sp, fontWeight = FontWeight.W500, color = Color.White )
            }

        }

        }
    }

}



@Composable
fun Interesno() {
    Column(modifier = Modifier.padding(start = 26.dp, end = 26.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = "Вам было интересно",
                fontSize = 18.sp,
                fontWeight = FontWeight.W600
            )
            Text(
                text = "Все",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
    Column() {

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            item { Spacer(modifier = Modifier.padding(start = 10.dp)) }
            items(6) { film ->
                MovieCard(

                )
            }
        }
    }
}
