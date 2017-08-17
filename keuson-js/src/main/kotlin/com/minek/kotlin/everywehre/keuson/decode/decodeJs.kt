package com.minek.kotlin.everywehre.keuson.decode

import com.minek.kotlin.everywhere.kelibs.result.Err
import com.minek.kotlin.everywhere.kelibs.result.Ok
import com.minek.kotlin.everywhere.kelibs.result.Result

typealias Decoder<T> = (element: Any?) -> Result<String, T>

fun <T : Any?> T.toJson(): String {
    return JSON.stringify(this)
}

internal val string: Decoder<String> = {
    val str = it as? String
    if (str != null) Ok(str) else Err("Expecting a String but instead got: ${it.toJson()}")
}

internal val boolean: Decoder<Boolean> = {
    val bool = it as? Boolean
    if (bool != null) Ok(bool) else Err("Expecting a Boolean but instead got: ${it.toJson()}")
}

internal val int: Decoder<Int> = {
    val i = it as? Int
    if (i != null && isInt(i)) Ok(i) else Err("Expecting a Int but instead got: ${it.toJson()}")
}

internal val long: Decoder<Long> = {
    val i = it as? Int
    if (i != null && isInt(i)) Ok(i.toLong()) else Err("Expecting a Long but instead got: ${it.toJson()}")
}

internal val float: Decoder<Float> = {
    val i = it as? Float
    if (i != null && !isInt(i)) Ok(i) else Err("Expecting a Float but instead got: ${it.toJson()}")
}

internal fun <T> field(name: String, decoder: Decoder<T>): Decoder<T> {
    return {
        val isObject = js("it === Object(it)") as Boolean
        if (isObject) {
            val i = it.asDynamic()[name]
            if (i !== undefined) {
                decoder(i as T)
            } else {
                Err("Expecting an object with a field named `$name` but instead got: ${it.toJson()}")
            }
        } else {
            Err("Expecting an object but instead got: ${it.toJson()}")
        }
    }
}

internal fun <T> list(decoder: Decoder<T>): Decoder<List<T>> {
    return {
        val isArray = js("Array.isArray(it)") as Boolean
        if (isArray) {
            val arr = it as Array<dynamic>
            arr.map(decoder).fold(Ok(listOf())) { acc, i ->
                when (acc) {
                    is Ok -> {
                        when (i) {
                            is Ok -> acc.copy(acc.value + i.value)
                            is Err -> Err(i.error)
                        }
                    }
                    is Err -> acc
                }
            }
        } else {
            Err("Expecting an Array but instead got: ${it.toJson()}")
        }
    }
}

internal fun parse(jsonString: String): Result<String, Any?> {
    try {
        return Ok(JSON.parse(jsonString))
    } catch (e: dynamic) {
        return Err("$e")
    }
}

private external fun isNaN(o: Any?): Boolean
private external fun parseFloat(o: Any?): Float?

private fun isInt(i: Number): Boolean {
    if (isNaN(i)) {
        return false
    }

    val f = parseFloat(i)
    @Suppress("UnsafeCastFromDynamic")
    return f != null && js("(f | 0) === f")
}

internal fun isNull(o: Any?): Boolean {
    return o == null
}