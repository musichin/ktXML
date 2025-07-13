package de.musichin.ktxml

sealed interface BaseElement :
    BaseNode,
    Iterable<BaseNode> {
    val namespace: String
    val name: String
    val nodes: List<BaseNode>

    operator fun component1(): String = namespace

    operator fun component2(): String = name

    operator fun component3(): List<BaseNode>

    /**
     * Returns size of contents in this element
     */
    val size: Int get() = nodes.size

    override fun iterator(): Iterator<BaseNode>

    /**
     * Returns node at specified position
     */
    operator fun get(index: Int): BaseNode

    fun textNodes(): List<BaseText>

    fun cdataNodes(): List<BaseCData>

    fun commentNodes(): List<BaseComment>

    /**
     * Returns first text
     */
    fun textNode(): BaseText?

    /**
     * Returns first cdata
     */
    fun cdataNode(): BaseCData?

    /**
     * Returns first comment
     */
    fun commentNode(): BaseComment?

    /**
     * Returns list of elements which match the specified namespace and name.
     */
    fun elementNodes(
        namespace: String? = null,
        name: String? = null,
    ): List<BaseElement>

    /**
     * Returns list of elements which match the specified name.
     */
    fun elementNodes(name: String?): List<BaseElement>

    /**
     * Returns list of all elements.
     */
    fun elementNodes(): List<BaseElement>

    /**
     * Returns first element which match specified namespace and name
     */
    fun elementNode(
        namespace: String? = null,
        name: String? = null,
    ): BaseElement?

    /**
     * Returns first element which match specified name
     */
    fun elementNode(name: String?): BaseElement?

    /**
     * Returns first element
     */
    fun elementNode(): BaseElement?

    /**
     * Returns attribute value of the first match
     */
    fun attributeNode(
        namespace: String? = null,
        name: String? = null,
    ): BaseAttribute?

    /**
     * Returns attribute value of the first match
     */
    fun attributeNode(name: String?): BaseAttribute?

    /**
     * Returns first attribute
     */
    fun attributeNode(): BaseAttribute?

    /**
     * Returns list of elements which match the specified namespace and name.
     */
    fun attributeNodes(
        namespace: String? = null,
        name: String? = null,
    ): List<BaseAttribute>

    /**
     * Returns list of attributes which match the specified name.
     */
    fun attributeNodes(name: String?): List<BaseAttribute>

    /**
     * Returns list of all attributes.
     */
    fun attributeNodes(): List<BaseAttribute>
}
