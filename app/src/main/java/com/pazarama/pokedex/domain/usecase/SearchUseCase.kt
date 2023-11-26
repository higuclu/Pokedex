package com.pazarama.pokedex.domain.usecase

import com.pazarama.pokedex.domain.model.search.SearchType
import com.pazarama.pokedex.domain.repository.PreferenceRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val preferenceRepository: PreferenceRepository) {
    fun setSearchByName() {
        preferenceRepository.setSearchByName()
    }

    fun setSearchById() {
        preferenceRepository.setSearchById()
    }

    fun getSearchType(): SearchType {
        return if (preferenceRepository.isSearchByName()) {
            SearchType.BY_NAME
        } else {
            SearchType.BY_ID
        }
    }
}