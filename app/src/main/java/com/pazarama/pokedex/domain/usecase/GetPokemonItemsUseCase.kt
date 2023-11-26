package com.pazarama.pokedex.domain.usecase

import com.pazarama.pokedex.data.service.PokemonAPI
import com.pazarama.pokedex.domain.model.item.PokemonItems
import com.pazarama.pokedex.util.Resource
import javax.inject.Inject

class GetPokemonItemsUseCase @Inject constructor(private val pokemonAPI: PokemonAPI) {
    suspend fun execute(): Resource<PokemonItems> {
        return try {
            val response = pokemonAPI.getPokemonItems()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Response body is empty!", null)
            } else Resource.error("Response failed", null)
        } catch (e: Exception) {
            Resource.error(e.toString(), null)
        }
    }
}
