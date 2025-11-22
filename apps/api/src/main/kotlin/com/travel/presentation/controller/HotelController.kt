package com.travel.presentation.controller

import com.travel.core.lang
import com.travel.data.model.HotelRequest
import com.travel.data.model.RatingUpdateRequest
import com.travel.domain.service.HotelService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall

class HotelController(private val hotelService: HotelService) : BaseController() {
    suspend fun createHotel(
        call: ApplicationCall,
        hotelRequest: HotelRequest,
    ) {
        val lang = call.lang()
        val hotelResponse = hotelService.createHotel(hotelRequest, lang)
        respondWith(call, hotelResponse)
    }

    suspend fun getAllHotels(call: ApplicationCall) {
        val lang = call.lang()
        val (page, pageSize) = getPaginationParams(call)
        val (hotels, total) = hotelService.getAllHotels(lang, page, pageSize)

        val totalPages = (total + pageSize - 1) / pageSize

        respondWith(
            call,
            com.travel.presentation.model.PaginatedResponse(
                data = hotels,
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

    suspend fun getHotelById(
        call: ApplicationCall,
        id: Long,
    ) {
        val lang = call.lang()
        val hotel = hotelService.getHotelById(id, lang)
        if (hotel != null) {
            respondWith(call, hotel)
        } else {
            respondWithError(call, "Hotel not found", HttpStatusCode.NotFound)
        }
    }

    suspend fun getHotelBySlug(
        call: ApplicationCall,
        slug: String,
    ) {
        val lang = call.lang()
        val hotel = hotelService.getHotelBySlug(slug, lang)
        if (hotel != null) {
            respondWith(call, hotel)
        } else {
            respondWithError(call, "Hotel not found", HttpStatusCode.NotFound)
        }
    }

    suspend fun updateHotel(
        call: ApplicationCall,
        id: Long,
        hotelRequest: HotelRequest,
    ) {
        val lang = call.lang()
        val updatedHotel = hotelService.updateHotel(id, hotelRequest, lang)
        if (updatedHotel != null) {
            respondWith(call, updatedHotel)
        } else {
            respondWithError(call, "Hotel not found", HttpStatusCode.NotFound)
        }
    }

    suspend fun deleteHotel(
        call: ApplicationCall,
        id: Long,
    ) {
        val deleted = hotelService.deleteHotel(id)
        if (deleted) {
            respondWith(call, true, "Hotel deleted successfully")
        } else {
            respondWithError(call, "Hotel not found", HttpStatusCode.NotFound)
        }
    }

    suspend fun incrementViewCount(
        call: ApplicationCall,
        id: Long,
    ) {
        val success = hotelService.incrementViews(id)
        if (success) {
            respondWith(call, true, "View count incremented")
        } else {
            respondWithError(call, "Hotel not found", HttpStatusCode.NotFound)
        }
    }

    suspend fun updateRating(
        call: ApplicationCall,
        id: Long,
        ratingUpdateRequest: RatingUpdateRequest,
    ) {
        val success = hotelService.updateRating(id, ratingUpdateRequest)
        if (success) {
            respondWith(call, true, "Rating updated successfully")
        } else {
            respondWithError(call, "Hotel not found", HttpStatusCode.NotFound)
        }
    }
}
