package com.pazarama.pokedex.domain.model.detail

import com.google.gson.annotations.SerializedName

data class SpriteOther (
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork

)
