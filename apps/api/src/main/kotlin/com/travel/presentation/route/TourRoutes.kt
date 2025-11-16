package com.travel.presentation.route

import com.travel.core.RateLimiter
import com.travel.core.lang
import com.travel.domain.repository.RedisRepository
import com.travel.presentation.controller.TourController
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.tourRoutes() {
    val tourController by inject<TourController>()
    val redisRepository by inject<RedisRepository>()
    val rateLimiter = RateLimiter(redisRepository)

    route("/api/tours") {
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
                    call.respondText("Tour not found", status = HttpStatusCode.NotFound)
                }
            } else {
                call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
            }
        }
    }
}
