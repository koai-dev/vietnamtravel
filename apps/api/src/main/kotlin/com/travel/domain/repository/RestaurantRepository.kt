package com.travel.domain.repository

import com.travel.data.model.RestaurantRequest
import com.travel.domain.model.LocalFood
import com.travel.domain.model.Restaurant

interface RestaurantRepository {
    suspend fun createRestaurant(restaurantRequest: RestaurantRequest): Restaurant

    suspend fun getRestaurantById(id: Long): Restaurant?

    suspend fun updateRestaurant(
        id: Long,
        restaurantRequest: RestaurantRequest,
    )

    suspend fun deleteRestaurant(id: Long)

    suspend fun getRestaurantsByDestinationId(
        destinationId: Long,
        page: Int = 1,
        pageSize: Int = 20,
    ): Pair<List<Restaurant>, Long>

    suspend fun getAll(page: Int = 1, pageSize: Int = 20): Pair<List<Restaurant>, Long>

    suspend fun getLocalFoodsForRestaurant(restaurantId: Long): List<LocalFood>
}
