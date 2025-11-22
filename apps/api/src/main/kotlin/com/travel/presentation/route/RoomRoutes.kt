package com.travel.presentation.route

import com.travel.presentation.controller.RoomController
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.roomRoutes() {
    val roomController by inject<RoomController>()

    route("/api/rooms") {
        get {
            roomController.getAll(call)
        }

        get("/{id}") {
            roomController.getById(call)
        }

        post {
            roomController.create(call)
        }

        put("/{id}") {
            roomController.update(call)
        }

        delete("/{id}") {
            roomController.delete(call)
        }
    }

    route("/api/hotels/{hotelId}/rooms") {
        get {
            roomController.getByHotel(call)
        }
    }
}
