package com.example.assignment3.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: KinopoiskApi by lazy {
        retrofit.create(KinopoiskApi::class.java)
    }

    val apii: FilmDetailsApi by lazy {
        retrofit.create(FilmDetailsApi::class.java)
    }

}
