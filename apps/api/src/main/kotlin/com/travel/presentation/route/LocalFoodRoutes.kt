package com.travel.presentation.route

import com.travel.core.RateLimiter
import com.travel.data.model.LocalFoodRequest
import com.travel.domain.repository.RedisRepository
import com.travel.presentation.controller.LocalFoodController
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.localFoodRoutes() {
    val localFoodController by inject<LocalFoodController>()
    val redisRepository by inject<RedisRepository>()
    val rateLimiter = RateLimiter(redisRepository)

    route("/api/local-foods") {
        install(rateLimiter.limit("/local-foods", 100, 60))

        post {
            val request = call.receive<LocalFoodRequest>()
            val localFood = localFoodController.create(request)
            call.respond(HttpStatusCode.Created, localFood)
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id != null) {
                val request = call.receive<LocalFoodRequest>()
                val updatedLocalFood = localFoodController.update(id, request)
                if (updatedLocalFood != null) {
                    call.respond(updatedLocalFood)
                } else {
                    call.respondText("Local Food not found", status = HttpStatusCode.NotFound)
                }
            } else {
                call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id != null) {
                localFoodController.delete(id)
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
            }
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id != null) {
                val localFood = localFoodController.getById(id)
                if (localFood != null) {
                    call.respond(localFood)
                } else {
                    call.respondText("Local Food not found", status = HttpStatusCode.NotFound)
                }
            } else {
                call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
            }
        }

        get("/destination/{destinationId}") {
            val destinationId = call.parameters["destinationId"]?.toLongOrNull()
            if (destinationId != null) {
                val localFoods = localFoodController.listByDestinationId(destinationId)
                call.respond(localFoods)
            } else {
                call.respondText("Invalid Destination ID", status = HttpStatusCode.BadRequest)
            }
        }
    }
}
