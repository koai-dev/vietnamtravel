package com.travel.data.table

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object Restaurants : Table() {
    val id = long("id").autoIncrement()
    val name = varchar("name", 255)
    val description = text("description")
    val images = text("images")
    val address = varchar("address", 255)
    val latitude = double("latitude")
    val longitude = double("longitude")
    val destinationId = long("destination_id").references(Destinations.id, onDelete = ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(id)
}
