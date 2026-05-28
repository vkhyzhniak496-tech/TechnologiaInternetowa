package com.example.technologiainternetowa

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")

    transaction {
        SchemaUtils.create(TramsTable)

        // ROZBUDOWANA I REALISTYCZNA BAZA DANYCH (Wszystkie Twoje 5 obrazków)
        TramsTable.insert {
            it[TramsTable.name] = "Pesa Swing"
            it[TramsTable.type] = "120Na"
            it[TramsTable.lines] = "7,9,22,24"
            it[TramsTable.isFavorite] = true
        }
        TramsTable.insert {
            it[TramsTable.name] = "Pesa Jazz Duo"
            it[TramsTable.type] = "128N"
            it[TramsTable.lines] = "2,4,33"
            it[TramsTable.isFavorite] = false
        }
        TramsTable.insert {
            it[TramsTable.name] = "Konstal 105Na"
            it[TramsTable.type] = "105Na"
            it[TramsTable.lines] = "13,26,27"
            it[TramsTable.isFavorite] = false
        }
        TramsTable.insert {
            it[TramsTable.name] = "Hyundai Rotem"
            it[TramsTable.type] = "140N"
            it[TramsTable.lines] = "3,25,17"
            it[TramsTable.isFavorite] = true
        }
        TramsTable.insert {
            it[TramsTable.name] = "Tygrys"
            it[TramsTable.type] = "Tygrys"
            it[TramsTable.lines] = "14,16,19"
            it[TramsTable.isFavorite] = true
        }
    }

    // 🔍 AUTOMATYCZNE WYKRYWANIE POPRAWNEJ ŚCIEŻKI DO ZASOBÓW:
    val staticDir = java.io.File("server/src/main/resources/static").let {
        if (it.exists()) it else java.io.File("src/main/resources/static")
    }

    println("================================================================")
    println("🚀 SERWER SZUKA OBRAZKÓW W: ${staticDir.absolutePath}")
    println("📂 CZY FOLDER Z DIZELAMI ISTNIEJE? -> ${staticDir.exists().toString().uppercase()}")
    println("================================================================")

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(ContentNegotiation) { json() }
        routing {

            // 🛠️ REWOLUCYJNA POPRAWKA: Ręczne nadpisywanie Content-Type i ignorowanie humoru Windowsa
            get("/static/{filename}") {
                val filename = call.parameters["filename"] ?: return@get call.respond(HttpStatusCode.BadRequest)
                val file = java.io.File(staticDir, filename)
                if (file.exists()) {
                    val ext = filename.substringAfterLast(".").lowercase()
                    val contentType = when (ext) {
                        "jpg", "jpeg" -> ContentType.Image.JPEG
                        "png" -> ContentType.Image.PNG
                        else -> ContentType.Application.OctetStream
                    }
                    call.respond(LocalFileContent(file, contentType))
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }

            get("/trams") {
                val trams = transaction {
                    TramsTable.selectAll().map {
                        val imageFile = when (it[TramsTable.type]) {
                            "120Na"  -> "swing.jpg"
                            "128N"   -> "jazz_duo.jpg"
                            "105Na"  -> "konstal105.jpg"
                            "140N"   -> "hyundai.jpg"
                            "Tygrys" -> "tygrys.png"
                            else     -> "swing.jpg"
                        }

                        TramDTO(
                            id = it[TramsTable.id],
                            name = it[TramsTable.name],
                            type = it[TramsTable.type],
                            lines = it[TramsTable.lines].split(","),
                            isFavorite = it[TramsTable.isFavorite],
                            imageUrl = "http://10.0.2.2:8080/static/$imageFile"
                        )
                    }
                }
                call.respond(trams)
            }

            post("/trams/{id}/favorite") {
                val tramId = call.parameters["id"]?.toIntOrNull()
                if (tramId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Niepoprawne ID tramwaju")
                    return@post
                }

                val success = transaction {
                    val currentFavorite = TramsTable
                        .selectAll()
                        .where { TramsTable.id eq tramId }
                        .map { it[TramsTable.isFavorite] }
                        .firstOrNull()

                    if (currentFavorite != null) {
                        val newFavorite = !currentFavorite
                        TramsTable.update({ TramsTable.id eq tramId }) {
                            it[TramsTable.isFavorite] = newFavorite
                        }
                        true
                    } else {
                        false
                    }
                }

                if (success) {
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Nie znaleziono tramwaju w bazie")
                }
            }
        }
    }.start(wait = true)
}