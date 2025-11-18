package com.travel.helper.upload

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ImageUrlResolver {
    private val gson = Gson()
    private val listType = object : TypeToken<List<String>>() {}.type

    fun resolveImages(
        jsonString: String?,
        uploadUrlMap: Map<String, String>,
    ): String {
        if (jsonString.isNullOrBlank()) return "[]"

        val images: List<String> = gson.fromJson(jsonString, listType)
        val resolvedImages =
            images.map { imageUrl ->
                if (imageUrl.startsWith("upload://")) {
                    uploadUrlMap[imageUrl] ?: imageUrl // Keep original if no mapping found
                } else {
                    imageUrl
                }
            }
        return gson.toJson(resolvedImages)
    }

    fun resolveImage(
        imageUrl: String?,
        uploadUrlMap: Map<String, String>,
    ): String? {
        if (imageUrl.isNullOrBlank()) return null
        return if (imageUrl.startsWith("upload://")) {
            uploadUrlMap[imageUrl] ?: imageUrl
        } else {
            imageUrl
        }
    }
}
