package com.travel.data.impl

import com.travel.core.UploadConfig
import com.travel.domain.service.FileUploader
import java.io.File
import java.time.LocalDate
import java.util.*

class LocalFileUploader(private val uploadConfig: UploadConfig) : FileUploader {

    override suspend fun upload(fileBytes: ByteArray, fileName: String): String {
        val fileExtension = fileName.substringAfterLast('.', "")
        val newFileName = "${UUID.randomUUID()}.$fileExtension"

        val now = LocalDate.now()
        val year = now.year.toString()
        val month = now.monthValue.toString().padStart(2, '0')

        val directory = File("${uploadConfig.localUploadPath}/$year/$month")
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val file = File(directory, newFileName)
        file.writeBytes(fileBytes)

        return "${uploadConfig.localUploadBaseUrl}/$year/$month/$newFileName"
    }
}
