package com.travel.presentation.controller

import com.travel.data.model.RestaurantRequest
import com.travel.domain.service.RestaurantService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall

class RestaurantController(private val restaurantService: RestaurantService) : BaseController() {
    suspend fun createRestaurant(
        call: ApplicationCall,
        restaurantRequest: RestaurantRequest,
    ) {
        val restaurant = restaurantService.createRestaurant(restaurantRequest)
        respondWith(call, restaurant)
    }

    suspend fun getRestaurantById(
        call: ApplicationCall,
        id: Long,
    ) {
        val restaurant = restaurantService.getRestaurantById(id)
        if (restaurant != null) {
            respondWith(call, restaurant)
        } else {
            respondWithError(call, "Restaurant not found", HttpStatusCode.NotFound)
        }
    }

    suspend fun updateRestaurant(
        call: ApplicationCall,
        id: Long,
        restaurantRequest: RestaurantRequest,
    ) {
        val updatedRestaurant = restaurantService.updateRestaurant(id, restaurantRequest)
        if (updatedRestaurant != null) {
            respondWith(call, updatedRestaurant)
        } else {
            respondWithError(call, "Restaurant not found", HttpStatusCode.NotFound)
        }
    }

    suspend fun deleteRestaurant(
        call: ApplicationCall,
        id: Long,
    ) {
        restaurantService.deleteRestaurant(id)
        respondWith(call, true, "Restaurant deleted successfully")
    }

    suspend fun getRestaurantsByDestinationId(
        call: ApplicationCall,
        destinationId: Long,
    ) {
        val (page, pageSize) = getPaginationParams(call)
        val (restaurants, total) = restaurantService.getRestaurantsByDestinationId(destinationId, page, pageSize)

        val totalPages = (total + pageSize - 1) / pageSize

        respondWith(
            call,
            com.travel.presentation.model.PaginatedResponse(
                data = restaurants,
                pagination =
                    com.travel.presentation.model.Pagination(
                        page = page,
                        pageSize = pageSize,
                        total = total,
                        totalPages = totalPages.toInt(),
                    ),
            ),
        )
    }

    suspend fun getAll(call: ApplicationCall) {
        val (page, pageSize) = getPaginationParams(call)
        val (restaurants, total) = restaurantService.getAll(page, pageSize)

        val totalPages = (total + pageSize - 1) / pageSize

        respondWith(
            call,
            com.travel.presentation.model.PaginatedResponse(
                data = restaurants,
                pagination =
                    com.travel.presentation.model.Pagination(
                        page = page,
                        pageSize = pageSize,
                        total = total,
                        totalPages = totalPages.toInt(),
                    ),
            ),
        )
    }
}
