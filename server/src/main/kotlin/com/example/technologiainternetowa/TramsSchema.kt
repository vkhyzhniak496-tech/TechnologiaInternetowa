package com.example.technologiainternetowa
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.selectAll

import kotlinx.serialization.Serializable
// To jest definicja tabeli w bazie SQL
object TramsTable : Table("trams") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)
    val type = varchar("type", 50)
    val isFavorite = bool("is_favorite").default(false)

    override val primaryKey = PrimaryKey(id)
}

