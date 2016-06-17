package com.github.musichin.ktxml

open class TextElement(
        val text: String
) : BaseElement() {

    override fun newBuilder() = Builder(text)

    override fun hashCode(): Int {
        return text.hashCode();
    }

    override fun equals(other: Any?): Boolean {
        if (other is TextElement) {
            return text == other.text
        }
        return super.equals(other)
    }

    override fun toString(): String {
        return "${javaClass.simpleName}(text=$text)"
    }

    open class Builder(protected var text: String? = null) : BaseElement.Builder {
        fun text(text: String): Builder {
            this.text = text
            return this
        }

        override fun build(): TextElement {
            return TextElement(text!!)
        }
    }
}
