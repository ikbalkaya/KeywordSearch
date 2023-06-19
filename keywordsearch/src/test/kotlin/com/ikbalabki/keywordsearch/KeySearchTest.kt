package com.ikbalabki.keywordsearch

import com.ikbalabki.keywordsearch.model.SearchResult
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class KeySearchTest {

    @Test
    fun testAddCities() {
        //load cities from json file from resources
        val citiesInputStream = KeySearchTest::class.java.getResourceAsStream("/cities.json")
        val keySearch = buildFrom(citiesInputStream, City::class.java)
        assert(keySearch.size() == 209557L)
    }

    @Test
    fun testAddPubs() {
        //load pubs from json file from resources
        val pubsInputStream = KeySearchTest::class.java.getResourceAsStream("/pubs.json")
        val keySearch = buildFrom(pubsInputStream, Pub::class.java)
        assert(keySearch.size() == 51548L)
    }

    @Test
    fun testAddGeonames() {
        //load geonames from json file from resources
        val geonamesInputStream =
            KeySearchTest::class.java.getResourceAsStream("/geonames-all-cities-with-a-population-1000.json")
        val keySearch = buildFrom(geonamesInputStream, Geoname::class.java)
        assert(keySearch.size() == 140938L)
    }

    @Test
    fun testKeywordHitShouldReturnCorrectResult() {
        //load cities from json file from resources
        val citiesInputStream = KeySearchTest::class.java.getResourceAsStream("/cities.json")
        val keySearch = buildFrom(citiesInputStream, City::class.java)
        val result = keySearch.itemsWithKeyword("london")
        assert(result is SearchResult.Hit)
        // there are 6 "London"s
        assert((result as SearchResult.Hit).items.size ==6 )
    }

    @Test
    fun testKeywordMisses() {
        val citiesInputStream = KeySearchTest::class.java.getResourceAsStream("/cities.json")
        val keySearch = buildFrom(citiesInputStream, City::class.java)
        val result = keySearch.itemsWithKeyword("lkn") // no city starts with "lkn"
        assert(result is SearchResult.Miss)
    }

    @Test
    fun testProgressiveSearchEndingWitHit() {
        val citiesInputStream = KeySearchTest::class.java.getResourceAsStream("/cities.json")
        val keySearch = buildFrom(citiesInputStream, City::class.java)

        val nameToSearch = "london"
        var count = Int.MAX_VALUE
        nameToSearch.forEachIndexed { index, _ ->
            val result = keySearch.itemsWithKeyword(nameToSearch.substring(0, index + 1))
            assert(result is SearchResult.Hit)
            val nowCount = (result as SearchResult.Hit).items.size
            // the number of hits should be less or equal to than the previous one
            assert(nowCount <= count)
            count = nowCount
        }

    }


    @Test
    fun testProgressiveSearchEndingWithMiss() {
        val citiesInputStream = KeySearchTest::class.java.getResourceAsStream("/cities.json")
        val keySearch = buildFrom(citiesInputStream, City::class.java)

        val nameToSearch = "londonk"
        var count = Int.MAX_VALUE
        nameToSearch.forEachIndexed { index, _ ->
            val result = keySearch.itemsWithKeyword(nameToSearch.substring(0, index + 1))
            if (index == nameToSearch.length - 1) {
                assert(result is SearchResult.Miss)
                return@forEachIndexed
            }
            assert(result is SearchResult.Hit)
            val nowCount = (result as SearchResult.Hit).items.size
            // the number of hits should be less or equal to than the previous one
            assert(nowCount <= count)
            count = nowCount
        }



    }

    @Test
    fun testKeywordCaseSensitiveMiss() {
        val citiesInputStream = KeySearchTest::class.java.getResourceAsStream("/cities.json")
        val keySearch = buildFrom(citiesInputStream, City::class.java, caseSensitive = true)
        val result = keySearch.itemsWithKeyword("londoN") // default is case insensitive
        assert(result is SearchResult.Miss)
    }

    @Test
    fun testKeywordCaseInsensitiveHit() {
        val citiesInputStream = KeySearchTest::class.java.getResourceAsStream("/cities.json")
        val keySearch = buildFrom(citiesInputStream, City::class.java, caseSensitive = false)
        val result = keySearch.itemsWithKeyword("londoN") // default is case insensitive
        assert(result is SearchResult.Hit)
    }

    @Test
    fun testKeysearchHasItemReturnsTrue() {
        val citiesInputStream = KeySearchTest::class.java.getResourceAsStream("/cities.json")
        val pair = getPair(citiesInputStream, City::class.java, caseSensitive = false)
        val keySearch = pair.first
        val list = pair.second

        val existingCity = list.random()
        assertTrue(keySearch.hasItem(existingCity))
    }

    @Test
    fun testKeysearchNoItemReturnsFalse() {
        val citiesInputStream = KeySearchTest::class.java.getResourceAsStream("/cities.json")
        val pair = getPair(citiesInputStream, City::class.java, caseSensitive = false)
        val keySearch = pair.first
        val list = pair.second

        val existingCity = list.random()
        val  invalidCity = existingCity.copy(country = "invalid")
        assertFalse(keySearch.hasItem(invalidCity))
    }

}
