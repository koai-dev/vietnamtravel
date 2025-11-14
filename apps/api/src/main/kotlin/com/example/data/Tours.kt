package com.example.data

import org.jetbrains.exposed.sql.Table

object Tours : Table("tours") {
    val id = long("id").autoIncrement()
    val titleVi = varchar("title_vi", 255).nullable()
    val titleEn = varchar("title_en", 255).nullable()
    val descriptionVi = text("description_vi").nullable()
    val descriptionEn = text("description_en").nullable()
    val price = decimal("price", 10, 2).nullable()
    val durationHours = integer("duration_hours").nullable()
    val destinationId = long("destination_id").references(Destinations.id).nullable()
    val images = text("images").nullable() // Storing JSON as TEXT

    override val primaryKey = PrimaryKey(id)
}
