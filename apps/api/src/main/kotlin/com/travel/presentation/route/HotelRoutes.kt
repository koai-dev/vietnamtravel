package com.travel.presentation.route

import com.travel.data.model.HotelRequest
import com.travel.data.model.RatingUpdateRequest
import com.travel.presentation.controller.HotelController
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.hotelRoutes() {
    val hotelController by inject<HotelController>()

    route("/api/hotels") {
        get {
            hotelController.getAllHotels(call)
        }
        get("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw IllegalArgumentException("Invalid ID")
            hotelController.getHotelById(call, id)
        }
        get("/slug/{slug}") {
            val slug = call.parameters["slug"] ?: throw IllegalArgumentException("Invalid slug")
            hotelController.getHotelBySlug(call, slug)
        }
        post("/{id}/view") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw IllegalArgumentException("Invalid ID")
            hotelController.incrementViewCount(call, id)
        }
        // Authenticated routes
        authenticate {
            post {
                val request = call.receive<HotelRequest>()
                hotelController.createHotel(call, request)
            }
            put("/{id}") {
                val id = call.parameters["id"]?.toLongOrNull() ?: throw IllegalArgumentException("Invalid ID")
                val request = call.receive<HotelRequest>()
                hotelController.updateHotel(call, id, request)
            }
            delete("/{id}") {
                val id = call.parameters["id"]?.toLongOrNull() ?: throw IllegalArgumentException("Invalid ID")
                hotelController.deleteHotel(call, id)
            }
            // Add other protected routes, e.g., for rating
            put("/{id}/rating") {
                val id = call.parameters["id"]?.toLongOrNull() ?: throw IllegalArgumentException("Invalid ID")
                val request = call.receive<RatingUpdateRequest>()
                hotelController.updateRating(call, id, request)
            }
        }
    }
}
