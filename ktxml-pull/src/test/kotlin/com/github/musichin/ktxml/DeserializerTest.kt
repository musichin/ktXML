package com.github.musichin.ktxml

import org.fest.assertions.api.Assertions.assertThat

import org.junit.Test

class DeserializerTest {
    @Test(expected = Throwable::class)
    fun testEmptyString() {
        "".deserialize()
    }

    @Test
    fun testEmptyTag() {
        val element = "<book></book>".deserialize()

        assertThat(element.name).isEqualTo("book")
    }

    @Test
    fun testTagWithText() {
        val element = "<book>The History of this Library</book>".deserialize()

        assertThat(element.name).isEqualTo("book")
        assertThat(element.text()).isEqualTo("The History of this Library")
    }

    @Test
    fun testTagWithTextAndElement() {
        val element = "<book>The History of <b>this</b> Library</book>".deserialize()

        assertThat(element.name).isEqualTo("book")
        assertThat(element.size).isEqualTo(3)
        assertThat(element.text()).isEqualTo("The History of ")
        assertThat(element[0]).isInstanceOf(Text::class.java)
        assertThat(element[1]).isInstanceOf(Element::class.java)
        assertThat(element[2]).isInstanceOf(Text::class.java)
    }

    @Test
    fun testTagWithNamespace() {
        val element = "<book xmlns=\"my namespace\">My Book</book>".deserialize()

        assertThat(element.name).isEqualTo("book")
        assertThat(element.namespace).isEqualTo("my namespace")
    }

    @Test
    fun testTagAttributes() {
        val element = "<book myAttribute=\"3\">My Book</book>".deserialize()

        assertThat(element.attributes).hasSize(1).contains(attributeOf("myAttribute", "3"))
    }
}