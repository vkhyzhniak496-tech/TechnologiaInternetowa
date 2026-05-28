package com.example.technologiainternetowa

import kotlinx.serialization.Serializable


@Serializable
data class TramDTO(
    val id: Int,
    val name: String,
    val type: String,
    val lines: List<String>, // Dynamiczna lista linii z serwera
    val isFavorite: Boolean,
    val imageUrl: String
)

