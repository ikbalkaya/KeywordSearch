package com.ikbalabki.keywordsearch

import com.ikbalabki.keywordsearch.model.SearchResult
import com.ikbalabki.keywordsearch.model.Searchable
import com.ikbalabki.keywordsearch.model.TernarySearchTrie

class KeySearch<T : Searchable>(private val caseSensitive: Boolean = false) {
    private val ternarySearchTrie = TernarySearchTrie<T>(caseSensitive)


    /**
     * Add an object to be searchable for later.
     *
     * @param searchable object to be searchable
     * **/
    fun add(searchable: T): KeySearch<T> {
        this.ternarySearchTrie.add(searchable)
        return KeySearch()
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

    fun size() = ternarySearchTrie.size()
}