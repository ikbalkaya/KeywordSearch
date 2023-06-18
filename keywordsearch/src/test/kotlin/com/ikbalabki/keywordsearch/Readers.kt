package com.ikbalabki.keywordsearch

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.ikbalabki.keywordsearch.model.Searchable
import java.io.InputStream
import java.util.*


//[{"id":1,"name":"Ark Bar Restaurant","address":"Ark Bar And Restaurant, Cattawade Street, Brantham, MANNINGTREE","fsaid":38,"easting":610194,"northing":233329,"latitude":51.958698,"longitude":1.057832,"local_authority":"Babergh","postcode":"CO11 1RH"},
data class Pub(val id: Int, val name: String,
               val address: String, val fsaid: Int,
               val easting: Int, val northing: Int,
               val latitude: Double, val longitude: Double,
               val local_authority: String, val postcode: String) : Searchable {
    override val text: String
        get() = name.lowercase(Locale.getDefault())
}

data class City(val country: String, var name: String, val coord:Coord) : Searchable {
    override val text: String
        get() = name.lowercase(Locale.getDefault())
}

data class Coord(val lon: Double, val lat: Double)


data class Geoname(val geoname_id: String, val name: String,
                   val ascii_name: String, val alternate_names: List<String>,
                   val feature_class: String, val feature_code: String,
                   val country_code: String, val cou_name_en: String,
                   val country_code_2: String, val admin1_code: String,
                   val admin2_code: String, val admin3_code: String,
                   val admin4_code: String, val population: Int,
                   val elevation: Int, val dem: Int,
                   val timezone: String, val modification_date: String,
                   val label_en: String, val coordinates: Coord) : Searchable {
    override val text: String
        get() = name.lowercase(Locale.getDefault())
}
inline fun <reified T:Searchable> buildFrom(inputStream: InputStream, t:Class<T> ,
                                            caseSensitive: Boolean = true) : KeySearch<T>{
    //read as stream for memory efficiency
    val keySearch = KeySearch<T>()
    val jsonReader = JsonReader(inputStream.reader())
    jsonReader.beginArray()
    //read json
    val gson = Gson()
    while (jsonReader.hasNext()) {
        val item = gson.fromJson<T>(jsonReader, t)
        keySearch.add(item)
    }
    jsonReader.close()

    return keySearch
}

//also return list of items
inline fun <reified T:Searchable> getPair(inputStream: InputStream, t:Class<T> ,
                                            caseSensitive: Boolean = true) :
        Pair<KeySearch<T>,List<T>>{
    //read as stream for memory efficiency
    val list = mutableListOf<T>()
    val keySearch = KeySearch<T>()
    val jsonReader = JsonReader(inputStream.reader())
    jsonReader.beginArray()
    //read json
    val gson = Gson()
    while (jsonReader.hasNext()) {
        val item = gson.fromJson<T>(jsonReader, t)
        keySearch.add(item)
        list.add(item)
    }
    jsonReader.close()

    return Pair(keySearch,list)
}
