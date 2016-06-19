package com.github.musichin.ktxml

interface Attribute {
    val namespace: String?
    val name: String
    val value: String

    fun mutable(): MutableAttribute

    operator fun component1(): String?
    operator fun component2(): String
    operator fun component3(): String

    companion object {
        fun of(name: String, value: String): Attribute = AttributeContent(null, name, value)

        fun of(namespace: String? = null, name: String, value: String): Attribute = AttributeContent(namespace, name, value)
    }
}

fun attributeOf(name: String, value: String) = Attribute.of(name, value)
fun attributeOf(namespace: String? = null, name: String, value: String) = Attribute.of(namespace, name, value)

interface MutableAttribute : Attribute {
    override var namespace: String?
    override var name: String
    override var value: String

    fun immutable(): Attribute

    companion object {
        fun of(name: String, value: String): MutableAttribute = MutableAttributeContent(null, name, value)

        fun of(namespace: String? = null, name: String, value: String): MutableAttribute
                = MutableAttributeContent(namespace, name, value)
    }
}

fun mutableAttributeOf(name: String, value: String) = MutableAttribute.of(name, value)
fun mutableAttributeOf(namespace: String? = null, name: String, value: String) = MutableAttribute.of(namespace, name, value)


open class AttributeContent(
        override val namespace: String?,
        override val name: String,
        override val value: String
) : Attribute {
    override fun mutable(): MutableAttribute = MutableAttributeContent(namespace, name, value)

    override operator fun component1() = namespace
    override operator fun component2() = name
    override operator fun component3() = value

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
        return "${javaClass.simpleName}(namespace=$namespace, name=$name, value=$value)"
    }
}

open class MutableAttributeContent(
        override var namespace: String? = null,
        override var name: String,
        override var value: String
) : AttributeContent(namespace, name, value), MutableAttribute {
    override fun immutable(): Attribute = AttributeContent(namespace, name, value)

    override fun mutable() = this
}
