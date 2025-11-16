package com.travel.domain.service

import com.travel.domain.model.Tour
import com.travel.domain.repository.RedisRepository
import com.travel.domain.repository.TourRepository

class TourService(
    private val tourRepository: TourRepository,
    private val redisRepository: RedisRepository
) {
    suspend fun getAll(): List<Tour> = tourRepository.getAll()
    suspend fun getById(id: Long): Tour? = tourRepository.findById(id)
    suspend fun getPopular(lang: String): List<Tour> {
        val key = "tours:popular:$lang"
        return com.travel.core.cache(redisRepository, key, 10 * 60) {
            tourRepository.getPopular()
        }
    }
}