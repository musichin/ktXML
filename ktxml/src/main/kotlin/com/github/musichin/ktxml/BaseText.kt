package com.github.musichin.ktxml

sealed interface BaseText : BaseNode {
    val text: String

    operator fun component1(): String = text
}
