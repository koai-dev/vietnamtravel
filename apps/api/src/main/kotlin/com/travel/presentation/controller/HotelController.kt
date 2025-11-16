package com.travel.presentation.controller

import com.travel.data.model.HotelResponse
import com.travel.data.mapper.toHotelResponse
import com.travel.domain.service.HotelService

class HotelController(private val hotelService: HotelService) {
    suspend fun getAll(city: String?, sort: String?, page: Int, lang: String): List<HotelResponse> {
        return hotelService.getAll(city, sort, page, lang).map { it.toHotelResponse(lang) }
    }

    suspend fun getById(id: Long, lang: String): HotelResponse? {
        return hotelService.getById(id)?.toHotelResponse(lang)
    }
}