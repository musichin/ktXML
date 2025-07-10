package com.github.musichin.ktxml

sealed interface BaseComment : BaseNode {
    val comment: String

    operator fun component1(): String = comment
}
