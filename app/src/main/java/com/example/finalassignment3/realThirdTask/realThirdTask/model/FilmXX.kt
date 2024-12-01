package com.example.finalassignment3.realThirdTask.realThirdTask.model

data class FilmXX(
    val countries: List<CountryX>,
    val description: String,
    val filmId: Int,
    val filmLength: String,
    val genres: List<Genre>,
    val nameEn: String,
    val nameRu: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val rating: String,
    val ratingVoteCount: Int,
    val type: String,
    val year: String
)