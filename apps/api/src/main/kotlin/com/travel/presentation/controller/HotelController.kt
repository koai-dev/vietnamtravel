package com.travel.presentation.controller

import com.travel.core.ApiResult
import com.travel.core.getLang
import com.travel.domain.service.HotelService
import com.travel.presentation.request.HotelRequest
import com.travel.presentation.request.RatingUpdateRequest
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class HotelController(private val hotelService: HotelService) {

    suspend fun createHotel(call: ApplicationCall) {
        val hotelRequest = call.receive<HotelRequest>()
        val lang = call.getLang()
        val hotelResponse = hotelService.createHotel(hotelRequest, lang)
        call.respond(ApiResult.success(hotelResponse))
    }

    suspend fun getAllHotels(call: ApplicationCall) {
        val lang = call.getLang()
        val hotels = hotelService.getAllHotels(lang)
        call.respond(ApiResult.success(hotels))
    }

    suspend fun getHotelById(call: ApplicationCall) {
        val id = call.parameters["id"]?.toLongOrNull() ?: throw IllegalArgumentException("Invalid ID")
        val lang = call.getLang()
        val hotel = hotelService.getHotelById(id, lang)
        if (hotel != null) {
            call.respond(ApiResult.success(hotel))
        } else {
            call.respond(ApiResult.error("Hotel not found"))
        }
    }

    suspend fun getHotelBySlug(call: ApplicationCall) {
        val slug = call.parameters["slug"] ?: throw IllegalArgumentException("Invalid slug")
        val lang = call.getLang()
        val hotel = hotelService.getHotelBySlug(slug, lang)
        if (hotel != null) {
            call.respond(ApiResult.success(hotel))
        } else {
            call.respond(ApiResult.error("Hotel not found"))
        }
    }

    suspend fun updateHotel(call: ApplicationCall) {
        val id = call.parameters["id"]?.toLongOrNull() ?: throw IllegalArgumentException("Invalid ID")
        val hotelRequest = call.receive<HotelRequest>()
        val lang = call.getLang()
        val updatedHotel = hotelService.updateHotel(id, hotelRequest, lang)
        if (updatedHotel != null) {
            call.respond(ApiResult.success(updatedHotel))
        } else {
            call.respond(ApiResult.error("Hotel not found"))
        }
    }

    suspend fun deleteHotel(call: ApplicationCall) {
        val id = call.parameters["id"]?.toLongOrNull() ?: throw IllegalArgumentException("Invalid ID")
        val deleted = hotelService.deleteHotel(id)
        if (deleted) {
            call.respond(ApiResult.success(true, "Hotel deleted successfully"))
        } else {
            call.respond(ApiResult.error("Hotel not found"))
        }
    }

    suspend fun incrementViewCount(call: ApplicationCall) {
        val id = call.parameters["id"]?.toLongOrNull() ?: throw IllegalArgumentException("Invalid ID")
        val success = hotelService.incrementViews(id)
        if (success) {
            call.respond(ApiResult.success(true, "View count incremented"))
        } else {
            call.respond(ApiResult.error("Hotel not found"))
        }
    }

    suspend fun updateRating(call: ApplicationCall) {
        val id = call.parameters["id"]?.toLongOrNull() ?: throw IllegalArgumentException("Invalid ID")
        val ratingUpdateRequest = call.receive<RatingUpdateRequest>()
        val success = hotelService.updateRating(id, ratingUpdateRequest)
        if (success) {
            call.respond(ApiResult.success(true, "Rating updated successfully"))
        } else {
            call.respond(ApiResult.error("Hotel not found"))
        }
    }
}