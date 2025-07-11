package de.musichin.ktxml.pull

import de.musichin.ktxml.Element
import de.musichin.ktxml.Node
import de.musichin.ktxml.attributeOf
import de.musichin.ktxml.cdataOf
import de.musichin.ktxml.commentOf
import de.musichin.ktxml.elementOf
import de.musichin.ktxml.textOf
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

    return element
}

private fun deserializeText(parser: XmlPullParser) = textOf(parser.readText())

private fun deserializeCData(parser: XmlPullParser) = cdataOf(parser.readText())

private fun deserializeComment(parser: XmlPullParser) = commentOf(parser.readText())

private fun deserializeElement(parser: XmlPullParser): Element {
    parser.require(XmlPullParser.START_TAG, null, null)

    val namespace = parser.namespace
    val name = parser.name
    val nodes = mutableListOf<Node>()

    for (index in 0 until parser.attributeCount) {
        val namespace: String = parser.getAttributeNamespace(index)
        val name = parser.getAttributeName(index)
        val value = parser.getAttributeValue(index)
        nodes.add(attributeOf(namespace, name, value))
    }

    parser.nextSkipWhitespace()
    while (!parser.isEndTagOrEndDocument()) {
        when (parser.eventType) {
            XmlPullParser.START_TAG -> nodes.add(deserializeElement(parser))
            XmlPullParser.TEXT -> nodes.add(deserializeText(parser))
            XmlPullParser.CDSECT -> nodes.add(deserializeCData(parser))
            XmlPullParser.COMMENT -> nodes.add(deserializeComment(parser))
            else -> throw IllegalArgumentException("Unsupported event type: ${parser.eventType}")
        }
    }

    parser.require(XmlPullParser.END_TAG, null, null)
    parser.nextSkipWhitespace()

    return elementOf(namespace, name, nodes)
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
