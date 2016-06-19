package com.github.musichin.ktxml

interface Element : Content, Iterable<Content> {
    val namespace: String?
    val name: String
    val contents: List<Content>
    val attributes: List<Attribute>

    override fun mutable(): MutableElement

    /**
     * Returns size of contents in this element
     */
    val size: Int

    /**
     * Returns content at specified position
     */
    operator fun get(i: Int): Content

    /**
     * Returns string of first text content in contents
     */
    fun text(): String?

    /**
     * Returns string of first cdata content in contents
     */
    fun cdata(): String?

    /**
     * Returns string of first comment
     */
    fun comment(): String?

    /**
     * Returns list of elements which match the specified namespace and name.
     */
    fun elements(namespace: String? = null, name: String? = null): List<Element>

    /**
     * Returns first element which match specified namespace and name
     */
    fun element(namespace: String? = null, name: String? = null): Element?

    /**
     * Returns first element which match specified name
     */
    fun element(name: String): Element?

    /**
     * Returns attribute value of the first match
     */
    fun attribute(namespace: String? = null, name: String? = null): String?

    companion object {
        fun of(name: String): Element = ElementContent(name = name)
        fun of(namespace: String?, name: String): Element = ElementContent(namespace, name)
        fun of(namespace: String?, name: String, contents: List<Content>, attributes: List<Attribute>): Element
                = ElementContent(namespace, name, contents, attributes)
    }
}

fun elementOf(name: String) = Element.of(name)
fun elementOf(namespace: String?, name: String) = Element.of(namespace, name)
fun elementOf(namespace: String?, name: String, contents: List<Content>, attributes: List<Attribute>)
        = Element.of(namespace, name, contents, attributes)


interface MutableElement : Element, MutableContent {
    override var namespace: String?
    override var name: String
    override val contents: MutableList<MutableContent>
    override val attributes: MutableList<MutableAttribute>

    override fun immutable(): Element

    override fun iterator(): Iterator<MutableContent>

    /**
     * Adds attribute
     */
    fun addAttribute(attribute: Attribute)

    fun addAttribute(namespace: String? = null, name: String, value: String)

    fun addAttribute(name: String, value: String)

    fun addText(text: String)

    fun addCData(text: String)

    fun addComment(comment: String)

    fun addElement(namespace: String? = null, name: String)

    fun addContent(content: Content)

    companion object {
        fun of(name: String): MutableElement = MutableElementContent(name = name)
        fun of(namespace: String?, name: String): MutableElement = MutableElementContent(namespace, name)
        fun of(namespace: String?,
               name: String,
               contents: MutableList<MutableContent>,
               attributes: MutableList<MutableAttribute>): MutableElement
                = MutableElementContent(namespace, name, contents, attributes)
    }
}

fun mutableElementOf(name: String) = MutableElement.of(name)
fun mutableElementOf(namespace: String?, name: String) = MutableElement.of(namespace, name)
fun mutableElementOf(namespace: String?,
                     name: String,
                     contents: MutableList<MutableContent>,
                     attributes: MutableList<MutableAttribute>)
        = MutableElement.of(namespace, name, contents, attributes)


open class ElementContent(
        override val namespace: String? = null,
        override val name: String,
        override val contents: List<Content> = listOf(),
        override val attributes: List<Attribute> = listOf()
) : Element {
    constructor(
            name: String,
            contents: List<Content> = listOf(),
            attributes: List<Attribute> = listOf()
    ) : this(null, name, contents, attributes)

    override fun mutable(): MutableElement = MutableElementContent(
            namespace,
            name,
            contents.map { it.mutable() }.toMutableList(),
            attributes.map { it.mutable() }.toMutableList())

    override fun iterator() = contents.iterator()

    fun name() = name

    fun namespace() = namespace

    override fun element(namespace: String?, name: String?): Element? {
        return contents.find { it is Element && it.matches(namespace, name) } as? Element
    }

    override fun element(name: String) = element(null, name)

    fun element() = element(null, null)

    fun elements() = contents

    fun elements(name: String? = null): List<Element> = elements(null, name)

    override fun elements(namespace: String?, name: String?): List<Element> {
        val filteredElements = mutableListOf<Element>()

        for (element in contents) {
            if (element is Element && element.matches(namespace, name)) {
                filteredElements.add(element)
            }
        }

        return filteredElements
    }

    fun attributes() = attributes

    override fun attribute(namespace: String?, name: String?) = attributes.find { it.matches(namespace, name) }?.value
    fun attribute(name: String? = null) = attribute(null, name)
    fun attribute() = attribute(null, null)

    override fun text(): String? {
        return (contents.find { it is Text } as? Text)?.text
    }

    override fun comment(): String? {
        return (contents.find { it is Comment } as? Comment)?.comment
    }

    override fun cdata(): String? {
        return (contents.find { it is CData } as? CData)?.text
    }

    override val size: Int get() = contents.size

    override operator fun get(i: Int) = contents[i]

    override fun hashCode(): Int {
        var result = (namespace?.hashCode() ?: 0)
        result = 31 * result + name.hashCode()
        result = 31 * result + contents.hashCode()
        result = 31 * result + attributes.hashCode()
        return result;
    }

    override fun equals(other: Any?): Boolean {
        if (other is Element) {
            return namespace == other.namespace &&
                    name == other.name &&
                    contents == other.contents &&
                    attributes == other.attributes
        }
        return super.equals(other)
    }

    override fun toString(): String {
        return "${javaClass.simpleName}(namespace=$namespace, name=$name, contents=$contents, attributes=$attributes)"
    }
}

