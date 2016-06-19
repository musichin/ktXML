package com.github.musichin.ktxml

interface CData : Text {
    override fun mutable(): MutableCData

    companion object {
        fun of(text: String): CData = CDataContent(text)
    }
}

fun cdataOf(text: String) = CData.of(text)

interface MutableCData : CData, MutableText {
    override fun immutable(): CData

    companion object {
        fun of(text: String): MutableCData = MutableCDataContent(text)
    }
}

fun mutableCDataOf(text: String) = MutableCData.of(text)


open class CDataContent(text: String) : CData, TextContent(text) {
    override fun mutable(): MutableCData = MutableCDataContent(text)

    override fun hashCode(): Int {
        return text.hashCode();
    }

    override fun equals(other: Any?): Boolean {
        if (other is CData) {
            return text == other.text
        }
        return super.equals(other)
    }
}

open class MutableCDataContent(
        override var text: String
) : CDataContent(text), MutableCData {
    override fun append(text: String) {
        this.text += text
    }

    override fun immutable(): CData = CDataContent(text)

    override fun mutable() = this
}