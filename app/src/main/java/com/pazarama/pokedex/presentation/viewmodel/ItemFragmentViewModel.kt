package com.pazarama.pokedex.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pazarama.pokedex.domain.model.item.PokemonItem
import com.pazarama.pokedex.domain.model.item.PokemonItems
import com.pazarama.pokedex.domain.model.search.SearchType
import com.pazarama.pokedex.domain.repository.PokemonRepository
import com.pazarama.pokedex.domain.usecase.SearchUseCase
import com.pazarama.pokedex.util.Resource
import com.pazarama.pokedex.util.UrlIdExtractor.formatId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ItemFragmentViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val searchUseCase: SearchUseCase
) : ViewModel() {
    val pokemonItems = MutableLiveData<Resource<PokemonItems>>()
    val pokemonError = MutableLiveData<Resource<Boolean>>()
    val pokemonLoading = MutableLiveData<Resource<Boolean>>()

    private val _searchType = MutableLiveData<SearchType>()
    val pokemonSearchItems = MutableLiveData<Resource<PokemonItems>>()
    val pokemonSearchNames = MutableLiveData<List<String>?>()
    val searchType: LiveData<SearchType>
        get() = _searchType

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println(throwable.localizedMessage)
        pokemonError.value = Resource.error(throwable.localizedMessage ?: "Error!", true)
    }

    fun loadData() {
        pokemonLoading.value = Resource.loading(true)

        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val resource = pokemonRepository.getPokemonItems()
            withContext(Dispatchers.Main) {
                resource.data?.let {
                    pokemonLoading.value = Resource.loading(false)
                    pokemonError.value = Resource.error("", false)
                    pokemonItems.value = resource
                }
            }
        }
    }

    fun onSearchByNameSelected() {
        searchUseCase.setSearchByName()
        _searchType.value = SearchType.BY_NAME
    }

    fun onSearchByIdSelected() {
        searchUseCase.setSearchById()
        _searchType.value = SearchType.BY_ID
    }

    fun searchInList(searchText: String) {
        val originalList = pokemonItems.value?.data?.results ?: emptyList()

        val subList = originalList.filter { pokemonItem ->
            when (searchUseCase.getSearchType()) {
                SearchType.BY_NAME -> pokemonItem.name.startsWith(searchText, ignoreCase = true)
                SearchType.BY_ID -> formatId(pokemonItem.getId()).startsWith(searchText)
                else -> pokemonItem.name.startsWith(searchText, ignoreCase = true)
            }
        }

        val resource = if (subList.isNotEmpty()) {
            Resource.success(PokemonItems(0, "", "", results = subList))
        } else {
            Resource.error("Not Found", PokemonItems(0, "", "", subList))
        }

        pokemonSearchItems.value = resource
    }
    fun processSearchResults() {
        val nameList = pokemonSearchItems.value?.data?.results?.map { it.name }
        pokemonSearchNames.value = nameList
    }


}