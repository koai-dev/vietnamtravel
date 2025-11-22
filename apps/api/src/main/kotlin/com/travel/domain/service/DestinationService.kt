package com.travel.domain.service

import com.google.gson.Gson
import com.travel.data.model.DestinationRequest
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
    private val imageMappingService: ImageMappingService,
) {
    suspend fun getAll(
        lang: String,
        page: Int,
        pageSize: Int,
    ): Pair<List<Destination>, Long> {
        val key = "destinations:all:$lang:$page:$pageSize"
        val (destinations, total) =
            com.travel.core.cache(redisRepository, key, 30 * 60) {
                destinationRepository.getAll(page, pageSize)
            }
        val enrichedDestinations =
            destinations.map { destination ->
                val foods = localFoodRepository.listByDestinationId(destination.id)
                val restaurants = restaurantRepository.getRestaurantsByDestinationId(destination.id)
                destination.copy(foods = foods.first, restaurants = restaurants.first)
            }
        return Pair(enrichedDestinations, total)
    }

    suspend fun getById(id: Long): Destination? {
        val destination = destinationRepository.findById(id)
        return destination?.let {
            val foods = localFoodRepository.listByDestinationId(it.id)
            val restaurants = restaurantRepository.getRestaurantsByDestinationId(it.id)
            it.copy(foods = foods.first, restaurants = restaurants.first)
        }
    }

    suspend fun getByIdDetail(id: Long): DestinationDetail? {
        val destination = destinationRepository.findByIdDetail(id)
        return destination?.let {
            val foods = localFoodRepository.listByDestinationId(it.id)
            val restaurants = restaurantRepository.getRestaurantsByDestinationId(it.id)
            it.copy(foods = foods.first, restaurants = restaurants.first)
        }
    }

    suspend fun getTree(id: Long): Destination? {
        val destination = destinationRepository.findTree(id)
        return destination?.let {
            val foods = localFoodRepository.listByDestinationId(it.id)
            val restaurants = restaurantRepository.getRestaurantsByDestinationId(it.id)
            it.copy(foods = foods.first, restaurants = restaurants.first)
        }
    }

    suspend fun getRootDestinations(): List<Destination> {
        val destinations = destinationRepository.listRoot()
        return destinations.map { destination ->
            val foods = localFoodRepository.listByDestinationId(destination.id)
            val restaurants = restaurantRepository.getRestaurantsByDestinationId(destination.id)
            destination.copy(foods = foods.first, restaurants = restaurants.first)
        }
    }

    suspend fun create(destinationRequest: DestinationRequest): Long {
//        val resolvedImages =
//            imageMappingService.resolveImages(
//                Gson().toJson(destinationRequest.images),
//                destinationRequest.tempUrlMap ?: emptyMap(),
//            )
//        val requestWithResolvedImages = destinationRequest.copy(images = Gson().fromJson(resolvedImages, List::class.java) as List<String>)
        return destinationRepository.create(destinationRequest).apply {
            redisRepository.del("destinations:all:vi")
            redisRepository.del("destinations:all:en")
        }
    }

    suspend fun update(
        id: Long,
        destinationRequest: DestinationRequest,
    ) {
        val resolvedImages =
            imageMappingService.resolveImages(
                Gson().toJson(destinationRequest.images),
                destinationRequest.tempUrlMap ?: emptyMap(),
            )
        val requestWithResolvedImages = destinationRequest.copy(images = Gson().fromJson(resolvedImages, List::class.java) as List<String>)
        destinationRepository.update(id, requestWithResolvedImages)
    }

    suspend fun search(
        query: String,
        types: List<com.travel.data.table.DestinationType>,
        page: Int,
        pageSize: Int,
    ): Pair<List<Destination>, Long> {
        return destinationRepository.search(query, types, page, pageSize)
    }

    suspend fun delete(id: Long): Boolean {
        return destinationRepository.deleteDestination(id).apply {
            redisRepository.del("destinations:all:vi")
            redisRepository.del("destinations:all:en")
        }
    }
}
