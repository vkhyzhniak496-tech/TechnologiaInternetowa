package com.example.technologiainternetowa
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")

    transaction {
        SchemaUtils.create(TramsTable)

        TramsTable.insert {
            it[TramsTable.name] = "Pesa Swing"
            it[TramsTable.type] = "120Na"
            it[TramsTable.isFavorite] = true
        }
        TramsTable.insert {
            it[TramsTable.name] = "Tygrys Nigga"
            it[TramsTable.type] = "1337"
            it[TramsTable.isFavorite] = false
        }
        TramsTable.insert {
            it[TramsTable.name] = "Tygrys"
            it[TramsTable.type] = "67"
            it[TramsTable.isFavorite] = true
        }
    }


    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(ContentNegotiation) { json() }
        routing {
            get("/trams") {
                // Pobieramy dane z bazy zamiast sztywnego tekstu
                val trams = transaction {
                    TramsTable.selectAll().map {
                        TramDTO(it[TramsTable.id], it[TramsTable.name], it[TramsTable.type], it[TramsTable.isFavorite])
                    }
                }
                call.respond(trams)
            }
        }
    }.start(wait = true)
}