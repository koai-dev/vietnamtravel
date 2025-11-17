package com.travel.domain.service

import com.travel.presentation.response.HotelResponse
import com.travel.presentation.request.HotelRequest
import com.travel.presentation.request.RatingUpdateRequest

interface HotelService {
    suspend fun createHotel(hotelRequest: HotelRequest, lang: String): HotelResponse
    suspend fun getAllHotels(lang: String): List<HotelResponse>
    suspend fun getHotelById(id: Long, lang: String): HotelResponse?
    suspend fun getHotelBySlug(slug: String, lang: String): HotelResponse?
    suspend fun updateHotel(id: Long, hotelRequest: HotelRequest, lang: String): HotelResponse?
    suspend fun deleteHotel(id: Long): Boolean
    suspend fun incrementViews(id: Long): Boolean
    suspend fun updateRating(id: Long, ratingUpdateRequest: RatingUpdateRequest): Boolean
}