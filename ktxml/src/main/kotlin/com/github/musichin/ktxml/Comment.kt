package com.github.musichin.ktxml

interface Comment : Content {
    val comment: String

    override fun mutable(): MutableComment

    companion object {
        fun of(comment: String): Comment = CommentContent(comment)
    }
}

fun commentOf(comment: String): Comment = CommentContent(comment)
fun String.toComment() = commentOf(this)

interface MutableComment : Comment, MutableContent {
    override var comment: String

    override fun immutable(): Comment

    companion object {
        fun of(comment: String): MutableComment = MutableCommentContent(comment)
    }
}

fun mutableCommentOf(comment: String): MutableComment = MutableCommentContent(comment)
fun String.toMutableComment() = mutableCommentOf(this)

open class CommentContent(
    override val comment: String
) : Comment {
    override fun mutable(): MutableComment = MutableCommentContent(comment)

    override fun hashCode(): Int {
        return comment.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other is CommentContent) {
            return comment == other.comment
        }
        return super.equals(other)
    }

    override fun toString(): String {
        return "${javaClass.simpleName}(comment=$comment)"
    }
}

open class MutableCommentContent(
    override var comment: String
) : CommentContent(comment), MutableComment {
    override fun immutable() = CommentContent(comment)

    override fun mutable() = this
}
