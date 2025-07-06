package com.github.musichin.ktxml

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class MutableElementMarkupTest {
    @Test
    fun testWithName() {
        val element = MutableElementMarkup.build(name = "myName", nodes = null) {}

        assertThat(element.namespace).isEqualTo("")
        assertThat(element.name).isEqualTo("myName")
    }

    @Test
    fun testWithNamespaceAndName() {
        val element = MutableElementMarkup.build("myNamespace", "myName") {}

        assertThat(element.namespace).isEqualTo("myNamespace")
        assertThat(element.name).isEqualTo("myName")
    }

    @Test
    fun testWithNamespaceAndNameAndNodes() {
        val element = MutableElementMarkup.build("myNamespace", "myName", mutableListOf()) {}

        assertThat(element.namespace).isEqualTo("myNamespace")
        assertThat(element.name).isEqualTo("myName")
    }

    @Test
    fun testFunctions() {
        val element =
            MutableElementMarkup.build("ns", "books") {
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
                node(mutableElementOf("book6"))
                element("book7")
                element(namespace, "book8")
                element(namespace, "book9", mutableListOf(mutableTextOf("val9")))
                element(namespace, "book10", mutableListOf(mutableTextOf("val10"))) {
                    +("key10" to "val10")
                }
                element("book11", mutableListOf(mutableTextOf("val11"))) {
                    +Triple("ns", "key11", "val11")
                }
                "book12" {
                }
            }

        assertThat(element).isEqualTo(
            mutableElementOf(
                "ns",
                "books",
                mutableListOf(
                    mutableElementOf("book1", mutableListOf(mutableTextOf("val1"))),
                    mutableElementOf("book2", mutableListOf(mutableCommentOf("val2"))),
                    mutableElementOf("book3", mutableListOf(mutableCDataOf("val3"))),
                    mutableElementOf("book4", mutableListOf(mutableAttributeOf("key4", "val4"))),
                    mutableElementOf("ns", "book5", mutableListOf(mutableAttributeOf("ns", "key5", "val5"))),
                    mutableElementOf("book6"),
                    mutableElementOf("book7"),
                    mutableElementOf("ns", "book8"),
                    mutableElementOf("ns", "book9", mutableListOf(mutableTextOf("val9"))),
                    mutableElementOf("ns", "book10", mutableListOf(mutableTextOf("val10"), mutableAttributeOf("key10", "val10"))),
                    mutableElementOf("book11", mutableListOf(mutableTextOf("val11"), mutableAttributeOf("ns", "key11", "val11"))),
                    mutableElementOf("book12"),
                ),
            ),
        )
    }
}
