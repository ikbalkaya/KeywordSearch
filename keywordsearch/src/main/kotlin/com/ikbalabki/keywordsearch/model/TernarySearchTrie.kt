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

    fun add(t: T) {
        //recursively add node using ternary object and character index
        root = add(root, t, 0)
        size++
    }

    fun size() = size

    private fun add(givenNode: Node<T>?, t: T, charIndex: Int): Node<T>? {
        val char = if (caseSensitive) t.text[charIndex] else t.text[charIndex].lowercaseChar()
        var node = givenNode
        //if deos not already exist create it
        if (givenNode == null) {
            //only  add the object to the  if it's the terminal character reached
            var nodeTernaryObject: T? = null
            if (t.text.length == (charIndex + 1)) {
                nodeTernaryObject = t
            }
            node = Node(
                    nodeTernaryObject,
                    char = char,
                    left = null,
                    middle = null,
                    right = null
            )
            if (root == null) root = node
        }

        //recursively add left/ right nodes, right and left nodes in ternary tree
        //if given char exist in mid node continue with middle link and finish middle link when terminal character is reached
        node?.let {
            when {
                char < it.char -> {
                    it.left = add(it.left, t, charIndex)
                }
                char > it.char -> {
                    it.right = add(it.right, t, charIndex)
                }
                charIndex < t.text.length - 1 -> {
                    //follow with next middle node
                    it.middle = add(node.middle, t, charIndex + 1)
                }
                else -> it.ternaryObject = t
            }
        }

        return node
    }

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
                    node.ternaryObject?.let {
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

        node.ternaryObject?.let {
            objectList.add(it)
        }
        traverseAdd(node.middle, objectList)
        traverseAdd(node.right, objectList)
    }
}

data class Node<T : Searchable>(
    var ternaryObject: T?,
    var char: Char,
    var left: Node<T>?,
    var middle: Node<T>?,
    var right: Node<T>?
)