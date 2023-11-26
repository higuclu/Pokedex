package com.pazarama.pokedex.util

object UrlIdExtractor {
    fun extractId(url: String): Int {
        val newUrl = url.substring(0, url.length - 1)
        val startIndex = newUrl.lastIndexOf('/')
        return newUrl.substring(startIndex + 1).toInt()
    }
    fun formatId(id: Int): String {
        return String.format("#%03d", id)
    }
}
