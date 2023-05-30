package com.ikbalabki.keysearch.ui

import com.ikbalabki.keysearch.data.City

sealed class CitiesViewState{
    object Loading : CitiesViewState()
    object ShowNoResult : CitiesViewState()
    data class ShowList(val cities:List<City>) : CitiesViewState()
}