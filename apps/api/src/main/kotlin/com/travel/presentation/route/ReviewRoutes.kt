package com.travel.presentation.route

import com.travel.presentation.controller.ReviewController
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.reviewRoutes() {
    val reviewController by inject<ReviewController>()

    route("/api/reviews") {
        get{
            reviewController.getAll(call)
        }

        get("/{id}") {
            reviewController.getById(call)
        }

        post {
            reviewController.create(call)
        }

        put("/{id}") {
            reviewController.update(call)
        }

        delete("/{id}") {
            reviewController.delete(call)
        }
    }

    route("/api/hotels/{hotelId}/reviews") {
        get {
            reviewController.getByHotel(call)
        }
    }
}
