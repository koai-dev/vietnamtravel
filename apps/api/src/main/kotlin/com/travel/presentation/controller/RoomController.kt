package com.travel.presentation.controller

import com.travel.domain.model.Room
import com.travel.domain.repository.RoomRepository
import com.travel.presentation.model.PaginatedResponse
import com.travel.presentation.model.Pagination
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.receive
import kotlinx.serialization.Serializable

@Serializable
data class CreateRoomRequest(
    val hotelId: Long,
    val roomTypeVi: String?,
    val roomTypeEn: String?,
    val maxGuest: Int?,
    val pricePerNight: Double?,
    val totalRooms: Int?,
    val availableRooms: Int?,
    val amenities: List<String>?,
)

@Serializable
data class UpdateRoomRequest(
    val roomTypeVi: String?,
    val roomTypeEn: String?,
    val maxGuest: Int?,
    val pricePerNight: Double?,
    val totalRooms: Int?,
    val availableRooms: Int?,
    val amenities: List<String>?,
)

class RoomController(
    private val roomRepository: RoomRepository,
) : BaseController() {
    suspend fun getAll(call: ApplicationCall) {
        val (page, pageSize) = getPaginationParams(call)
        val (rooms, total) = roomRepository.getAll(page, pageSize)
        val totalPages = ((total + pageSize - 1) / pageSize).toInt()

        respondWith(
            call,
            PaginatedResponse(
                data = rooms,
                pagination = Pagination(page, pageSize, total, totalPages),
            ),
        )
    }

    suspend fun getByHotel(call: ApplicationCall) {
        val hotelId = call.parameters["hotelId"]?.toLongOrNull()
            ?: return respondWithError(call, "Invalid hotel ID", HttpStatusCode.BadRequest)

        val (page, pageSize) = getPaginationParams(call)
        val (rooms, total) = roomRepository.getByHotel(hotelId, page, pageSize)
        val totalPages = ((total + pageSize - 1) / pageSize).toInt()

        respondWith(
            call,
            PaginatedResponse(
                data = rooms,
                pagination = Pagination(page, pageSize, total, totalPages),
            ),
        )
    }

    suspend fun getById(call: ApplicationCall) {
        val id = call.parameters["id"]?.toLongOrNull()
            ?: return respondWithError(call, "Invalid room ID", HttpStatusCode.BadRequest)

        val room = roomRepository.findById(id)
            ?: return respondWithError(call, "Room not found", HttpStatusCode.NotFound)

        respondWith(call, room)
    }

    suspend fun create(call: ApplicationCall) {
        val request = call.receive<CreateRoomRequest>()

        val room = Room(
            id = 0,
            hotelId = request.hotelId,
            roomTypeVi = request.roomTypeVi ?: "",
            roomTypeEn = request.roomTypeEn ?: "",
            maxGuest = request.maxGuest ?: 0,
            pricePerNight = request.pricePerNight ?: 0.0,
            totalRooms = request.totalRooms ?: 0,
            availableRooms = request.availableRooms ?: 0,
            amenities = request.amenities ?: emptyList(),
        )

        val created = roomRepository.create(room)
        respondWith(call, created, "Room created successfully")
    }

    suspend fun update(call: ApplicationCall) {
        val id = call.parameters["id"]?.toLongOrNull()
            ?: return respondWithError(call, "Invalid room ID", HttpStatusCode.BadRequest)

        val request = call.receive<UpdateRoomRequest>()
        val existing = roomRepository.findById(id)
            ?: return respondWithError(call, "Room not found", HttpStatusCode.NotFound)

        val room = Room(
            id = id,
            hotelId = existing.hotelId,
            roomTypeVi = request.roomTypeVi ?: existing.roomTypeVi,
            roomTypeEn = request.roomTypeEn ?: existing.roomTypeEn,
            maxGuest = request.maxGuest ?: existing.maxGuest,
            pricePerNight = request.pricePerNight ?: existing.pricePerNight,
            totalRooms = request.totalRooms ?: existing.totalRooms,
            availableRooms = request.availableRooms ?: existing.availableRooms,
            amenities = request.amenities ?: existing.amenities,
        )

        val updated = roomRepository.update(id, room)
            ?: return respondWithError(call, "Room not found", HttpStatusCode.NotFound)

        respondWith(call, updated, "Room updated successfully")
    }

    suspend fun delete(call: ApplicationCall) {
        val id = call.parameters["id"]?.toLongOrNull()
            ?: return respondWithError(call, "Invalid room ID", HttpStatusCode.BadRequest)

        val deleted = roomRepository.delete(id)
        if (deleted) {
            respondWith(call, true, "Room deleted successfully")
        } else {
            respondWithError(call, "Room not found", HttpStatusCode.NotFound)
        }
    }
}
