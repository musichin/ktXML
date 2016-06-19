package com.github.musichin.ktxml

import org.fest.assertions.api.Assertions.assertThat
import org.junit.Test
/*
class AttributeBuilderTest {

    @Test(expected = NullPointerException::class) fun testBuildThrowsExceptionWhenNameIsNull() {
        Attribute.Builder().namespace("testNamespace").value("testValue").build()
    }

    @Test(expected = NullPointerException::class) fun testBuildThrowsExceptionWhenValueIsNull() {
        Attribute.Builder().namespace("testNamespace").name("testName").build()
    }

    @Test fun testBuildWithoutNamespace() {
        val attribute = Attribute.Builder().name("testName").value("testValue").build()

        assertThat(attribute.name).isEqualTo("testName")
        assertThat(attribute.value).isEqualTo("testValue")
        assertThat(attribute.namespace).isNull()
    }

    @Test fun testBuildWithNamespace() {
        val attribute = Attribute.Builder().namespace("myNamespace").name("testName").value("testValue").build()

        assertThat(attribute.name).isEqualTo("testName")
        assertThat(attribute.value).isEqualTo("testValue")
        assertThat(attribute.namespace).isEqualTo("myNamespace")
    }

    @Test fun testBuildWithFallbackNamespaceWhenNamespaceIsNotSet() {
        val attribute = Attribute.Builder().name("testName").value("testValue").build("myFallbackNamespace")

        assertThat(attribute.name).isEqualTo("testName")
        assertThat(attribute.value).isEqualTo("testValue")
        assertThat(attribute.namespace).isEqualTo("myFallbackNamespace")
    }

    @Test fun testBuildWithFallbackNamespaceWhenNamespaceIsSet() {
        val attribute = Attribute.Builder().namespace("myNamespace").name("testName").value("testValue").build("myFallbackNamespace")

        assertThat(attribute.name).isEqualTo("testName")
        assertThat(attribute.value).isEqualTo("testValue")
        assertThat(attribute.namespace).isEqualTo("myNamespace")
    }
}*/