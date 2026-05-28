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
import technologiainternetowa.composeapp.generated.resources.jazz_duo
import technologiainternetowa.composeapp.generated.resources.konstal105
import technologiainternetowa.composeapp.generated.resources.swing

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

                        // 1. Dotychczasowe lokalne zasoby (zostawiamy bez zmian)
                        imageRes = when (dto.type) {
                            "120Na" -> Res.drawable.swing
                            "1337"  -> Res.drawable.jazz_duo
                            "67"    -> Res.drawable.konstal105
                            else    -> Res.drawable.compose_multiplatform
                        },

                        // 2. NOWOŚĆ: Generujemy pełny adres sieciowy do zdjęć na serwerze Ktor!
                        imageUrl = when (dto.type) {
                            "120Na" -> "http://10.0.2.2:8080/static/swing.jpg"
                            "1337"  -> "http://10.0.2.2:8080/static/jazz_duo.jpg"
                            "67"    -> "http://10.0.2.2:8080/static/konstal105.jpg"
                            else    -> "http://10.0.2.2:8080/static/compose_multiplatform.png"
                        },

                        lines = dto.lines

                    )
                }
            } catch (e: Exception) {
                println("Błąd ViewModelu: ${e.message}")
            }
        }
    }
    fun toggleFavorite(tramId: String) {
        val idInt = tramId.toIntOrNull() ?: return

        viewModelScope.launch {
            // 1. Ślemy strzał na serwer
            val isServerUpdated = dataSource.toggleFavoriteOnServer(idInt)

            // 2. Jeśli serwer zatwierdził zmianę, aktualizujemy stan w telefonie
            if (isServerUpdated) {
                _trams.value = _trams.value.map { tram ->
                    if (tram.id == tramId) {
                        // Kopiujemy obiekt zmieniając tylko wartość favorite na przeciwną
                        tram.copy(favorite = !tram.favorite)
                    } else {
                        tram
                    }
                }
            }
        }
    }
}