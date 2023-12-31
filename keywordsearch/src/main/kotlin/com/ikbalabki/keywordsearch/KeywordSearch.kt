package com.ikbalabki.keywordsearch

import com.ikbalabki.keywordsearch.model.SearchResult
import com.ikbalabki.keywordsearch.model.Searchable
import com.ikbalabki.keywordsearch.model.TernarySearchTrie

class KeywordSearch<T : Searchable>(caseSensitive: Boolean = false) {
    private val ternarySearchTrie = TernarySearchTrie<T>(caseSensitive)

    /**
     * Add an object to be searchable for later.
     *
     * @param searchable object to be searchable
     * **/
    fun add(searchable: T): KeywordSearch<T> {
        this.ternarySearchTrie.add(searchable)
        return KeywordSearch()
    }

    /**
     * Find obects that matches the start of the keyword.
     *
     * @param keyword keyword to be searched
     *
     * @return [SearchResult] object that contains the result of the search
     * If there is no match, [SearchResult.Miss] will be returned.
     * If there is a match, [SearchResult.Hit] will be returned, with the list of matched objects.
     * */
    fun itemsWithKeyword(keyword: String): SearchResult<T> {
        val result = ternarySearchTrie.itemsWithPrefix(keyword)
        return if (result.isEmpty()) {
            SearchResult.Miss()
        } else {
            SearchResult.Hit(result)
        }
    }

    /**
     * Check if the searchable object is already added.
     *
     * @param searchable object to be checked
     *
     * @return true if the object is already added, false otherwise
     * */
    fun hasItem(searchable: T) = ternarySearchTrie.contains(searchable)

    /**
     * Number of searchable objects.
     * */
    fun size() = ternarySearchTrie.size()
}