package com.travel.data.table

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Hotels : Table("hotels") {
    val id = long("id").autoIncrement()
    val nameVi = varchar("name_vi", 255)
    val nameEn = varchar("name_en", 255)
    val descriptionVi = text("description_vi").nullable()
    val descriptionEn = text("description_en").nullable()
    val address = text("address").nullable()
    val city = varchar("city", 255).nullable()
    val latitude = double("latitude").nullable()
    val longitude = double("longitude").nullable()
    val hostId = long("host_id").references(Users.id).nullable()
    val rating = float("rating").default(0.0f)
    val reviewCount = integer("review_count").default(0)
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")

    override val primaryKey = PrimaryKey(id)
}