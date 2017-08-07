package com.minek.kotline.everywehre.keuson.encode

data class Value(internal val value: dynamic)

internal val string: Encoder<String> = { Value(it) }
internal val int: Encoder<Int> = { Value(it) }
internal val long: Encoder<Long> = { Value(it.toDouble()) }
internal val float: Encoder<Float> = { Value(it) }
internal val boolean: Encoder<Boolean> = { Value(it) }

internal fun object_(vararg fields: Pair<String, Value>): Value {
    val obj = js("({})")
    for ((name, value) in fields) {
        obj[name] = value.value
    }
    return Value(obj)
}

@Suppress("UnsafeCastFromDynamic")
internal val array: Encoder<Collection<Value>> = { Value(it.map { it.value }.toTypedArray()) }

internal fun <T> nullable(encoder: Encoder<T>): Encoder<T?> {
    return {
        if (it != null) encoder(it)
        else Value(null)
    }
}

internal fun _encode(value: Value): String {
    @Suppress("UnsafeCastFromDynamic")
    return JSON.stringify(value.value)
}