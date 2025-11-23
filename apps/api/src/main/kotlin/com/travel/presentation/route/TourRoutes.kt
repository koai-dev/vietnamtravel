import com.travel.core.RateLimiter
import com.travel.core.lang
import com.travel.domain.repository.RedisRepository
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.tourRoutes() {
    val tourController by inject<TourController>()
    val redisRepository by inject<RedisRepository>()
    val rateLimiter = RateLimiter(redisRepository)

    route("/api/tours") {
        install(rateLimiter.limit("/tours", 100, 60))
        get {
            val lang = call.lang()
            tourController.getAll(call, lang)
        }

        get("/popular") {
            val lang = call.lang()
            tourController.getPopular(call, lang)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw IllegalArgumentException("Invalid ID")
            val lang = call.lang()
            tourController.getById(call, id, lang)
        }

        post {
            tourController.create(call)
        }

        put("/{id}") {
            tourController.update(call)
        }

        delete("/{id}") {
            tourController.delete(call)
        }
    }
}
