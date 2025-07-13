package de.musichin.ktxml

import org.assertj.core.api.Assertions.assertThat
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

    @Test fun testComponents() {
        val (text) = "myText".toMutableText()

        assertThat(text).isEqualTo("myText")
    }

    @Test fun testHashCode() {
        val text = "myText".toMutableText()

        assertThat(text.hashCode()).isEqualTo("myText".hashCode())
    }

    @Test fun testEquals() {
        val text1 = "myText".toMutableText()
        val text2 = "myText".toMutableText()
        val text3 = "myData".toMutableText()

        assertThat(text1).isEqualTo(text1)
        assertThat(text1 == text2).isTrue
        assertThat(text1 == text3).isFalse
        assertThat(text1.equals(null)).isFalse
    }

    @Test fun testToImmutable() {
        val text = "myText".toMutableText()
        val immutableText = text.toImmutable()

        assertThat(text == immutableText).isFalse
        assertThat(text.text == immutableText.text).isTrue
    }

    @Test fun testToString() {
        val text = "myText".toMutableText()

        assertThat(text.toString()).isEqualTo("MutableText(text=myText)")
    }

    @Test fun testAppend() {
        val text = "my".toMutableText().apply { append("Text") }

        assertThat(text.text).isEqualTo("myText")
    }
}
