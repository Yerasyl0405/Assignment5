package com.example.finalassignment3.realThirdTask.realThirdTask.network

import com.example.assignment3.network.FilmDetailsApi
import com.example.assignment3.network.RetrofitInstance
import com.example.finalassignment3.realThirdTask.realThirdTask.model.Actor
import com.example.finalassignment3.realThirdTask.realThirdTask.model.FilmDetailsResponse


class ActorRepository {
    private val api = RetrofitInstance.api

    suspend fun getActorDetails(Id: Int): Actor {
        return api.getActorDetails(Id)
    }
}
class FilmRepository(private val filmDetailsApi: FilmDetailsApi) {
    suspend fun getFilmDetails(filmId: Int): FilmDetailsResponse {
        return filmDetailsApi.getFilmDetails(filmId)
    }
}
