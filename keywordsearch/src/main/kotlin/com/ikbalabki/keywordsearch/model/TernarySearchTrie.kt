package com.ikbalabki.keywordsearch.model

/***
This class implements ternary search tree
(https://en.wikipedia.org/wiki/Ternary_search_tree)
Which is similar to a binary tree with exception of nodes having 3 child nodes instead of one
This data structure is memory efficient and can be used for large data sets.
 * */
internal class TernarySearchTrie<T : Searchable>(private val caseSensitive: Boolean) {
    private var root: Node<T>? = null
    private var size = 0L

    /**
     * Add an object to be searchable for later.
     *
     * @param searchable object to be searchable
     * **/
    fun add(searchable: T) {
        //recursively add node using ternary object and character index
        root = add(root, searchable, 0)
        size++
    }

    /**
     * Number of searchable objects in the tree.
     * */
    fun size() = size

    private fun add(givenNode: Node<T>?, searchable: T, charIndex: Int): Node<T>? {
        val char = if (caseSensitive) searchable.text[charIndex] else searchable.text[charIndex].lowercaseChar()
        var node = givenNode
        //if deos not already exist create it
        if (givenNode == null) {
            //only  add the object to the tree if it's the terminal character reached
            var searchableObject: T? = null
            if (searchable.text.length == (charIndex + 1)) {
                searchableObject = searchable
            }
            node = Node(
                    searchableObject,
                    char = char,
                    left = null,
                    middle = null,
                    right = null
            )
            if (root == null) root = node
        }

        //recursively add left/ right nodes, right and left nodes in ternary tree
        //if given char exist in mid node continue with middle link and finish middle link when
        // terminal character is reached
        node?.let { tNode ->
            when {
                char < tNode.char -> {
                    tNode.left = add(tNode.left, searchable, charIndex)
                }
                char > tNode.char -> {
                    tNode.right = add(tNode.right, searchable, charIndex)
                }
                charIndex < searchable.text.length - 1 -> {
                    //follow with next middle node
                    tNode.middle = add(node.middle, searchable, charIndex + 1)
                }
                else -> tNode.searchable = searchable
            }
        }

        return node
    }

    /**
     * Find objects that matches the start of the keyword.
     *
     * @param prefix prefix to be searched
     *
     * @return list of objects that matches the prefix
     * */
    fun itemsWithPrefix(prefix: String): List<T> {
        require(prefix.isNotEmpty())
        val items = mutableListOf<T>()
        findItemsWithPrefix(root, prefix, items)
        return items
    }

    private fun findItemsWithPrefix(node: Node<T>?, prefix: String, foundObjects: MutableList<T>) {
        if (node == null) return
        when {
            prefix.first() < node.char -> {
                findItemsWithPrefix(node.left, prefix, foundObjects)
            }
            prefix.first() > node.char -> {
                findItemsWithPrefix(node.right, prefix, foundObjects)
            }
            prefix.first() == node.char -> {
                if (prefix.length > 1) {
                    //last character not reached yet
                    //if keyword is 'lon' - and 'l' is found, remove first char from keyword and
                    // continue search for 'on'
                    val newKeyword = prefix.substring(1, prefix.length)
                    findItemsWithPrefix(node.middle, newKeyword, foundObjects)
                } else {
                    //we have reached to the end of keyword, now we should add
                    // remaining objects sorted using pre-order traversal to get items
                    // sorted
                    //add the keyword itself if it's also a ternary object
                    node.searchable?.let {
                        foundObjects.add(it)
                    }
                    //add all objects traversing from the middle node
                    traverseAdd(node.middle, foundObjects)
                }
            }
        }
    }

    private fun traverseAdd(node: Node<T>?, objectList: MutableList<T>) {
        if (node == null) {
            return
        }
        traverseAdd(node.left, objectList)

        node.searchable?.let {
            objectList.add(it)
        }
        traverseAdd(node.middle, objectList)
        traverseAdd(node.right, objectList)
    }
}

data class Node<T : Searchable>(
    var searchable: T?,
    var char: Char,
    var left: Node<T>?,
    var middle: Node<T>?,
    var right: Node<T>?
)