package com.github.musichin.ktxml

final class CommentElement(
        val comment: String
) : BaseElement() {
    open class Builder(protected var comment: String? = null) : BaseElement.Builder {
        fun comment(comment: String): Builder {
            this.comment = comment
            return this
        }

        override fun build(): CommentElement {
            return CommentElement(comment!!)
        }
    }

    override fun newBuilder() = Builder(comment)
}