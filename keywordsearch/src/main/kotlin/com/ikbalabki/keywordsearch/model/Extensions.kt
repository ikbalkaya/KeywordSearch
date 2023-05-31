package com.ikbalabki.keywordsearch.model

import java.util.Locale

enum class SearchableMode {
    LOWERCASE,
    UPPERCASE
}

/**
 * Extension function to convert a [String] to [Searchable].
 *
 * @param searchableMode mode to convert the [String] to [Searchable]
 * Do not provide any value to use the original [String].
 *
 * @return [Searchable] object
 * */

fun String.toSearchable(searchableMode: SearchableMode? = null): Searchable {
    val searchableText = when (searchableMode) {
        SearchableMode.LOWERCASE -> this.lowercase(Locale.ROOT)
        SearchableMode.UPPERCASE -> this.uppercase(Locale.ROOT)
        else -> this
    }
    return object : Searchable {
        override val text = searchableText
    }
}
