package com.example.technologiainternetowa.presentation.tramList

import com.example.technologiainternetowa.domain.model.Tram

data class TramListUiState(
    val trams: List<Tram> = emptyList()
)
