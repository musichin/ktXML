package com.github.musichin.ktxml

interface Content {
    fun mutable(): MutableContent
}

interface MutableContent : Content {
    fun immutable(): Content
}
