package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    initDatabase()
    install(ContentNegotiation) {
        json()
    }
    install(CallLogging)
    install(CORS) {
        anyHost()
    }
    configureRouting()
}

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
        get("/api/destinations") {
            val destinations = transaction {
                Destinations.selectAll().map {
                    Destination(
                        it[Destinations.id],
                        it[Destinations.name],
                        it[Destinations.description],
                        it[Destinations.imageUrl]
                    )
                }
            }
            call.respond(destinations)
        }
        get("/api/destinations/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(io.ktor.http.HttpStatusCode.BadRequest, "Invalid ID")
                return@get
            }
            val destination = transaction {
                Destinations.select { Destinations.id eq id }.map {
                    Destination(
                        it[Destinations.id],
                        it[Destinations.name],
                        it[Destinations.description],
                        it[Destinations.imageUrl]
                    )
                }.singleOrNull()
            }
            if (destination == null) {
                call.respond(io.ktor.http.HttpStatusCode.NotFound, "Destination not found")
            } else {
                call.respond(destination)
            }
        }
    }
}
