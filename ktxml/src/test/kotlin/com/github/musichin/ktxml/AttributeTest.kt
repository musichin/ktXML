package com.github.musichin.ktxml

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.Objects

class AttributeTest {
    @Test fun testCompanionAttributeOfWithNamespace() {
        val attribute = Attribute.of("myNamespace", "myName", "myValue")

        assertThat(attribute.namespace).isEqualTo("myNamespace")
        assertThat(attribute.name).isEqualTo("myName")
        assertThat(attribute.value).isEqualTo("myValue")
    }

    @Test fun testCompanionAttributeOfWithoutNamespace() {
        val attribute = Attribute.of("myName", "myValue")

        assertThat(attribute.namespace).isEqualTo("")
        assertThat(attribute.name).isEqualTo("myName")
        assertThat(attribute.value).isEqualTo("myValue")
    }

    @Test fun testAttributeOfWithNamespace() {
        val attribute = attributeOf("myNamespace", "myName", "myValue")

        assertThat(attribute.namespace).isEqualTo("myNamespace")
        assertThat(attribute.name).isEqualTo("myName")
        assertThat(attribute.value).isEqualTo("myValue")
    }

    @Test fun testAttributeOfWithoutNamespace() {
        val attribute = attributeOf("myName", "myValue")

        assertThat(attribute.namespace).isEqualTo("")
        assertThat(attribute.name).isEqualTo("myName")
        assertThat(attribute.value).isEqualTo("myValue")
    }

    @Test fun testPairToAttribute() {
        val attribute = ("myName" to "myValue").toAttribute()

        assertThat(attribute.namespace).isEqualTo("")
        assertThat(attribute.name).isEqualTo("myName")
        assertThat(attribute.value).isEqualTo("myValue")
    }

    @Test fun testTripleToAttribute() {
        val attribute = Triple("myNamespace", "myName", "myValue").toAttribute()

        assertThat(attribute.namespace).isEqualTo("myNamespace")
        assertThat(attribute.name).isEqualTo("myName")
        assertThat(attribute.value).isEqualTo("myValue")
    }

    @Test fun testComponents() {
        val (namespace, name, value) = attributeOf("myNamespace", "myName", "myValue")

        assertThat(namespace).isEqualTo("myNamespace")
        assertThat(name).isEqualTo("myName")
        assertThat(value).isEqualTo("myValue")
    }

    @Test fun testMutable() {
        val attribute = attributeOf("myName", "myValue")
        val mutableAttribute = attribute.toMutable()

        assertThat(mutableAttribute.namespace).isEqualTo(attribute.namespace)
        assertThat(mutableAttribute.name).isEqualTo(attribute.name)
        assertThat(mutableAttribute.value).isEqualTo(attribute.value)
    }

    @Test fun testHashCode() {
        val attribute = attributeOf("myNamespace", "myName", "myValue")

        assertThat(attribute.hashCode()).isEqualTo(Objects.hash("myNamespace", "myName", "myValue"))
    }

    @Test fun testEquals() {
        val attribute1 = attributeOf("myNamespace", "myName", "myValue")
        val attribute2 = attributeOf("myNamespace", "myName", "myValue")
        val attribute3 = attributeOf("myNamespace", "myData", "myValue")
        val attribute4 = attributeOf("myNamespace", "myName", "myData")

        assertThat(attribute1).isEqualTo(attribute1)
        assertThat(attribute1 == attribute2).isTrue
        assertThat(attribute1 == attribute3).isFalse
        assertThat(attribute1 == attribute4).isFalse
        assertThat(attribute1.equals(null)).isFalse
        assertThat(attribute1.equals("myNamespace")).isFalse
    }

    @Test fun testToString() {
        val attribute = attributeOf("myNamespace", "myName", "myValue")

        assertThat(attribute.toString()).isEqualTo("Attribute(namespace=myNamespace, name=myName, value=myValue)")
    }
}
