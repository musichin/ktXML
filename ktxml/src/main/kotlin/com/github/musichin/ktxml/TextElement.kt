package com.github.musichin.ktxml

final class TextElement(
        val text: String
) : BaseElement() {
    open class Builder(protected var text: String? = null) : BaseElement.Builder {
        fun text(text: String): Builder {
            this.text = text
            return this
        }

        override fun build(): TextElement {
            return TextElement(text!!)
        }
    }

    override fun newBuilder() = Builder(text)
}