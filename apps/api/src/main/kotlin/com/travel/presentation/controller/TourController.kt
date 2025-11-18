package com.travel.presentation.controller

import com.travel.data.mapper.toTourResponse
import com.travel.domain.service.TourService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall

class TourController(private val tourService: TourService) : BaseController() {
    suspend fun getAll(
        call: ApplicationCall,
        lang: String,
    ) {
        val tours = tourService.getAll().map { it.toTourResponse(lang) }
        respondWith(call, tours)
    }

    suspend fun getById(
        call: ApplicationCall,
        id: Long,
        lang: String,
    ) {
        val tour = tourService.getById(id)?.toTourResponse(lang)
        if (tour != null) {
            respondWith(call, tour)
        } else {
            respondWithError(call, "Tour not found", HttpStatusCode.NotFound)
        }
    }

    suspend fun getPopular(
        call: ApplicationCall,
        lang: String,
    ) {
        val tours = tourService.getPopular(lang).map { it.toTourResponse(lang) }
        respondWith(call, tours)
    }
}
