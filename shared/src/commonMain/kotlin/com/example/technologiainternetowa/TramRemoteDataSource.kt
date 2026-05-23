package com.example.technologiainternetowa

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class TramRemoteDataSource {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // To ważne, gdyby API wysłało coś ekstra
            })
        }
    }

    suspend fun getAllTrams(): List<TramDTO> {
        return try {
            // Adres 10.0.2.2 to "localhost" z perspektywy emulatora Androida
            client.get("http://10.0.2.2:8080/trams").body()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}