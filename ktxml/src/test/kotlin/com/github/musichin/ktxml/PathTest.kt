package com.github.musichin.ktxml

import org.fest.assertions.api.Assertions.assertThat
import org.junit.Test

class PathTest {
    val element = elementOf("author-namespace", "root") {
        attribute("author", "Very important author")
        element("books") {
            attribute("count", "10")
            for (index in 0..9)
                element("book") {
                    text("title$index")
                    attribute("rating", "${index % 5}")
                }
        }
        element("image-namespace", "authorImage") {
            comment("data to use")
            cdata("imageData")
        }
    }

    val empty = elementOf("empty")

    @Test fun testNamespace() {
        assertThat(element / XNamespace).isEqualTo("author-namespace")
        assertThat(element / "books" / XNamespace).isEqualTo("author-namespace")
        assertThat(element / XPaths("books") / XNamespace).containsExactly("author-namespace")


        assertThat(empty / XNamespace).isNull()
        assertThat(element / "noItem" / XNamespace).isNull()
    }

    @Test fun testName() {
        assertThat(element / XName).isEqualTo("root")
        assertThat(element / "books" / XName).isEqualTo("books")

        assertThat(element / "noItem" / XName).isNull()
    }

    @Test fun testText() {
        assertThat(element / XText).isNull()
        assertThat(element / "books" / XPaths("book") / XText).hasSize(10).contains("title4")
        assertThat(element / XPaths("authorImage") / XText).isEmpty()
    }

    @Test fun testCData() {
        assertThat(element / XCData).isNull()
        assertThat(element / "authorImage" / XCData).isEqualTo("imageData")
        assertThat(element / XPaths("authorImage") / XCData).containsExactly("imageData")
    }

    @Test fun testComment() {
        assertThat(element / XComment).isNull()
        assertThat(element / "authorImage" / XComment).isEqualTo("data to use")
        assertThat(element / XPaths("authorImage") / XComment).containsExactly("data to use")
    }

    @Test fun testAttribute() {
        assertThat(empty / XAttribute()).isNull()

        assertThat(empty / "null" / XAttribute()).isNull()

        assertThat(element / XAttribute()).isEqualTo("Very important author")
        assertThat(element / XAttribute("author")).isEqualTo("Very important author")
        assertThat(element / XAttribute("author-namespace", "author")).isEqualTo("Very important author")

        assertThat(element / "books" / XAttribute()).isEqualTo("10")
        assertThat(element / "books" / XAttribute("count")).isEqualTo("10")
        assertThat(element / "books" / XAttribute("author-namespace", "count")).isEqualTo("10")
    }

    @Test fun testPath() {
        assertThat(element / XElement).isEqualTo(element)
        assertThat(empty / XPath("null") / XElement).isNull()
        assertThat(element / XPath("null") / XPath("null") / XElement).isNull()

        assertThat(element / XPath("books") / XName).isEqualTo("books")
        assertThat(element / "books" / XName).isEqualTo("books")
        assertThat(element / XPath("books") / XPath("book") / XName).isEqualTo("book")
        assertThat(element / "books" / "book" / XName).isEqualTo("book")

        assertThat(element / XPath("author-namespace", "books") / XName).isEqualTo("books")
        assertThat(element / XPath("author-namespace", "books") / XPath("author-namespace", "book") / XName).isEqualTo("book")
    }

    @Test fun testPaths() {
        assertThat(elementOf("empty") / XPaths("null") / XElement).isEmpty()
        assertThat(element / XPaths("null") / XPaths("null") / XElement).isEmpty()
        assertThat(element / XPaths("null") / XPath("null") / XElement).isEmpty()
        assertThat(element / XPath("null") / XPaths("null") / XElement).isEmpty()

        assertThat(element / XPaths() / XName).containsExactly("books", "authorImage")
        assertThat(element / XPaths("books") / XName).containsExactly("books")
        assertThat(element / XPaths("books") / XPaths("book") / XName).containsOnly("book")
        assertThat(element / XPath("books") / XPaths("book") / XName).containsOnly("book")
        assertThat(element / XPaths("books") / XPath("book") / XName).containsOnly("book")

        assertThat(element / XPaths("author-namespace", "books") / XName).containsExactly("books")
        assertThat(element / XPaths("author-namespace", "books") / XPaths("author-namespace", "book") / XName).containsOnly("book")
        assertThat(element / XPath("author-namespace", "books") / XPaths("author-namespace", "book") / XName).containsOnly("book")
        assertThat(element / XPaths("author-namespace", "books") / XPath("author-namespace", "book") / XName).containsOnly("book")
        assertThat(element / XPaths("author-namespace", "books") / "book" / XName).containsExactly("book")

        assertThat(element / "books" / "book" / XAttribute()).isEqualTo("0")
        assertThat(element / "books" / XPaths("book") / XAttribute()).contains("0", "1", "2", "3", "4")
    }

    @Test fun testPathsWithGetOperator() {
        assertThat(empty / XPaths("null")[12] / XElement).isNull()
        assertThat(element / XPath("null") / XPaths("null")[12] / XElement).isNull()

        assertThat(element / XPaths()[0] / XName).isEqualTo("books")
        assertThat(element / XPaths("books")[0] / XName).isEqualTo("books")
        assertThat(element / XPaths("books")[0] / XPaths("book")[0] / XName).isEqualTo("book")
        assertThat(element / XPaths("books")[0] / XPaths("book") / XName).containsOnly("book")
        assertThat(element / XPaths("books") / XPaths("book")[0] / XName).containsOnly("book")

        assertThat(element / XPaths("author-namespace", "books")[0] / XName).isEqualTo("books")
        assertThat(element / XPaths("author-namespace", "books")[0] / XPaths("author-namespace", "book")[2] / XText).isEqualTo("title2")
        assertThat(element / XPaths("author-namespace", "books")[0] / XPaths("author-namespace", "book") / XText).hasSize(10)
        assertThat(element / XPaths("author-namespace", "books") / XPaths("author-namespace", "book")[3] / XText).containsExactly("title3")
    }
}