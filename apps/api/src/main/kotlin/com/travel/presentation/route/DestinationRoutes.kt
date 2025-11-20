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
            destinationController.getAll(call, lang)
        }

        get("/search") {
            val query = call.request.queryParameters["q"] ?: ""
            val types = call.request.queryParameters["types"] ?: ""
            val lang = call.lang()
            destinationController.search(call, query, types, lang)
        }

        get("/roots") {
            val lang = call.lang()
            destinationController.getRootDestinations(call, lang)
        }

        get("/tree/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id != null) {
                val lang = call.lang()
                destinationController.getTree(call, id, lang)
            } else {
                call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
            }
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id != null) {
                val lang = call.lang()
                destinationController.getById(call, id, lang)
            } else {
                call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
            }
        }

        get("/{id}/detail") {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id != null) {
                val lang = call.lang()
                destinationController.getByIdDetail(call, id, lang)
            } else {
                call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
            }
        }

        post {
            destinationController.create(call)
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id != null) {
                destinationController.update(call, id)
            } else {
                call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
            }
        }
    }
}
