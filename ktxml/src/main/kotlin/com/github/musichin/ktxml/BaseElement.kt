package com.github.musichin.ktxml

abstract class BaseElement internal constructor() {
    interface Builder {
        fun build(): BaseElement
    }

    interface MarkupBuilder : Builder {
        fun builder(): Builder
    }

    abstract fun newBuilder(): Builder
}

internal interface IsFullyQualified {
    val namespace: String?
    val name: String?
}