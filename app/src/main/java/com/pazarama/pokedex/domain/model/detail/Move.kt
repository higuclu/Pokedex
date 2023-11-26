package com.pazarama.pokedex.domain.model.detail

import com.google.gson.annotations.SerializedName

data class Move(
    @SerializedName("move")
    val move: Species
)
