package de.musichin.ktxml

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.Objects

class ElementTest {
    @Test fun testComponents() {
        val element = elementOf("myNamespace", "myElement", listOf(textOf("myText")))

        val (namespace, name, nodes) = element

        assertThat(namespace).isEqualTo("myNamespace")
        assertThat(name).isEqualTo("myElement")
        assertThat(nodes).isEqualTo(listOf(textOf("myText")))
    }

    @Test fun testElementOfName() {
        val element = elementOf("myName")

        assertThat(element.namespace).isEqualTo("")
        assertThat(element.name).isEqualTo("myName")
        assertThat(element.nodes).isEmpty()
    }

    @Test fun testElementOfNamespaceAndName() {
        val element = elementOf("myNamespace", "myName")

        assertThat(element.namespace).isEqualTo("myNamespace")
        assertThat(element.name).isEqualTo("myName")
        assertThat(element.nodes).isEmpty()
    }

    @Test fun testElementOfNameAndNodes() {
        val element = elementOf("myName", listOf(textOf("myText")))

        assertThat(element.namespace).isEqualTo("")
        assertThat(element.name).isEqualTo("myName")
        assertThat(element.nodes).containsOnly(textOf("myText"))
    }

    @Test fun testElementOfNamespaceAndNameAndNodes() {
        val element = elementOf("myNamespace", "myName", listOf(textOf("myText")))

        assertThat(element.namespace).isEqualTo("myNamespace")
        assertThat(element.name).isEqualTo("myName")
        assertThat(element.nodes).containsOnly(textOf("myText"))
    }

    @Test fun testElementOfNamespaceAndNameAndInit() {
        val element =
            elementOf("myNamespace", "myName") {
                +"myText"
            }

        assertThat(element.namespace).isEqualTo("myNamespace")
        assertThat(element.name).isEqualTo("myName")
        assertThat(element.nodes).containsOnly(textOf("myText"))
    }

    @Test fun testElementOfNameAndInit() {
        val element =
            elementOf("myName") {
                +"myText"
            }

        assertThat(element.namespace).isEqualTo("")
        assertThat(element.name).isEqualTo("myName")
        assertThat(element.nodes).containsOnly(textOf("myText"))
    }

    @Test fun testElementOfNameAndNodesAndInit() {
        val element =
            elementOf("myName", listOf()) {
                +"myText"
            }

        assertThat(element.namespace).isEqualTo("")
        assertThat(element.name).isEqualTo("myName")
        assertThat(element.nodes).containsOnly(textOf("myText"))
    }

    @Test fun testElementOfNamespaceAndNameAndNodesAndInit() {
        val element =
            elementOf("myNamespace", "myName", listOf()) {
                +"myText"
            }

        assertThat(element.namespace).isEqualTo("myNamespace")
        assertThat(element.name).isEqualTo("myName")
        assertThat(element.nodes).containsOnly(textOf("myText"))
    }

    @Test fun testSize() {
        val element = elementOf("myElement", listOf(textOf("myText")))

        assertThat(element.size).isEqualTo(1)
    }

    @Test fun testGet() {
        val element = elementOf("myElement", listOf(textOf("myText")))

        assertThat(element[0]).isEqualTo(textOf("myText"))
    }

    @Test fun testIterator() {
        val element = elementOf("myElement", listOf(textOf("myText")))

        assertThat(element.iterator().next()).isEqualTo(textOf("myText"))
    }

    @Test fun testTextNodes() {
        val element = elementOf("myElement", listOf(textOf("myText")))

        assertThat(element.textNodes()).containsOnly(textOf("myText"))
    }

    @Test fun testTextNode() {
        val element = elementOf("myElement", listOf(textOf("myText")))

        assertThat(element.textNode()).isEqualTo(textOf("myText"))
    }

    @Test fun testCdataNodes() {
        val element = elementOf("myElement", listOf(cdataOf("myText")))

        assertThat(element.cdataNodes()).containsOnly(cdataOf("myText"))
    }

    @Test fun testCdataNode() {
        val element = elementOf("myElement", listOf(cdataOf("myText")))

        assertThat(element.cdataNode()).isEqualTo(cdataOf("myText"))
    }

    @Test fun testCommentNodes() {
        val element = elementOf("myElement", listOf(commentOf("myText")))

        assertThat(element.commentNodes()).containsOnly(commentOf("myText"))
    }

    @Test fun testCommentNode() {
        val element = elementOf("myElement", listOf(commentOf("myText")))

        assertThat(element.commentNode()).isEqualTo(commentOf("myText"))
    }

    @Test fun testAttributeNodes() {
        val element = elementOf("myElement", listOf(attributeOf("myName", "myValue")))

        assertThat(element.attributeNodes()).containsOnly(attributeOf("myName", "myValue"))
    }

    @Test fun testAttributeNode() {
        val element = elementOf("myElement", listOf(attributeOf("myName", "myValue")))

        assertThat(element.attributeNode()).isEqualTo(attributeOf("myName", "myValue"))
    }

    @Test fun testAttributeNodeWithNamespaceFilter() {
        val element = elementOf("myElement", listOf(attributeOf("ns", "myName", "myValue")))

        assertThat(element.attributeNode(namespace = "ns")).isEqualTo(attributeOf("myName", "myValue"))
        assertThat(element.attributeNode(namespace = "")).isNull()
    }

    @Test fun testAttributeNodesWithNamespaceFilter() {
        val element = elementOf("myElement", listOf(attributeOf("ns", "myName", "myValue")))

        assertThat(element.attributeNodes(namespace = "ns")).containsOnly(attributeOf("myName", "myValue"))
        assertThat(element.attributeNodes(namespace = "")).isEmpty()
    }

