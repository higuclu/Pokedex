package com.pazarama.pokedex.domain.model.detail

import com.google.gson.annotations.SerializedName

data class Sprite(
    @SerializedName("front_default")
    val sprite: String
)
