package com.example.technologiainternetowa.domain.useCase

import com.example.technologiainternetowa.domain.repository.TramsRepository

class GetAllTramsUseCase(
    private val repository: TramsRepository
) {
    operator fun invoke() = repository.trams
}
