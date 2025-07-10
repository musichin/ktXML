package com.github.musichin.ktxml

import java.util.Objects

class MutableAttribute internal constructor(
    override var namespace: String = "",
    override var name: String,
    override var value: String,
) : BaseAttribute,
    MutableNode {
    override fun toImmutable(): Attribute = Attribute.of(namespace, name, value)

    override fun hashCode(): Int = Objects.hash(namespace, name, value)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is MutableAttribute) return false
        return name == other.name && value == other.value
    }

    override fun toString(): String = "MutableAttribute(namespace=$namespace, name=$name, value=$value)"

    companion object {
        fun of(
            name: String,
            value: String,
        ): MutableAttribute = MutableAttribute(name = name, value = value)

        fun of(
            namespace: String,
            name: String,
            value: String,
        ): MutableAttribute = MutableAttribute(namespace, name, value)
    }
}

fun mutableAttributeOf(
    name: String,
    value: String,
) = MutableAttribute.of(name, value)

fun mutableAttributeOf(
    namespace: String,
    name: String,
    value: String,
) = MutableAttribute.of(namespace, name, value)

fun Pair<String, String>.toMutableAttribute() = MutableAttribute.of(first, second)

fun Triple<String, String, String>.toMutableAttribute() = MutableAttribute.of(first, second, third)
