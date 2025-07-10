package com.github.musichin.ktxml.experimental

import com.github.musichin.ktxml.BaseElement

object XElement

object XText

object XCData

object XComment

object XName

object XNamespace

class ElementWrapper(
    val value: BaseElement? = null,
) {
    operator fun div(x: XPath) = value?.element(x) ?: ElementWrapper()

    operator fun div(x: XPaths) = value?.elements(x) ?: ElementListWrapper()

    operator fun div(name: String) = div(XPath(name))

    operator fun div(x: XElement) = value?.let { it / x }

    operator fun div(x: XText) = value?.let { it / x }

    operator fun div(x: XCData) = value?.let { it / x }

    operator fun div(x: XComment) = value?.let { it / x }

    operator fun div(x: XName) = value?.let { it / x }

    operator fun div(x: XNamespace) = value?.let { it / x }

    operator fun div(x: XAttribute) = value?.let { it / x }
}

class ElementListWrapper<N : BaseElement>(
    val value: List<N> = listOf(),
) {
    operator fun div(x: XPaths) = ElementListWrapper(value.map { it.elements(x).value }.accumulate())

    operator fun div(x: XPath) = ElementListWrapper(value.mapNotNull { it.element(x).value })

    operator fun div(name: String) = div(XPath(name))

    operator fun div(
        @Suppress("UNUSED_PARAMETER") x: XElement,
    ) = value

    operator fun div(x: XText) = value.mapNotNull { it / x }

    operator fun div(x: XCData) = value.mapNotNull { it / x }

    operator fun div(x: XComment) = value.mapNotNull { it / x }

    operator fun div(x: XName) = value.map { it / x }

    operator fun div(x: XNamespace) = value.map { it / x }

    operator fun div(x: XAttribute) = value.mapNotNull { it / x }
}

class XAttribute(
    internal val namespace: String? = null,
    internal val name: String? = null,
) {
    constructor(name: String) : this(null, name)
}

class XPath(
    val namespace: String? = null,
    val name: String? = null,
    val index: Int? = null,
) {
    constructor() : this(null, null, null)
    constructor(index: Int) : this(null, null, index)
    constructor(name: String) : this(null, name, null)
    constructor(namespace: String?, name: String) : this(namespace, name, null)
}

class XPaths(
    val namespace: String? = null,
    val name: String? = null,
) {
    constructor() : this(null, null)

    constructor(name: String) : this(null, name)

    operator fun get(index: Int) = XPath(namespace, name, index)
}

operator fun BaseElement.div(x: XPath) = element(x)

operator fun BaseElement.div(x: XPaths) = elements(x)

operator fun BaseElement.div(name: String) = div(XPath(null, name))

operator fun BaseElement.div(x: XAttribute) = attribute(x)

operator fun BaseElement.div(
    @Suppress("UNUSED_PARAMETER") x: XElement,
) = this

operator fun BaseElement.div(
    @Suppress("UNUSED_PARAMETER") x: XText,
) = textNode()?.text

operator fun BaseElement.div(
    @Suppress("UNUSED_PARAMETER") x: XCData,
) = cdataNode()?.text

operator fun BaseElement.div(
    @Suppress("UNUSED_PARAMETER") x: XComment,
) = commentNode()?.comment

operator fun BaseElement.div(
    @Suppress("UNUSED_PARAMETER") x: XName,
) = name

operator fun BaseElement.div(
    @Suppress("UNUSED_PARAMETER") x: XNamespace,
) = namespace

private fun BaseElement.element(x: XPath): ElementWrapper {
    val candidates = elementNodes(x.namespace, x.name)

    val element = if (x.index != null) candidates.elementAtOrNull(x.index) else candidates.firstOrNull()
    return ElementWrapper(element)
}

private fun <T : BaseElement> T.elements(x: XPaths) = ElementListWrapper(elementNodes(x.namespace, x.name).toList())

private fun BaseElement.attribute(x: XAttribute) = attributeNodes(x.namespace, x.name).firstOrNull()?.value

private fun <T, C1 : List<T>, C2 : List<C1>> C2.accumulate(): List<T> {
    val accumulatedList = mutableListOf<T>()

    forEach { accumulatedList.addAll(it) }

    return accumulatedList
}
