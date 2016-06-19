package com.github.musichin.ktxml

import org.fest.assertions.api.Assertions.assertThat
import org.junit.Test
/*
class ElementMarkupBuilderTest {
    @Test fun testEmptyElement() {
        val element = elementOf("myNamespace", "myName")

        assertThat(element.namespace()).isEqualTo("myNamespace")
        assertThat(element.name()).isEqualTo("myName")
    }

    @Test fun testEmptyElementWithAttribute() {
        val element = elementOf("myNamespace", "myName") {
            attribute("myAttribute", "myAttributeValue")
        }

        assertThat(element.attribute("myAttribute")).isEqualTo("myAttributeValue")
    }

    @Test fun testEmptyElementWithoutNamespace() {
        val element = elementOf("myName")

        assertThat(element.namespace()).isNull()
        assertThat(element.name()).isEqualTo("myName")
    }

    @Test fun testElementWithText() {
        val element = elementOf("myNamespace", "myName") {
            text("myText")
        }

        assertThat(element.text()).isEqualTo("myText")
    }

    @Test fun testElementWithCData() {
        val element = elementOf("myNamespace", "myName") {
            cdata("myData")
        }

        assertThat(element.cdata()).isEqualTo("myData")
    }

    @Test fun testElementWithComment() {
        val element = elementOf("myNamespace", "myName") {
            comment("myComment")
        }

        assertThat(element.comment()).isEqualTo("myComment")
    }

    @Test fun testElementWithInstantiatedElement() {
        val nested = elementOf("nested") {
            text("myText")
        }

        val element = elementOf("myNamespace", "myName") {
            element(nested)
        }

        assertThat(element.element("myNamespace", "nested")?.text()).isEqualTo("myText")
    }

    @Test fun testElementWithInstantiatedAttribute() {
        val attr = Attribute("key", "value")

        val element = elementOf("myNamespace", "myName") {
            attribute(attr)
        }

        assertThat(element.attribute("myNamespace", "key")).isEqualTo("value")
    }

    @Test fun testNestedElementsHaveSameNamespaceAsFallback() {
        val element = elementOf("myNamespace", "myName") {
            element("nested") {
            }
        }

        assertThat(element.namespace()).isEqualTo("myNamespace")
        assertThat(element.element("nested")?.namespace()).isEqualTo("myNamespace")
    }

    @Test fun testNestedElementsHaveDifferentNamespacesIfSpecified() {
        val element = elementOf("myNamespace", "myName") {
            element("myNestedNamespace", "nested") {
            }
        }

        assertThat(element.namespace()).isEqualTo("myNamespace")
        assertThat(element.element("nested")?.namespace()).isEqualTo("myNestedNamespace")
    }

    @Test fun testAttributesHaveSameNamespaceAsFallback() {
        val element = elementOf("myNamespace", "myName") {
            attribute("myKey", "myValue")
        }

        assertThat(element.namespace()).isEqualTo("myNamespace")
        assertThat(element.attribute("myKey")).isEqualTo("myValue")
        assertThat(element.attribute("myNamespace", "myKey")).isEqualTo("myValue")
        assertThat(element.attribute("wrongNamespace", "myKey")).isNull()
    }

    @Test fun testAttributesHaveDifferentNamespacesIfSpecified() {
        val element = elementOf("myNamespace", "myName") {
            attribute("myNestedNamespace", "myKey", "myValue")
        }

        assertThat(element.namespace()).isEqualTo("myNamespace")
        assertThat(element.attribute("myKey")).isEqualTo("myValue")
        assertThat(element.attribute("myNestedNamespace", "myKey")).isEqualTo("myValue")
        assertThat(element.attribute("myNamespace", "myKey")).isNull()
        assertThat(element.attribute("wrongNamespace", "myKey")).isNull()
    }

    @Test fun testNestedElementWithText() {
        val element = elementOf("myNamespace", "myName") {
            element("nested") {
                text("myText")
            }
        }

        assertThat(element.namespace()).isEqualTo("myNamespace")
        assertThat(element.name()).isEqualTo("myName")
        assertThat(element.element("nested")?.text()).isEqualTo("myText")
        assertThat(element.element("myNamespace", "nested")?.text()).isEqualTo("myText")
    }

    @Test fun testNestedElementsWithSameName() {
        val element = elementOf("myNamespace", "myName") {
            for (i in 0..4)
                element("items") {
                    text("item$i")
                }
        }

        assertThat(element.elements("items").map { it.text() }).containsExactly("item0", "item1", "item2", "item3", "item4")
    }

    @Test fun testUnaryPlusStringOperator() {
        val element = elementOf("root") {
            element("textWrapper") {
                +"text"
            }
        }

        assertThat(element.element("textWrapper")?.text()).isEqualTo("text")
    }

    @Test fun testUnaryPlusPairOperator() {
        val element = elementOf("root") {
            +("myKey"  to "myValue")
        }

        assertThat(element.attribute("myKey")).isEqualTo("myValue")
    }

    @Test fun testElementBuilderOfWithoutNamespace() {
        val builder = elementBuilderOf("root") {
            text("innerText")
        }

        assertThat(builder.build().text()).isEqualTo("innerText")

    }

    @Test fun testElementBuilderOfWithNamespace() {
        val builder = elementBuilderOf("myNamespace", "root") {
            text("innerText")
        }

        assertThat(builder.build().text()).isEqualTo("innerText")
        assertThat(builder.build().namespace()).isEqualTo("myNamespace")
    }

    @Test fun testElementBuilderOfWithNullNamespace() {
        val builder = elementBuilderOf(null, "root")

        assertThat(builder.build().namespace()).isNull()
        assertThat(builder.build().elements()).isEmpty()
    }

    @Test fun testMarkupBuilderConstructor() {
        val builder = Element.MarkupBuilder("root") {
            element("nested") {
                text("innerText")
            }
        }

        assertThat(builder.build().namespace()).isNull()
        assertThat(builder.build().element("nested")?.text()).isEqualTo("innerText")
    }
}*/