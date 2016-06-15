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

    fun component1() = namespace
    fun component2() = name
    fun component3() = value

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

    fun newBuilder() = Builder(namespace, name, value)
}