package com.travel.domain.service

interface FileUploader {
    /**
     * Uploads a file.
     *
     * @param fileBytes The content of the file as a byte array.
     * @param fileName The original name of the file, used to determine the extension.
     * @return The publicly accessible URL of the uploaded file.
     */
    suspend fun upload(fileBytes: ByteArray, fileName: String): String
}
