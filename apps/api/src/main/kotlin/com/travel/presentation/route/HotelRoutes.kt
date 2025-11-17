package com.travel.presentation.route

import com.travel.presentation.controller.HotelController
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.hotelRoutes() {
    val hotelController by inject<HotelController>()

    route("/hotels") {
        get {
            hotelController.getAllHotels(context)
        }
        get("/{id}") {
            hotelController.getHotelById(context)
        }
        get("/slug/{slug}") {
            hotelController.getHotelBySlug(context)
        }
        post("/{id}/view") {
            hotelController.incrementViewCount(context)
        }
        // Authenticated routes
        authenticate {
            post {
                hotelController.createHotel(context)
            }
            put("/{id}") {
                hotelController.updateHotel(context)
            }
            delete("/{id}") {
                hotelController.deleteHotel(context)
            }
            // Add other protected routes, e.g., for rating
            put("/{id}/rating") {
                hotelController.updateRating(context)
            }
        }
    }
}
