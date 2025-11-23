package com.travel.domain.service

import com.travel.domain.model.Tour
import com.travel.domain.repository.RedisRepository
import com.travel.domain.repository.TourRepository

class TourService(
    private val tourRepository: TourRepository,
    private val redisRepository: RedisRepository,
) {
    suspend fun getAll(
        page: Int,
        pageSize: Int,
    ): Pair<List<Tour>, Long> = tourRepository.getAll(page, pageSize)

    suspend fun getById(id: Long): Tour? = tourRepository.findById(id)

    suspend fun getPopular(lang: String): List<Tour> {
        val key = "tours:popular:$lang"
        return com.travel.core.cache(redisRepository, key, 10 * 60) {
            tourRepository.getPopular()
        }
    }

    suspend fun create(tour: Tour): Tour = tourRepository.create(tour)

    suspend fun update(
        id: Long,
        tour: Tour,
    ): Tour? = tourRepository.update(id, tour)

    suspend fun delete(id: Long): Boolean = tourRepository.delete(id)
}
