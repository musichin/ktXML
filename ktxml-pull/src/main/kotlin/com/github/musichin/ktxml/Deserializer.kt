package com.github.musichin.ktxml

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

fun String.deserialize() = byteInputStream().deserialize()

fun InputStream.deserialize(): Element {
    val parser = this.xmlPullParser()

    parser.require(XmlPullParser.START_DOCUMENT, null, null)
    parser.next

    val element = deserializeElement(parser)

    parser.require(XmlPullParser.END_DOCUMENT, null, null)

    close()

    return element
}

private fun InputStream.xmlPullParser(): XmlPullParser {
    val parserFactory = XmlPullParserFactory.newInstance()
    parserFactory.isNamespaceAware = true
    val parser = parserFactory.newPullParser()
    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true)
    parser.setInput(this, null)
    return parser
}

private fun deserializeText(parser: XmlPullParser): TextElement {
    return TextElement(parser.readText())
}

private fun deserializeElement(parser: XmlPullParser): Element {
    parser.require(XmlPullParser.START_TAG, null, null)

    val builder = Element.Builder().namespace(parser.namespace).name(parser.name)

    for (index in 0..parser.attributeCount - 1) {
        val namespace: String = parser.getAttributeNamespace(index)
        val name = parser.getAttributeName(index)
        val value = parser.getAttributeValue(index)
        builder.addAttribute(if (namespace == "") null else namespace, name, value)
    }


    parser.next
    while (!parser.isEndTagOrEndDocument()) {
        when (parser.eventType) {
            XmlPullParser.START_TAG -> builder.addElement(deserializeElement(parser))
            XmlPullParser.TEXT -> builder.addElement(deserializeText(parser))
            else -> parser.skip()
        }
    }

    parser.require(XmlPullParser.END_TAG, null, null)
    parser.next

    return builder.build()
}

private fun XmlPullParser.isEndTagOrEndDocument() = eventType == XmlPullParser.END_TAG || eventType == XmlPullParser.END_DOCUMENT

private fun XmlPullParser.readText(): String {
    require(XmlPullParser.TEXT, null, null)

    var value = ""
    do {
        value = text
    } while(next == XmlPullParser.TEXT)

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