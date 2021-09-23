package com.github.musichin.ktxml

import org.fest.assertions.api.Assertions.assertThat
import org.junit.Test

class CommentTest {
    @Test fun testCommentOf() {
        val element = commentOf("myComment")

        assertThat(element.comment).isEqualTo("myComment")
    }
}
