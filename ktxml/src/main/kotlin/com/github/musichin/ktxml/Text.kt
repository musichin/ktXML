package com.github.musichin.ktxml

interface Text : Content {
    val text: String

    override fun mutable(): MutableText

    companion object {
        fun of(text: String): Text = TextContent(text)
    }
}

fun textOf(text: String) = Text.of(text)
fun String.toText() = textOf(this)

interface MutableText : Text, MutableContent {
    override var text: String

    override fun immutable(): Text

    fun append(text: String)

    companion object {
        fun of(text: String): MutableText = MutableTextContent(text)
    }
}

fun mutableTextOf(text: String) = MutableText.of(text)
fun String.toMutableText() = mutableTextOf(this)

open class TextContent(
    override val text: String
) : Text {
    override fun mutable(): MutableText = MutableTextContent(text)

    override fun hashCode(): Int {
        return text.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other is Text) {
            return text == other.text
        }
        return super.equals(other)
    }

    override fun toString(): String {
        return "${javaClass.simpleName}(text=$text)"
    }
}

open class MutableTextContent(
    override var text: String
) : TextContent(text), MutableText {
    override fun append(text: String) {
        this.text += text
    }

    override fun immutable(): Text = TextContent(text)
    override fun mutable() = this
}
