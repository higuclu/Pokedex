package com.pazarama.pokedex.domain.repository

interface PreferenceRepository {
    fun setSearchByName()
    fun setSearchById()
    fun isSearchByName(): Boolean
}