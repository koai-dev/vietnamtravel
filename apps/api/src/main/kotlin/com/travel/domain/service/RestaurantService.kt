package com.travel.domain.service

import com.google.gson.Gson
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
    private val imageMappingService: ImageMappingService,
) {
    suspend fun createRestaurant(restaurantRequest: RestaurantRequest): RestaurantResponse {
        destinationRepository.findById(restaurantRequest.destinationId)
            ?: throw Exception("Destination with id ${restaurantRequest.destinationId} not found")

        restaurantRequest.localFoodIds.forEach {
            localFoodRepository.getById(it) ?: throw Exception("LocalFood with id $it not found")
        }
        val resolvedImages =
            imageMappingService.resolveImages(
                Gson().toJson(restaurantRequest.images),
                restaurantRequest.tempUrlMap ?: emptyMap(),
            )
        val requestWithResolvedImages = restaurantRequest.copy(images = Gson().fromJson(resolvedImages, List::class.java) as List<String>)

        val restaurant = restaurantRepository.createRestaurant(requestWithResolvedImages)
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

        val resolvedImages =
            imageMappingService.resolveImages(
                Gson().toJson(restaurantRequest.images),
                restaurantRequest.tempUrlMap ?: emptyMap(),
            )
        val requestWithResolvedImages = restaurantRequest.copy(images = Gson().fromJson(resolvedImages, List::class.java) as List<String>)

        restaurantRepository.updateRestaurant(id, requestWithResolvedImages)
    }

    suspend fun deleteRestaurant(id: Long) {
        restaurantRepository.getRestaurantById(id)
            ?: throw Exception("Restaurant with id $id not found")
        restaurantRepository.deleteRestaurant(id)
    }

    suspend fun getRestaurantsByDestinationId(
        destinationId: Long,
        page: Int,
        pageSize: Int,
    ): Pair<List<RestaurantResponse>, Long> {
        val (restaurants, total) = restaurantRepository.getRestaurantsByDestinationId(destinationId, page, pageSize)
        val response = restaurants.map { restaurant ->
            val localFoods = restaurantRepository.getLocalFoodsForRestaurant(restaurant.id)
            restaurant.copy(localFoods = localFoods).toRestaurantResponse()
        }
        return Pair(response, total)
    }

    suspend fun getAll(page: Int, pageSize: Int): Pair<List<RestaurantResponse>, Long> {
        val (restaurants, total) = restaurantRepository.getAll(page, pageSize)
        val response = restaurants.map { restaurant ->
            val localFoods = restaurantRepository.getLocalFoodsForRestaurant(restaurant.id)
            restaurant.copy(localFoods = localFoods).toRestaurantResponse()
        }
        return Pair(response, total)
    }
}
