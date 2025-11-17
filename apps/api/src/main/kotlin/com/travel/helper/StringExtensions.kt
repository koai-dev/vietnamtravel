package com.travel.helper

import java.text.Normalizer
import java.util.regex.Pattern

private val NONLATIN = Pattern.compile("[^\\w-]")
private val WHITESPACE = Pattern.compile("[\\s]")

fun String.toSlug(): String {
    val nowhitespace = WHITESPACE.matcher(this).replaceAll("-")
    val normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD)
    val slug = NONLATIN.matcher(normalized).replaceAll("")
    return slug.toLowerCase()
}
