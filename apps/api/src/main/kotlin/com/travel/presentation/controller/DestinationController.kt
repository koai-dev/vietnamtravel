package com.travel.presentation.controller

import com.travel.data.mapper.toDestinationResponse
import com.travel.data.model.DestinationResponse
import com.travel.data.model.DestinationResponseDetail
import com.travel.domain.service.DestinationService

class DestinationController(private val destinationService: DestinationService) {
    suspend fun getAll(lang: String): List<DestinationResponse> {
        return destinationService.getAll(lang).map { it.toDestinationResponse(lang) }
    }

    suspend fun getById(
        id: Long,
        lang: String,
    ): DestinationResponse? {
        return destinationService.getById(id)?.toDestinationResponse(lang)
    }

    suspend fun getByIdDetail(
        id: Long,
        lang: String,
    ): DestinationResponseDetail? {
        return destinationService.getByIdDetail(id)?.toDestinationResponse(lang)
    }

    suspend fun getTree(
        id: Long,
        lang: String,
    ): DestinationResponse? {
        return destinationService.getTree(id)?.toDestinationResponse(lang)
    }

    suspend fun getRootDestinations(lang: String): List<DestinationResponse> {
        return destinationService.getRootDestinations().map { it.toDestinationResponse(lang) }
    }
}
