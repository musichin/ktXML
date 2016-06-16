package com.github.musichin.ktxml

final class CDataElement(
        val data: String
) : BaseElement() {

    override fun hashCode(): Int {
        return data.hashCode();
    }

    override fun equals(other: Any?): Boolean {
        if (other is CDataElement) {
            return data == other.data
        }
        return super.equals(other)
    }

    override fun newBuilder() = Builder(data)

    open class Builder(protected var data: String? = null) : BaseElement.Builder {
        fun data(data: String): Builder {
            this.data = data
            return this
        }

        override fun build(): CDataElement {
            return CDataElement(data!!)
        }
    }
}