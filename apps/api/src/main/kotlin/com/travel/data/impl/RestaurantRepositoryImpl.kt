package com.travel.data.impl

import com.travel.data.mapper.toRestaurant
import com.travel.data.model.RestaurantRequest
import com.travel.data.table.LocalFoods
import com.travel.data.table.RestaurantLocalFoods
import com.travel.data.table.Restaurants
import com.travel.domain.model.LocalFood
import com.travel.domain.model.Restaurant
import com.travel.domain.repository.RestaurantRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class RestaurantRepositoryImpl : RestaurantRepository {
    override suspend fun createRestaurant(restaurantRequest: RestaurantRequest): Restaurant =
        newSuspendedTransaction {
            val insertedStatement =
                Restaurants.insert {
                    it[name] = restaurantRequest.name
                    it[description] = restaurantRequest.description
                    it[images] = Json.encodeToString(restaurantRequest.images)
                    it[address] = restaurantRequest.address
                    it[latitude] = restaurantRequest.latitude
                    it[longitude] = restaurantRequest.longitude
                    it[destinationId] = restaurantRequest.destinationId
                }

            val restaurantId = insertedStatement[Restaurants.id]

            RestaurantLocalFoods.batchInsert(restaurantRequest.localFoodIds) { localFoodId ->
                this[RestaurantLocalFoods.restaurantId] = restaurantId
                this[RestaurantLocalFoods.localFoodId] = localFoodId
            }

            insertedStatement.resultedValues?.single()?.toRestaurant() ?: throw Exception("Failed to create restaurant")
        }

    override suspend fun getRestaurantById(id: Long): Restaurant? =
        newSuspendedTransaction {
            Restaurants.selectAll().where { Restaurants.id eq id }.singleOrNull()?.toRestaurant()
        }

    override suspend fun updateRestaurant(
        id: Long,
        restaurantRequest: RestaurantRequest,
    ) {
        newSuspendedTransaction {
            Restaurants.update({ Restaurants.id eq id }) {
                it[name] = restaurantRequest.name
                it[description] = restaurantRequest.description
                it[images] = Json.encodeToString(restaurantRequest.images)
                it[address] = restaurantRequest.address
                it[latitude] = restaurantRequest.latitude
                it[longitude] = restaurantRequest.longitude
                it[destinationId] = restaurantRequest.destinationId
            }

            RestaurantLocalFoods.deleteWhere { restaurantId eq id }

            RestaurantLocalFoods.batchInsert(restaurantRequest.localFoodIds) { localFoodId ->
                this[RestaurantLocalFoods.restaurantId] = id
                this[RestaurantLocalFoods.localFoodId] = localFoodId
            }
        }
    }

    override suspend fun deleteRestaurant(id: Long) {
        newSuspendedTransaction {
            Restaurants.deleteWhere { Restaurants.id eq id }
        }
    }

    override suspend fun getRestaurantsByDestinationId(destinationId: Long): List<Restaurant> =
        newSuspendedTransaction {
            Restaurants.select { Restaurants.destinationId eq destinationId }.map { it.toRestaurant() }
        }

    override suspend fun getLocalFoodsForRestaurant(restaurantId: Long): List<LocalFood> =
        newSuspendedTransaction {
            (RestaurantLocalFoods innerJoin LocalFoods)
                .select { RestaurantLocalFoods.restaurantId eq restaurantId }
                .map { it.toLocalFood() }
        }

    private fun ResultRow.toLocalFood(): LocalFood =
        LocalFood(
            id = this[LocalFoods.id],
            destinationId = this[LocalFoods.destinationId],
            nameVi = this[LocalFoods.nameVi],
            nameEn = this[LocalFoods.nameEn],
            descriptionVi = this[LocalFoods.descriptionVi],
            descriptionEn = this[LocalFoods.descriptionEn],
            images = this[LocalFoods.images]?.let { Json.decodeFromString<List<String>>(it) } ?: emptyList(),
        )
}
