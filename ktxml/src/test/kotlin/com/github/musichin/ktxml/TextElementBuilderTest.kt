package com.github.musichin.ktxml

import org.fest.assertions.api.Assertions.assertThat
import org.junit.Test

class TextElementBuilderTest {
    @Test fun testBuild() {
        val cdataElement = TextElement.Builder().text("myText").build()

        assertThat(cdataElement.text).isEqualTo("myText")
    }

    @Test(expected = NullPointerException::class) fun testBuildThrowsExceptionWhenTextIsNull() {
        TextElement.Builder().build()
    }
}