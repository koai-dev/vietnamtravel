package com.example

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SchemaUtils.create

@Serializable
data class Destination(val id: Int, val name: String, val description: String, val imageUrl: String)

object Destinations : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val description = text("description")
    val imageUrl = varchar("imageUrl", 255)
    override val primaryKey = PrimaryKey(id)
}

fun initDatabase() {
    val driverClassName = "com.mysql.cj.jdbc.Driver"
    val jdbcURL = "jdbc:mysql://localhost:3306/vietnam_travel"
    val user = "root"
    val password = "root"
    val database = Database.connect(jdbcURL, driverClassName, user, password)
    transaction(database) {
        create(Destinations)
        if (Destinations.selectAll().count() == 0L) {
            Destinations.insert {
                it[name] = "Ha Long Bay"
                it[description] = "Famous for its emerald waters and thousands of towering limestone islands."
                it[imageUrl] = "https://example.com/ha-long-bay.jpg"
            }
            Destinations.insert {
                it[name] = "Hoi An"
                it[description] = "A well-preserved ancient town, known for its canals and French colonial buildings."
                it[imageUrl] = "https://example.com/hoi-an.jpg"
            }
        }
    }
}
