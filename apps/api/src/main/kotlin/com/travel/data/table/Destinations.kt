package com.travel.data.table

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object Destinations : Table("destinations") {
    val id = long("id").autoIncrement()
    val nameVi = varchar("name_vi", 255).nullable()
    val nameEn = varchar("name_en", 255).nullable()
    val descriptionVi = text("description_vi").nullable()
    val descriptionEn = text("description_en").nullable()
    val latitude = double("latitude").nullable()
    val longitude = double("longitude").nullable()
    val type = enumerationByName("type", 20, DestinationType::class).nullable()
    val images = text("images").nullable() // Storing JSON as TEXT
    val parentId = long("parent_id").references(id, onDelete = ReferenceOption.CASCADE).nullable()

    override val primaryKey = PrimaryKey(id)
}

enum class DestinationType {
    region, city, attraction, spot
}
