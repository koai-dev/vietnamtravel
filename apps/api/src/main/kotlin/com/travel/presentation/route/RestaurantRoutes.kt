package com.travel.presentation.route

import com.travel.data.model.RestaurantRequest
import com.travel.presentation.controller.RestaurantController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.restaurantRoutes() {
    val restaurantController by inject<RestaurantController>()

    route("/restaurants") {
        post {
            val restaurantRequest = call.receive<RestaurantRequest>()
            val restaurant = restaurantController.createRestaurant(restaurantRequest)
            call.respond(HttpStatusCode.Created, restaurant)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw IllegalArgumentException("Invalid ID")
            val restaurant = restaurantController.getRestaurantById(id)
            if (restaurant != null) {
                call.respond(restaurant)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw IllegalArgumentException("Invalid ID")
            val restaurantRequest = call.receive<RestaurantRequest>()
            restaurantController.updateRestaurant(id, restaurantRequest)
            call.respond(HttpStatusCode.OK)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw IllegalArgumentException("Invalid ID")
            restaurantController.deleteRestaurant(id)
            call.respond(HttpStatusCode.NoContent)
        }

        get("/destination/{destinationId}") {
            val destinationId = call.parameters["destinationId"]?.toLongOrNull() ?: throw IllegalArgumentException("Invalid ID")
            val restaurants = restaurantController.getRestaurantsByDestinationId(destinationId)
            call.respond(restaurants)
        }
    }
}
