package com.github.musichin.ktxml

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class MutableCDataTest {
    @Test fun testMutableTextOf() {
        val text = mutableCDataOf("myText")

        assertThat(text.text).isEqualTo("myText")
    }

    @Test fun testToMutableCData() {
        val text = "myText".toMutableCData()

        assertThat(text.text).isEqualTo("myText")
    }

    @Test fun testComponents() {
        val (text) = "myText".toMutableCData()

        assertThat(text).isEqualTo("myText")
    }

    @Test fun testHashCode() {
        val text = "myText".toMutableCData()

        assertThat(text.hashCode()).isEqualTo("myText".hashCode())
    }

    @Test fun testEquals() {
        val text1 = "myText".toMutableCData()
        val text2 = "myText".toMutableCData()
        val text3 = "myData".toMutableCData()

        assertThat(text1).isEqualTo(text1)
        assertThat(text1 == text2).isTrue
        assertThat(text1 == text3).isFalse
        assertThat(text1.equals(null)).isFalse
    }

    @Test fun testToImmutable() {
        val text = "myText".toMutableCData()
        val immutableText = text.toImmutable()

        assertThat(text == immutableText).isFalse
        assertThat(text.text == immutableText.text).isTrue
    }

    @Test fun testToString() {
        val text = "myText".toMutableCData()

        assertThat(text.toString()).isEqualTo("MutableCData(text=myText)")
    }

    @Test fun testAppend() {
        val text = "my".toMutableCData().apply { append("Text") }

        assertThat(text.text).isEqualTo("myText")
    }
}
