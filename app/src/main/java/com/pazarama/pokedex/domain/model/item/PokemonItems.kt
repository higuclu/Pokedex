package com.pazarama.pokedex.domain.model.item

import com.google.gson.annotations.SerializedName

data class PokemonItems(
    @SerializedName("count")
    val count: Long,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val results: List<PokemonItem>
)
