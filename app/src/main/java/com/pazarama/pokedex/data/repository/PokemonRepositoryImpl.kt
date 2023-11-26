package com.pazarama.pokedex.data.repository

import com.pazarama.pokedex.domain.model.detail.PokemonDetail
import com.pazarama.pokedex.domain.model.item.PokemonItems
import com.pazarama.pokedex.domain.repository.PokemonRepository
import com.pazarama.pokedex.domain.usecase.GetPokemonDetailUseCase
import com.pazarama.pokedex.domain.usecase.GetPokemonItemsUseCase
import com.pazarama.pokedex.util.Resource
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val getPokemonItemsUseCase: GetPokemonItemsUseCase,
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase
) : PokemonRepository {
    override suspend fun getPokemonItems(): Resource<PokemonItems> {
        return getPokemonItemsUseCase.execute()
    }

    override suspend fun getPokemonDetail(name: String): Resource<PokemonDetail> {
        return getPokemonDetailUseCase.execute(name)
    }
}
