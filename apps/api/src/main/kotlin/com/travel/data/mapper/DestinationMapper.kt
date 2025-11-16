package com.travel.data.mapper

import com.travel.core.pickLang
import com.travel.data.model.DestinationResponse
import com.travel.domain.model.Destination

fun Destination.toDestinationResponse(lang: String): DestinationResponse {
    val children = children.map { it.toDestinationResponse(lang) }
    val foods = foods.map { it.toLocalFoodResponse(lang) }
    val restaurants = restaurants.map { it.toRestaurantResponse() }
    return DestinationResponse(
        id = id,
        name = pickLang(lang, nameVi, nameEn),
        description = pickLang(lang, descriptionVi, descriptionEn),
        latitude = latitude,
        longitude = longitude,
        type = type?.name,
        images = images,
        parentId = parentId,
        children = children,
        foods = foods,
        restaurants = restaurants
    )
}
