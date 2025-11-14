package com.example.presentation

import com.example.core.RateLimiter
import com.example.core.lang
import com.example.domain.RedisRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.hotelRoutes() {
    val hotelController by inject<HotelController>()
    val redisRepository by inject<RedisRepository>()
    val rateLimiter = RateLimiter(redisRepository)

    route("/hotels") {
        install(rateLimiter.limit("/hotels", 100, 60))
        get {
            val city = call.request.queryParameters["city"]
            val sort = call.request.queryParameters["sort"]
            val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
            val lang = call.lang()
            val hotels = hotelController.getAll(city, sort, page, lang)
            call.respond(hotels)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id != null) {
                val lang = call.lang()
                val hotel = hotelController.getById(id, lang)
                if (hotel != null) {
                    call.respond(hotel)
                } else {
                    call.respondText("Hotel not found", status = io.ktor.http.HttpStatusCode.NotFound)
                }
            } else {
                call.respondText("Invalid ID", status = io.ktor.http.HttpStatusCode.BadRequest)
            }
        }
    }
}
