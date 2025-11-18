package com.travel.presentation.controller

import com.travel.data.mapper.toDestinationResponse
import com.travel.data.model.DestinationRequest
import com.travel.domain.service.DestinationService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive

class DestinationController(private val destinationService: DestinationService) : BaseController() {
    suspend fun getAll(
        call: ApplicationCall,
        lang: String,
    ) {
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

    suspend fun getRootDestinations(
        call: ApplicationCall,
        lang: String,
    ) {
        val destinations = destinationService.getRootDestinations().map { it.toDestinationResponse(lang) }
        respondWith(call, destinations)
    }

    suspend fun create(call: ApplicationCall) {
        val request = call.receive<DestinationRequest>()
        val newId = destinationService.create(request)
        respondWith(call, mapOf("id" to newId))
    }

    suspend fun update(
        call: ApplicationCall,
        id: Long,
    ) {
        val request = call.receive<DestinationRequest>()
        destinationService.update(id, request)
        respondWith(call, mapOf("success" to true))
    }
}
