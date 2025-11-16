package com.travel.data.mapper

import com.travel.core.pickLang
import com.travel.data.model.TourResponse
import com.travel.domain.model.Tour

fun Tour.toTourResponse(lang: String) = TourResponse(
    id = id,
    title = pickLang(lang, titleVi, titleEn),
    description = pickLang(lang, descriptionVi, descriptionEn),
    price = price,
    durationHours = durationHours,
    destinationId = destinationId,
    images = images
)