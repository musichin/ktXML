package com.github.musichin.ktxml

import org.fest.assertions.api.Assertions.assertThat
import org.junit.Test

class TextTest {
    @Test fun testTextOf() {
        val text = textOf("myText")

        assertThat(text.text).isEqualTo("myText")
    }

    @Test fun testToText() {
        val text = "myText".toText()

        assertThat(text.text).isEqualTo("myText")
    }
}
