package com.github.musichin.ktxml

open class Comment internal constructor(
    override val comment: String,
) : BaseComment,
    Node {
    override fun hashCode(): Int = comment.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is Comment) return false
        return comment == other.comment
    }

    override fun toString(): String = "Comment(comment=$comment)"

    override fun toMutable(): MutableComment = MutableComment.of(comment)

    companion object {
        fun of(comment: String): Comment = Comment(comment)
    }
}

fun commentOf(comment: String): Comment = Comment.of(comment)

fun String.toComment() = Comment.of(this)
