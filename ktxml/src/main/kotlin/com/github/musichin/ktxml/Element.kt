package com.github.musichin.ktxml

import java.util.Objects

open class Element internal constructor(
    override val namespace: String = "",
    override val name: String,
    override val nodes: List<Node> = emptyList(),
) : BaseElement,
    Node {
    override fun toMutable(): MutableElement =
        MutableElement.of(
            namespace,
            name,
            nodes.asSequence().map { it.toMutable() }.toMutableList(),
        )

    override operator fun component3() = nodes

    override fun iterator(): Iterator<Node> = nodes.iterator()

    override fun get(index: Int): Node = nodes[index]

    override fun textNodes(): List<Text> =
        nodes
            .asSequence()
            .filterIsInstance<Text>()
            .toList()

    override fun textNode(): Text? =
        nodes
            .asSequence()
            .filterIsInstance<Text>()
            .firstOrNull()

    override fun cdataNodes(): List<CData> =
        nodes
            .asSequence()
            .filterIsInstance<CData>()
            .toList()

    override fun cdataNode(): CData? =
        nodes
            .asSequence()
            .filterIsInstance<CData>()
            .firstOrNull()

    override fun commentNodes(): List<Comment> =
        nodes
            .asSequence()
            .filterIsInstance<Comment>()
            .toList()

    override fun commentNode(): Comment? =
        nodes
            .asSequence()
            .filterIsInstance<Comment>()
            .firstOrNull()

    override fun elementNodes(
        namespace: String?,
        name: String?,
    ): List<Element> =
        nodes
            .asSequence()
            .filterIsInstance<Element>()
            .run { if (namespace != null) filter { namespace == it.namespace } else this }
            .run { if (name != null) filter { name == it.name } else this }
            .toList()

    override fun elementNodes(name: String?): List<Element> = elementNodes(null, name)

    override fun elementNodes(): List<Element> = elementNodes(null, null)

    override fun elementNode(
        namespace: String?,
        name: String?,
    ): Element? =
        nodes
            .asSequence()
            .filterIsInstance<Element>()
            .run { if (namespace != null) filter { namespace == it.namespace } else this }
            .run { if (name != null) filter { name == it.name } else this }
            .firstOrNull()

    override fun elementNode(name: String?): Element? = elementNode(null, name)

    override fun elementNode(): Element? = elementNode(null, null)

    override fun attributeNode(
        namespace: String?,
        name: String?,
    ): Attribute? =
        nodes
            .asSequence()
            .filterIsInstance<Attribute>()
            .run { if (namespace != null) filter { namespace == it.namespace } else this }
            .run { if (name != null) filter { name == it.name } else this }
            .firstOrNull()

    override fun attributeNode(name: String?): Attribute? = attributeNode(null, name)

    override fun attributeNode(): Attribute? = attributeNode(null, null)

    override fun attributeNodes(
        namespace: String?,
        name: String?,
    ): List<Attribute> =
        nodes
            .asSequence()
            .filterIsInstance<Attribute>()
            .run { if (namespace != null) filter { namespace == it.namespace } else this }
            .run { if (name != null) filter { name == it.name } else this }
            .toList()

    override fun attributeNodes(name: String?): List<Attribute> = attributeNodes(null, name)

    override fun attributeNodes(): List<Attribute> = attributeNodes(null, null)

    override fun hashCode(): Int = Objects.hash(namespace, name, nodes)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is Element) return false
        return namespace == other.namespace && name == other.name && nodes == other.nodes
    }

    override fun toString(): String = "Element(namespace=$namespace, name=$name, nodes=$nodes)"

    companion object {
        fun of(name: String): Element = Element(name = name)

        fun of(
            name: String,
            init: ElementMarkup.() -> Unit,
        ): Element = ElementMarkup.build(name = name, init = init)

        fun of(
            namespace: String,
            name: String,
        ): Element = Element(namespace, name)

        fun of(
            namespace: String,
            name: String,
            init: ElementMarkup.() -> Unit,
        ): Element = ElementMarkup.build(namespace, name, init = init)

        fun of(
            name: String,
            nodes: List<Node>,
        ): Element = Element(name = name, nodes = nodes)

        fun of(
            name: String,
            nodes: List<Node>,
            init: ElementMarkup.() -> Unit,
        ): Element = ElementMarkup.build(name = name, nodes = nodes, init = init)

        fun of(
            namespace: String,
            name: String,
            nodes: List<Node>,
        ): Element = Element(namespace, name, nodes)

        fun of(
            namespace: String,
            name: String,
            nodes: List<Node>,
            init: ElementMarkup.() -> Unit,
        ): Element = ElementMarkup.build(namespace, name, nodes, init)
    }
}

fun elementOf(name: String) = Element.of(name)

fun elementOf(
    namespace: String,
    name: String,
) = Element.of(namespace, name)

fun elementOf(
    name: String,
    nodes: List<Node>,
) = Element.of(name, nodes)

fun elementOf(
    name: String,
    nodes: List<Node>,
    init: ElementMarkup.() -> Unit,
) = Element.of(name, nodes, init)

fun elementOf(
    namespace: String,
    name: String,
    nodes: List<Node>,
) = Element.of(namespace, name, nodes)

fun elementOf(
    name: String,
    init: ElementMarkup.() -> Unit,
) = Element.of(name, init)

fun elementOf(
    namespace: String,
    name: String,
    init: ElementMarkup.() -> Unit,
) = Element.of(namespace, name, init)

fun elementOf(
    namespace: String,
    name: String,
    nodes: List<Node>,
    init: ElementMarkup.() -> Unit,
) = Element.of(namespace, name, nodes, init)
