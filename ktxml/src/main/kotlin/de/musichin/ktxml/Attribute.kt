package de.musichin.ktxml

import java.util.Objects

open class Attribute internal constructor(
    override val namespace: String = "",
    override val name: String,
    override val value: String,
) : BaseAttribute,
    Node {
    override fun toMutable(): MutableAttribute = MutableAttribute.of(namespace, name, value)

    override fun hashCode(): Int = Objects.hash(namespace, name, value)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is Attribute) return false
        return name == other.name && value == other.value
    }

    override fun toString(): String = "Attribute(namespace=$namespace, name=$name, value=$value)"

    companion object {
        fun of(
            name: String,
            value: String,
        ): Attribute = Attribute(name = name, value = value)

        fun of(
            namespace: String,
            name: String,
            value: String,
        ): Attribute = Attribute(namespace, name, value)
    }
}

fun attributeOf(
    name: String,
    value: String,
) = Attribute.of(name, value)

fun attributeOf(
    namespace: String,
    name: String,
    value: String,
) = Attribute.of(namespace, name, value)

fun Pair<String, String>.toAttribute() = Attribute.of(first, second)

fun Triple<String, String, String>.toAttribute() = Attribute.of(first, second, third)
