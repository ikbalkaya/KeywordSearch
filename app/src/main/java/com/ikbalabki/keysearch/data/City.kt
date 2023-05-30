package com.ikbalabki.keysearch.data

import com.ikbalabki.keywordsearch.model.Searchable
import java.util.*

data class City(val country: String, var name: String, val coord:Coord) : Searchable {
    override val text: String
        get() = name.lowercase(Locale.getDefault())
}

data class Coord(val lon: Double, val lat: Double)

