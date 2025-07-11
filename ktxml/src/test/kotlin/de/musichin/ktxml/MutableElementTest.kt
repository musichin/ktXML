package de.musichin.ktxml

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.Objects

class MutableElementTest {
    @Test fun testComponents() {
        val element = mutableElementOf("myNamespace", "myElement", mutableListOf(mutableTextOf("myText")))

        val (namespace, name, nodes) = element

        assertThat(namespace).isEqualTo("myNamespace")
        assertThat(name).isEqualTo("myElement")
        assertThat(nodes).isEqualTo(listOf(mutableTextOf("myText")))
    }

    @Test fun testMutableElementOfName() {
        val element = mutableElementOf("myName")

        assertThat(element.namespace).isEqualTo("")
        assertThat(element.name).isEqualTo("myName")
        assertThat(element.nodes).isEmpty()
    }

    @Test fun testMutableElementOfNamespaceAndName() {
        val element = mutableElementOf("myNamespace", "myName")

        assertThat(element.namespace).isEqualTo("myNamespace")
        assertThat(element.name).isEqualTo("myName")
        assertThat(element.nodes).isEmpty()
    }

    @Test fun testMutableElementOfNameAndNodes() {
        val element = mutableElementOf("myName", mutableListOf(mutableTextOf("myText")))

        assertThat(element.namespace).isEqualTo("")
        assertThat(element.name).isEqualTo("myName")
        assertThat(element.nodes).containsOnly(mutableTextOf("myText"))
    }

    @Test fun testMutableElementOfNamespaceAndNameAndNodes() {
        val element = mutableElementOf("myNamespace", "myName", mutableListOf(mutableTextOf("myText")))

        assertThat(element.namespace).isEqualTo("myNamespace")
        assertThat(element.name).isEqualTo("myName")
        assertThat(element.nodes).containsOnly(mutableTextOf("myText"))
    }

    @Test fun testMutableElementOfNamespaceAndNameAndInit() {
        val element =
            mutableElementOf("myNamespace", "myName") {
                +"myText"
            }

        assertThat(element.namespace).isEqualTo("myNamespace")
        assertThat(element.name).isEqualTo("myName")
        assertThat(element.nodes).containsOnly(mutableTextOf("myText"))
    }

    @Test fun testMutableElementOfNameAndInit() {
        val element =
            mutableElementOf("myName") {
                +"myText"
            }

        assertThat(element.namespace).isEqualTo("")
        assertThat(element.name).isEqualTo("myName")
        assertThat(element.nodes).containsOnly(mutableTextOf("myText"))
    }

    @Test fun testMutableElementOfNameAndNodesAndInit() {
        val element =
            mutableElementOf("myName", mutableListOf()) {
                +"myText"
            }

        assertThat(element.namespace).isEqualTo("")
        assertThat(element.name).isEqualTo("myName")
        assertThat(element.nodes).containsOnly(mutableTextOf("myText"))
    }

    @Test fun testMutableElementOfNamespaceAndNameAndNodesAndInit() {
        val element =
            mutableElementOf("myNamespace", "myName", mutableListOf()) {
                +"myText"
            }

        assertThat(element.namespace).isEqualTo("myNamespace")
        assertThat(element.name).isEqualTo("myName")
        assertThat(element.nodes).containsOnly(mutableTextOf("myText"))
    }

    @Test fun testSize() {
        val element = mutableElementOf("myElement", mutableListOf(mutableTextOf("myText")))

        assertThat(element.size).isEqualTo(1)
    }

    @Test fun testGet() {
        val element = mutableElementOf("myElement", mutableListOf(mutableTextOf("myText")))

        assertThat(element[0]).isEqualTo(mutableTextOf("myText"))
    }

    @Test fun testIterator() {
        val element = mutableElementOf("myElement", mutableListOf(mutableTextOf("myText")))

        assertThat(element.iterator().next()).isEqualTo(mutableTextOf("myText"))
    }

    @Test fun testTextNodes() {
        val element = mutableElementOf("myElement", mutableListOf(mutableTextOf("myText")))

        assertThat(element.textNodes()).containsOnly(mutableTextOf("myText"))
    }

