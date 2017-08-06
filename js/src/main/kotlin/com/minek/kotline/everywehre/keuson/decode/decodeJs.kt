package com.minek.kotline.everywehre.keuson.decode

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

internal fun parse(jsonString: String): Any? {
    return JSON.parse(jsonString)
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