open class MutableElementContent constructor(
        override var namespace: String? = null,
        override var name: String,
        override val contents: MutableList<MutableContent> = mutableListOf(),
        override val attributes: MutableList<MutableAttribute> = mutableListOf()
) : ElementContent(namespace, name, contents, attributes), MutableElement {
    override fun immutable(): Element = ElementContent(
            namespace,
            name,
            contents.map { it.immutable() }.toList(),
            attributes.map { it.immutable() }.toList())

    override fun mutable(): MutableElement = this

    override fun iterator() = contents.iterator()

    fun name(name: String) {
        this.name = name
    }

    fun namespace(namespace: String?) {
        this.namespace = namespace
    }

    override fun addAttribute(attribute: Attribute) {
        attributes.add(attribute.mutable())
    }

    override fun addAttribute(namespace: String?, name: String, value: String) = addAttribute(mutableAttributeOf(namespace, name, value))

    override fun addAttribute(name: String, value: String) = addAttribute(null, name, value)

    override fun addContent(content: Content) {
        contents.add(content.mutable())
    }

    fun addElement(namespace: String? = null, name: String, init: MarkupBuilder.() -> Unit = {}) {
        addContent(mutableElementOf(namespace, name, init))
    }

    override fun addElement(namespace: String?, name: String) = addElement(namespace, name, {})

    fun addElement(name: String) = addElement(null, name, {})

    fun addElement(name: String, init: MarkupBuilder.() -> Unit = {}) = addElement(null, name, init)

    override fun addText(text: String) = addContent(mutableTextOf(text))

    override fun addComment(comment: String) = addContent(mutableCommentOf(comment))

    override fun addCData(text: String) = addContent(mutableCDataOf(text))

    fun remove(attribute: Attribute) {
        attributes.remove(attribute.mutable())
    }

    fun remove(element: Content) {
        contents.remove(element.mutable())
    }

    fun clearAttributes() {
        attributes.clear()
    }

    fun clearContent() {
        contents.clear()
    }

    fun clear() {
        clearAttributes()
        clearContent()
    }

    operator fun String.unaryPlus() = addText(this)
}

open class MarkupBuilder(
        internal val element: MutableElement,
        init: MarkupBuilder.() -> Unit = {}
) : Content, MutableContent {
    override fun mutable(): MutableElement = setNamespace(element)

    override fun immutable(): Element = mutable().immutable()

    constructor(name: String, init: MarkupBuilder.() -> Unit = {}) : this(null, name, init)

    constructor(namespace: String?, name: String, init: MarkupBuilder.() -> Unit = {}) : this(mutableElementOf(namespace, name), init)

    init {
        init()
    }

    internal fun setNamespace(attribute: Attribute): Attribute {
        val result = attribute.mutable()
        result.namespace = namespace(result.namespace)
        return result
    }

    internal fun <T : Content> setNamespace(content: T): T {
        if (content is Element) {
            val result = content.mutable()
            result.namespace = namespace(result.namespace)
            content.contents.forEach { setNamespace(it) }
            content.attributes.forEach { setNamespace(it) }

            @Suppress("UNCHECKED_CAST")
            return result as T
        }
        return content
    }

    internal fun namespace(other: String?): String? {
        val namespace = element.namespace ?: return other
        if (other == null) return namespace
        return other
    }

    fun attribute(attribute: Attribute) = element.addAttribute(attribute)
    fun attribute(namespace: String? = null, name: String, value: String) = attribute(mutableAttributeOf(namespace, name, value))
    fun attribute(name: String, value: String) = attribute(null, name, value)

    fun content(content: Content) = element.addContent(content)
    fun element(namespace: String?, name: String, init: MarkupBuilder.() -> Unit = {}) = content(mutableElementOf(namespace, name, init))
    fun element(name: String, init: MarkupBuilder.() -> Unit = {}) = element(null, name, init)

    fun text(text: String) = element.addText(text)

    fun comment(comment: String) = element.addComment(comment)

    fun cdata(data: String) = element.addCData(data)

    operator fun String.unaryPlus() = text(this)
    operator fun Pair<String, String>.unaryPlus() = attribute(first, second)


}


fun elementOf(name: String, init: MarkupBuilder.() -> Unit = {}) = elementOf(null, name, init)
fun elementOf(namespace: String? = null, name: String, init: MarkupBuilder.() -> Unit = {}) = mutableElementOf(namespace, name, init).immutable()

fun mutableElementOf(name: String, init: MarkupBuilder.() -> Unit = {}) = mutableElementOf(null, name, init)
fun mutableElementOf(namespace: String? = null, name: String, init: MarkupBuilder.() -> Unit = {}) = MarkupBuilder(namespace, name, init).mutable()

internal fun Element.matches(namespace: String?, name: String?) = (name == null || this.name == name) && (namespace == null || this.namespace == namespace)
internal fun Attribute.matches(namespace: String?, name: String?) = (name == null || this.name == name) && (namespace == null || this.namespace == namespace)
