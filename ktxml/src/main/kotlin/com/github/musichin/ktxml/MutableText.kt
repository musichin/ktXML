package com.github.musichin.ktxml

open class MutableText internal constructor(
    override var text: String,
) : BaseText,
    MutableNode {
    override fun toImmutable(): Text = Text.of(text)

    fun append(text: CharSequence): MutableText {
        this.text += text
        return this
    }

    override fun hashCode(): Int = text.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is MutableText) return false
        return text == other.text
    }

    override fun toString(): String = "MutableText(text=$text)"

    companion object {
        fun of(text: String): MutableText = MutableText(text)
    }
}

fun mutableTextOf(text: String) = MutableText.of(text)

fun String.toMutableText() = MutableText.of(this)
