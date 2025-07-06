package com.github.musichin.ktxml

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CDataTest {
    @Test fun testTextOf() {
        val text = cdataOf("myText")

        assertThat(text.text).isEqualTo("myText")
    }

    @Test fun testToCData() {
        val text = "myText".toCData()

        assertThat(text.text).isEqualTo("myText")
    }

    @Test fun testComponents() {
        val (text) = "myText".toCData()

        assertThat(text).isEqualTo("myText")
    }

    @Test fun testHashCode() {
        val text = "myText".toCData()

        assertThat(text.hashCode()).isEqualTo("myText".hashCode())
    }

    @Test fun testEquals() {
        val text1 = "myText".toCData()
        val text2 = "myText".toCData()
        val text3 = "myData".toCData()

        assertThat(text1).isEqualTo(text1)
        assertThat(text1 == text2).isTrue
        assertThat(text1 == text3).isFalse
        assertThat(text1.equals(null)).isFalse
    }

    @Test fun testToMutable() {
        val text = "myText".toCData()
        val mutableText = text.toMutable()

        assertThat(text == mutableText).isFalse
        assertThat(text.text == mutableText.text).isTrue
    }

    @Test fun testToString() {
        val text = "myText".toCData()

        assertThat(text.toString()).isEqualTo("CData(text=myText)")
    }
}