    @Test fun testTextNode() {
        val element = mutableElementOf("myElement", mutableListOf(mutableTextOf("myText")))

        assertThat(element.textNode()).isEqualTo(mutableTextOf("myText"))
    }

    @Test fun testCdataNodes() {
        val element = mutableElementOf("myElement", mutableListOf(mutableCDataOf("myText")))

        assertThat(element.cdataNodes()).containsOnly(mutableCDataOf("myText"))
    }

    @Test fun testCdataNode() {
        val element = mutableElementOf("myElement", mutableListOf(mutableCDataOf("myText")))

        assertThat(element.cdataNode()).isEqualTo(mutableCDataOf("myText"))
    }

    @Test fun testCommentNodes() {
        val element = mutableElementOf("myElement", mutableListOf(mutableCommentOf("myText")))

        assertThat(element.commentNodes()).containsOnly(mutableCommentOf("myText"))
    }

    @Test fun testCommentNode() {
        val element = mutableElementOf("myElement", mutableListOf(mutableCommentOf("myText")))

        assertThat(element.commentNode()).isEqualTo(mutableCommentOf("myText"))
    }

    @Test fun testAttributeNodes() {
        val element = mutableElementOf("myElement", mutableListOf(mutableAttributeOf("myName", "myValue")))

        assertThat(element.attributeNodes()).containsOnly(mutableAttributeOf("myName", "myValue"))
    }

    @Test fun testAttributeNode() {
        val element = mutableElementOf("myElement", mutableListOf(mutableAttributeOf("myName", "myValue")))

        assertThat(element.attributeNode()).isEqualTo(mutableAttributeOf("myName", "myValue"))
    }

    @Test fun testAttributeNodeWithNamespaceFilter() {
        val element = mutableElementOf("myElement", mutableListOf(mutableAttributeOf("ns", "myName", "myValue")))

        assertThat(element.attributeNode(namespace = "ns")).isEqualTo(mutableAttributeOf("myName", "myValue"))
        assertThat(element.attributeNode(namespace = "")).isNull()
    }

    @Test fun testAttributeNodesWithNamespaceFilter() {
        val element = mutableElementOf("myElement", mutableListOf(mutableAttributeOf("ns", "myName", "myValue")))

        assertThat(element.attributeNodes(namespace = "ns")).containsOnly(mutableAttributeOf("myName", "myValue"))
        assertThat(element.attributeNodes(namespace = "")).isEmpty()
    }

    @Test fun testAttributeNodeWithNamespaceAndNameFilter() {
        val element = mutableElementOf("myElement", mutableListOf(mutableAttributeOf("ns", "myName", "myValue")))

        assertThat(element.attributeNode("ns", "myName")).isEqualTo(mutableAttributeOf("myName", "myValue"))
        assertThat(element.attributeNode("ns", "mySecondName")).isNull()
    }

    @Test fun testAttributeNodesWithNamespaceAndNameFilter() {
        val element = mutableElementOf("myElement", mutableListOf(mutableAttributeOf("ns", "myName", "myValue")))

        assertThat(element.attributeNodes("ns", "myName")).containsOnly(mutableAttributeOf("myName", "myValue"))
        assertThat(element.attributeNodes("ns", "mySecondName")).isEmpty()
    }

    @Test fun testAttributeNodeWithNameFilter() {
        val element = mutableElementOf("myElement", mutableListOf(mutableAttributeOf("ns", "myName", "myValue")))

        assertThat(element.attributeNode("myName")).isEqualTo(mutableAttributeOf("myName", "myValue"))
        assertThat(element.attributeNode("mySecondName")).isNull()
    }

    @Test fun testAttributeNodesWithNameFilter() {
        val element = mutableElementOf("myElement", mutableListOf(mutableAttributeOf("ns", "myName", "myValue")))

        assertThat(element.attributeNodes("myName")).containsOnly(mutableAttributeOf("myName", "myValue"))
        assertThat(element.attributeNodes("mySecondName")).isEmpty()
    }

    @Test fun testElementNodes() {
        val element = mutableElementOf("myElement", mutableListOf(mutableElementOf("myName")))

        assertThat(element.elementNodes()).containsOnly(mutableElementOf("myName"))
    }

