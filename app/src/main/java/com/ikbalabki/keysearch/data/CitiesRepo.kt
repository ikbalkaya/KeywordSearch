package com.ikbalabki.keysearch.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.ikbalabki.keywordsearch.KeySearch
import com.ikbalabki.keywordsearch.model.SearchResult
import java.io.InputStream
import java.util.*


class CitiesRepo(private val citySearch: KeySearch<City>,
                 private val inputStream: InputStream) {
    private val _result = MutableLiveData<SearchResult<City>>()
    val result: LiveData<SearchResult<City>> = _result

    fun searchFor(keyword: String) {
        val result = citySearch.find(keyword.lowercase(Locale.getDefault()))
        _result.value = result
    }

    fun loadData() {
        val jsonReader = JsonReader(inputStream.reader())
        jsonReader.beginArray()
        val gson = Gson()
        while (jsonReader.hasNext()) {
            val city = gson.fromJson<City>(jsonReader, City::class.java)
            citySearch.add(city)
        }
        jsonReader.close()
    }

}