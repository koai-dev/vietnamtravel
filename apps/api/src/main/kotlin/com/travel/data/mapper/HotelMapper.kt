package com.travel.data.mapper

import com.travel.core.pickLang
import com.travel.data.model.HotelResponse
import com.travel.domain.model.Hotel

fun Hotel.toHotelResponse(lang: String) = HotelResponse(
    id = id,
    name = pickLang(lang, nameVi, nameEn),
    description = pickLang(lang, descriptionVi, descriptionEn),
    address = address,
    city = city,
    latitude = latitude,
    longitude = longitude,
    hostId = hostId,
    rating = rating,
    reviewCount = reviewCount
)