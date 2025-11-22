package com.travel.presentation.controller

import com.travel.data.mapper.toDestinationResponse
import com.travel.data.model.DestinationRequest
import com.travel.domain.service.DestinationService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*

class DestinationController(private val destinationService: DestinationService) : BaseController() {
    suspend fun getAll(
        call: ApplicationCall,
        lang: String,
    ) {
        val (page, pageSize) = getPaginationParams(call)
        val (destinations, total) = destinationService.getAll(lang, page, pageSize)
        val response = destinations.map { it.toDestinationResponse(lang) }

        val totalPages = (total + pageSize - 1) / pageSize

        respondWith(
            call,
            com.travel.presentation.model.PaginatedResponse(
                data = response,
                pagination =
                    com.travel.presentation.model.Pagination(
                        page = page,
                        pageSize = pageSize,
                        total = total,
                        totalPages = totalPages.toInt(),
                    ),
            ),
        )
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

    suspend fun search(
        call: ApplicationCall,
        query: String,
        types: String,
        lang: String,
    ) {
        val (page, pageSize) = getPaginationParams(call)
        val typeList =
            types.split(",").mapNotNull {
                try {
                    com.travel.data.table.DestinationType.valueOf(it)
                } catch (e: Exception) {
                    null
                }
            }
        val (destinations, total) = destinationService.search(query, typeList, page, pageSize)
        val response = destinations.map { it.toDestinationResponse(lang) }

        val totalPages = (total + pageSize - 1) / pageSize

        respondWith(
            call,
            com.travel.presentation.model.PaginatedResponse(
                data = response,
                pagination =
                    com.travel.presentation.model.Pagination(
                        page = page,
                        pageSize = pageSize,
                        total = total,
                        totalPages = totalPages.toInt(),
                    ),
            ),
        )
    }

    suspend fun delete(
        call: ApplicationCall,
        id: Long? = null,
    ) {
        val response = id?.let { destinationService.delete(it) } ?: false
        if (response) {
            respondWith(call, true)
        } else {
            respondWithError(
                call,
                "Destination not found",
                HttpStatusCode.NotFound,
            )
        }
    }
}
