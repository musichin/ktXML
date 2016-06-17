package com.github.musichin.ktxml

open class CommentElement(
        val comment: String
) : BaseElement() {

    override fun newBuilder() = Builder(comment)

    override fun hashCode(): Int {
        return comment.hashCode();
    }

    override fun equals(other: Any?): Boolean {
        if (other is CommentElement) {
            return comment == other.comment
        }
        return super.equals(other)
    }

    override fun toString(): String {
        return "${javaClass.simpleName}(comment=$comment)"
    }

    open class Builder(protected var comment: String? = null) : BaseElement.Builder {
        fun comment(comment: String): Builder {
            this.comment = comment
            return this
        }

        override fun build(): CommentElement {
            return CommentElement(comment!!)
        }
    }
}
