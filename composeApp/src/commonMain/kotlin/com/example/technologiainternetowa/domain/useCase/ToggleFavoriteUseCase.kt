package com.example.technologiainternetowa.domain.useCase

import com.example.technologiainternetowa.domain.repository.TramsRepository

class ToggleFavoriteUseCase(
    private val repository: TramsRepository
) {
    operator fun invoke(id: String) {
        repository.toggleFavorite(id)
    }
}
