package com.travel.data.mapper

import com.travel.core.pickLang
import com.travel.data.model.DestinationResponse
import com.travel.data.model.DestinationResponseDetail
import com.travel.domain.model.Destination
import com.travel.domain.model.DestinationDetail

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
        restaurants = restaurants,
    )
}

fun DestinationDetail.toDestinationResponse(lang: String): DestinationResponseDetail {
    val children = children.map { it.toDestinationResponse(lang) }
    val foods = foods.map { it.toLocalFoodResponse(lang) }
    val restaurants = restaurants.map { it.toRestaurantResponse() }
    return DestinationResponseDetail(
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
        restaurants = restaurants,
        slug = slug,
        address = address,
        city = city,
        tags = tags,
        bestTimeToVisit = bestTimeToVisit,
        openingHours = openingHours,
        priceFrom = priceFrom,
        priceTo = priceTo,
        externalLinks = externalLinks,
        addressLink = addressLink,
        avgRating = avgRating,
        reviewCount = reviewCount,
        viewsCount = viewsCount,
        favoritesCount = favoritesCount,
        status = status,
        sortOrder = sortOrder,
    )
}
