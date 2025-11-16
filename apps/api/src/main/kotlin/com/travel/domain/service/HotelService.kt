package com.travel.domain.service

import com.travel.domain.model.Hotel
import com.travel.domain.repository.HotelRepository
import com.travel.domain.repository.RedisRepository

class HotelService(
    private val hotelRepository: HotelRepository,
    private val redisRepository: RedisRepository
) {
    suspend fun getAll(city: String?, sort: String?, page: Int, lang: String): List<Hotel> {
        val key = "hotels:list:${city ?: "all"}:${sort ?: "none"}:$page:$lang"
        return com.travel.core.cache(redisRepository, key, 5 * 60) {
            hotelRepository.getAll(city, sort, page)
        }
    }
    suspend fun getById(id: Long): Hotel? = hotelRepository.findById(id)
}