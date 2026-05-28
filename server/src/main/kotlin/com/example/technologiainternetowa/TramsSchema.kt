package com.example.technologiainternetowa
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.selectAll

import kotlinx.serialization.Serializable
// To jest definicja tabeli w bazie SQL
object TramsTable : Table("TRAMS") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)
    val type = varchar("type", 50)
    val lines = varchar("lines", 100) // Nowa kolumna na linie np. "7,9,24"
    val isFavorite = bool("is_favorite")
    override val primaryKey = PrimaryKey(id)
}
