package de.musichin.ktxml

class ElementMarkup private constructor(
    override val namespace: String,
    override val name: String,
    val nodes: MutableList<Node>,
    init: ElementMarkup.() -> Unit,
) : BaseElementMarkup {
    init {
        init()
    }

    fun node(node: Node) {
        nodes.add(node)
    }

    fun attribute(
        namespace: String,
        name: String,
        value: String,
    ) = node(Attribute.of(namespace, name, value))

    fun attribute(
        name: String,
        value: String,
    ) = node(Attribute.of(name, value))

    fun element(
        namespace: String,
        name: String,
        nodes: List<Node>,
    ) = node(Element.of(namespace, name, nodes))

    fun element(
        namespace: String,
        name: String,
    ) = node(Element.of(namespace, name))

    fun element(name: String) = node(Element.of(name))

    fun element(
        name: String,
        nodes: List<Node>,
        init: ElementMarkup.() -> Unit,
    ) = node(build(name = name, nodes = nodes, init = init))

    fun element(
        namespace: String,
        name: String,
        nodes: List<Node>,
        init: ElementMarkup.() -> Unit,
    ) = node(build(namespace, name, nodes, init))

    fun element(
        namespace: String,
        name: String,
        init: ElementMarkup.() -> Unit,
    ) = node(build(namespace, name, init = init))

    fun element(
        name: String,
        init: ElementMarkup.() -> Unit,
    ) = node(build(name = name, init = init))

    fun text(text: String) = node(Text.of(text))

    fun comment(comment: String) = node(Comment.of(comment))

    fun cdata(text: String) = node(CData.of(text))

    operator fun String.unaryPlus() = text(this)

    operator fun String.unaryMinus() = comment(this)

    operator fun Pair<String, String>.unaryPlus() = attribute(first, second)

    operator fun Triple<String, String, String>.unaryPlus() = attribute(first, second, third)

    operator fun String.invoke(init: ElementMarkup.() -> Unit) = element(this, init)

    companion object {
        internal fun build(
            namespace: String = "",
            name: String,
            nodes: List<Node>? = null,
            init: ElementMarkup.() -> Unit,
        ): Element {
            val markup = ElementMarkup(namespace, name, nodes?.toMutableList() ?: mutableListOf(), init)
            return Element.of(markup.namespace, markup.name, markup.nodes.toList())
        }
    }
}
