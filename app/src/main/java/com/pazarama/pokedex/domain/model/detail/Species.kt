package com.pazarama.pokedex.domain.model.detail

import com.google.gson.annotations.SerializedName

data class Species(
    @SerializedName("name")
    val name: String
)
