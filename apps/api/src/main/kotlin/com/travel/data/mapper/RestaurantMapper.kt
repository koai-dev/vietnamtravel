package com.travel.data.mapper

import com.travel.data.model.RestaurantResponse
import com.travel.data.table.Restaurants
import com.travel.domain.model.Restaurant
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toRestaurant(): Restaurant {
    return Restaurant(
        id = this[Restaurants.id],
        name = this[Restaurants.name],
        description = this[Restaurants.description],
        images = Json.decodeFromString(this[Restaurants.images]),
        address = this[Restaurants.address],
        latitude = this[Restaurants.latitude],
        longitude = this[Restaurants.longitude],
        destinationId = this[Restaurants.destinationId]
    )
}

fun Restaurant.toRestaurantResponse(): RestaurantResponse {
    return RestaurantResponse(
        id = id,
        name = name,
        description = description,
        images = images,
        address = address,
        latitude = latitude,
        longitude = longitude,
        destinationId = destinationId,
        localFoods = localFoods
    )
}
