package com.minek.kotlin.everywehre.keuson.encode

import com.minek.kotlin.everywhere.kelibs.result.Err
import com.minek.kotlin.everywhere.kelibs.result.Ok
import com.minek.kotlin.everywhere.kelibs.result.Result

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

    fun <E, T> result(errorEncoder: Encoder<E>, valueEncoder: Encoder<T>): Encoder<Result<E, T>> {
        return {
            when (it) {
                is Ok -> object_("type" to string("Ok"), "value" to valueEncoder(it.value))
                is Err -> object_("type" to string("Err"), "error" to errorEncoder(it.error))
            }
        }
    }
}

fun encode(value: Value): String {
    return _encode(value)
}