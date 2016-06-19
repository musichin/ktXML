package com.github.musichin.ktxml

import org.xmlpull.v1.XmlPullParserFactory
import org.xmlpull.v1.XmlSerializer
import java.io.ByteArrayOutputStream
import java.io.OutputStream

fun Element.serialize(): String {
    val output = ByteArrayOutputStream()
    serialize(output)
    return output.toString()
}

fun Element.serialize(output: OutputStream) {
    val serializer = output.createSerializer()

    serializer.startDocument(null, null)
    serialize(serializer)
    serializer.endDocument()

    output.close()
}

private fun OutputStream.createSerializer(parserFactory: XmlPullParserFactory? = null): XmlSerializer {
    val factory = parserFactory ?: XmlPullParserFactory.newInstance()
    val serializer = factory.newSerializer();
    serializer.setOutput(this, null);
    return serializer
}

private fun Element.serialize(serializer: XmlSerializer) {
    serializer.startTag(namespace, name)

    for ((namespace, key, value) in attributes) {
        serializer.attribute(namespace, key, value)
    }

    for (element in contents) {
        element.serialize(serializer)
    }

    serializer.endTag(namespace, name)
}

private fun Text.serialize(serializer: XmlSerializer) {
    serializer.text(text)
}

private fun CData.serialize(serializer: XmlSerializer) {
    serializer.cdsect(text)
}

private fun Comment.serialize(serializer: XmlSerializer) {
    serializer.comment(comment)
}

private fun Content.serialize(serializer: XmlSerializer) {
    when (this) {
        is Element -> serialize(serializer)
        is CData -> serialize(serializer)
        is Text -> serialize(serializer)
        is Comment -> serialize(serializer)
        else -> throw UnsupportedOperationException("${javaClass.name} is unsupported")
    }
}
