package com.travel.data.table

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object RestaurantLocalFoods : Table("restaurant_local_foods") {
    val restaurantId = long("restaurant_id").references(Restaurants.id, onDelete = ReferenceOption.CASCADE)
    val localFoodId = long("local_food_id").references(LocalFoods.id, onDelete = ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(restaurantId, localFoodId)
}
