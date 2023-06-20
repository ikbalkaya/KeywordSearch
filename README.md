# KeywordSearch
Memory efficient fast keyword search library written in Kotlin based on ternary search trie data structure.

This library is ideal for memory constrained environments like Android. It is also fast and can be used for high performance applications.

An example app is provided in the `app` module. It is a simple app that searches for cities in the world based on a json file.
Google maps is used to show the location of the city. You should provide your own Google maps API key in the `local.properties` file like below
    
    ```MAPS_API_KEY=YOUR_API_KEY```

## Features
- [x] Prefix search
- [x] Custom searchable object support
- [x] Memory efficient
- [x] Fast
- [x] Case insensitive / case sensitive search

## Installation
This library currently isn't hosted in any repository. You can download the source code and build it yourself.

## Usage
```groovy
    implementation project(path: ':keywordsearch')
```
provided you have library module in your project. 

### Create a searchable object
Create a class that implements the `Searchable` interface. The `text` field should return the text that you want to search on.
For example

```kotlin    
    data class City(val name: String, val country: String, val latitude: Double, val longitude: Double) : Searchable {
        override fun getSearchableText(): String {
            return name
        }
    }   
```
### Create a keyword search object
You can create a keyword search object to perform your searches on. You can create a keyword search object by passing a list of searchable objects to the constructor.
```kotlin
    val keywordSearch = KeywordSearch(caseSensitive = true) // case insensitive search
```

Note that the default configuration for caseSensitive is false. If you want to perform case sensitive search, you should pass true to the constructor.

### Add a searchable object
You can add a searchable object to the keyword search object by calling the `add` method on the keyword search object.
```kotlin
    keywordSearch.add(city)
```
Your searchable object will be added to the keyword search object and will be available for search.

### Perform a search
You can perform a keyword search by by calling the `itemsWithKeyword` method on the keyword search object. The `itemsWithKeyword` method returns a list of searchable objects that match the search query.
```kotlin
    val results = keywordSearch.itemsWithKeyword("lond")
```
