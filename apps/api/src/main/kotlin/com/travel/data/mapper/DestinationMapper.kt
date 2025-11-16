package com.travel.data.mapper

import com.travel.core.pickLang
import com.travel.data.model.DestinationResponse
import com.travel.domain.model.Destination

fun Destination.toDestinationResponse(lang: String) = DestinationResponse(
    id = id,
    name = pickLang(lang, nameVi, nameEn),
    description = pickLang(lang, descriptionVi, descriptionEn),
    latitude = latitude,
    longitude = longitude,
    type = type?.name,
    images = images
)