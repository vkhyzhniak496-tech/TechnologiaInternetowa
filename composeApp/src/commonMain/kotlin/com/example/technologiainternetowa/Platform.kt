package com.example.technologiainternetowa

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform