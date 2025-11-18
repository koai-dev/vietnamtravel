package com.travel.core

import io.github.cdimascio.dotenv.Dotenv

data class UploadConfig(
    val uploadProvider: String,
    val localUploadPath: String,
    val localUploadBaseUrl: String,
    val awsS3Bucket: String,
    val awsS3Region: String,
    val awsS3AccessKey: String,
    val awsS3SecretKey: String,
    val awsCloudfrontDomain: String,
)

fun loadUploadConfig(dotenv: Dotenv): UploadConfig {
    return UploadConfig(
        uploadProvider = dotenv["UPLOAD_PROVIDER"] ?: "local",
        localUploadPath = dotenv["LOCAL_UPLOAD_PATH"] ?: "assets/uploads",
        localUploadBaseUrl = dotenv["LOCAL_UPLOAD_BASE_URL"] ?: "http://localhost:8080/uploads",
        awsS3Bucket = dotenv["AWS_S3_BUCKET"] ?: "",
        awsS3Region = dotenv["AWS_S3_REGION"] ?: "",
        awsS3AccessKey = dotenv["AWS_S3_ACCESS_KEY"] ?: "",
        awsS3SecretKey = dotenv["AWS_S3_SECRET_KEY"] ?: "",
        awsCloudfrontDomain = dotenv["AWS_CLOUDFRONT_DOMAIN"] ?: "",
    )
}
