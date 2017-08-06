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

internal fun parse(jsonString: String): Any? {
    return JSON.parse(jsonString)
}

