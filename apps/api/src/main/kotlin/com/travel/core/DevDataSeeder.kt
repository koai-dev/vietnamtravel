package com.travel.core

import com.travel.data.table.Hotels
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.random.Random

object DevDataSeeder {
    suspend fun seedHotelsIfEmpty() =
        newSuspendedTransaction(Dispatchers.IO) {
            if (Hotels.selectAll().count() == 0L) {
                val hotels =
                    (1..10).map { i ->
                        val minPrice = Random.nextDouble(50.0, 200.0)
                        val maxPrice = minPrice + Random.nextDouble(50.0, 300.0)
                        val contactInfo =
                            if (Random.nextBoolean()) {
                                mapOf(
                                    "phone" to "098765432$i",
                                    "email" to "hotel$i@example.com",
                                    "website" to "www.hotel$i.com",
                                )
                            } else {
                                null
                            }

                        val images = (1..5).map { j -> "http://localhost:8080/uploads/dummy/img$j.jpg" }
                        val amenities = listOf("Pool", "WiFi", "Breakfast", "Gym", "Parking").shuffled().take(Random.nextInt(2, 5))
                        val tags =
                            listOf(
                                "Luxury",
                                "Family-Friendly",
                                "Business",
                                "Budget",
                                "Boutique",
                            ).shuffled().take(Random.nextInt(1, 4))
                        val bookingLinks =
                            if (Random.nextBoolean()) {
                                listOf(
                                    "https://booking.com/hotel$i",
                                    "https://agoda.com/hotel$i",
                                )
                            } else {
                                emptyList()
                            }

                        Triple(
                            "Hotel Example $i",
                            "Khách sạn Ví dụ $i",
                            mapOf(
                                "slug" to "hotel-example-$i",
                                "descriptionVi" to "Đây là mô tả chi tiết cho khách sạn ví dụ $i.",
                                "descriptionEn" to "This is a detailed description for hotel example $i.",
                                "address" to "$i Example Street, Example City",
                                "city" to listOf("Hanoi", "Da Nang", "Ho Chi Minh City").random(),
                                "latitude" to Random.nextDouble(20.0, 22.0),
                                "longitude" to Random.nextDouble(105.0, 106.0),
                                "addressLink" to "https://maps.google.com/?q=hotel$i",
                                "contact" to contactInfo?.let { Json.encodeToString(it) },
                                "images" to Json.encodeToString(images),
                                "minPrice" to BigDecimal.valueOf(minPrice),
                                "maxPrice" to BigDecimal.valueOf(maxPrice),
                                "amenities" to Json.encodeToString(amenities),
                                "checkInTime" to "14:00",
                                "checkOutTime" to "12:00",
                                "cancellationPolicy" to "Free cancellation up to 24 hours before check-in.",
                                "childPolicy" to "Children under 6 stay free.",
                                "petPolicy" to "No pets allowed.",
                                "tags" to Json.encodeToString(tags),
                                "externalBookingLinks" to Json.encodeToString(bookingLinks),
                                "rating" to Random.nextFloat() * 2 + 3, // Rating between 3.0 and 5.0
                                "reviewCount" to Random.nextInt(10, 500),
                                "viewsCount" to Random.nextLong(100, 10000),
                                "favoritesCount" to Random.nextInt(5, 200),
                            ),
                        )
                    }

                Hotels.batchInsert(hotels) { (nameEn, nameVi, data) ->
                    this[Hotels.nameEn] = nameEn
                    this[Hotels.nameVi] = nameVi
                    this[Hotels.slug] = data["slug"] as String
                    this[Hotels.descriptionEn] = data["descriptionEn"] as String
                    this[Hotels.descriptionVi] = data["descriptionVi"] as String
                    this[Hotels.address] = data["address"] as String
                    this[Hotels.city] = data["city"] as String
                    this[Hotels.latitude] = data["latitude"] as Double
                    this[Hotels.longitude] = data["longitude"] as Double
                    this[Hotels.addressLink] = data["addressLink"] as String
                    this[Hotels.contact] = data["contact"] as String?
                    this[Hotels.images] = data["images"] as String
                    this[Hotels.minPrice] = data["minPrice"] as BigDecimal
                    this[Hotels.maxPrice] = data["maxPrice"] as BigDecimal
                    this[Hotels.amenities] = data["amenities"] as String
                    this[Hotels.checkInTime] = data["checkInTime"] as String
                    this[Hotels.checkOutTime] = data["checkOutTime"] as String
                    this[Hotels.cancellationPolicy] = data["cancellationPolicy"] as String
                    this[Hotels.childPolicy] = data["childPolicy"] as String
                    this[Hotels.petPolicy] = data["petPolicy"] as String
                    this[Hotels.tags] = data["tags"] as String
                    this[Hotels.externalBookingLinks] = data["externalBookingLinks"] as String
                    this[Hotels.rating] = data["rating"] as Float
                    this[Hotels.reviewCount] = data["reviewCount"] as Int
                    this[Hotels.viewsCount] = data["viewsCount"] as Long
                    this[Hotels.favoritesCount] = data["favoritesCount"] as Int
                    this[Hotels.createdAt] = LocalDateTime.now()
                    this[Hotels.updatedAt] = LocalDateTime.now()
                }
            }
        }
}
