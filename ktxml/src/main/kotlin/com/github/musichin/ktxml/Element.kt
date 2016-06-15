package com.github.musichin.ktxml

final class Element(
        override val namespace: String? = null,
        override val name: String,
        val elements: List<BaseElement> = listOf(),
        val attributes: List<Attribute> = listOf()
) : BaseElement(), IsFullyQualified, Iterable<BaseElement> {
    constructor(
            name: String,
            elements: List<BaseElement> = listOf(),
            attributes: List<Attribute> = listOf()
    ) : this(null, name, elements, attributes)

    override fun newBuilder(): Builder {
        return Builder(
                namespace(),
                name(),
                elements.map { it.newBuilder() }.toMutableList(),
                attributes.map { it.newBuilder() }.toMutableList()
        )
    }

    override fun iterator() = elements.iterator()

    fun name() = name

    fun namespace() = namespace

    fun element(namespace: String? = null, name: String? = null): Element? {
        return elements.find { it is Element && it.matches(namespace, name) } as? Element
    }

    fun element(name: String) = element(null, name)

    fun element() = element(null, null)

    fun elements() = elements

    fun elements(name: String? = null): List<Element> = elements(null, name)

    fun elements(namespace: String? = null, name: String? = null): List<Element> {
        val filteredElements = mutableListOf<Element>()

        for (element in elements) {
            if (element is Element && element.matches(namespace, name)) {
                filteredElements.add(element)
            }
        }

        return filteredElements
    }

    fun attributes() = attributes

    fun attribute(namespace: String? = null, name: String? = null) = attributes.find(namespace, name)?.value
    fun attribute(name: String? = null) = attribute(null, name)
    fun attribute() = attribute(null, null)

    fun text(): String? {
        return (elements.find { it is TextElement } as? TextElement)?.text
    }

    fun comment(): String? {
        return (elements.find { it is CommentElement } as? CommentElement)?.comment

    }

    fun cdata(): String? {
        return (elements.find { it is CDataElement } as? CDataElement)?.data
    }

    open class Builder constructor(
            override var namespace: String? = null,
            override var name: String? = null,
            val elements: MutableList<BaseElement.Builder> = mutableListOf(),
            val attributes: MutableList<Attribute.Builder> = mutableListOf()
    ) : BaseElement.Builder, IsFullyQualified {
        constructor() : this(null, null)

        override fun build() = build(null)
        fun build(fallbackNamespace: String? = null): Element {
            val finalNamespace = namespace ?: fallbackNamespace

            val mappedElements = elements.map { if (it is Element.Builder) it.build(finalNamespace) else it.build() }
            val mappedAttributes = attributes.map { it.build(finalNamespace) }

            return Element(finalNamespace, name!!, mappedElements, mappedAttributes)
        }

        fun name(name: String): Builder {
            this.name = name
            return this
        }

        fun namespace(namespace: String?): Builder {
            this.namespace = namespace
            return this
        }

        fun addAttribute(attribute: Attribute.Builder): Builder {
            attributes.add(attribute)
            return this
        }

        fun addAttribute(attribute: Attribute) = addAttribute(attribute.newBuilder())

        fun addAttribute(namespace: String?, name: String, value: String) = addAttribute(Attribute.Builder(namespace, name, value))

        fun addAttribute(name: String, value: String) = addAttribute(null, name, value)

        fun removeAttribute(attribute: Attribute.Builder): Builder {
            attributes.remove(attribute)
            return this
        }

        fun removeAttribute(name: String) = removeAttribute(null, name)
        fun removeAttribute(namespace: String?, name: String) = attributes.find(namespace, name)?.let { removeAttribute(it) } ?: this

        fun clearAttributes(): Builder {
            attributes.clear()
            return this
        }

        fun addElement(element: BaseElement.Builder): Builder {
            elements.add(element)
            return this
        }

        fun addElement(element: BaseElement) = addElement(element.newBuilder())

        fun addElement(namespace: String? = null, name: String, init: MarkupBuilder.() -> Unit = {}): Builder {
            return addElement(elementBuilderOf(namespace, name, init))
        }

        fun addElement(namespace: String? = null, name: String) = addElement(namespace, name, {})

        fun addElement(name: String) = addElement(null, name, {})

        fun addElement(name: String, init: MarkupBuilder.() -> Unit = {}) = addElement(null, name, init)

        fun addText(text: String) = addElement(TextElement.Builder(text))

        fun addComment(comment: String) = addElement(CommentElement.Builder(comment))

        fun addCData(data: String) = addElement(CDataElement.Builder(data))


        fun removeElement(element: BaseElement.Builder): Builder {
            elements.remove(element)
            return this
        }

        fun removeElement(element: BaseElement) = removeElement(element.newBuilder())

        fun clearElements(): Builder {
            elements.clear()
            return this
        }

        operator fun String.unaryPlus() = addText(this)
    }

    open class MarkupBuilder(
            private val builder: Builder,
            init: MarkupBuilder.() -> Unit = {}
    ) : BaseElement.MarkupBuilder {
        constructor(name: String, init: MarkupBuilder.() -> Unit = {}) : this(null, name, init)

        constructor(namespace: String?, name: String, init: MarkupBuilder.() -> Unit = {}) : this(Builder(namespace, name), init)

        init {
            init()
        }

        override fun builder() = builder

        override fun build() = builder().build()

        fun attribute(attribute: Attribute.Builder) = builder.addAttribute(attribute)
        fun attribute(attribute: Attribute) = builder.addAttribute(attribute)
        fun attribute(namespace: String?, name: String, value: String) = attribute(Attribute.Builder(namespace, name, value))
        fun attribute(name: String, value: String) = attribute(null, name, value)

        fun element(element: BaseElement.Builder) = builder.addElement(element)
        fun element(element: BaseElement) = builder.addElement(element)
        fun element(namespace: String?, name: String, init: MarkupBuilder.() -> Unit = {}) = element(elementBuilderOf(namespace, name, init))
        fun element(name: String, init: MarkupBuilder.() -> Unit = {}) = element(null, name, init)

        fun text(text: String) = builder.addText(text)

        fun comment(comment: String) = builder.addComment(comment)

        fun cdata(data: String) = builder.addCData(data)

        operator fun String.unaryPlus() = text(this)
        operator fun Pair<String, String>.unaryPlus() = attribute(first, second)
    }
}

internal fun IsFullyQualified.matches(namespace: String?, name: String?) = (name == null || this.name == name) && (namespace == null || this.namespace == namespace)

internal fun <T : IsFullyQualified> List<T>.find(namespace: String? = null, name: String? = null) = find { it.matches(namespace, name) }

fun elementOf(name: String, init: Element.MarkupBuilder.() -> Unit = {}) = elementOf(null, name, init)
fun elementOf(namespace: String? = null, name: String, init: Element.MarkupBuilder.() -> Unit = {}) = elementBuilderOf(namespace, name, init).build()

fun elementBuilderOf(name: String, init: Element.MarkupBuilder.() -> Unit = {}) = elementBuilderOf(null, name, init)
fun elementBuilderOf(namespace: String?, name: String, init: Element.MarkupBuilder.() -> Unit = {}) = Element.MarkupBuilder(namespace, name, init).builder()

