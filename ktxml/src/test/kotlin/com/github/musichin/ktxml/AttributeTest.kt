package com.github.musichin.ktxml

import org.fest.assertions.api.Assertions.assertThat
import org.junit.Test

class AttributeTest {
    @Test fun testOfWithNamespace() {
        val attribute = attributeOf("myNamespace", "myName", "myValue")

        assertThat(attribute.namespace).isEqualTo("myNamespace")
        assertThat(attribute.name).isEqualTo("myName")
        assertThat(attribute.value).isEqualTo("myValue")
    }

    @Test fun testOfWithoutNamespace() {
        val attribute = attributeOf("myName", "myValue")

        assertThat(attribute.namespace).isNull()
        assertThat(attribute.name).isEqualTo("myName")
        assertThat(attribute.value).isEqualTo("myValue")
    }

    @Test fun testMutable() {
        val attribute = attributeOf("myName", "myValue")
        val mutableAttribute = attribute.mutable()

        assertThat(mutableAttribute.namespace).isEqualTo(attribute.namespace)
        assertThat(mutableAttribute.name).isEqualTo(attribute.name)
        assertThat(mutableAttribute.value).isEqualTo(attribute.value)
    }
}