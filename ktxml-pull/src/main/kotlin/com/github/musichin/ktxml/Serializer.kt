package com.github.musichin.ktxml

import org.xmlpull.v1.XmlPullParserFactory
import org.xmlpull.v1.XmlSerializer
import java.io.ByteArrayOutputStream
import java.io.OutputStream

fun BaseElement.serialize(): String {
    val os = ByteArrayOutputStream()
    serialize(os)
    return os.toString()
}

fun BaseElement.serialize(output: OutputStream) {
    val serializer = createSerializer(output)

    serializer.startDocument(null, null)
    serialize(serializer)
    serializer.endDocument()

    output.close()
}

private fun Element.serialize(serializer: XmlSerializer) {
    serializer.startTag(namespace, name)

    for ((namespace, key, value) in attributes) {
        serializer.attribute(namespace, key, value)
    }

    for (element in elements) {
        element.serialize(serializer)
    }

    serializer.endTag(namespace, name)
}

private fun TextElement.serialize(serializer: XmlSerializer) {
    serializer.text(text)
}

private fun CDataElement.serialize(serializer: XmlSerializer) {
    serializer.cdsect(text)
}

private fun CommentElement.serialize(serializer: XmlSerializer) {
    serializer.comment(comment)
}

private fun BaseElement.serialize(serializer: XmlSerializer) {
    when (this) {
        is Element -> serialize(serializer)
        is CDataElement -> serialize(serializer)
        is TextElement -> serialize(serializer)
        is CommentElement -> serialize(serializer)
        else -> throw UnsupportedOperationException("${javaClass.name} is unsupported")
    }
}

private fun createSerializer(output: OutputStream): XmlSerializer {
    val pullParserFactory = XmlPullParserFactory.newInstance()
    val serializer = pullParserFactory.newSerializer();
    serializer.setOutput(output, null);
    return serializer
}