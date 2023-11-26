package com.pazarama.pokedex.data.repository

import android.content.SharedPreferences
import com.pazarama.pokedex.domain.repository.PreferenceRepository
import javax.inject.Inject

class PreferenceRepositoryImpl @Inject constructor(private val sharedPreferences: SharedPreferences) : PreferenceRepository {
    override fun setSearchByName() {
        sharedPreferences.edit().putBoolean("searchByName", true).apply()
    }

    override fun setSearchById() {
        sharedPreferences.edit().putBoolean("searchByName", false).apply()
    }

    override fun isSearchByName(): Boolean {
        return sharedPreferences.getBoolean("searchByName", true)
    }
}