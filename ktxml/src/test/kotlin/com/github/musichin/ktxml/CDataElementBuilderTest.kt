package com.github.musichin.ktxml

import org.fest.assertions.api.Assertions.assertThat
import org.junit.Test

class CDataElementBuilderTest {
    @Test fun testBuild() {
        val cdataElement = CDataElement.Builder().text("myData").build()

        assertThat(cdataElement.text).isEqualTo("myData")
    }

    @Test(expected = NullPointerException::class) fun testBuildThrowsExceptionWhenDataIsNull() {
        CDataElement.Builder().build()
    }
}