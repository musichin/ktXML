package com.github.musichin.ktxml

import org.fest.assertions.api.Assertions.assertThat
import org.junit.Test

class AttributeTest {
    @Test fun testNewBuilder() {
        val attribute = Attribute("myNamespace", "myName", "myValue").newBuilder().build()

        assertThat(attribute.namespace).isEqualTo("myNamespace")
        assertThat(attribute.name).isEqualTo("myName")
        assertThat(attribute.value).isEqualTo("myValue")
    }
}