package com.example.presentation

import com.example.core.RateLimiter
import com.example.core.lang
import com.example.domain.RedisRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.tourRoutes() {
    val tourController by inject<TourController>()
    val redisRepository by inject<RedisRepository>()
    val rateLimiter = RateLimiter(redisRepository)

    route("/tours") {
        install(rateLimiter.limit("/tours", 100, 60))
        get {
            val lang = call.lang()
            val tours = tourController.getAll(lang)
            call.respond(tours)
        }

        get("/popular") {
            val lang = call.lang()
            val tours = tourController.getPopular(lang)
            call.respond(tours)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id != null) {
                val lang = call.lang()
                val tour = tourController.getById(id, lang)
                if (tour != null) {
                    call.respond(tour)
                } else {
                    call.respondText("Tour not found", status = io.ktor.http.HttpStatusCode.NotFound)
                }
            } else {
                call.respondText("Invalid ID", status = io.ktor.http.HttpStatusCode.BadRequest)
            }
        }
    }
}
