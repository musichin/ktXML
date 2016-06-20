package com.github.musichin.ktxml

interface Attribute {
    val namespace: String?
    val name: String
    val value: String

    val shortValue: Short
    val intValue: Int
    val longValue: Long
    val floatValue: Float
    val doubleValue: Double

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
fun Pair<String, String>.toAttribute() = attributeOf(first, second)

interface MutableAttribute : Attribute {
    override var namespace: String?
    override var name: String
    override var value: String

    override var shortValue: Short
    override var intValue: Int
    override var longValue: Long
    override var floatValue: Float
    override var doubleValue: Double

    fun immutable(): Attribute

    companion object {
        fun of(name: String, value: String): MutableAttribute = MutableAttributeContent(null, name, value)

        fun of(namespace: String? = null, name: String, value: String): MutableAttribute
                = MutableAttributeContent(namespace, name, value)
    }
}

fun mutableAttributeOf(name: String, value: String) = MutableAttribute.of(name, value)
fun mutableAttributeOf(namespace: String? = null, name: String, value: String) = MutableAttribute.of(namespace, name, value)
fun Pair<String, String>.toMutableAttribute() = mutableAttributeOf(first, second)


open class AttributeContent(
        override val namespace: String? = null,
        override val name: String,
        override val value: String
) : Attribute {
    constructor(
            namespace: String? = null,
            name: String,
            value: Short
    ) : this(namespace, name, value.toString())

    constructor(
            namespace: String? = null,
            name: String,
            value: Int
    ) : this(namespace, name, value.toString())

    constructor(
            namespace: String? = null,
            name: String,
            value: Long
    ) : this(namespace, name, value.toString())

    constructor(
            namespace: String? = null,
            name: String,
            value: Float
    ) : this(namespace, name, value.toString())

    constructor(
            namespace: String? = null,
            name: String,
            value: Double
    ) : this(namespace, name, value.toString())

    override fun mutable(): MutableAttribute = MutableAttributeContent(namespace, name, value)

    override operator fun component1() = namespace
    override operator fun component2() = name
    override operator fun component3() = value

    override val shortValue: Short get() = value.toShort()
    override val intValue: Int get() = value.toInt()
    override val longValue: Long get() = value.toLong()
    override val floatValue: Float get() = value.toFloat()
    override val doubleValue: Double get() = value.toDouble()

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
    constructor(
            namespace: String? = null,
            name: String,
            value: Short
    ) : this(namespace, name, value.toString())

    constructor(
            namespace: String? = null,
            name: String,
            value: Int
    ) : this(namespace, name, value.toString())

    constructor(
            namespace: String? = null,
            name: String,
            value: Long
    ) : this(namespace, name, value.toString())

    constructor(
            namespace: String? = null,
            name: String,
            value: Float
    ) : this(namespace, name, value.toString())

    constructor(
            namespace: String? = null,
            name: String,
            value: Double
    ) : this(namespace, name, value.toString())

    override fun immutable(): Attribute = AttributeContent(namespace, name, value)

    override var shortValue: Short
        get() = super.shortValue
        set(value) {
            this.value = value.toString()
        }

    override var intValue: Int
        get() = super.intValue
        set(value) {
            this.value = value.toString()
        }

    override var longValue: Long
        get() = super.longValue
        set(value) {
            this.value = value.toString()
        }

    override var floatValue: Float
        get() = super.floatValue
        set(value) {
            this.value = value.toString()
        }

    override var doubleValue: Double
        get() = super.doubleValue
        set(value) {
            this.value = value.toString()
        }

    override fun mutable() = this
}
