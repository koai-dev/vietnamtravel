package com.travel.data.impl

import com.travel.data.model.ReviewResponse
import com.travel.data.table.Hotels
import com.travel.data.table.Reviews
import com.travel.data.table.Users
import com.travel.domain.repository.ReviewRepository
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlinx.coroutines.Dispatchers

class ReviewRepositoryImpl : ReviewRepository {
    override suspend fun getRecentReviews(limit: Int): List<ReviewResponse> = newSuspendedTransaction(Dispatchers.IO) {
        Reviews
            .join(Users, JoinType.LEFT, Reviews.userId, Users.id)
            .join(Hotels, JoinType.INNER, Reviews.hotelId, Hotels.id)
            .selectAll()
            .orderBy(Reviews.createdAt, SortOrder.DESC)
            .limit(limit)
            .map {
                ReviewResponse(
                    id = it[Reviews.id],
                    userId = it[Reviews.userId],
                    hotelId = it[Reviews.hotelId],
                    rating = it[Reviews.rating],
                    comment = it[Reviews.comment],
                    createdAt = it[Reviews.createdAt].toString(),
                    userName = it[Users.name],
                    hotelName = it[Hotels.nameVi] // Or nameEn depending on preference, using nameVi for now
                )
            }
    }

    override suspend fun count(): Long = newSuspendedTransaction(Dispatchers.IO) {
        Reviews.selectAll().count()
    }
}
