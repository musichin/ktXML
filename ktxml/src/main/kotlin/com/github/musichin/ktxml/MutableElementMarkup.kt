package com.github.musichin.ktxml

class MutableElementMarkup private constructor(
    override val namespace: String,
    override val name: String,
    val nodes: MutableList<MutableNode>,
    init: MutableElementMarkup.() -> Unit,
) : BaseElementMarkup {
    init {
        init()
    }

    fun node(node: MutableNode) {
        nodes.add(node)
    }

    fun attribute(
        namespace: String,
        name: String,
        value: String,
    ) = node(MutableAttribute.of(namespace, name, value))

    fun attribute(
        name: String,
        value: String,
    ) = node(MutableAttribute.of(name, value))

    fun element(
        namespace: String,
        name: String,
        nodes: MutableList<MutableNode>,
    ) = node(MutableElement.of(namespace, name, nodes))

    fun element(
        namespace: String,
        name: String,
    ) = node(MutableElement.of(namespace, name))

    fun element(name: String) = node(MutableElement.of("", name))

    fun element(
        name: String,
        nodes: MutableList<MutableNode>,
        init: MutableElementMarkup.() -> Unit,
    ) = node(build(name = name, nodes = nodes, init = init))

    fun element(
        namespace: String,
        name: String,
        nodes: MutableList<MutableNode>,
        init: MutableElementMarkup.() -> Unit,
    ) = node(build(namespace, name, nodes, init))

    fun element(
        namespace: String,
        name: String,
        init: MutableElementMarkup.() -> Unit,
    ) = node(build(namespace, name, init = init))

    fun element(
        name: String,
        init: MutableElementMarkup.() -> Unit,
    ) = node(build(name = name, init = init))

    fun text(text: String) = node(MutableText.of(text))

    fun comment(comment: String) = node(MutableComment.of(comment))

    fun cdata(text: String) = node(MutableCData.of(text))

    operator fun String.unaryPlus() = text(this)

    operator fun String.unaryMinus() = comment(this)

    operator fun Pair<String, String>.unaryPlus() = attribute(first, second)

    operator fun Triple<String, String, String>.unaryPlus() = attribute(first, second, third)

    operator fun String.invoke(init: MutableElementMarkup.() -> Unit) = element(this, init)

    companion object {
        internal fun build(
            namespace: String = "",
            name: String,
            nodes: MutableList<MutableNode>? = null,
            init: MutableElementMarkup.() -> Unit,
        ): MutableElement {
            val markup = MutableElementMarkup(namespace, name, nodes?.toMutableList() ?: mutableListOf(), init)
            return MutableElement.of(markup.namespace, markup.name, markup.nodes)
        }
    }
}
