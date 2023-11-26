package com.pazarama.pokedex.data.service

import com.pazarama.pokedex.domain.model.detail.PokemonDetail
import com.pazarama.pokedex.domain.model.item.PokemonItems
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonAPI {
    @GET("pokemon")
    suspend fun getPokemonItems(): Response<PokemonItems>

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(@Path("name") name: String): Response<PokemonDetail>
}
