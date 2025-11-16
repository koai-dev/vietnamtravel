package com.travel.domain.service

import com.travel.domain.model.Destination
import com.travel.domain.repository.DestinationRepository
import com.travel.domain.repository.LocalFoodRepository
import com.travel.domain.repository.RedisRepository

class DestinationService(
    private val destinationRepository: DestinationRepository,
    private val redisRepository: RedisRepository,
    private val localFoodRepository: LocalFoodRepository
) {
    suspend fun getAll(lang: String): List<Destination> {
        val key = "destinations:all:$lang"
        val destinations = com.travel.core.cache(redisRepository, key, 30 * 60) {
            destinationRepository.getAll()
        }
        return destinations.map { destination ->
            val foods = localFoodRepository.listByDestinationId(destination.id)
            destination.copy(foods = foods)
        }
    }

    suspend fun getById(id: Long): Destination? {
        val destination = destinationRepository.findById(id)
        return destination?.let {
            val foods = localFoodRepository.listByDestinationId(it.id)
            it.copy(foods = foods)
        }
    }

    suspend fun getTree(id: Long): Destination? {
        val destination = destinationRepository.findTree(id)
        return destination?.let {
            val foods = localFoodRepository.listByDestinationId(it.id)
            it.copy(foods = foods)
        }
    }

    suspend fun getRootDestinations(): List<Destination> {
        val destinations = destinationRepository.listRoot()
        return destinations.map { destination ->
            val foods = localFoodRepository.listByDestinationId(destination.id)
            destination.copy(foods = foods)
        }
    }
}