    @Test fun testAttributeNodeWithNamespaceAndNameFilter() {
        val element = elementOf("myElement", listOf(attributeOf("ns", "myName", "myValue")))

        assertThat(element.attributeNode("ns", "myName")).isEqualTo(attributeOf("myName", "myValue"))
        assertThat(element.attributeNode("ns", "mySecondName")).isNull()
    }

    @Test fun testAttributeNodesWithNamespaceAndNameFilter() {
        val element = elementOf("myElement", listOf(attributeOf("ns", "myName", "myValue")))

        assertThat(element.attributeNodes("ns", "myName")).containsOnly(attributeOf("myName", "myValue"))
        assertThat(element.attributeNodes("ns", "mySecondName")).isEmpty()
    }

    @Test fun testAttributeNodeWithNameFilter() {
        val element = elementOf("myElement", listOf(attributeOf("ns", "myName", "myValue")))

        assertThat(element.attributeNode("myName")).isEqualTo(attributeOf("myName", "myValue"))
        assertThat(element.attributeNode("mySecondName")).isNull()
    }

    @Test fun testAttributeNodesWithNameFilter() {
        val element = elementOf("myElement", listOf(attributeOf("ns", "myName", "myValue")))

        assertThat(element.attributeNodes("myName")).containsOnly(attributeOf("myName", "myValue"))
        assertThat(element.attributeNodes("mySecondName")).isEmpty()
    }

    @Test fun testElementNodes() {
        val element = elementOf("myElement", listOf(elementOf("myName")))

        assertThat(element.elementNodes()).containsOnly(elementOf("myName"))
    }

    @Test fun testElementNode() {
        val element = elementOf("myElement", listOf(elementOf("myName")))

        assertThat(element.elementNode()).isEqualTo(elementOf("myName"))
    }

    @Test fun testElementNodeWithNamespaceFilter() {
        val element = elementOf("myElement", listOf(elementOf("ns", "myName")))

        assertThat(element.elementNode(namespace = "ns")).isEqualTo(elementOf("ns", "myName"))
        assertThat(element.elementNode(namespace = "")).isNull()
    }

    @Test fun testElementNodesWithNamespaceFilter() {
        val element = elementOf("myElement", listOf(elementOf("ns", "myName")))

        assertThat(element.elementNodes(namespace = "ns")).containsOnly(elementOf("ns", "myName"))
        assertThat(element.elementNodes(namespace = "")).isEmpty()
    }

    @Test fun testElementNodeWithNamespaceAndNameFilter() {
        val element = elementOf("myElement", listOf(elementOf("ns", "myName")))

        assertThat(element.elementNode("ns", "myName")).isEqualTo(elementOf("ns", "myName"))
        assertThat(element.elementNode("ns", "mySecondName")).isNull()
    }

    @Test fun testElementNodesWithNamespaceAndNameFilter() {
        val element = elementOf("myElement", listOf(elementOf("ns", "myName")))

        assertThat(element.elementNodes("ns", "myName")).containsOnly(elementOf("ns", "myName"))
        assertThat(element.elementNodes("ns", "mySecondName")).isEmpty()
    }

    @Test fun testElementNodeWithNameFilter() {
        val element = elementOf("myElement", listOf(elementOf("ns", "myName")))

        assertThat(element.elementNode("myName")).isEqualTo(elementOf("ns", "myName"))
        assertThat(element.elementNode("mySecondName")).isNull()
    }

    @Test fun testElementNodesWithNameFilter() {
        val element = elementOf("myElement", listOf(elementOf("ns", "myName")))

        assertThat(element.elementNodes("myName")).containsOnly(elementOf("ns", "myName"))
        assertThat(element.elementNodes("mySecondName")).isEmpty()
    }

    @Test fun testMutable() {
        val element = elementOf("myNamespace", "myElement", listOf(attributeOf("myName", "myValue")))
        val mutable = element.toMutable()

        assertThat(mutable.namespace).isEqualTo("myNamespace")
        assertThat(mutable.name).isEqualTo("myElement")
        assertThat(mutable.nodes).isEqualTo(mutableListOf(mutableAttributeOf("myName", "myValue")))
    }

    @Test fun testToString() {
        val element = elementOf("myElement", listOf(attributeOf("myName", "myValue")))

        assertThat(
            element.toString(),
        ).isEqualTo("Element(namespace=, name=myElement, nodes=[Attribute(namespace=, name=myName, value=myValue)])")
    }

    @Test fun testHashCode() {
        val element = elementOf("myElement", listOf(attributeOf("myName", "myValue")))

        assertThat(element.hashCode()).isEqualTo(Objects.hash("", "myElement", listOf(attributeOf("myName", "myValue"))))
    }

    @Test fun testEquals() {
        val element1 = elementOf("myElement", listOf(attributeOf("myName", "myValue")))
        val element2 = elementOf("myElement", listOf(attributeOf("myName", "myValue")))
        val element3 = elementOf("myElement", listOf(attributeOf("alternativeName", "myValue")))
        val element4 = elementOf("ns", "myElement", listOf(attributeOf("alternativeName", "myValue")))
        val element5 = elementOf("alternativeElement", listOf(attributeOf("myName", "myValue")))

        assertThat(element1).isEqualTo(element1)
        assertThat(element1 == element2).isTrue
        assertThat(element1 == element3).isFalse
        assertThat(element1 == element4).isFalse
        assertThat(element1 == element5).isFalse
        assertThat(element1.equals(null)).isFalse
        assertThat(element1.equals("")).isFalse
    }
}
