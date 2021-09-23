package com.github.musichin.ktxml

// Experimental
object XElement

object XText

object XCData

object XComment

object XName

object XNamespace

abstract class AbstractContentWrapper<T : Element>(val value: T? = null) {
    abstract operator fun div(x: XPath): AbstractContentWrapper<T>
    abstract operator fun div(x: XPaths): AbstractContentListWrapper<T>
    operator fun div(name: String) = div(XPath(name))

    operator fun div(x: XElement) = value?.let { it / x }
    operator fun div(x: XText) = value?.let { it / x }
    operator fun div(x: XCData) = value?.let { it / x }
    operator fun div(x: XComment) = value?.let { it / x }
    operator fun div(x: XName) = value?.let { it / x }
    operator fun div(x: XNamespace) = value?.let { it / x }

    operator fun div(x: XAttribute) = value?.let { it / x }
}

class ElementWrapper(value: Element? = null) : AbstractContentWrapper<Element>(value) {
    override operator fun div(x: XPath) = value?.element(x) ?: ElementWrapper()
    override operator fun div(x: XPaths) = value?.elements(x) ?: ElementListWrapper()
}

class MutableElementWrapper(value: MutableElement? = null) : AbstractContentWrapper<MutableElement>(value) {
    override operator fun div(x: XPath) = value?.element(x) ?: MutableElementWrapper()
    override operator fun div(x: XPaths) = value?.elements(x) ?: MutableElementListWrapper()
}

abstract class AbstractContentListWrapper<T : Element>(val value: List<T> = listOf()) {
    abstract operator fun div(x: XPaths): AbstractContentListWrapper<T>
    abstract operator fun div(x: XPath): AbstractContentListWrapper<T>
    operator fun div(name: String) = div(XPath(name))

    operator fun div(@Suppress("UNUSED_PARAMETER") x: XElement) = value
    operator fun div(x: XText) = value.mapNotNull { it / x }
    operator fun div(x: XCData) = value.mapNotNull { it / x }
    operator fun div(x: XComment) = value.mapNotNull { it / x }
    operator fun div(x: XName) = value.map { it / x }
    operator fun div(x: XNamespace) = value.map { it / x }

    operator fun div(x: XAttribute) = value.mapNotNull { it / x }
}

class ElementListWrapper(value: List<Element> = listOf()) : AbstractContentListWrapper<Element>(value) {
    override operator fun div(x: XPaths) = ElementListWrapper(value.mapNotNull { it.elements(x).value }.accumulate())

    override operator fun div(x: XPath) = ElementListWrapper(value.mapNotNull { it.element(x).value })
}

class MutableElementListWrapper(value: List<MutableElement> = listOf()) : AbstractContentListWrapper<MutableElement>(value) {
    override operator fun div(x: XPaths) = MutableElementListWrapper(value.mapNotNull { it.elements(x).value }.accumulate())
    override operator fun div(x: XPath) = MutableElementListWrapper(value.mapNotNull { it.element(x).value })
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

operator fun <T : Element> T.div(x: XPath) = element(x)
operator fun <T : MutableElement> T.div(x: XPath) = element(x)
operator fun <T : Element> T.div(x: XPaths) = elements(x)
operator fun <T : MutableElement> T.div(x: XPaths) = elements(x)
operator fun <T : Element> T.div(name: String) = div(XPath(null, name))
operator fun <T : MutableElement> T.div(name: String) = div(XPath(null, name))
operator fun <T : Element> T.div(x: XAttribute) = attribute(x)

operator fun <T : Element> T.div(@Suppress("UNUSED_PARAMETER") x: XElement) = this
operator fun <T : MutableElement> T.div(@Suppress("UNUSED_PARAMETER") x: XElement) = this
operator fun <T : Element> T.div(@Suppress("UNUSED_PARAMETER") x: XText) = text()
operator fun <T : Element> T.div(@Suppress("UNUSED_PARAMETER") x: XCData) = cdata()
operator fun <T : Element> T.div(@Suppress("UNUSED_PARAMETER") x: XComment) = comment()
operator fun <T : Element> T.div(@Suppress("UNUSED_PARAMETER") x: XName) = name
operator fun <T : Element> T.div(@Suppress("UNUSED_PARAMETER") x: XNamespace) = namespace

private fun Element.element(x: XPath) = ElementWrapper(if (x.index != null) elements(x.namespace, x.name).getOrNull(x.index) else element(x.namespace, x.name))
private fun MutableElement.element(x: XPath) = MutableElementWrapper(if (x.index != null) elements(x.namespace, x.name).getOrNull(x.index) else element(x.namespace, x.name))
private fun <T : Element> T.elements(x: XPaths) = ElementListWrapper(elements(x.namespace, x.name))
private fun <T : MutableElement> T.elements(x: XPaths) = MutableElementListWrapper(elements(x.namespace, x.name))

private fun Element.attribute(x: XAttribute) = attribute(x.namespace, x.name)

private fun <T, C1 : List<T>, C2 : List<C1>> C2.accumulate(): List<T> {
    val accumulatedList = mutableListOf<T>()

    forEach { accumulatedList.addAll(it) }

    return accumulatedList
}
