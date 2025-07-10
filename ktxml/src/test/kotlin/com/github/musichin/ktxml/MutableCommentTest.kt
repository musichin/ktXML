package com.github.musichin.ktxml

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class MutableCommentTest {
    @Test fun testMutableTextOf() {
        val comment = mutableCommentOf("myText")

        assertThat(comment.comment).isEqualTo("myText")
    }

    @Test fun testToMutableComment() {
        val comment = "myText".toMutableComment()

        assertThat(comment.comment).isEqualTo("myText")
    }

    @Test fun testComponents() {
        val (text) = "myText".toMutableComment()

        assertThat(text).isEqualTo("myText")
    }

    @Test fun testHashCode() {
        val comment = "myText".toMutableComment()

        assertThat(comment.hashCode()).isEqualTo("myText".hashCode())
    }

    @Test fun testEquals() {
        val comment1 = "myText".toMutableComment()
        val comment2 = "myText".toMutableComment()
        val comment3 = "myData".toMutableComment()

        assertThat(comment1).isEqualTo(comment1)
        assertThat(comment1 == comment2).isTrue
        assertThat(comment1 == comment3).isFalse
        assertThat(comment1.equals(null)).isFalse
    }

    @Test fun testToImmutable() {
        val comment = "myText".toMutableComment()
        val immutableComment = comment.toImmutable()

        assertThat(comment == immutableComment).isFalse
        assertThat(comment.comment == immutableComment.comment).isTrue
    }

    @Test fun testToString() {
        val comment = "myText".toMutableComment()

        assertThat(comment.toString()).isEqualTo("MutableComment(comment=myText)")
    }

    @Test fun testAppend() {
        val comment = "my".toMutableComment().apply { append("Text") }

        assertThat(comment.comment).isEqualTo("myText")
    }
}
