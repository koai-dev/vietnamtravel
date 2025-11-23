package com.travel.presentation.route

import com.travel.data.model.RestaurantRequest
import com.travel.presentation.controller.RestaurantController
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.restaurantRoutes() {
    val restaurantController by inject<RestaurantController>()

    route("/api/restaurants") {
        post {
            val restaurantRequest = call.receive<RestaurantRequest>()
            restaurantController.createRestaurant(call, restaurantRequest)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw IllegalArgumentException("Invalid ID")
            restaurantController.getRestaurantById(call, id)
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw IllegalArgumentException("Invalid ID")
            val restaurantRequest = call.receive<RestaurantRequest>()
            restaurantController.updateRestaurant(call, id, restaurantRequest)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw IllegalArgumentException("Invalid ID")
            restaurantController.deleteRestaurant(call, id)
        }

        get("/destination/{destinationId}") {
            val destinationId = call.parameters["destinationId"]?.toLongOrNull() ?: throw IllegalArgumentException("Invalid ID")
            restaurantController.getRestaurantsByDestinationId(call, destinationId)
        }

        get {
            restaurantController.getAll(call)
        }
    }
}
