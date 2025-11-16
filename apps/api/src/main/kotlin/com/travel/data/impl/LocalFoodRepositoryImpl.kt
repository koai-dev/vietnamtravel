package com.travel.data.impl

import com.travel.data.table.LocalFoods
import com.travel.domain.model.LocalFood
import com.travel.domain.repository.LocalFoodRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class LocalFoodRepositoryImpl : LocalFoodRepository {

    private fun ResultRow.toLocalFood(): LocalFood = LocalFood(
        id = this[LocalFoods.id],
        destinationId = this[LocalFoods.destinationId],
        nameVi = this[LocalFoods.nameVi],
        nameEn = this[LocalFoods.nameEn],
        descriptionVi = this[LocalFoods.descriptionVi],
        descriptionEn = this[LocalFoods.descriptionEn],
        images = this[LocalFoods.images]?.let { Json.decodeFromString<List<String>>(it) } ?: emptyList()
    )

    override suspend fun create(localFood: LocalFood): LocalFood = newSuspendedTransaction {
        val insertedId = LocalFoods.insert {
            it[destinationId] = localFood.destinationId
            it[nameVi] = localFood.nameVi
            it[nameEn] = localFood.nameEn
            it[descriptionVi] = localFood.descriptionVi
            it[descriptionEn] = localFood.descriptionEn
            it[images] = Json.encodeToString(localFood.images)
        } get LocalFoods.id
        localFood.copy(id = insertedId)
    }

    override suspend fun update(id: Long, localFood: LocalFood): LocalFood? {
        newSuspendedTransaction {
            LocalFoods.update({ Op.build { LocalFoods.id eq id } }) {
                it[destinationId] = localFood.destinationId
                it[nameVi] = localFood.nameVi
                it[nameEn] = localFood.nameEn
                it[descriptionVi] = localFood.descriptionVi
                it[descriptionEn] = localFood.descriptionEn
                it[images] = Json.encodeToString(localFood.images)
            }
        }
        return getById(id)
    }

    override suspend fun delete(id: Long) {
        newSuspendedTransaction {
            LocalFoods.deleteWhere { Op.build { LocalFoods.id eq id } }
        }
    }

    override suspend fun getById(id: Long): LocalFood? = newSuspendedTransaction {
        LocalFoods.select { LocalFoods.id eq id }
            .map { it.toLocalFood() }
            .singleOrNull()
    }

    override suspend fun listByDestinationId(destinationId: Long): List<LocalFood> = newSuspendedTransaction {
        LocalFoods.select { LocalFoods.destinationId eq destinationId }
            .map { it.toLocalFood() }
    }
}
