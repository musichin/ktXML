package de.musichin.ktxml

import org.assertj.core.api.Assertions.assertThat
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

    @Test fun testComponents() {
        val (text) = "myText".toText()

        assertThat(text).isEqualTo("myText")
    }

    @Test fun testHashCode() {
        val text = "myText".toText()

        assertThat(text.hashCode()).isEqualTo("myText".hashCode())
    }

    @Test fun testEquals() {
        val text1 = "myText".toText()
        val text2 = "myText".toText()
        val text3 = "myData".toText()

        assertThat(text1).isEqualTo(text1)
        assertThat(text1 == text2).isTrue
        assertThat(text1 == text3).isFalse
        assertThat(text1.equals(null)).isFalse
    }

    @Test fun testToMutable() {
        val text = "myText".toText()
        val mutableText = text.toMutable()

        assertThat(text == mutableText).isFalse
        assertThat(text.text == mutableText.text).isTrue
    }

    @Test fun testToString() {
        val text = "myText".toText()

        assertThat(text.toString()).isEqualTo("Text(text=myText)")
    }
}
