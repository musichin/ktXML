package com.github.musichin.ktxml

final class CDataElement(
        val data: String
) : BaseElement() {
    open class Builder(protected var data: String? = null) : BaseElement.Builder {
        fun data(data: String): Builder {
            this.data = data
            return this
        }

        override fun build(): CDataElement {
            return CDataElement(data!!)
        }
    }

    override fun newBuilder() = Builder(data)
}