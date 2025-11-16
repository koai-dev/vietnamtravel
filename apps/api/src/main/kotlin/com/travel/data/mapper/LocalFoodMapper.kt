package com.travel.data.mapper

import com.travel.core.pickLang
import com.travel.data.model.LocalFoodResponse
import com.travel.domain.model.LocalFood

fun LocalFood.toLocalFoodResponse(lang: String): LocalFoodResponse {
    return LocalFoodResponse(
        id = id,
        destinationId = destinationId,
        nameVi = pickLang(lang, nameVi, nameEn ?: ""),
        nameEn = nameEn,
        descriptionVi = pickLang(lang, descriptionVi ?: "", descriptionEn ?: ""),
        descriptionEn = descriptionEn,
        images = images
    )
}
