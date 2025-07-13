package de.musichin.ktxml

open class MutableComment internal constructor(
    override var comment: String,
) : BaseComment,
    MutableNode {
    override fun toImmutable() = Comment.of(comment)

    fun append(text: CharSequence): MutableComment {
        this.comment += text
        return this
    }

    override fun hashCode(): Int = comment.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is MutableComment) return false
        return comment == other.comment
    }

    override fun toString(): String = "MutableComment(comment=$comment)"

    companion object {
        fun of(comment: String): MutableComment = MutableComment(comment)
    }
}

fun mutableCommentOf(comment: String): MutableComment = MutableComment.of(comment)

fun String.toMutableComment() = MutableComment.of(this)
