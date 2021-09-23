package com.github.musichin.ktxml

import org.fest.assertions.api.Assertions.assertThat
import org.junit.Test

class MutableCDataTest {
    @Test fun testBuild() {
        val mutableElement = mutableCDataOf("myData")

        assertThat(mutableElement.text).isEqualTo("myData")
    }
}
