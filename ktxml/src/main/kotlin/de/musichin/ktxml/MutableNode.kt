package de.musichin.ktxml

sealed interface MutableNode : BaseNode {
    /**
     * Returns a new immutable instance of the content.
     */
    fun toImmutable(): Node
}
