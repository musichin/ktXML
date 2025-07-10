package com.github.musichin.ktxml

sealed interface Node : BaseNode {
    /**
     * Returns a new mutable instance of the content, regardless of the original object's mutability.
     */
    fun toMutable(): MutableNode
}
