package com.pazarama.pokedex.domain.repository

import com.pazarama.pokedex.domain.model.detail.PokemonDetail
import com.pazarama.pokedex.domain.model.item.PokemonItems
import com.pazarama.pokedex.util.Resource

interface PokemonRepository {
    suspend fun getPokemonItems(): Resource<PokemonItems>
    suspend fun getPokemonDetail(name: String): Resource<PokemonDetail>
}
