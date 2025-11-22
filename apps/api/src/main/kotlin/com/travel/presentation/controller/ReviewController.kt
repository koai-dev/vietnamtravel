package com.travel.presentation.controller

import com.travel.domain.repository.ReviewRepository
import com.travel.presentation.model.PaginatedResponse
import com.travel.presentation.model.Pagination
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.receive
import kotlinx.serialization.Serializable

@Serializable
data class CreateReviewRequest(
    val userId: Long?,
    val hotelId: Long,
    val rating: Int?,
    val comment: String?,
)

@Serializable
data class UpdateReviewRequest(
    val rating: Int?,
    val comment: String?,
)

class ReviewController(
    private val reviewRepository: ReviewRepository,
) : BaseController() {
    suspend fun getAll(call: ApplicationCall) {
        val (page, pageSize) = getPaginationParams(call)
        val (reviews, total) = reviewRepository.getAll(page, pageSize)
        val totalPages = ((total + pageSize - 1) / pageSize).toInt()

        respondWith(
            call,
            PaginatedResponse(
                data = reviews,
                pagination = Pagination(page, pageSize, total, totalPages),
            ),
        )
    }

    suspend fun getByHotel(call: ApplicationCall) {
        val hotelId = call.parameters["hotelId"]?.toLongOrNull()
            ?: return respondWithError(call, "Invalid hotel ID", HttpStatusCode.BadRequest)

        val (page, pageSize) = getPaginationParams(call)
        val (reviews, total) = reviewRepository.getByHotel(hotelId, page, pageSize)
        val totalPages = ((total + pageSize - 1) / pageSize).toInt()

        respondWith(
            call,
            PaginatedResponse(
                data = reviews,
                pagination = Pagination(page, pageSize, total, totalPages),
            ),
        )
    }

    suspend fun getById(call: ApplicationCall) {
        val id = call.parameters["id"]?.toLongOrNull()
            ?: return respondWithError(call, "Invalid review ID", HttpStatusCode.BadRequest)

        val review = reviewRepository.getById(id)
            ?: return respondWithError(call, "Review not found", HttpStatusCode.NotFound)

        respondWith(call, review)
    }

    suspend fun create(call: ApplicationCall) {
        val request = call.receive<CreateReviewRequest>()
        val review = reviewRepository.create(
            userId = request.userId,
            hotelId = request.hotelId,
            rating = request.rating,
            comment = request.comment,
        )

        respondWith(call, review, "Review created successfully")
    }

    suspend fun update(call: ApplicationCall) {
        val id = call.parameters["id"]?.toLongOrNull()
            ?: return respondWithError(call, "Invalid review ID", HttpStatusCode.BadRequest)

        val request = call.receive<UpdateReviewRequest>()
        val review = reviewRepository.update(
            id = id,
            rating = request.rating,
            comment = request.comment,
        ) ?: return respondWithError(call, "Review not found", HttpStatusCode.NotFound)

        respondWith(call, review, "Review updated successfully")
    }

    suspend fun delete(call: ApplicationCall) {
        val id = call.parameters["id"]?.toLongOrNull()
            ?: return respondWithError(call, "Invalid review ID", HttpStatusCode.BadRequest)

        val deleted = reviewRepository.delete(id)
        if (deleted) {
            respondWith(call, true, "Review deleted successfully")
        } else {
            respondWithError(call, "Review not found", HttpStatusCode.NotFound)
        }
    }
}
