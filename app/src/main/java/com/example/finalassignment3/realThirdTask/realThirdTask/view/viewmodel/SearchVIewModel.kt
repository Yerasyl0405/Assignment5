package com.example.finalassignment3.realThirdTask.realThirdTask.view.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment3.model.Movie
import com.example.assignment3.model.MovieCollectionResponse
import com.example.assignment3.network.KinopoiskApi
import com.example.finalassignment3.realThirdTask.realThirdTask.model.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response

import androidx.lifecycle.viewModelScope
import com.example.finalassignment3.realThirdTask.realThirdTask.model.FilmXX
import com.example.finalassignment3.realThirdTask.realThirdTask.model.SearchMovie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
class SearchViewModel(private val api: KinopoiskApi) : ViewModel() {

    private val _moviesBySearch = MutableStateFlow<Map<String, List<FilmXX>>>(emptyMap())
    val moviesBySearch: StateFlow<Map<String, List<FilmXX>>> get() = _moviesBySearch

    fun fetchMoviesBySearch(searchText: String) {
        // Avoid fetching if the searchText is already in the map
        if (_moviesBySearch.value.containsKey(searchText)) return

        viewModelScope.launch {
            try {
                val response = api.getFilmsByKeyword(searchText) // Use the API call
                if (response.isSuccessful) {
                    response.body()?.let { movieCollectionResponse ->
                        val updatedMap = _moviesBySearch.value.toMutableMap()
                        updatedMap[searchText] = movieCollectionResponse.films.take(1)
                        _moviesBySearch.emit(updatedMap)
                    } ?: run {
                        // In case of no data
                        _moviesBySearch.emit(_moviesBySearch.value + (searchText to emptyList()))
                    }
                } else {
                    // Handle unsuccessful response
                    _moviesBySearch.emit(_moviesBySearch.value + (searchText to emptyList()))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle failure by emitting an empty list
                _moviesBySearch.emit(_moviesBySearch.value + (searchText to emptyList()))
            }
        }
    }
}