package com.github.musichin.ktxml

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.Objects

class MutableAttributeTest {
    @Test fun testCompanionMutableAttributeOfWithNamespace() {
        val attribute = MutableAttribute.of("myNamespace", "myName", "myValue")

        assertThat(attribute.namespace).isEqualTo("myNamespace")
        assertThat(attribute.name).isEqualTo("myName")
        assertThat(attribute.value).isEqualTo("myValue")
    }

    @Test fun testCompanionMutableAttributeOfWithoutNamespace() {
        val attribute = MutableAttribute.of("myName", "myValue")

        assertThat(attribute.namespace).isEqualTo("")
        assertThat(attribute.name).isEqualTo("myName")
        assertThat(attribute.value).isEqualTo("myValue")
    }

    @Test fun testMutableAttributeOfWithNamespace() {
        val attribute = mutableAttributeOf("myNamespace", "myName", "myValue")

        assertThat(attribute.namespace).isEqualTo("myNamespace")
        assertThat(attribute.name).isEqualTo("myName")
        assertThat(attribute.value).isEqualTo("myValue")
    }

    @Test fun testMutableAttributeOfWithoutNamespace() {
        val attribute = mutableAttributeOf("myName", "myValue")

        assertThat(attribute.namespace).isEqualTo("")
        assertThat(attribute.name).isEqualTo("myName")
        assertThat(attribute.value).isEqualTo("myValue")
    }

    @Test fun testPairToAttribute() {
        val attribute = ("myName" to "myValue").toMutableAttribute()

        assertThat(attribute.namespace).isEqualTo("")
        assertThat(attribute.name).isEqualTo("myName")
        assertThat(attribute.value).isEqualTo("myValue")
    }

    @Test fun testTripleToAttribute() {
        val attribute = Triple("myNamespace", "myName", "myValue").toMutableAttribute()

        assertThat(attribute.namespace).isEqualTo("myNamespace")
        assertThat(attribute.name).isEqualTo("myName")
        assertThat(attribute.value).isEqualTo("myValue")
    }

    @Test fun testComponents() {
        val (namespace, name, value) = mutableAttributeOf("myNamespace", "myName", "myValue")

        assertThat(namespace).isEqualTo("myNamespace")
        assertThat(name).isEqualTo("myName")
        assertThat(value).isEqualTo("myValue")
    }

    @Test fun testMutable() {
        val attribute = mutableAttributeOf("myName", "myValue")
        val immutableAttribute = attribute.toImmutable()

        assertThat(immutableAttribute.namespace).isEqualTo(attribute.namespace)
        assertThat(immutableAttribute.name).isEqualTo(attribute.name)
        assertThat(immutableAttribute.value).isEqualTo(attribute.value)
    }

    @Test fun testHashCode() {
        val attribute = mutableAttributeOf("myNamespace", "myName", "myValue")

        assertThat(attribute.hashCode()).isEqualTo(Objects.hash("myNamespace", "myName", "myValue"))
    }

    @Test fun testEquals() {
        val attribute1 = mutableAttributeOf("myNamespace", "myName", "myValue")
        val attribute2 = mutableAttributeOf("myNamespace", "myName", "myValue")
        val attribute3 = mutableAttributeOf("myNamespace", "myData", "myValue")
        val attribute4 = mutableAttributeOf("myNamespace", "myName", "myData")

        assertThat(attribute1).isEqualTo(attribute1)
        assertThat(attribute1 == attribute2).isTrue
        assertThat(attribute1 == attribute3).isFalse
        assertThat(attribute1 == attribute4).isFalse
        assertThat(attribute1.equals(null)).isFalse
        assertThat(attribute1.equals("myNamespace")).isFalse
    }

    @Test fun testToString() {
        val attribute = mutableAttributeOf("myNamespace", "myName", "myValue")

        assertThat(attribute.toString()).isEqualTo("MutableAttribute(namespace=myNamespace, name=myName, value=myValue)")
    }

    @Test fun testSetters() {
        val attribute = mutableAttributeOf("myNamespace", "myName", "myValue")
        attribute.namespace = "newNamespace"
        attribute.name = "newName"
        attribute.value = "newValue"

        assertThat(attribute.namespace).isEqualTo("newNamespace")
        assertThat(attribute.name).isEqualTo("newName")
        assertThat(attribute.value).isEqualTo("newValue")
    }
}
