package de.musichin.ktxml

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ElementMarkupTest {
    @Test fun testWithName() {
        val element = ElementMarkup.build(name = "myName", nodes = null) {}

        assertThat(element.namespace).isEqualTo("")
        assertThat(element.name).isEqualTo("myName")
    }

    @Test fun testWithNamespaceAndName() {
        val element = ElementMarkup.build("myNamespace", "myName") {}

        assertThat(element.namespace).isEqualTo("myNamespace")
        assertThat(element.name).isEqualTo("myName")
    }

    @Test fun testWithNamespaceAndNameAndNodes() {
        val element = ElementMarkup.build("myNamespace", "myName", listOf()) {}

        assertThat(element.namespace).isEqualTo("myNamespace")
        assertThat(element.name).isEqualTo("myName")
    }

    @Test fun testFunctions() {
        val element =
            ElementMarkup.build("ns", "books") {
                element("book1") {
                    +"val1"
                }
                element("book2") {
                    -"val2"
                }
                element("book3") {
                    cdata("val3")
                }
                element("book4") {
                    attribute("key4", "val4")
                }
                element(namespace, "book5") {
                    attribute(namespace, "key5", "val5")
                }
                node(elementOf("book6"))
                element("book7")
                element(namespace, "book8")
                element(namespace, "book9", listOf(textOf("val9")))
                element(namespace, "book10", listOf(textOf("val10"))) {
                    +("key10" to "val10")
                }
                element("book11", listOf(textOf("val11"))) {
                    +Triple("ns", "key11", "val11")
                }
                "book12" {
                }
            }

        assertThat(element).isEqualTo(
            elementOf(
                "ns",
                "books",
                listOf(
                    elementOf("book1", listOf(textOf("val1"))),
                    elementOf("book2", listOf(commentOf("val2"))),
                    elementOf("book3", listOf(cdataOf("val3"))),
                    elementOf("book4", listOf(attributeOf("key4", "val4"))),
                    elementOf("ns", "book5", listOf(attributeOf("ns", "key5", "val5"))),
                    elementOf("book6"),
                    elementOf("book7"),
                    elementOf("ns", "book8"),
                    elementOf("ns", "book9", listOf(textOf("val9"))),
                    elementOf("ns", "book10", listOf(textOf("val10"), attributeOf("key10", "val10"))),
                    elementOf("book11", listOf(textOf("val11"), attributeOf("ns", "key11", "val11"))),
                    elementOf("book12"),
                ),
            ),
        )
    }
}
