package com.github.musichin.ktxml

import org.xmlpull.v1.XmlPullParserFactory
import org.xmlpull.v1.XmlSerializer
import java.io.ByteArrayOutputStream
import java.io.OutputStream

fun Content.serialize(): String {
    val os = ByteArrayOutputStream()
    serialize(os)
    return os.toString()
}

fun Content.serialize(output: OutputStream) {
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

private fun createSerializer(output: OutputStream): XmlSerializer {
    val pullParserFactory = XmlPullParserFactory.newInstance()
    val serializer = pullParserFactory.newSerializer();
    serializer.setOutput(output, null);
    return serializer
}