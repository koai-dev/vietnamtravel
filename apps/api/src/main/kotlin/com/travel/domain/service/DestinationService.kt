package com.travel.domain.service

import com.travel.domain.model.Destination
import com.travel.domain.repository.DestinationRepository
import com.travel.domain.repository.RedisRepository

class DestinationService(
    private val destinationRepository: DestinationRepository,
    private val redisRepository: RedisRepository
) {
    suspend fun getAll(lang: String): List<Destination> {
        val key = "destinations:all:$lang"
        return com.travel.core.cache(redisRepository, key, 30 * 60) {
            destinationRepository.getAll()
        }
    }
    suspend fun getById(id: Long): Destination? = destinationRepository.findById(id)
}