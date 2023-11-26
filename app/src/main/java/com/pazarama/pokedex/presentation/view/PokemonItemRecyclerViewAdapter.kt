package com.pazarama.pokedex.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pazarama.pokedex.databinding.FragmentItemBinding
import com.pazarama.pokedex.domain.model.item.PokemonItem
import com.pazarama.pokedex.util.UrlIdExtractor.formatId
import com.pazarama.pokedex.util.setPokemonImage

class PokemonItemRecyclerViewAdapter(
    private val pokemonItems: ArrayList<PokemonItem>,
    private val listener: Listener
) : RecyclerView.Adapter<PokemonItemRecyclerViewAdapter.ViewHolder>() {

    interface Listener{
        fun onItemClick(pokemonItem : PokemonItem)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = pokemonItems[position]
        holder.itemView.setOnClickListener {
            listener.onItemClick(pokemonItems[position])
        }
        holder.contentView.text = item.name.capitalize()
        holder.idView.text = formatId(item.getId())
        holder.icon.setPokemonImage(item.geticonUrl())
    }

    override fun getItemCount(): Int = pokemonItems.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.pokemonIdText
        val contentView: TextView = binding.pokemonNameText
        val icon : ImageView = binding.imageViewPokemonIcon

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}
