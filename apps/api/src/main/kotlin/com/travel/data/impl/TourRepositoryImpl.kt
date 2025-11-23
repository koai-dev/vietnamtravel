import com.travel.data.table.Tours
import com.travel.domain.model.Tour
import com.travel.domain.repository.TourRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class TourRepositoryImpl : TourRepository {
    override suspend fun getAll(
        page: Int,
        pageSize: Int,
    ): Pair<List<Tour>, Long> =
        newSuspendedTransaction {
            val total = Tours.selectAll().count()
            val items =
                Tours.selectAll()
                    .limit(pageSize, offset = ((page - 1) * pageSize).toLong())
                    .map { it.toTour() }
            Pair(items, total)
        }

    override suspend fun findById(id: Long): Tour? =
        newSuspendedTransaction {
            Tours.selectAll().where { Tours.id eq id }.map { it.toTour() }.singleOrNull()
        }

    override suspend fun getPopular(): List<Tour> =
        newSuspendedTransaction {
            Tours.selectAll().limit(5).map { it.toTour() }
        }

    override suspend fun create(tour: Tour): Tour =
        newSuspendedTransaction {
            val id =
                Tours.insert {
                    it[titleVi] = tour.titleVi
                    it[titleEn] = tour.titleEn
                    it[descriptionVi] = tour.descriptionVi
                    it[descriptionEn] = tour.descriptionEn
                    it[price] = tour.price.toBigDecimal()
                    it[durationHours] = tour.durationHours
                    it[destinationId] = tour.destinationId
                    it[images] = Json.encodeToString(tour.images)
                } get Tours.id
            findById(id)!!
        }

    override suspend fun update(
        id: Long,
        tour: Tour,
    ): Tour? =
        newSuspendedTransaction {
            Tours.update({ Tours.id eq id }) {
                it[titleVi] = tour.titleVi
                it[titleEn] = tour.titleEn
                it[descriptionVi] = tour.descriptionVi
                it[descriptionEn] = tour.descriptionEn
                it[price] = tour.price.toBigDecimal()
                it[durationHours] = tour.durationHours
                it[destinationId] = tour.destinationId
                it[images] = Json.encodeToString(tour.images)
            }
            findById(id)
        }

    override suspend fun delete(id: Long): Boolean =
        newSuspendedTransaction {
            Tours.deleteWhere { Tours.id eq id } > 0
        }
}

private fun ResultRow.toTour(): Tour =
    Tour(
        id = this[Tours.id],
        titleVi = this[Tours.titleVi] ?: "",
        titleEn = this[Tours.titleEn] ?: "",
        descriptionVi = this[Tours.descriptionVi] ?: "",
        descriptionEn = this[Tours.descriptionEn] ?: "",
        price = this[Tours.price]?.toDouble() ?: 0.0,
        durationHours = this[Tours.durationHours] ?: 0,
        destinationId = this[Tours.destinationId] ?: 0,
        images =
            this[Tours.images]?.let { Json.decodeFromString<List<String>>(it) }
                ?: emptyList(),
    )
