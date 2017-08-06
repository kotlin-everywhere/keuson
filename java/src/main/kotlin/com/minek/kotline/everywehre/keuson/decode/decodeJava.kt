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

internal fun parse(jsonString: String): JsonElement {
    return JsonParser().parse(jsonString)
}
