package com.github.musichin.ktxml

import org.fest.assertions.api.Assertions.assertThat
import org.junit.Test

class SerializerTest {
    @Test fun testElementWithText() {
        val element = elementOf("book") {
            text("my first book")
        }.serialize()

        assertThat(element).contains("<book>my first book</book>")
    }

    @Test fun testElementWithNamespaceAndText() {
        val element = elementOf("booksNs", "book") {
            text("my first book")
        }.serialize()

        assertThat(element).contains("=\"booksNs\">my first book</")
    }

    @Test fun testElementWithAttribute() {
        val element = elementOf("booksNs", "book") {
            attribute("title", "MyFirstBook")
        }.serialize()

        assertThat(element).contains("title=\"MyFirstBook\"")
    }

    @Test fun testElementWithCData() {
        val element = elementOf("booksNs", "book") {
            cdata("my first book")
        }.serialize()

        assertThat(element).contains("=\"booksNs\"><![CDATA[my first book]]></")
    }

    @Test fun testElementWithComment() {
        val element = elementOf("booksNs", "book") {
            comment("my first book")
        }.serialize()

        assertThat(element).contains("><!--my first book--></")
    }

    @Test fun testComplexXml() {
        val serialized = elementOf("booksNs", "book") {
            element("books")
                for(i in 0..9) {
                    element("book") {
                        attribute("index", i.toString())
                        text("title $i")
                    }
                }
        }

        val str = serialized.serialize()

        val deserialized = str.deserialize()

        assertThat(deserialized).isEqualTo(serialized)
    }
}
