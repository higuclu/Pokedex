package com.pazarama.pokedex.util

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.setPokemonImage(imagePath:String?) {
    Glide.with(this).load(imagePath)
        .placeholder(com.google.android.material.R.drawable.mtrl_dropdown_arrow)
        .error(com.google.android.material.R.drawable.mtrl_ic_error)
        .into(this)
}