package com.example.technologiainternetowa.presentation.tramList

import com.example.technologiainternetowa.domain.model.Tram
import com.example.technologiainternetowa.domain.useCase.GetAllTramsUseCase
import com.example.technologiainternetowa.domain.useCase.ToggleFavoriteUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.map

class TramListViewModel(
    private val getAllTramsUseCase: GetAllTramsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) {

    private val viewModelScope = CoroutineScope(Dispatchers.Default)

    val uiState: StateFlow<TramListUiState> =
        getAllTramsUseCase()
            .map { trams -> TramListUiState(trams) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = TramListUiState()
            )


    fun onFavoriteClick(id: String) {
        viewModelScope.launch {
            toggleFavoriteUseCase(id)
        }
    }
}
