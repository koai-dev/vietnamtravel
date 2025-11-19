package com.travel.presentation.route

import com.travel.core.RateLimiter
import com.travel.core.lang
import com.travel.domain.repository.RedisRepository
import com.travel.presentation.controller.TourController
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.tourRoutes() {
    val tourController by inject<TourController>()
    val redisRepository by inject<RedisRepository>()
    val rateLimiter = RateLimiter(redisRepository)

    route("/api/tours") {
        install(rateLimiter.limit("/tours", 100, 60))
        get {
            val lang = call.lang()
            tourController.getAll(call, lang)
        }

        get("/popular") {
            val lang = call.lang()
            tourController.getPopular(call, lang)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw IllegalArgumentException("Invalid ID")
            val lang = call.lang()
            tourController.getById(call, id, lang)
        }
    }
}
