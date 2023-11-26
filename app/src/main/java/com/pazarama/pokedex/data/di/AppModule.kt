package com.pazarama.pokedex.data.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.pazarama.pokedex.data.repository.PokemonRepositoryImpl
import com.pazarama.pokedex.data.repository.PreferenceRepositoryImpl
import com.pazarama.pokedex.data.service.PokemonAPI
import com.pazarama.pokedex.domain.repository.PokemonRepository
import com.pazarama.pokedex.domain.repository.PreferenceRepository
import com.pazarama.pokedex.domain.usecase.GetPokemonDetailUseCase
import com.pazarama.pokedex.domain.usecase.GetPokemonItemsUseCase
import com.pazarama.pokedex.domain.usecase.SearchUseCase
import com.pazarama.pokedex.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun injectPokemonAPI(): PokemonAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokemonAPI::class.java)
    }

    @Provides
    @Singleton
    fun injectPokemonRepository(
        getPokemonItemsUseCase: GetPokemonItemsUseCase,
        getPokemonDetailUseCase: GetPokemonDetailUseCase
    ) = PokemonRepositoryImpl(getPokemonItemsUseCase, getPokemonDetailUseCase) as PokemonRepository

    //Search filter
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    @Singleton
    fun providePreferenceRepository(sharedPreferences: SharedPreferences): PreferenceRepository {
        return PreferenceRepositoryImpl(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideSearchUseCase(preferenceRepository: PreferenceRepository): SearchUseCase {
        return SearchUseCase(preferenceRepository)
    }
}
