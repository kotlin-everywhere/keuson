package com.minek.kotlin.everywehre.keuson.encode

import com.google.gson.*

typealias Value = JsonElement

internal val string: Encoder<String> = ::JsonPrimitive
internal val int: Encoder<Int> = ::JsonPrimitive
internal val long: Encoder<Long> = ::JsonPrimitive
internal val float: Encoder<Float> = ::JsonPrimitive
internal val boolean: Encoder<Boolean> = ::JsonPrimitive

internal fun object_(vararg fields: Pair<String, Value>): Value {
    return fields.fold(JsonObject()) { obj, (name, value) -> obj.add(name, value); obj }
}

internal val list: Encoder<List<Value>> = {
    it.fold(JsonArray()) { arr, value -> arr.add(value); arr }
}

internal fun <T> nullable(encoder: Encoder<T>): Encoder<T?> {
    return {
        if (it != null) encoder(it)
        else JsonNull.INSTANCE
    }
}

private val gson = GsonBuilder().create()!!

internal fun _encode(value: Value): String {
    return gson.toJson(value)
}