    @Test fun testElementNode() {
        val element = mutableElementOf("myElement", mutableListOf(mutableElementOf("myName")))

        assertThat(element.elementNode()).isEqualTo(mutableElementOf("myName"))
    }

    @Test fun testElementNodeWithNamespaceFilter() {
        val element = mutableElementOf("myElement", mutableListOf(mutableElementOf("ns", "myName")))

        assertThat(element.elementNode(namespace = "ns")).isEqualTo(mutableElementOf("ns", "myName"))
        assertThat(element.elementNode(namespace = "")).isNull()
    }

    @Test fun testElementNodesWithNamespaceFilter() {
        val element = mutableElementOf("myElement", mutableListOf(mutableElementOf("ns", "myName")))

        assertThat(element.elementNodes(namespace = "ns")).containsOnly(mutableElementOf("ns", "myName"))
        assertThat(element.elementNodes(namespace = "")).isEmpty()
    }

    @Test fun testElementNodeWithNamespaceAndNameFilter() {
        val element = mutableElementOf("myElement", mutableListOf(mutableElementOf("ns", "myName")))

        assertThat(element.elementNode("ns", "myName")).isEqualTo(mutableElementOf("ns", "myName"))
        assertThat(element.elementNode("ns", "mySecondName")).isNull()
    }

    @Test fun testElementNodesWithNamespaceAndNameFilter() {
        val element = mutableElementOf("myElement", mutableListOf(mutableElementOf("ns", "myName")))

        assertThat(element.elementNodes("ns", "myName")).containsOnly(mutableElementOf("ns", "myName"))
        assertThat(element.elementNodes("ns", "mySecondName")).isEmpty()
    }

    @Test fun testElementNodeWithNameFilter() {
        val element = mutableElementOf("myElement", mutableListOf(mutableElementOf("ns", "myName")))

        assertThat(element.elementNode("myName")).isEqualTo(mutableElementOf("ns", "myName"))
        assertThat(element.elementNode("mySecondName")).isNull()
    }

    @Test fun testElementNodesWithNameFilter() {
        val element = mutableElementOf("myElement", mutableListOf(mutableElementOf("ns", "myName")))

        assertThat(element.elementNodes("myName")).containsOnly(mutableElementOf("ns", "myName"))
        assertThat(element.elementNodes("mySecondName")).isEmpty()
    }

    @Test fun testImmutable() {
        val element = mutableElementOf("myNamespace", "myElement", mutableListOf(mutableAttributeOf("myName", "myValue")))
        val immutable = element.toImmutable()

        assertThat(immutable.namespace).isEqualTo("myNamespace")
        assertThat(immutable.name).isEqualTo("myElement")
        assertThat(immutable.nodes).isEqualTo(listOf(attributeOf("myName", "myValue")))
    }

    @Test fun testToString() {
        val element = mutableElementOf("myElement", mutableListOf(mutableAttributeOf("myName", "myValue")))

        assertThat(
            element.toString(),
        ).isEqualTo("MutableElement(namespace=, name=myElement, nodes=[MutableAttribute(namespace=, name=myName, value=myValue)])")
    }

    @Test fun testHashCode() {
        val element = mutableElementOf("myElement", mutableListOf(mutableAttributeOf("myName", "myValue")))

        assertThat(element.hashCode()).isEqualTo(Objects.hash("", "myElement", mutableListOf(mutableAttributeOf("myName", "myValue"))))
    }

    @Test fun testEquals() {
        val element1 = mutableElementOf("myElement", mutableListOf(mutableAttributeOf("myName", "myValue")))
        val element2 = mutableElementOf("myElement", mutableListOf(mutableAttributeOf("myName", "myValue")))
        val element3 = mutableElementOf("myElement", mutableListOf(mutableAttributeOf("alternativeName", "myValue")))
        val element4 = mutableElementOf("ns", "myElement", mutableListOf(mutableAttributeOf("alternativeName", "myValue")))
        val element5 = mutableElementOf("alternativeElement", mutableListOf(mutableAttributeOf("myName", "myValue")))

        assertThat(element1).isEqualTo(element1)
        assertThat(element1 == element2).isTrue
        assertThat(element1 == element3).isFalse
        assertThat(element1 == element4).isFalse
        assertThat(element1 == element5).isFalse
        assertThat(element1.equals(null)).isFalse
        assertThat(element1.equals("")).isFalse
    }

