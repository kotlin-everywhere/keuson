package com.minek.kotline.everywehre.keuson.decode

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.minek.kotlin.everywhere.kelibs.result.Err
import com.minek.kotlin.everywhere.kelibs.result.Ok
import com.minek.kotlin.everywhere.kelibs.result.Result

typealias Decoder<T> = (element: JsonElement) -> Result<String, T>

internal val string: Decoder<String> = {
    if (it.isJsonPrimitive && it.asJsonPrimitive.isString) {
        Ok(it.asString)
    } else {
        Err("Expecting a String but instead got: $it")
    }
}

internal val boolean: Decoder<Boolean> = {
    if (it.isJsonPrimitive && it.asJsonPrimitive.isBoolean) {
        Ok(it.asBoolean)
    } else {
        Err("Expecting a Boolean but instead got: $it")
    }
}

internal val int: Decoder<Int> = {
    if (it.isJsonPrimitive && it.asJsonPrimitive.isNumber && "$it".isInt) {
        Ok(it.asInt)
    } else {
        Err("Expecting a Int but instead got: $it")
    }
}

internal val long: Decoder<Long> = {
    if (it.isJsonPrimitive && it.asJsonPrimitive.isNumber && "$it".isLong) {
        Ok(it.asLong)
    } else {
        Err("Expecting a Long but instead got: $it")
    }
}

val float: Decoder<Float> = {
    if (it.isJsonPrimitive && it.asJsonPrimitive.isNumber && !"$it".isInt && "$it".isFloat) {
        Ok(it.asFloat)
    } else {
        Err("Expecting a Float but instead got: $it")
    }
}

private val String.isInt: Boolean
    get() {
        try {
            Integer.parseInt(this)
            return true
        } catch (e: NumberFormatException) {
            return false
        }
    }

private val String.isLong: Boolean
    get() {
        try {
            java.lang.Long.parseLong(this)
            return true
        } catch (e: NumberFormatException) {
            return false
        }
    }

private val String.isFloat: Boolean
    get() {
        try {
            java.lang.Float.parseFloat(this)
            return true
        } catch (e: NumberFormatException) {
            return false
        }
    }


internal fun parse(jsonString: String): JsonElement {
    return JsonParser().parse(jsonString)
}
