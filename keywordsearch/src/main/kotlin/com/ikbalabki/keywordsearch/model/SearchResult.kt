package com.ikbalabki.keywordsearch.model

sealed class SearchResult<T> {
    data class Hit<T>(val items:List<T>): SearchResult<T>()
    class Miss<T> : SearchResult<T>()
}
