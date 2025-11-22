import com.travel.data.table.Rooms
import com.travel.domain.model.Room
import com.travel.domain.repository.RoomRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class RoomRepositoryImpl : RoomRepository {
    override suspend fun findById(id: Long): Room? =
        newSuspendedTransaction {
            Rooms.selectAll().where { Rooms.id eq id }.map { it.toRoom() }.singleOrNull()
        }

    override suspend fun updateAvailableRooms(
        id: Long,
        availableRooms: Int,
    ) {
        newSuspendedTransaction {
            Rooms.update({ Rooms.id eq id }) {
                it[Rooms.availableRooms] = availableRooms
            }
        }
    }

    override suspend fun getAll(
        page: Int,
        pageSize: Int,
    ): Pair<List<Room>, Long> =
        newSuspendedTransaction {
            val query = Rooms.selectAll()
            val total = query.count()
            val items =
                query
                    .limit(pageSize, offset = ((page - 1) * pageSize).toLong())
                    .map { it.toRoom() }
            Pair(items, total)
        }

    override suspend fun getByHotel(
        hotelId: Long,
        page: Int,
        pageSize: Int,
    ): Pair<List<Room>, Long> =
        newSuspendedTransaction {
            val query = Rooms.selectAll().where { Rooms.hotelId eq hotelId }
            val total = query.count()
            val items =
                query
                    .limit(pageSize, offset = ((page - 1) * pageSize).toLong())
                    .map { it.toRoom() }
            Pair(items, total)
        }

    override suspend fun create(room: Room): Room =
        newSuspendedTransaction {
            val id =
                Rooms.insert {
                    it[hotelId] = room.hotelId
                    it[roomTypeVi] = room.roomTypeVi
                    it[roomTypeEn] = room.roomTypeEn
                    it[maxGuest] = room.maxGuest
                    it[pricePerNight] = room.pricePerNight?.toBigDecimal()
                    it[totalRooms] = room.totalRooms
                    it[availableRooms] = room.availableRooms
                    it[amenities] = room.amenities?.let { a -> Json.encodeToString(a) }
                } get Rooms.id
            findById(id)!!
        }

    override suspend fun update(
        id: Long,
        room: Room,
    ): Room? =
        newSuspendedTransaction {
            Rooms.update({ Rooms.id eq id }) {
                it[roomTypeVi] = room.roomTypeVi
                it[roomTypeEn] = room.roomTypeEn
                it[maxGuest] = room.maxGuest
                it[pricePerNight] = room.pricePerNight?.toBigDecimal()
                it[totalRooms] = room.totalRooms
                it[availableRooms] = room.availableRooms
                it[amenities] = room.amenities?.let { a -> Json.encodeToString(a) }
            }
            findById(id)
        }

    override suspend fun delete(id: Long): Boolean =
        newSuspendedTransaction {
            Rooms.deleteWhere { Rooms.id eq id } > 0
        }
}

private fun ResultRow.toRoom(): Room =
    Room(
        id = this[Rooms.id],
        hotelId = this[Rooms.hotelId],
        roomTypeVi = this[Rooms.roomTypeVi] ?: "",
        roomTypeEn = this[Rooms.roomTypeEn] ?: "",
        maxGuest = this[Rooms.maxGuest] ?: 0,
        pricePerNight = this[Rooms.pricePerNight]?.toDouble() ?: 0.0,
        totalRooms = this[Rooms.totalRooms] ?: 0,
        availableRooms = this[Rooms.availableRooms] ?: 0,
        amenities = this[Rooms.amenities]?.let { Json.decodeFromString<List<String>>(it) } ?: emptyList(),
    )
