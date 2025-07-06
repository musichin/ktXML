package com.github.musichin.ktxml

open class MutableCData internal constructor(
    text: String,
) : MutableText(text),
    BaseCData,
    MutableNode {
    override fun toImmutable(): CData = CData.of(text)

    override fun hashCode(): Int = text.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is MutableCData) return false
        return text == other.text
    }

    override fun toString(): String = "MutableCData(text=$text)"

    companion object {
        fun of(text: String): MutableCData = MutableCData(text)
    }
}

fun mutableCDataOf(text: String) = MutableCData.of(text)

fun String.toMutableCData() = MutableCData.of(this)
