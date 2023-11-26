package com.pazarama.pokedex.domain.model.detail

import com.google.gson.annotations.SerializedName

data class Stat(
    @SerializedName("base_stat")
    val baseStat: Int,
    @SerializedName("stat")
    val stat: Species
)
