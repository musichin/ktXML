package de.musichin.ktxml.pull

import de.musichin.ktxml.elementOf
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.xmlpull.v1.XmlPullParserFactory
import java.io.ByteArrayOutputStream
import java.io.StringWriter

class SerializerTest {
    @Test fun testStringSerialization() {
        val element =
            elementOf("book") {
                text("my first book")
            }.serialize()

        assertThat(element).contains("<book>my first book</book>")
    }

    @Test fun testOutputStreamSerialization() {
        val stream = ByteArrayOutputStream()
        elementOf("book") {
            text("my first book")
        }.serialize(stream)

        assertThat(stream.toString("UTF-8")).contains("<book>my first book</book>")
    }

    @Test fun testWriterSerialization() {
        val writer = StringWriter()
        elementOf("book") {
            text("my first book")
        }.serialize(writer)

        assertThat(writer.toString()).contains("<book>my first book</book>")
    }

    @Test fun testSerializerSerialization() {
        val writer = StringWriter()
        val serializer = XmlPullParserFactory.newInstance().newSerializer().apply { setOutput(writer) }
        elementOf("book") {
            text("my first book")
        }.serialize(serializer)

        assertThat(writer.toString()).contains("<book>my first book</book>")
    }

    @Test fun testElementWithNamespaceAndText() {
        val element =
            elementOf("booksNs", "book") {
                text("my first book")
            }.serialize()

        assertThat(element).contains("=\"booksNs\">my first book</")
    }

    @Test fun testElementWithAttribute() {
        val element =
            elementOf("booksNs", "book") {
                attribute("title", "MyFirstBook")
            }.serialize()

        assertThat(element).contains("title=\"MyFirstBook\"")
    }

    @Test fun testElementWithCData() {
        val element =
            elementOf("booksNs", "book") {
                cdata("my first book")
            }.serialize()

        assertThat(element).contains("=\"booksNs\"><![CDATA[my first book]]></")
    }

    @Test fun testElementWithComment() {
        val element =
            elementOf("booksNs", "book") {
                comment("my first book")
            }.serialize()

        assertThat(element).contains("<!--my first book-->")
    }

    @Test fun testComplexXml() {
        val serialized =
            elementOf("booksNs", "books") {
                element("book")
                for (i in 0..9) {
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

    @Test fun testComplexXmlWithNamespacePrefix() {
        val serialized =
            elementOf("books-ns", "books") {
                element("book")
                for (i in 0..9) {
                    element("book-ns-${i % 2}", "book") {
                        attribute("index", i.toString())
                        text("title $i")
                    }
                }
            }

        val namespaces =
            listOf(
                ("" to "books-ns"),
                ("b0" to "book-ns-0"),
                ("b1" to "book-ns-1"),
            )
        val str = serialized.serialize(namespaces)

        val deserialized = str.deserialize()

        assertThat(deserialized).isEqualTo(serialized)
    }

    @Test fun testStandaloneTrue() {
        val serialized = elementOf("books")

        val str = serialized.serialize(standalone = true)

        assertThat(str).contains("standalone='yes'")
    }

    @Test fun testStandaloneFalse() {
        val serialized = elementOf("books")

        val str = serialized.serialize(standalone = false)

        assertThat(str).contains("standalone='no'")
    }

    @Test fun testStandaloneNull() {
        val serialized = elementOf("books")

        val str = serialized.serialize(standalone = null)

        assertThat(str).doesNotContain("standalone")
    }

    @Test fun testEncoding() {
        val serialized = elementOf("books")

        val str = serialized.serialize(encoding = "US-ASCII")

        assertThat(str).contains("encoding='US-ASCII'")
    }
}
