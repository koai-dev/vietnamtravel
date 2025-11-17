package com.travel.presentation.route

import com.travel.core.RateLimiter
import com.travel.data.model.LocalFoodRequest
import com.travel.domain.repository.RedisRepository
import com.travel.presentation.controller.LocalFoodController
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.localFoodRoutes() {
    val localFoodController by inject<LocalFoodController>()
    val redisRepository by inject<RedisRepository>()
    val rateLimiter = RateLimiter(redisRepository)

    route("/api/local-foods") {
        install(rateLimiter.limit("/local-foods", 100, 60))

        post {
            val request = call.receive<LocalFoodRequest>()
            localFoodController.create(call, request)
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw IllegalArgumentException("Invalid ID")
            val request = call.receive<LocalFoodRequest>()
            localFoodController.update(call, id, request)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw IllegalArgumentException("Invalid ID")
            localFoodController.delete(call, id)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw IllegalArgumentException("Invalid ID")
            localFoodController.getById(call, id)
        }

        get("/destination/{destinationId}") {
            val destinationId = call.parameters["destinationId"]?.toLongOrNull() ?: throw IllegalArgumentException("Invalid Destination ID")
            localFoodController.listByDestinationId(call, destinationId)
        }
    }
}
