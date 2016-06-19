package com.github.musichin.ktxml

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

fun String.deserialize(parserFactory: XmlPullParserFactory? = null) = byteInputStream().deserialize(parserFactory)

fun InputStream.deserialize(parserFactory: XmlPullParserFactory? = null): Element {
    val parser = xmlPullParser(parserFactory)

    parser.require(XmlPullParser.START_DOCUMENT, null, null)
    parser.next

    val element = deserializeElement(parser)

    parser.require(XmlPullParser.END_DOCUMENT, null, null)

    close()

    return element.mutable()
}

private fun InputStream.xmlPullParser(parserFactory: XmlPullParserFactory? = null): XmlPullParser {
    val factory = parserFactory ?: XmlPullParserFactory.newInstance()
    factory.isNamespaceAware = true
    val parser = factory.newPullParser()
    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true)
    parser.setInput(this, null)
    return parser
}

private fun deserializeText(parser: XmlPullParser): MutableText {
    return mutableTextOf(parser.readText())
}

private fun deserializeElement(parser: XmlPullParser): MutableElement {
    parser.require(XmlPullParser.START_TAG, null, null)

    val element = mutableElementOf(parser.namespace, parser.name)

    for (index in 0..parser.attributeCount - 1) {
        val namespace: String = parser.getAttributeNamespace(index)
        val name = parser.getAttributeName(index)
        val value = parser.getAttributeValue(index)
        element.addAttribute(if (namespace == "") null else namespace, name, value)
    }


    parser.next
    while (!parser.isEndTagOrEndDocument()) {
        when (parser.eventType) {
            XmlPullParser.START_TAG -> element.addContent(deserializeElement(parser))
            XmlPullParser.TEXT -> element.addContent(deserializeText(parser))
            else -> parser.skip()
        }
    }

    parser.require(XmlPullParser.END_TAG, null, null)
    parser.next

    return element
}

private fun XmlPullParser.isEndTagOrEndDocument() = eventType == XmlPullParser.END_TAG || eventType == XmlPullParser.END_DOCUMENT

private fun XmlPullParser.readText(): String {
    require(XmlPullParser.TEXT, null, null)

    var value = ""
    do {
        value += text
    } while (next == XmlPullParser.TEXT)

    return value
}

private val XmlPullParser.next: Int get() {
    next()

    return if (eventType == XmlPullParser.TEXT && isWhitespace) {
        next
    } else {
        eventType
    }
}

private fun XmlPullParser.skip() {
    if (eventType == XmlPullParser.END_TAG) {
        return
    }

    require(XmlPullParser.START_TAG, null, null)

    var depth = 1
    while (depth > 0) {
        depth += when (next) {
            XmlPullParser.END_TAG -> -1
            XmlPullParser.START_TAG -> +1
            else -> 0
        }
    }
}