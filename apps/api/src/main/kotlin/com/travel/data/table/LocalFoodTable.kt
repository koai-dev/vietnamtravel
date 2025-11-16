package com.travel.data.table

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object LocalFoods : Table("local_foods") {
    val id = long("id").autoIncrement()
    val destinationId = long("destination_id").references(Destinations.id, onDelete = ReferenceOption.CASCADE)
    val nameVi = varchar("name_vi", 255)
    val nameEn = varchar("name_en", 255).nullable()
    val descriptionVi = text("description_vi").nullable()
    val descriptionEn = text("description_en").nullable()
    val images = text("images").nullable() // JSON list string

    override val primaryKey = PrimaryKey(id)
}
