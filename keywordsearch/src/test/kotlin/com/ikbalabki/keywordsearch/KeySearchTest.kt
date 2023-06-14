package com.ikbalabki.keywordsearch

import com.ikbalabki.keywordsearch.model.SearchResult
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

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
        val result = keySearch.find("london")
        assert(result is SearchResult.Hit)
        // there are 6 "London"s
        assert((result as SearchResult.Hit).items.size ==6 )
    }

}
