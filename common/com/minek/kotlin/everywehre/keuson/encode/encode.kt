package com.minek.kotlin.everywehre.keuson.encode

typealias Encoder<T> = (T) -> Value

object Encoders {
    val string: Encoder<String> = com.minek.kotlin.everywehre.keuson.encode.string
    val int: Encoder<Int> = com.minek.kotlin.everywehre.keuson.encode.int
    val long: Encoder<Long> = com.minek.kotlin.everywehre.keuson.encode.long
    val float: Encoder<Float> = com.minek.kotlin.everywehre.keuson.encode.float
    val boolean: Encoder<Boolean> = com.minek.kotlin.everywehre.keuson.encode.boolean

    fun object_(vararg fields: Pair<String, Value>): Value {
        return com.minek.kotlin.everywehre.keuson.encode.object_(*fields)
    }

    val list: Encoder<List<Value>> = com.minek.kotlin.everywehre.keuson.encode.list

    fun <T> nullable(encoder: Encoder<T>): Encoder<T?> {
        return com.minek.kotlin.everywehre.keuson.encode.nullable(encoder)
    }
}

fun encode(value: Value): String {
    return _encode(value)
}