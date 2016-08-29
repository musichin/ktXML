package com.github.musichin.ktxml

import org.fest.assertions.api.Assertions.assertThat
import org.junit.Test

class CDataTest {
    @Test fun testCDataOf() {
        val text = cdataOf("myCData")

        assertThat(text.text).isEqualTo("myCData")
    }

    @Test fun testToCData() {
        val text = "myCData".toCData()

        assertThat(text.text).isEqualTo("myCData")
    }
}