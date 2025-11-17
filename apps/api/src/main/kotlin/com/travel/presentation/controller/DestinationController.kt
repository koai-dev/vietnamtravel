package com.travel.presentation.controller

import com.travel.data.mapper.toDestinationResponse
import com.travel.domain.service.DestinationService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall

class DestinationController(private val destinationService: DestinationService) : BaseController() {
    suspend fun getAll(call: ApplicationCall, lang: String) {
        val destinations = destinationService.getAll(lang).map { it.toDestinationResponse(lang) }
        respondWith(call, destinations)
    }

    suspend fun getById(
        call: ApplicationCall,
        id: Long,
        lang: String,
    ) {
        val destination = destinationService.getById(id)?.toDestinationResponse(lang)
        if (destination != null) {
            respondWith(call, destination)
        } else {
            respondWithError(call, "Destination not found", HttpStatusCode.NotFound)
        }
    }

    suspend fun getByIdDetail(
        call: ApplicationCall,
        id: Long,
        lang: String,
    ) {
        val destination = destinationService.getByIdDetail(id)?.toDestinationResponse(lang)
        if (destination != null) {
            respondWith(call, destination)
        } else {
            respondWithError(call, "Destination not found", HttpStatusCode.NotFound)
        }
    }

    suspend fun getTree(
        call: ApplicationCall,
        id: Long,
        lang: String,
    ) {
        val destination = destinationService.getTree(id)?.toDestinationResponse(lang)
        if (destination != null) {
            respondWith(call, destination)
        } else {
            respondWithError(call, "Destination not found", HttpStatusCode.NotFound)
        }
    }

    suspend fun getRootDestinations(call: ApplicationCall, lang: String) {
        val destinations = destinationService.getRootDestinations().map { it.toDestinationResponse(lang) }
        respondWith(call, destinations)
    }
}
