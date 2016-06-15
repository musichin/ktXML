package com.github.musichin.ktxml

// Experimental
object XElement

object XText

object XCData

object XComment

object XName

object XNamespace

class ElementWrapper(val value: Element? = null) {
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

class ElementListWrapper(val value: List<Element> = listOf()) {
    operator fun div(x: XPaths) = ElementListWrapper(value.mapNotNull { it.elements(x).value }.accumulate())
    operator fun div(x: XPath) = ElementListWrapper(value.mapNotNull { it.element(x).value })
    operator fun div(name: String) = div(XPath(name))

    operator fun div(@Suppress("UNUSED_PARAMETER") x: XElement) = value
    operator fun div(x: XText) = value.mapNotNull { it / x }
    operator fun div(x: XCData) = value.mapNotNull { it / x }
    operator fun div(x: XComment) = value.mapNotNull { it / x }
    operator fun div(x: XName) = value.map { it / x }
    operator fun div(x: XNamespace) = value.map { it / x }

    operator fun div(x: XAttribute) = value.mapNotNull { it / x }
}

class XAttribute(val namespace: String? = null, val name: String? = null) {
    constructor(name: String) : this(null, name)
}

class XPath(val namespace: String? = null, val name: String? = null, val index: Int? = null) {
    constructor(name: String) : this(null, name)
}

class XPaths(val namespace: String? = null, val name: String? = null) {
    constructor(name: String) : this(null, name)

    operator fun get(index: Int) = XPath(namespace, name, index)
}

operator fun Element.div(x: XPath) = element(x)
operator fun Element.div(name: String) = div(XPath(null, name))
operator fun Element.div(x: XPaths) = elements(x)
operator fun Element.div(x: XAttribute) = attribute(x)


operator fun Element.div(@Suppress("UNUSED_PARAMETER") x: XElement) = this
operator fun Element.div(@Suppress("UNUSED_PARAMETER") x: XText) = text()
operator fun Element.div(@Suppress("UNUSED_PARAMETER") x: XCData) = cdata()
operator fun Element.div(@Suppress("UNUSED_PARAMETER") x: XComment) = comment()
operator fun Element.div(@Suppress("UNUSED_PARAMETER") x: XName) = name()
operator fun Element.div(@Suppress("UNUSED_PARAMETER") x: XNamespace) = namespace()

private fun Element.element(x: XPath) = ElementWrapper(if (x.index != null) elements(x.namespace, x.name).getOrNull(x.index) else element(x.namespace, x.name))
private fun List<Element>.getOrNull(index: Int): Element? = getOrElse(index, { null })
private fun Element.elements(x: XPaths) = ElementListWrapper(elements(x.namespace, x.name))

private fun Element.attribute(x: XAttribute) = attribute(x.namespace, x.name)

private fun <T, C1 : List<T>, C2 : List<C1>> C2.accumulate(): List<T> {
    val accumulatedList = mutableListOf<T>()

    forEach { accumulatedList.addAll(it) }

    return accumulatedList
}