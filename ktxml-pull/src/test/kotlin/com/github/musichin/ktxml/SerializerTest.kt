package com.github.musichin.ktxml

import org.fest.assertions.api.Assertions.assertThat
import org.junit.Test

class SerializerTest {
    @Test
    fun test() {
        val element = elementOf("book") {
            text("my first book")
        }.serialize()

        assertThat(element).contains("<book>my first book</book>")
    }
}