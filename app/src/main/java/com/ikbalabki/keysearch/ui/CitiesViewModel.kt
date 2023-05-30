package com.ikbalabki.keysearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.ikbalabki.keysearch.CitySearchApp
import com.ikbalabki.keysearch.data.City
import com.ikbalabki.keywordsearch.model.SearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(private val app: CitySearchApp) : ViewModel() {
    private val _selectedCity = MutableLiveData<City>()
    val selectedCity: LiveData<City> = _selectedCity
    private val _viewState = app.repo.result.switchMap {
        when (it) {
            is SearchResult.Hit -> {
                MutableLiveData(CitiesViewState.ShowList(it.items))
            }
            is SearchResult.Miss ->{
                MutableLiveData(CitiesViewState.ShowNoResult)
            }
        }
    }

    val viewState: LiveData<CitiesViewState> = _viewState

    fun getCities(keyword: String) {
        app.repo.searchFor(keyword)
    }

    fun setSelected(city: City) {
        _selectedCity.value = city
    }

}