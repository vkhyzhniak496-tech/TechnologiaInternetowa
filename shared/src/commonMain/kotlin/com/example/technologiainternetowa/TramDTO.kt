package com.example.technologiainternetowa

import kotlinx.serialization.Serializable

@Serializable
data class TramDTO(
    val id: Int? = null,
    val name: String,
    val type: String,
    val isFavorite: Boolean
)

