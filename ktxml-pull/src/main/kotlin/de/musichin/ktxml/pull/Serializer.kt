package de.musichin.ktxml.pull

import de.musichin.ktxml.BaseAttribute
import de.musichin.ktxml.BaseCData
import de.musichin.ktxml.BaseComment
import de.musichin.ktxml.BaseElement
import de.musichin.ktxml.BaseNode
import de.musichin.ktxml.BaseText
import org.xmlpull.v1.XmlPullParserFactory
import org.xmlpull.v1.XmlSerializer
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.io.Writer

fun BaseElement.serialize(
    namespaces: List<Pair<String, String>> = emptyList(),
    encoding: String? = null,
    standalone: Boolean? = null,
): String {
    val output = ByteArrayOutputStream()
    serialize(output, namespaces, encoding ?: "UTF-8", standalone)
    return output.toString(encoding ?: "UTF-8")
}

fun BaseElement.serialize(
    output: OutputStream,
    namespaces: List<Pair<String, String>> = emptyList(),
    encoding: String? = null,
    standalone: Boolean? = null,
) {
    val factory = XmlPullParserFactory.newInstance()
    val serializer = factory.newSerializer()
    serializer.setOutput(output, null)

    serialize(serializer, namespaces, encoding, standalone)
    serializer.endDocument()

    output.close()
}

fun BaseElement.serialize(
    output: Writer,
    namespaces: List<Pair<String, String>> = emptyList(),
    encoding: String? = null,
    standalone: Boolean? = null,
) {
    val factory = XmlPullParserFactory.newInstance()
    val serializer = factory.newSerializer()
    serializer.setOutput(output)

    serialize(serializer, namespaces, encoding, standalone)
    serializer.endDocument()

    output.close()
}

fun BaseElement.serialize(
    serializer: XmlSerializer,
    namespaces: List<Pair<String, String>> = emptyList(),
    encoding: String? = null,
    standalone: Boolean? = null,
) {
    serializer.startDocument(encoding, standalone)

    for ((prefix, namespace) in namespaces) {
        serializer.setPrefix(prefix, namespace)
    }
    serialize(serializer, this)

    serializer.endDocument()
}

private fun serialize(
    serializer: XmlSerializer,
    node: BaseNode,
) {
    when (node) {
        is BaseCData -> {
            serializer.cdsect(node.text)
        }
        is BaseText -> {
            serializer.text(node.text)
        }
        is BaseComment -> {
            serializer.comment(node.comment)
        }
        is BaseAttribute -> {
            serializer.attribute(node.namespace, node.name, node.value)
        }
        is BaseElement -> {
            serializer.startTag(node.namespace, node.name)

            for (node in node.nodes) {
                if (node is BaseAttribute) serialize(serializer, node)
            }

            for (node in node.nodes) {
                if (node !is BaseAttribute) serialize(serializer, node)
            }

            serializer.endTag(node.namespace, node.name)
        }
    }
}
