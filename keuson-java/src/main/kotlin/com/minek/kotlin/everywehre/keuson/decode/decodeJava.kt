package com.minek.kotlin.everywehre.keuson.decode

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.minek.kotlin.everywhere.kelibs.result.Err
import com.minek.kotlin.everywhere.kelibs.result.Ok
import com.minek.kotlin.everywhere.kelibs.result.Result
import kotlinx.serialization.KSerialLoader

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

internal val float: Decoder<Float> = {
    if (it.isJsonPrimitive && it.asJsonPrimitive.isNumber && !"$it".isInt && "$it".isFloat) {
        Ok(it.asFloat)
    } else {
        Err("Expecting a Float but instead got: $it")
    }
}

internal val unit: Decoder<Unit> = {
    if (it.isJsonNull) {
        Ok(Unit)
    } else {
        Err("Expecting a Unit but instead got: $it")
    }
}

internal fun <T> field(name: String, decoder: Decoder<T>): Decoder<T> {
    return {
        if (it.isJsonObject) {
            val obj = it.asJsonObject
            if (obj.has(name)) {
                decoder(it.asJsonObject[name])
            } else {
                Err("Expecting an object with a field named `$name` but instead got: $it")
            }
        } else {
            Err("Expecting an object but instead got: $it")
        }
    }
}

internal fun <T> list(decoder: Decoder<T>): Decoder<List<T>> {
    return {
        if (it.isJsonArray) {
            it.asJsonArray.map(decoder).fold(Ok(listOf())) { acc, i ->
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
            Err("Expecting an Array but instead got: $it")
        }
    }
}

fun <T> deserialize(loader: KSerialLoader<T>): Decoder<T> {
    return {
        if (!it.isJsonObject) {
            Err("Expecting an serializable object but instead got: $it")
        } else {
            Ok(kotlinx.serialization.json.JSON.parse(loader, it.toString()))
        }
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


internal fun parse(jsonString: String): Result<String, JsonElement> {
    try {
        return Ok(JsonParser().parse(jsonString))
    } catch (e: Exception) {
        return Err(e.causedMessage ?: e.toString())
    }
}

private val Throwable.causedMessage: String?
    get() {
        return cause?.causedMessage ?: message
    }

internal fun isNull(it: JsonElement): Boolean {
    return it.isJsonNull
}
