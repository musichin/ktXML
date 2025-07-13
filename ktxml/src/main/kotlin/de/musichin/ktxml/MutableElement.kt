package de.musichin.ktxml

import java.util.Objects

open class MutableElement internal constructor(
    override var namespace: String = "",
    override var name: String,
    override var nodes: MutableList<MutableNode> = mutableListOf(),
) : BaseElement,
    MutableNode {
    override fun toImmutable(): Element =
        Element.of(
            namespace,
            name,
            nodes.map { it.toImmutable() }.toList(),
        )

    override operator fun component3() = nodes

    override fun iterator(): Iterator<MutableNode> = nodes.iterator()

    override fun get(index: Int): MutableNode = nodes[index]

    override fun textNodes(): List<MutableText> =
        nodes
            .asSequence()
            .filterIsInstance<MutableText>()
            .toList()

    override fun textNode(): MutableText? =
        nodes
            .asSequence()
            .filterIsInstance<MutableText>()
            .firstOrNull()

    override fun cdataNodes(): List<MutableCData> =
        nodes
            .asSequence()
            .filterIsInstance<MutableCData>()
            .toList()

    override fun cdataNode(): MutableCData? =
        nodes
            .asSequence()
            .filterIsInstance<MutableCData>()
            .firstOrNull()

    override fun commentNodes(): List<MutableComment> =
        nodes
            .asSequence()
            .filterIsInstance<MutableComment>()
            .toList()

    override fun commentNode(): MutableComment? =
        nodes
            .asSequence()
            .filterIsInstance<MutableComment>()
            .firstOrNull()

    override fun elementNodes(
        namespace: String?,
        name: String?,
    ): List<MutableElement> =
        nodes
            .asSequence()
            .filterIsInstance<MutableElement>()
            .run { if (namespace != null) filter { namespace == it.namespace } else this }
            .run { if (name != null) filter { name == it.name } else this }
            .toList()

    override fun elementNodes(name: String?): List<MutableElement> = elementNodes(null, name)

    override fun elementNodes(): List<MutableElement> = elementNodes(null, null)

    override fun elementNode(
        namespace: String?,
        name: String?,
    ): MutableElement? =
        nodes
            .asSequence()
            .filterIsInstance<MutableElement>()
            .run { if (namespace != null) filter { namespace == it.namespace } else this }
            .run { if (name != null) filter { name == it.name } else this }
            .firstOrNull()

    override fun elementNode(name: String?): MutableElement? = elementNode(null, name)

    override fun elementNode(): MutableElement? = elementNode(null, null)

    override fun attributeNode(
        namespace: String?,
        name: String?,
    ): MutableAttribute? =
        nodes
            .asSequence()
            .filterIsInstance<MutableAttribute>()
            .run { if (namespace != null) filter { namespace == it.namespace } else this }
            .run { if (name != null) filter { name == it.name } else this }
            .firstOrNull()

    override fun attributeNode(name: String?): MutableAttribute? = attributeNode(null, name)

    override fun attributeNode(): MutableAttribute? = attributeNode(null, null)

    override fun attributeNodes(
        namespace: String?,
        name: String?,
    ): List<MutableAttribute> =
        nodes
            .asSequence()
            .filterIsInstance<MutableAttribute>()
            .run { if (namespace != null) filter { namespace == it.namespace } else this }
            .run { if (name != null) filter { name == it.name } else this }
            .toList()

    override fun attributeNodes(name: String?): List<MutableAttribute> = attributeNodes(null, name)

    override fun attributeNodes(): List<MutableAttribute> = attributeNodes(null, null)

    fun clearNodes() = nodes.clear()

    fun addNode(node: MutableNode): MutableElement {
        nodes.add(node)
        return this
    }

    fun addAttribute(
        namespace: String,
        name: String,
        value: String,
    ) = addNode(MutableAttribute.of(namespace, name, value))

    fun addAttribute(
        name: String,
        value: String,
    ) = addNode(MutableAttribute.of(name, value))

    fun addElement(
        namespace: String,
        name: String,
        nodes: MutableList<MutableNode>,
    ) = addNode(of(namespace, name, nodes))

    fun addElement(
        name: String,
        nodes: MutableList<MutableNode>,
    ) = addNode(of(name, nodes))

    fun addElement(
        namespace: String,
        name: String,
    ) = addNode(of(namespace, name))

    fun addElement(name: String) = addNode(of(name))

    fun addElement(
        namespace: String,
        name: String,
        nodes: MutableList<MutableNode>,
        init: MutableElementMarkup.() -> Unit,
    ) = addNode(MutableElementMarkup.Companion.build(namespace, name, nodes, init))

    fun addElement(
        namespace: String,
        name: String,
        init: MutableElementMarkup.() -> Unit,
    ) = addNode(MutableElementMarkup.Companion.build(namespace, name, init = init))

    fun addElement(
        name: String,
        init: MutableElementMarkup.() -> Unit,
    ) = addNode(MutableElementMarkup.Companion.build(name = name, init = init))

    fun addText(text: String) = addNode(MutableText.of(text))

    fun addCData(text: String) = addNode(MutableCData.of(text))

    fun addComment(comment: String) = addNode(MutableComment.of(comment))

    override fun hashCode(): Int = Objects.hash(namespace, name, nodes)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is MutableElement) return false
        return namespace == other.namespace && name == other.name && nodes == other.nodes
    }

    override fun toString(): String = "MutableElement(namespace=$namespace, name=$name, nodes=$nodes)"

    companion object {
        fun of(name: String): MutableElement = MutableElement(name = name)

        fun of(
            name: String,
            init: MutableElementMarkup.() -> Unit,
        ): MutableElement = MutableElementMarkup.build(name = name, init = init)

        fun of(
            namespace: String,
            name: String,
        ): MutableElement = MutableElement(namespace, name)

        fun of(
            namespace: String,
            name: String,
            init: MutableElementMarkup.() -> Unit,
        ): MutableElement = MutableElementMarkup.build(namespace, name, init = init)

        fun of(
            name: String,
            nodes: MutableList<MutableNode>,
        ): MutableElement = MutableElement(name = name, nodes = nodes)

        fun of(
            name: String,
            nodes: MutableList<MutableNode>,
            init: MutableElementMarkup.() -> Unit,
        ): MutableElement = MutableElementMarkup.build(name = name, nodes = nodes, init = init)

        fun of(
            namespace: String,
            name: String,
            nodes: MutableList<MutableNode>,
        ): MutableElement = MutableElement(namespace, name, nodes)

        fun of(
            namespace: String,
            name: String,
            nodes: MutableList<MutableNode>,
            init: MutableElementMarkup.() -> Unit,
        ): MutableElement = MutableElementMarkup.build(namespace, name, nodes, init)
    }
}

fun mutableElementOf(name: String) = MutableElement.of(name)

fun mutableElementOf(
    namespace: String,
    name: String,
) = MutableElement.of(namespace, name)

fun mutableElementOf(
    name: String,
    nodes: MutableList<MutableNode>,
) = MutableElement.of(name, nodes)

fun mutableElementOf(
    name: String,
    nodes: MutableList<MutableNode>,
    init: MutableElementMarkup.() -> Unit,
) = MutableElement.of(name, nodes, init)

fun mutableElementOf(
    namespace: String,
    name: String,
    nodes: MutableList<MutableNode>,
) = MutableElement.of(namespace, name, nodes)

fun mutableElementOf(
    name: String,
    init: MutableElementMarkup.() -> Unit,
) = MutableElement.of(name, init)

fun mutableElementOf(
    namespace: String,
    name: String,
    init: MutableElementMarkup.() -> Unit,
) = MutableElement.of(namespace, name, init)

fun mutableElementOf(
    namespace: String,
    name: String,
    nodes: MutableList<MutableNode>,
    init: MutableElementMarkup.() -> Unit,
) = MutableElement.of(namespace, name, nodes, init)
