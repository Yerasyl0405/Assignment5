package com.example.finalassignment3.realThirdTask.realThirdTask.model

import com.example.finalassignment3.R

data class DataClass(
    val titles:String,
    val image:Int,
    val title:String,
    val descrip:String
)

fun getData():List<DataClass>{
    return listOf(
        DataClass("Премьеры",R.drawable.card,"Близкие", "драма" ),
        DataClass("Боевики США",R.drawable.card,"Близкие", "драма" ),
        DataClass("ТОП-250",R.drawable.card,"Близкие", "Драма" ),
                DataClass("Драмы Франции",R.drawable.card,"Близкие", "Драма" ),
    DataClass("Популярное",R.drawable.card,"Близкие", "Драма" ),
    DataClass("Сериалы",R.drawable.card,"Близкие", "Драма" ),
    DataClass("Премьеры",R.drawable.card,"Близкие", "Драма" ),
    DataClass("Премьеры",R.drawable.card,"Близкие", "Драма" ),
    DataClass("Премьеры",R.drawable.card,"Близкие", "Драма" )


    )
}

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String?) : UiState<Nothing>()
}

