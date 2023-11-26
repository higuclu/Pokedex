package com.pazarama.pokedex.domain.model.item

import com.google.gson.annotations.SerializedName
import com.pazarama.pokedex.util.Constants.ICON_BASE_URL
import com.pazarama.pokedex.util.UrlIdExtractor

data class PokemonItem(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
) {
    fun getId(): Int = UrlIdExtractor.extractId(url) // get id from url
    fun geticonUrl(): String = ICON_BASE_URL + getId().toString() + ".png" // create icon url
}
