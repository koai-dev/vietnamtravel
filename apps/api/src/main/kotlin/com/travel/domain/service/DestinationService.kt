package com.travel.domain.service

import com.travel.domain.model.Destination
import com.travel.domain.model.DestinationDetail
import com.travel.domain.repository.DestinationRepository
import com.travel.domain.repository.LocalFoodRepository
import com.travel.domain.repository.RedisRepository
import com.travel.domain.repository.RestaurantRepository

class DestinationService(
    private val destinationRepository: DestinationRepository,
    private val redisRepository: RedisRepository,
    private val localFoodRepository: LocalFoodRepository,
    private val restaurantRepository: RestaurantRepository,
) {
    suspend fun getAll(lang: String): List<Destination> {
        val key = "destinations:all:$lang"
        val destinations =
            com.travel.core.cache(redisRepository, key, 30 * 60) {
                destinationRepository.getAll()
            }
        return destinations.map { destination ->
            val foods = localFoodRepository.listByDestinationId(destination.id)
            val restaurants = restaurantRepository.getRestaurantsByDestinationId(destination.id)
            destination.copy(foods = foods, restaurants = restaurants)
        }
    }

    suspend fun getById(id: Long): Destination? {
        val destination = destinationRepository.findById(id)
        return destination?.let {
            val foods = localFoodRepository.listByDestinationId(it.id)
            val restaurants = restaurantRepository.getRestaurantsByDestinationId(it.id)
            it.copy(foods = foods, restaurants = restaurants)
        }
    }

    suspend fun getByIdDetail(id: Long): DestinationDetail? {
        val destination = destinationRepository.findByIdDetail(id)
        return destination?.let {
            val foods = localFoodRepository.listByDestinationId(it.id)
            val restaurants = restaurantRepository.getRestaurantsByDestinationId(it.id)
            it.copy(foods = foods, restaurants = restaurants)
        }
    }

    suspend fun getTree(id: Long): Destination? {
        val destination = destinationRepository.findTree(id)
        return destination?.let {
            val foods = localFoodRepository.listByDestinationId(it.id)
            val restaurants = restaurantRepository.getRestaurantsByDestinationId(it.id)
            it.copy(foods = foods, restaurants = restaurants)
        }
    }

    suspend fun getRootDestinations(): List<Destination> {
        val destinations = destinationRepository.listRoot()
        return destinations.map { destination ->
            val foods = localFoodRepository.listByDestinationId(destination.id)
            val restaurants = restaurantRepository.getRestaurantsByDestinationId(destination.id)
            destination.copy(foods = foods, restaurants = restaurants)
        }
    }
}
