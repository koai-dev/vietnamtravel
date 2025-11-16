package com.travel.presentation.controller

import com.travel.data.model.RestaurantRequest
import com.travel.domain.service.RestaurantService

class RestaurantController(private val restaurantService: RestaurantService) {

    suspend fun createRestaurant(restaurantRequest: RestaurantRequest) = restaurantService.createRestaurant(restaurantRequest)

    suspend fun getRestaurantById(id: Long) = restaurantService.getRestaurantById(id)

    suspend fun updateRestaurant(id: Long, restaurantRequest: RestaurantRequest) = restaurantService.updateRestaurant(id, restaurantRequest)

    suspend fun deleteRestaurant(id: Long) = restaurantService.deleteRestaurant(id)

    suspend fun getRestaurantsByDestinationId(destinationId: Long) = restaurantService.getRestaurantsByDestinationId(destinationId)
}
