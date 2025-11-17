package com.travel.data.table

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

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
    val slug = varchar("slug", 255).uniqueIndex().nullable()
    val address = varchar("address", 512).nullable()
    val city = varchar("city", 255).nullable()
    val tags = text("tags").nullable()
    val bestTimeToVisit = varchar("best_time_to_visit", 255).nullable()
    val openingHours = varchar("opening_hours", 255).nullable()
    val priceFrom = decimal("price_from", 10, 2).nullable()
    val priceTo = decimal("price_to", 10, 2).nullable()
    val externalLinks = text("external_links").nullable()
    val addressLink = varchar("address_link", 1024).nullable()
    val avgRating = double("avg_rating").default(0.0)
    val reviewCount = integer("review_count").default(0)
    val viewsCount = long("views_count").default(0)
    val favoritesCount = integer("favorites_count").default(0)
    val status = enumerationByName("status", 20, DestinationStatus::class).default(DestinationStatus.ACTIVE)
    val sortOrder = integer("sort_order").default(0)
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")

    override val primaryKey = PrimaryKey(id)
}

enum class DestinationType {
    region, city, attraction, spot
}

enum class DestinationStatus {
    ACTIVE, INACTIVE, DRAFT
}
