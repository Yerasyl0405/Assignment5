package com.example.finalassignment3.realThirdTask.realThirdTask.view.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalassignment3.realThirdTask.realThirdTask.model.Actor
import com.example.finalassignment3.realThirdTask.realThirdTask.network.ActorRepository
import com.example.finalassignment3.realThirdTask.realThirdTask.network.FilmRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ActorViewModel(
    private val repository: ActorRepository,
    private val filmRepository: FilmRepository
) : ViewModel() {
    private val _actor = MutableStateFlow<Actor?>(null)
    val actor: StateFlow<Actor?> get() = _actor

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun fetchActorDetails(Id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val actorResponse = repository.getActorDetails(Id)
                val updatedFilms = actorResponse.films.take(8).map { film ->
                    val details = runCatching {
                        filmRepository.getFilmDetails(film.filmId)
                    }.getOrNull()
                    film.copy(posterUrl = details?.posterUrl)
                }
                _actor.value = actorResponse.copy(films = updatedFilms)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}
