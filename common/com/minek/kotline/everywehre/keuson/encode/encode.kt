package com.minek.kotline.everywehre.keuson.encode

typealias Encoder<T> = (T) -> Value

object Encoders {
    val string: Encoder<String> = com.minek.kotline.everywehre.keuson.encode.string
    val int: Encoder<Int> = com.minek.kotline.everywehre.keuson.encode.int
    val long: Encoder<Long> = com.minek.kotline.everywehre.keuson.encode.long
    val float: Encoder<Float> = com.minek.kotline.everywehre.keuson.encode.float
    val bool: Encoder<Boolean> = com.minek.kotline.everywehre.keuson.encode.bool

    fun object_(vararg fields: Pair<String, Value>): Value {
        return com.minek.kotline.everywehre.keuson.encode.object_(*fields)
    }

    val array: Encoder<Collection<Value>> = com.minek.kotline.everywehre.keuson.encode.array

    fun <T> nullable(encoder: Encoder<T>): Encoder<T?> {
        return com.minek.kotline.everywehre.keuson.encode.nullable(encoder)
    }
}

fun encode(value: Value): String {
    return com.minek.kotline.everywehre.keuson.encode._encode(value)
}