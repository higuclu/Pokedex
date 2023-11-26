package com.pazarama.pokedex.presentation.viewmodel

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pazarama.pokedex.R
import com.pazarama.pokedex.domain.model.detail.PokemonDetail
import com.pazarama.pokedex.domain.model.detail.Type
import com.pazarama.pokedex.domain.model.item.PokemonItems
import com.pazarama.pokedex.domain.model.search.SearchType
import com.pazarama.pokedex.domain.repository.PokemonRepository
import com.pazarama.pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class DetailFragmentViewModel @Inject constructor(private val pokemonRepository: PokemonRepository) :
    ViewModel() {
    val pokemonDetail = MutableLiveData<Resource<PokemonDetail>>()
    val pokemonError = MutableLiveData<Resource<Boolean>>()
    val pokemonLoading = MutableLiveData<Resource<Boolean>>()

    val _searchNamesList = MutableLiveData<String>()
//    var searchType: LiveData<String>
//        get() = searchNamesList
//        set(value)

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println(throwable.localizedMessage)
        pokemonError.value = Resource.error(throwable.localizedMessage ?: "Error!",true)
    }
    fun loadData(name: String) {
        pokemonLoading.value = Resource.loading(true)

        viewModelScope.launch(Dispatchers.IO + exceptionHandler)  {
            val resource = pokemonRepository.getPokemonDetail(name)
            withContext(Dispatchers.Main) {
                resource.data?.let {
                    pokemonLoading.value = Resource.loading(false)
                    pokemonError.value = Resource.error("",false)
                    pokemonDetail.value = resource
                }
            }
        }
    }
    fun getColorForType(type: Type): Int {
        val colorMap = mapOf(
            "bug" to R.color.color_type_bug,
            "dark" to R.color.color_type_dark,
            "dragon" to R.color.color_type_dragon,
            "electric" to R.color.color_type_electric,
            "fairy" to R.color.color_type_fairy,
            "fighting" to R.color.color_type_fighting,
            "fire" to R.color.color_type_fire,
            "flying" to R.color.color_type_flying,
            "ghost" to R.color.color_type_ghost,
            "normal" to R.color.color_type_normal,
            "grass" to R.color.color_type_grass,
            "ground" to R.color.color_type_ground,
            "ice" to R.color.color_type_ice,
            "poison" to R.color.color_type_poison,
            "psychic" to R.color.color_type_psychic,
            "rock" to R.color.color_type_rock,
            "steel" to R.color.color_type_steel,
            "water" to R.color.color_type_water
        )

        val typeColorResId = colorMap[type.type.name.lowercase(Locale.getDefault())]
        return typeColorResId ?: R.color.black
    }
    fun processSearchResults(searchNames: String) {
        val resultNames = searchNames
            .removeSurrounding("[", "]")
            .split(", ")
        //_searchNamesList.value = resultNames
    }
}
