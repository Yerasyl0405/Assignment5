package com.example.finalassignment3.realThirdTask.realThirdTask.model


data class Actor(
    val personId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val posterUrl: String?,
    val films: List<Filmm>
)

data class Filmm(
    val filmId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val rating: String?,
    val description: String?,
    val professionKey: String?,
    var posterUrl: String? = null // Поле для постера
)
data class FilmDetailsResponse(
    val kinopoiskId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val posterUrl: String?
)

