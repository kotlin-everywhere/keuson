package com.minek.kotlin.everywehre.keuson.encode

fun <T> javaEncoder(): Encoder<T> {
    return { gson.toJsonTree(it) }
}

