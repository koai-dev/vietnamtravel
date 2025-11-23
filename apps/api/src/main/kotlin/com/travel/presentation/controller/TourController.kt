import com.travel.data.mapper.toTourResponse
import com.travel.domain.model.Tour
import com.travel.domain.service.TourService
import com.travel.presentation.controller.BaseController
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.*
import kotlinx.serialization.Serializable

@Serializable
data class CreateTourRequest(
    val titleVi: String,
    val titleEn: String,
    val descriptionVi: String,
    val descriptionEn: String,
    val price: Double,
    val durationHours: Int,
    val destinationId: Long,
    val images: List<String>,
)

@Serializable
data class UpdateTourRequest(
    val titleVi: String?,
    val titleEn: String?,
    val descriptionVi: String?,
    val descriptionEn: String?,
    val price: Double?,
    val durationHours: Int?,
    val destinationId: Long?,
    val images: List<String>?,
)

class TourController(private val tourService: TourService) : BaseController() {
    suspend fun getAll(
        call: ApplicationCall,
        lang: String,
    ) {
        val (page, pageSize) = getPaginationParams(call)
        val (tours, total) = tourService.getAll(page, pageSize)
        val response = tours.map { it.toTourResponse(lang) }

        val totalPages = (total + pageSize - 1) / pageSize

        respondWith(
            call,
            com.travel.presentation.model.PaginatedResponse(
                data = response,
                pagination =
                    com.travel.presentation.model.Pagination(
                        page = page,
                        pageSize = pageSize,
                        total = total,
                        totalPages = totalPages.toInt(),
                    ),
            ),
        )
    }

    suspend fun getById(
        call: ApplicationCall,
        id: Long,
        lang: String,
    ) {
        val tour = tourService.getById(id)?.toTourResponse(lang)
        if (tour != null) {
            respondWith(call, tour)
        } else {
            respondWithError(call, "Tour not found", HttpStatusCode.NotFound)
        }
    }

    suspend fun getPopular(
        call: ApplicationCall,
        lang: String,
    ) {
        val tours = tourService.getPopular(lang).map { it.toTourResponse(lang) }
        respondWith(call, tours)
    }

    suspend fun create(call: ApplicationCall) {
        val request = call.receive<CreateTourRequest>()
        val tour =
            Tour(
                id = 0,
                titleVi = request.titleVi,
                titleEn = request.titleEn,
                descriptionVi = request.descriptionVi,
                descriptionEn = request.descriptionEn,
                price = request.price,
                durationHours = request.durationHours,
                destinationId = request.destinationId,
                images = request.images,
            )
        val created = tourService.create(tour)
        respondWith(call, created, "Tour created successfully")
    }

    suspend fun update(call: ApplicationCall) {
        val id =
            call.parameters["id"]?.toLongOrNull()
                ?: return respondWithError(call, "Invalid tour ID", HttpStatusCode.BadRequest)

        val request = call.receive<UpdateTourRequest>()
        val existing =
            tourService.getById(id)
                ?: return respondWithError(call, "Tour not found", HttpStatusCode.NotFound)

        val tour =
            Tour(
                id = id,
                titleVi = request.titleVi ?: existing.titleVi,
                titleEn = request.titleEn ?: existing.titleEn,
                descriptionVi = request.descriptionVi ?: existing.descriptionVi,
                descriptionEn = request.descriptionEn ?: existing.descriptionEn,
                price = request.price ?: existing.price,
                durationHours = request.durationHours ?: existing.durationHours,
                destinationId = request.destinationId ?: existing.destinationId,
                images = request.images ?: existing.images,
            )

        val updated =
            tourService.update(id, tour)
                ?: return respondWithError(call, "Tour not found", HttpStatusCode.NotFound)

        respondWith(call, updated, "Tour updated successfully")
    }

    suspend fun delete(call: ApplicationCall) {
        val id =
            call.parameters["id"]?.toLongOrNull()
                ?: return respondWithError(call, "Invalid tour ID", HttpStatusCode.BadRequest)

        val deleted = tourService.delete(id)
        if (deleted) {
            respondWith(call, true, "Tour deleted successfully")
        } else {
            respondWithError(call, "Tour not found", HttpStatusCode.NotFound)
        }
    }
}
