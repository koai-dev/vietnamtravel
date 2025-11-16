package com.travel.presentation.controller

import com.travel.data.model.DestinationResponse
import com.travel.data.mapper.toDestinationResponse
import com.travel.domain.service.DestinationService

class DestinationController(private val destinationService: DestinationService) {
    suspend fun getAll(lang: String): List<DestinationResponse> {
        return destinationService.getAll(lang).map { it.toDestinationResponse(lang) }
    }

    suspend fun getById(id: Long, lang: String): DestinationResponse? {
        return destinationService.getById(id)?.toDestinationResponse(lang)
    }
}