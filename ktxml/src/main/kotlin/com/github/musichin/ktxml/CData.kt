package com.github.musichin.ktxml

open class CData internal constructor(
    text: String,
) : Text(text),
    BaseCData,
    Node {
    override fun toMutable(): MutableCData = MutableCData.of(text)

    override fun hashCode(): Int = text.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is CData) return false
        return text == other.text
    }

    override fun toString(): String = "CData(text=$text)"

    companion object {
        fun of(text: String): CData = CData(text)
    }
}

fun cdataOf(text: String) = CData.of(text)

fun String.toCData() = CData.of(this)
