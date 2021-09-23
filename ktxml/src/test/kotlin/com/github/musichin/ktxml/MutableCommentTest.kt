package com.github.musichin.ktxml

import org.fest.assertions.api.Assertions.assertThat
import org.junit.Test

class MutableCommentTest {
    @Test fun testMutableCommentOf() {
        val mutableElement = mutableCommentOf("myComment")

        assertThat(mutableElement.comment).isEqualTo("myComment")
    }
}
