package com.example.presentation

import com.example.core.RateLimiter
import com.example.core.lang
import com.example.domain.RedisRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.destinationRoutes() {
    val destinationController by inject<DestinationController>()
    val redisRepository by inject<RedisRepository>()
    val rateLimiter = RateLimiter(redisRepository)

    route("/destinations") {
        install(rateLimiter.limit("/destinations", 100, 60))
        get {
            val lang = call.lang()
            val destinations = destinationController.getAll(lang)
            call.respond(destinations)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id != null) {
                val lang = call.lang()
                val destination = destinationController.getById(id, lang)
                if (destination != null) {
                    call.respond(destination)
                } else {
                    call.respondText("Destination not found", status = io.ktor.http.HttpStatusCode.NotFound)
                }
            } else {
                call.respondText("Invalid ID", status = io.ktor.http.HttpStatusCode.BadRequest)
            }
        }
    }
}
