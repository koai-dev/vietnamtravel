package com.travel.domain.service

import com.travel.data.mapper.toRestaurantResponse
import com.travel.data.model.RestaurantRequest
import com.travel.data.model.RestaurantResponse
import com.travel.domain.repository.DestinationRepository
import com.travel.domain.repository.LocalFoodRepository
import com.travel.domain.repository.RestaurantRepository

class RestaurantService(
    private val restaurantRepository: RestaurantRepository,
    private val destinationRepository: DestinationRepository,
    private val localFoodRepository: LocalFoodRepository,
) {
    suspend fun createRestaurant(restaurantRequest: RestaurantRequest): RestaurantResponse {
        destinationRepository.findById(restaurantRequest.destinationId)
            ?: throw Exception("Destination with id ${restaurantRequest.destinationId} not found")

        restaurantRequest.localFoodIds.forEach {
            localFoodRepository.getById(it) ?: throw Exception("LocalFood with id $it not found")
        }

        val restaurant = restaurantRepository.createRestaurant(restaurantRequest)
        val localFoods = restaurantRepository.getLocalFoodsForRestaurant(restaurant.id)

        return restaurant.copy(localFoods = localFoods).toRestaurantResponse()
    }

    suspend fun getRestaurantById(id: Long): RestaurantResponse? {
        val restaurant = restaurantRepository.getRestaurantById(id) ?: return null
        val localFoods = restaurantRepository.getLocalFoodsForRestaurant(id)
        return restaurant.copy(localFoods = localFoods).toRestaurantResponse()
    }

    suspend fun updateRestaurant(
        id: Long,
        restaurantRequest: RestaurantRequest,
    ) {
        restaurantRepository.getRestaurantById(id)
            ?: throw Exception("Restaurant with id $id not found")

        destinationRepository.findById(restaurantRequest.destinationId)
            ?: throw Exception("Destination with id ${restaurantRequest.destinationId} not found")

        restaurantRequest.localFoodIds.forEach {
            localFoodRepository.getById(it) ?: throw Exception("LocalFood with id $it not found")
        }

        restaurantRepository.updateRestaurant(id, restaurantRequest)
    }

    suspend fun deleteRestaurant(id: Long) {
        restaurantRepository.getRestaurantById(id)
            ?: throw Exception("Restaurant with id $id not found")
        restaurantRepository.deleteRestaurant(id)
    }

    suspend fun getRestaurantsByDestinationId(destinationId: Long): List<RestaurantResponse> {
        val restaurants = restaurantRepository.getRestaurantsByDestinationId(destinationId)
        return restaurants.map { restaurant ->
            val localFoods = restaurantRepository.getLocalFoodsForRestaurant(restaurant.id)
            restaurant.copy(localFoods = localFoods).toRestaurantResponse()
        }
    }
}
