package com.travel.domain.repository

import com.travel.domain.model.Hotel

interface HotelRepository {
    suspend fun getAll(city: String?, sort: String?, page: Int): List<Hotel>
    suspend fun findById(id: Long): Hotel?
}