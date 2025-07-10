package com.github.musichin.ktxml

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import java.io.Reader
import java.nio.charset.Charset

fun String.deserialize(encoding: String? = null): Element =
    byteInputStream(Charset.forName(encoding ?: "UTF-8")).use {
        it.deserialize(encoding ?: "UTF-8")
    }

fun InputStream.deserialize(encoding: String? = null): Element {
    val parser = XmlPullParserFactory.newInstance().newPullParser()
    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true)
    parser.setInput(this, encoding)
    return parser.deserialize()
}

fun Reader.deserialize(): Element {
    val parser = XmlPullParserFactory.newInstance().newPullParser()
    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true)
    parser.setInput(this)
    return parser.deserialize()
}

fun XmlPullParser.deserialize(): Element {
    require(XmlPullParser.START_DOCUMENT, null, null)
    nextSkipWhitespace()
    val element = deserializeElement(this)
    require(XmlPullParser.END_DOCUMENT, null, null)
    nextSkipWhitespace()

    return element.toImmutable()
}

private fun deserializeText(parser: XmlPullParser): MutableText = mutableTextOf(parser.readText())

private fun deserializeCData(parser: XmlPullParser): MutableCData = mutableCDataOf(parser.readText())

private fun deserializeComment(parser: XmlPullParser): MutableComment = mutableCommentOf(parser.readText())

private fun deserializeElement(parser: XmlPullParser): MutableElement {
    parser.require(XmlPullParser.START_TAG, null, null)

    val element = mutableElementOf(parser.namespace, parser.name)

    for (index in 0 until parser.attributeCount) {
        val namespace: String = parser.getAttributeNamespace(index)
        val name = parser.getAttributeName(index)
        val value = parser.getAttributeValue(index)
        element.addAttribute(namespace, name, value)
    }

    parser.nextSkipWhitespace()
    while (!parser.isEndTagOrEndDocument()) {
        when (parser.eventType) {
            XmlPullParser.START_TAG -> element.addNode(deserializeElement(parser))
            XmlPullParser.TEXT -> element.addNode(deserializeText(parser))
            XmlPullParser.CDSECT -> element.addNode(deserializeCData(parser))
            XmlPullParser.COMMENT -> element.addNode(deserializeComment(parser))
            else -> throw IllegalArgumentException("Unsupported event type: ${parser.eventType}")
        }
    }

    parser.require(XmlPullParser.END_TAG, null, null)
    parser.nextSkipWhitespace()

    return element
}

private fun XmlPullParser.isEndTagOrEndDocument() = eventType == XmlPullParser.END_TAG || eventType == XmlPullParser.END_DOCUMENT

private fun XmlPullParser.readText(): String {
    require(XmlPullParser.TEXT, null, null)

    var value = ""
    do {
        value += text
    } while (nextSkipWhitespace() == XmlPullParser.TEXT)

    return value
}

private fun XmlPullParser.nextSkipWhitespace(): Int {
    do {
        next()
    } while (eventType == XmlPullParser.TEXT && isWhitespace)
    return eventType
}
