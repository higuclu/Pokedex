package com.pazarama.pokedex.domain.model.detail

import com.google.gson.annotations.SerializedName

data class PokemonDetail(
    @SerializedName("name")
    val name: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("weight")
    val weight: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("moves")
    val moves: List<Move>,
    @SerializedName("types")
    val types: List<Type>,
    @SerializedName("stats")
    val stats: List<Stat>,
    @SerializedName("sprites")
    val sprite: Sprite
)
