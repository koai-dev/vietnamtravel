package com.travel.domain.service

import com.travel.data.model.HotelRequest
import com.travel.data.model.HotelResponse
import com.travel.data.model.RatingUpdateRequest

interface HotelService {
    suspend fun createHotel(
        hotelRequest: HotelRequest,
        lang: String,
    ): HotelResponse

    suspend fun getAllHotels(
        lang: String,
        page: Int,
        pageSize: Int,
    ): Pair<List<HotelResponse>, Long>

    suspend fun getHotelById(
        id: Long,
        lang: String,
    ): HotelResponse?

    suspend fun getHotelBySlug(
        slug: String,
        lang: String,
    ): HotelResponse?

    suspend fun updateHotel(
        id: Long,
        hotelRequest: HotelRequest,
        lang: String,
    ): HotelResponse?

    suspend fun deleteHotel(id: Long): Boolean

    suspend fun incrementViews(id: Long): Boolean

    suspend fun updateRating(
        id: Long,
        ratingUpdateRequest: RatingUpdateRequest,
    ): Boolean
}
