package com.github.musichin.ktxml

open class CDataElement(text: String) : TextElement(text) {

    override fun hashCode(): Int {
        return text.hashCode();
    }

    override fun equals(other: Any?): Boolean {
        if (other is CDataElement) {
            return text == other.text
        }
        return super.equals(other)
    }

    override fun newBuilder() = Builder(text)

    open class Builder(text: String? = null) : TextElement.Builder(text) {
        override fun build(): TextElement {
            return CDataElement(text!!)
        }
    }
}