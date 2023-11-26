package com.pazarama.pokedex.domain.model.detail

import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("type")
    val type: Species
)
