package com.travel.data.impl

import com.travel.data.model.ReviewResponse
import com.travel.data.table.Hotels
import com.travel.data.table.Reviews
import com.travel.data.table.Users
import com.travel.domain.repository.ReviewRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

class ReviewRepositoryImpl : ReviewRepository {
    override suspend fun getRecentReviews(limit: Int): List<ReviewResponse> =
        newSuspendedTransaction(Dispatchers.IO) {
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
                        hotelName = it[Hotels.nameVi], // Or nameEn depending on preference, using nameVi for now
                    )
                }
        }

    override suspend fun count(): Long =
        newSuspendedTransaction(Dispatchers.IO) {
            Reviews.selectAll().count()
        }

    override suspend fun getAll(
        page: Int,
        pageSize: Int,
    ): Pair<List<ReviewResponse>, Long> =
        newSuspendedTransaction(Dispatchers.IO) {
            val query =
                Reviews
                    .join(Users, JoinType.LEFT, Reviews.userId, Users.id)
                    .join(Hotels, JoinType.INNER, Reviews.hotelId, Hotels.id)
                    .selectAll()
            val total = query.count()
            val items =
                query
                    .orderBy(Reviews.createdAt, SortOrder.DESC)
                    .limit(pageSize, offset = ((page - 1) * pageSize).toLong())
                    .map { it.toReviewResponse() }
            Pair(items, total)
        }

    override suspend fun getByHotel(
        hotelId: Long,
        page: Int,
        pageSize: Int,
    ): Pair<List<ReviewResponse>, Long> =
        newSuspendedTransaction(Dispatchers.IO) {
            val query =
                Reviews
                    .join(Users, JoinType.LEFT, Reviews.userId, Users.id)
                    .join(Hotels, JoinType.INNER, Reviews.hotelId, Hotels.id)
                    .selectAll()
                    .where { Reviews.hotelId eq hotelId }
            val total = query.count()
            val items =
                query
                    .orderBy(Reviews.createdAt, SortOrder.DESC)
                    .limit(pageSize, offset = ((page - 1) * pageSize).toLong())
                    .map { it.toReviewResponse() }
            Pair(items, total)
        }

    override suspend fun getById(id: Long): ReviewResponse? =
        newSuspendedTransaction(Dispatchers.IO) {
            Reviews
                .join(Users, JoinType.LEFT, Reviews.userId, Users.id)
                .join(Hotels, JoinType.INNER, Reviews.hotelId, Hotels.id)
                .selectAll()
                .where { Reviews.id eq id }
                .map { it.toReviewResponse() }
                .singleOrNull()
        }

    override suspend fun create(
        userId: Long?,
        hotelId: Long,
        rating: Int?,
        comment: String?,
    ): ReviewResponse =
        newSuspendedTransaction(Dispatchers.IO) {
            val id =
                Reviews.insert {
                    it[Reviews.userId] = userId
                    it[Reviews.hotelId] = hotelId
                    it[Reviews.rating] = rating
                    it[Reviews.comment] = comment
                    it[createdAt] = java.time.LocalDateTime.now()
                } get Reviews.id
            getById(id)!!
        }

    override suspend fun update(
        id: Long,
        rating: Int?,
        comment: String?,
    ): ReviewResponse? =
        newSuspendedTransaction(Dispatchers.IO) {
            Reviews.update({ Reviews.id eq id }) {
                rating?.let { r -> it[Reviews.rating] = r }
                comment?.let { c -> it[Reviews.comment] = c }
            }
            getById(id)
        }

    override suspend fun delete(id: Long): Boolean =
        newSuspendedTransaction(Dispatchers.IO) {
            Reviews.deleteWhere { Reviews.id eq id } > 0
        }

    private fun org.jetbrains.exposed.sql.ResultRow.toReviewResponse() =
        ReviewResponse(
            id = this[Reviews.id],
            userId = this[Reviews.userId],
            hotelId = this[Reviews.hotelId],
            rating = this[Reviews.rating],
            comment = this[Reviews.comment],
            createdAt = this[Reviews.createdAt].toString(),
            userName = this[Users.name],
            hotelName = this[Hotels.nameVi],
        )
}
