package com.example.finalassignment3.realThirdTask.realThirdTask.model

data class SearchMovie(
    val films: List<FilmXX>,
    val keyword: String,
    val pagesCount: Int,
    val searchFilmsCountResult: Int
)