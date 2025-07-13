package de.musichin.ktxml

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CommentTest {
    @Test fun testTextOf() {
        val text = commentOf("myText")

        assertThat(text.comment).isEqualTo("myText")
    }

    @Test fun testToComment() {
        val text = "myText".toComment()

        assertThat(text.comment).isEqualTo("myText")
    }

    @Test fun testComponents() {
        val (text) = "myText".toComment()

        assertThat(text).isEqualTo("myText")
    }

    @Test fun testHashCode() {
        val text = "myText".toComment()

        assertThat(text.hashCode()).isEqualTo("myText".hashCode())
    }

    @Test fun testEquals() {
        val text1 = "myText".toComment()
        val text2 = "myText".toComment()
        val text3 = "myData".toComment()

        assertThat(text1).isEqualTo(text1)
        assertThat(text1 == text2).isTrue
        assertThat(text1 == text3).isFalse
        assertThat(text1.equals(null)).isFalse
    }

    @Test fun testToMutable() {
        val text = "myText".toComment()
        val mutableText = text.toMutable()

        assertThat(text == mutableText).isFalse
        assertThat(text.comment == mutableText.comment).isTrue
    }

    @Test fun testToString() {
        val text = "myText".toComment()

        assertThat(text.toString()).isEqualTo("Comment(comment=myText)")
    }
}
