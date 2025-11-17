package com.travel.presentation.controller

import com.travel.data.mapper.toTourResponse
import com.travel.data.model.TourResponse
import com.travel.domain.service.TourService

class TourController(private val tourService: TourService) {
    suspend fun getAll(lang: String): List<TourResponse> {
        return tourService.getAll().map { it.toTourResponse(lang) }
    }

    suspend fun getById(
        id: Long,
        lang: String,
    ): TourResponse? {
        return tourService.getById(id)?.toTourResponse(lang)
    }

    suspend fun getPopular(lang: String): List<TourResponse> {
        return tourService.getPopular(lang).map { it.toTourResponse(lang) }
    }
}
