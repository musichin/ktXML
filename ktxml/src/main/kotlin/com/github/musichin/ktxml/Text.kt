package com.github.musichin.ktxml

open class Text internal constructor(
    override val text: String,
) : BaseText,
    Node {
    override fun toMutable(): MutableText = MutableText.of(text)

    override fun hashCode(): Int = text.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is Text) return false
        return text == other.text
    }

    override fun toString(): String = "Text(text=$text)"

    companion object {
        fun of(text: String): Text = Text(text)
    }
}

fun textOf(text: String) = Text.of(text)

fun String.toText() = Text.of(this)
