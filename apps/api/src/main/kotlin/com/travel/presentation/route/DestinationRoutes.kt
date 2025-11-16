package com.travel.presentation.route

import com.travel.core.RateLimiter
import com.travel.core.lang
import com.travel.domain.repository.RedisRepository
import com.travel.presentation.controller.DestinationController
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.destinationRoutes() {
    val destinationController by inject<DestinationController>()
    val redisRepository by inject<RedisRepository>()
    val rateLimiter = RateLimiter(redisRepository)

    route("/api/destinations") {
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
                    call.respondText("Destination not found", status = HttpStatusCode.NotFound)
                }
            } else {
                call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
            }
        }
    }
}
