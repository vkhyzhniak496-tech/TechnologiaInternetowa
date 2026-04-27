package com.example.technologiainternetowa.data.dataSource

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.technologiainternetowa.domain.model.Tram

// Importy wygenerowanych zasobów
import technologiainternetowa.composeapp.generated.resources.Res
import technologiainternetowa.composeapp.generated.resources.*

object TramsLocalDataSource {

    private val _trams = MutableStateFlow(
        value = listOf(
            Tram(
                id = "pesa_128n",
                name = "Pesa Jazz Duo 128N",
                lines = listOf("11", "33"),
                imageRes = Res.drawable.jazz_duo
            ),
            Tram(
                id = "105N",
                name = "Konstal 105N",
                lines = listOf("1", "9", "22"),
                imageRes = Res.drawable.konstal105
            ),
            Tram(
                id = "hyundai_140n",
                name = "Hyundai Warsolino 140N",
                lines = listOf("4", "17", "25"),
                imageRes = Res.drawable.hyundai
            ),
            Tram(
                id = "pesa_120na",
                name = "Pesa Swing 120Na",
                lines = listOf("10", "26", "20"),
                imageRes = Res.drawable.swing
            ),
            Tram(
                id = "tygrys",
                name = "Tygrys",
                lines = listOf("14", "16", "19"),
                imageRes = Res.drawable.tygrys
            )
        )
    )

    val trams: StateFlow<List<Tram>> = _trams.asStateFlow()

    fun toggleFavorite(id: String) {
        _trams.update { list ->
            list.map { tram ->
                if (tram.id == id) tram.copy(favorite = !tram.favorite)
                else tram
            }
        }
    }
}