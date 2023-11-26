package com.pazarama.pokedex.util

object StatFormatter {
    fun formatStat(stat: Int): String {
        return String.format("%03d", stat)
    }
}