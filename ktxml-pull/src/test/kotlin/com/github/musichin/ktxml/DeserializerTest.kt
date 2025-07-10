package com.github.musichin.ktxml

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader

class DeserializerTest {
    @Test(expected = Throwable::class)
    fun testEmptyString() {
        "".deserialize()
    }

    @Test fun testStringDeserialization() {
        val element = "<book>some-text</book>".deserialize()

        assertThat(element.name).isEqualTo("book")
        assertThat(element.textNode()?.text).isEqualTo("some-text")
    }

    @Test fun testInputStreamDeserialization() {
        val element = "<book>some-text</book>".byteInputStream().deserialize()

        assertThat(element.name).isEqualTo("book")
        assertThat(element.textNode()?.text).isEqualTo("some-text")
    }

    @Test fun testReaderDeserialization() {
        val element = StringReader("<book>some-text</book>").deserialize()

        assertThat(element.name).isEqualTo("book")
        assertThat(element.textNode()?.text).isEqualTo("some-text")
    }

    @Test fun testXmlPullParserDeserialization() {
        val stream = "<book>some-text</book>".byteInputStream()
        val parser = XmlPullParserFactory.newInstance().newPullParser()
        parser.setInput(stream, "UTF-8")
        val element = parser.deserialize()

        assertThat(element.name).isEqualTo("book")
        assertThat(element.textNode()?.text).isEqualTo("some-text")
    }

    @Test fun testEmptyClosingTag() {
        val element = "<book/>".deserialize()

        assertThat(element.name).isEqualTo("book")
    }

    @Test fun testEmptyTag() {
        val element = "<book></book>".deserialize()

        assertThat(element.name).isEqualTo("book")
    }

    @Test fun testEmptyTagWithWhitespaces() {
        val element = "<book>   \t  </book>".deserialize()

        assertThat(element.name).isEqualTo("book")
    }

    @Test fun testElementWithText() {
        val element = "<book>The History of this Library</book>".deserialize()

        assertThat(element.name).isEqualTo("book")
        assertThat(element.textNode()?.text).isEqualTo("The History of this Library")
    }

    @Test fun testElementWithCData() {
        val element = "<book><![CDATA[The History of this Library]]></book>".deserialize()

        assertThat(element.name).isEqualTo("book")
        assertThat(element.textNode()?.text).isEqualTo("The History of this Library")
    }

    @Test fun testElementWithTextAndElement() {
        val element = "<book>The History of <b>this</b> Library</book>".deserialize()

        assertThat(element.name).isEqualTo("book")
        assertThat(element.size).isEqualTo(3)
        assertThat(element.textNode()?.text).isEqualTo("The History of ")
        assertThat(element[0]).isInstanceOf(Text::class.java)
        assertThat(element[1]).isInstanceOf(Element::class.java)
        assertThat(element[2]).isInstanceOf(Text::class.java)
    }

    @Test fun testTagWithNamespace() {
        val element = "<book xmlns=\"my namespace\">My Book</book>".deserialize()

        assertThat(element.name).isEqualTo("book")
        assertThat(element.namespace).isEqualTo("my namespace")
    }

    @Test fun testTagAttributes() {
        val element = "<book myAttribute=\"3\">My Book</book>".deserialize()

        assertThat(element.attributeNodes()).hasSize(1).contains(attributeOf("myAttribute", "3"))
    }

    @Test fun testTagAttributesWithNamespace() {
        val element = "<book xmlns:mns=\"my-namespace\" mns:myAttribute=\"3\">My Book</book>".deserialize()

        assertThat(element.attributeNodes()).hasSize(1).contains(attributeOf("my-namespace", "myAttribute", "3"))
    }
}
