package com.pazarama.pokedex.domain.model.detail

import com.google.gson.annotations.SerializedName

data class Sprite(
    @SerializedName("other")
    val other: SpriteOther
)
