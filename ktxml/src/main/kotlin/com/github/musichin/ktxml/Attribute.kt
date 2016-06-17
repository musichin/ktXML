package com.github.musichin.ktxml

final class Attribute(
        override val namespace: String? = null,
        override val name: String,
        val value: String
) : IsFullyQualified {
    constructor(
            name: String,
            value: String
    ) : this(null, name, value)

    operator fun component1() = namespace
    operator fun component2() = name
    operator fun component3() = value

    override fun hashCode(): Int {
        var result = name.hashCode();
        result = 31 * result + value.hashCode()
        return result;
    }

    override fun equals(other: Any?): Boolean {
        if (other is Attribute) {
            return name == other.name && value == other.value
        }
        return super.equals(other)
    }

    override fun toString(): String {
        return "${javaClass.simpleName}(name=$name, value=$value)"
    }

    fun newBuilder() = Builder(namespace, name, value)

    open class Builder(
            override var namespace: String? = null,
            override var name: String? = null,
            var value: String? = null
    ) : IsFullyQualified {
        fun namespace(namespace: String?): Builder {
            this.namespace = namespace
            return this
        }

        fun name(name: String): Builder {
            this.name = name
            return this
        }

        fun value(value: String): Builder {
            this.value = value
            build()
            return this
        }

        fun build() = build(null)

        fun build(fallbackNamespace: String? = null) = Attribute(namespace ?: fallbackNamespace, name!!, value!!)
    }
}