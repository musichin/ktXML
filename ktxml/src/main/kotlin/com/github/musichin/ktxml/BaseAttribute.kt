package com.github.musichin.ktxml

sealed interface BaseAttribute : BaseNode {
    val namespace: String
    val name: String
    val value: String

    operator fun component1(): String = namespace

    operator fun component2(): String = name

    operator fun component3(): String = value
}
