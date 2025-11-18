package com.travel.data.impl

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.ObjectCannedAcl
import aws.sdk.kotlin.services.s3.putObject
import aws.smithy.kotlin.runtime.content.ByteStream
import com.travel.core.UploadConfig
import com.travel.domain.service.FileUploader
import java.time.LocalDate
import java.util.UUID

class S3FileUploader(private val uploadConfig: UploadConfig) : FileUploader {
    private val s3Client =
        S3Client {
            region = uploadConfig.awsS3Region
            credentialsProvider =
                StaticCredentialsProvider {
                    accessKeyId = uploadConfig.awsS3AccessKey
                    secretAccessKey = uploadConfig.awsS3SecretKey
                }
        }

    override suspend fun upload(
        fileBytes: ByteArray,
        fileName: String,
    ): String {
        val fileExtension = fileName.substringAfterLast('.', "")
        val newFileName = "${UUID.randomUUID()}.$fileExtension"

        val now = LocalDate.now()
        val year = now.year.toString()
        val month = now.monthValue.toString().padStart(2, '0')

        val objectKey = "uploads/$year/$month/$newFileName"

        s3Client.putObject {
            bucket = uploadConfig.awsS3Bucket
            key = objectKey
            body = ByteStream.fromBytes(fileBytes)
            acl = ObjectCannedAcl.fromValue("public-read")
        }

        return if (uploadConfig.awsCloudfrontDomain.isNotBlank()) {
            "${uploadConfig.awsCloudfrontDomain}/$objectKey"
        } else {
            "https://${uploadConfig.awsS3Bucket}.s3.${uploadConfig.awsS3Region}.amazonaws.com/$objectKey"
        }
    }
}
