package com.github.musichin.ktxml

import org.fest.assertions.api.Assertions.assertThat
import org.junit.Test

class MutableAttributeTest {

    @Test fun testOfWithNamespace() {
        val attribute = mutableAttributeOf("myNamespace", "myName", "myValue")

        assertThat(attribute.namespace).isEqualTo("myNamespace")
        assertThat(attribute.name).isEqualTo("myName")
        assertThat(attribute.value).isEqualTo("myValue")
    }

    @Test fun testOfWithoutNamespace() {
        val attribute = mutableAttributeOf("myName", "myValue")

        assertThat(attribute.namespace).isNull()
        assertThat(attribute.name).isEqualTo("myName")
        assertThat(attribute.value).isEqualTo("myValue")
    }

    @Test fun testImmutable() {
        val mutableAttribute = mutableAttributeOf("myNamespace", "myName", "myValue")
        val attribute = mutableAttribute.immutable()

        assertThat(attribute.namespace).isEqualTo(mutableAttribute.namespace)
        assertThat(attribute.name).isEqualTo(mutableAttribute.name)
        assertThat(attribute.value).isEqualTo(mutableAttribute.value)
    }
}
