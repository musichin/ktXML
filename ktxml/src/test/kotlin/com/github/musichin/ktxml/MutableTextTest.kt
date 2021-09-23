package com.github.musichin.ktxml

import org.fest.assertions.api.Assertions.assertThat
import org.junit.Test

class MutableTextTest {
    @Test fun testMutableTextOf() {
        val text = mutableTextOf("myText")

        assertThat(text.text).isEqualTo("myText")
    }

    @Test fun testToMutableText() {
        val text = "myText".toMutableText()

        assertThat(text.text).isEqualTo("myText")
    }

    @Test fun testAppend() {
        val text = "hello".toMutableText()
        text.append(" world")

        assertThat(text.text).isEqualTo("hello world")
    }
}
