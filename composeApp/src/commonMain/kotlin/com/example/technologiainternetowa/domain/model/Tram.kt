package com.example.technologiainternetowa.domain.model

import org.jetbrains.compose.resources.DrawableResource

data class Tram(
    val id: String,
    val name: String,
    val imageUrl: String, // 👈 Dodajemy nową właściwość do konstruktora
    val imageRes: DrawableResource, // Zostawiamy ją na razie, dopóki nie podepniesz Kamel/Coil
    val lines: List<String>,
    val favorite: Boolean = false
)