    @Test fun testSetters() {
        val element = mutableElementOf("myNamespace", "myName")
        element.namespace = "newNamespace"
        element.name = "newName"
        element.nodes = mutableListOf(mutableTextOf("myText"))

        assertThat(element.namespace).isEqualTo("newNamespace")
        assertThat(element.name).isEqualTo("newName")
        assertThat(element.nodes).containsOnly(mutableTextOf("myText"))
    }

    @Test fun testClearNodes() {
        val element = mutableElementOf("myNamespace", "myName", mutableListOf(mutableTextOf("myText")))
        element.clearNodes()

        assertThat(element.nodes).isEmpty()
    }

    @Test fun testAddText() {
        val element = mutableElementOf("myNamespace", "myName")
        element.addText("myText")

        assertThat(element.nodes).containsOnly(mutableTextOf("myText"))
    }

    @Test fun testAddCData() {
        val element = mutableElementOf("myNamespace", "myName")
        element.addCData("myText")

        assertThat(element.nodes).containsOnly(mutableCDataOf("myText"))
    }

    @Test fun testAddComment() {
        val element = mutableElementOf("myNamespace", "myName")
        element.addComment("myText")

        assertThat(element.nodes).containsOnly(mutableCommentOf("myText"))
    }

    @Test fun testAddAttribute() {
        val element = mutableElementOf("myNamespace", "myName")
        element.addAttribute("myNamespace", "myText1", "myValue")
        element.addAttribute("myText1", "myValue")

        assertThat(element.nodes).containsOnly(
            mutableAttributeOf("myNamespace", "myText1", "myValue"),
            mutableAttributeOf("myText1", "myValue"),
        )
    }

    @Test fun testAddElementWithName() {
        val element = mutableElementOf("myNamespace", "myName")
        element.addElement("subName")

        assertThat(element.nodes).containsOnly(
            mutableElementOf("subName"),
        )
    }

    @Test fun testAddElementWithNamespaceAndName() {
        val element = mutableElementOf("myNamespace", "myName")
        element.addElement("subNamespace", "subName")

        assertThat(element.nodes).containsOnly(
            mutableElementOf("subNamespace", "subName"),
        )
    }

    @Test fun testAddElementWithNameAndNodes() {
        val element = mutableElementOf("myNamespace", "myName")
        element.addElement("subName", mutableListOf(mutableTextOf("myText")))

        assertThat(element.nodes).containsOnly(
            mutableElementOf("subName", mutableListOf(mutableTextOf("myText"))),
        )
    }

    @Test fun testAddElementWithNamespaceAndNameAndNodes() {
        val element = mutableElementOf("myNamespace", "myName")
        element.addElement("subNamespace", "subName", mutableListOf(mutableTextOf("myText")))

        assertThat(element.nodes).containsOnly(
            mutableElementOf("subNamespace", "subName", mutableListOf(mutableTextOf("myText"))),
        )
    }

    @Test fun testAddElementWithNameAndInit() {
        val element = mutableElementOf("myNamespace", "myName")
        element.addElement("subName") {
            +"myText"
        }

        assertThat(element.nodes).containsOnly(
            mutableElementOf("subName", mutableListOf(mutableTextOf("myText"))),
        )
    }

    @Test fun testAddElementWithNamespaceAndNameAndInit() {
        val element = mutableElementOf("myNamespace", "myName")
        element.addElement("subNamespace", "subName") {
            +"myText"
        }

        assertThat(element.nodes).containsOnly(
            mutableElementOf("subNamespace", "subName", mutableListOf(mutableTextOf("myText"))),
        )
    }

    @Test fun testAddElementWithNamespaceAndNameAndNodesAndInit() {
        val element = mutableElementOf("myNamespace", "myName")
        element.addElement("subNamespace", "subName", mutableListOf()) {
            +"myText"
        }

        assertThat(element.nodes).containsOnly(
            mutableElementOf("subNamespace", "subName", mutableListOf(mutableTextOf("myText"))),
        )
    }
}
