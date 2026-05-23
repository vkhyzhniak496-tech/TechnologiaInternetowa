package com.example.technologiainternetowa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.technologiainternetowa.TramDTO
import com.example.technologiainternetowa.TramRemoteDataSource
// 1. DODAJEMY POPRAWNY IMPORT DLA KLASY TRAM
import com.example.technologiainternetowa.domain.model.Tram
import technologiainternetowa.composeapp.generated.resources.Res
import technologiainternetowa.composeapp.generated.resources.compose_multiplatform

class TramViewModel : ViewModel() {
    private val dataSource = TramRemoteDataSource()

    // 2. POPRAWIAMY TYP: UI oczekuje obiektów typu 'Tram', a nie 'TramDTO'
    private val _trams = MutableStateFlow<List<Tram>>(emptyList())
    val trams: StateFlow<List<Tram>> = _trams

    init {
        loadTrams()
    }

    fun loadTrams() {
        viewModelScope.launch {
            try {
                val result = dataSource.getAllTrams()

                _trams.value = result.map { dto ->

                    Tram(
                        id = dto.id.toString(),
                        name = dto.name,
                        favorite = dto.isFavorite,
                        imageRes = Res.drawable.compose_multiplatform,
                        lines = listOf("7", "9", "24")
                    )
                }
            } catch (e: Exception) {
                println("Błąd ViewModelu: ${e.message}")
            }
        }
    }
}