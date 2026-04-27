package com.example.technologiainternetowa.domain.repository

import com.example.technologiainternetowa.domain.model.Tram
import kotlinx.coroutines.flow.StateFlow

interface TramsRepository {
    val trams: StateFlow<List<Tram>>
    fun toggleFavorite(id: String)
}
