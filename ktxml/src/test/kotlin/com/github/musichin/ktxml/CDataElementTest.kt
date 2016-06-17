package com.github.musichin.ktxml

import org.fest.assertions.api.Assertions.assertThat
import org.junit.Test

class CDataElementTest {
    @Test fun testNewBuilder() {
        val cdataElement = CDataElement("myData").newBuilder().build()

        assertThat(cdataElement.text).isEqualTo("myData")
    }
}