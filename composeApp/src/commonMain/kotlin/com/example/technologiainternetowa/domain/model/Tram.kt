package com.example.technologiainternetowa.domain.model

import org.jetbrains.compose.resources.DrawableResource

data class Tram(
    val id: String,
    val name: String,
    val imageRes: DrawableResource,
    val lines: List<String>,
    val favorite: Boolean = false
)