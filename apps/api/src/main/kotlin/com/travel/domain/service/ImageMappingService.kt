package com.travel.domain.service

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.travel.helper.upload.ImageUrlResolver

class ImageMappingService {
    private val gson = Gson()
    private val listType = object : TypeToken<List<String>>() {}.type

    /**
     * Resolves a JSON array of image URLs, converting any `upload://` placeholders.
     * This assumes a temporary storage mechanism (like Redis) holds the mapping
     * from the placeholder to the final URL, which was stored after the initial upload.
     *
     * For this implementation, we will simulate this by requiring a pre-filled map.
     */
    fun resolveImages(
        imageJson: String?,
        tempUrlMap: Map<String, String>,
    ): String {
        return ImageUrlResolver.resolveImages(imageJson, tempUrlMap)
    }

    /**
     * Resolves a single image URL.
     */
    fun resolveImage(
        imageUrl: String?,
        tempUrlMap: Map<String, String>,
    ): String? {
        return ImageUrlResolver.resolveImage(imageUrl, tempUrlMap)
    }
}
