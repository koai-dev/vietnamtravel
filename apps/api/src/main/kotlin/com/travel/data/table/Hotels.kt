package com.travel.data.table

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Hotels : Table("hotels") {
    val id = long("id").autoIncrement()
    val nameVi = varchar("name_vi", 255)
    val nameEn = varchar("name_en", 255)
    val slug = varchar("slug", 255).uniqueIndex()
    val descriptionVi = text("description_vi").nullable()
    val descriptionEn = text("description_en").nullable()
    val address = text("address").nullable()
    val city = varchar("city", 255).nullable()
    val latitude = double("latitude").nullable()
    val longitude = double("longitude").nullable()
    val addressLink = varchar("address_link", 1024).nullable()
    val contact = text("contact").nullable() // JSON object as String
    val images = text("images").nullable() // JSON list as String
    val minPrice = decimal("min_price", 10, 2).nullable()
    val maxPrice = decimal("max_price", 10, 2).nullable()
    val amenities = text("amenities").nullable() // JSON list as String
    val checkInTime = varchar("check_in_time", 50).nullable()
    val checkOutTime = varchar("check_out_time", 50).nullable()
    val cancellationPolicy = text("cancellation_policy").nullable()
    val childPolicy = text("child_policy").nullable()
    val petPolicy = text("pet_policy").nullable()
    val tags = text("tags").nullable() // JSON list as String
    val externalBookingLinks = text("external_booking_links").nullable() // JSON list as String
    val rating = float("rating").default(0.0f)
    val reviewCount = integer("review_count").default(0)
    val viewsCount = long("views_count").default(0)
    val favoritesCount = integer("favorites_count").default(0)
    val hostId = long("host_id").references(Users.id).nullable()
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")

    override val primaryKey = PrimaryKey(id)
}
