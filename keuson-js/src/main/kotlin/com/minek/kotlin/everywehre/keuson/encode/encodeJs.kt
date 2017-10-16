package com.minek.kotlin.everywehre.keuson.encode

import kotlinx.serialization.KSerialSaver
import kotlinx.serialization.json.JSON

sealed class Value
private class Native(internal val value: dynamic) : Value()
private class SerializableValue<T : Any>(internal val value: T, internal val saver: KSerialSaver<T>) : Value() {
    internal fun stringify(): String {
        return JSON.stringify(saver, value)
    }
}

fun Value.toJs(): dynamic {
    return when (this) {
        is Native -> value
        is SerializableValue<*> -> {
            kotlin.js.JSON.parse(stringify())
        }
    }
}

internal val string: Encoder<String> = { Native(it) }
internal val int: Encoder<Int> = { Native(it) }
internal val long: Encoder<Long> = { Native(it.toDouble()) }
internal val float: Encoder<Float> = { Native(it) }
internal val boolean: Encoder<Boolean> = { Native(it) }
internal val unit: Encoder<Unit> = { Native(null) }

internal fun object_(vararg fields: Pair<String, Value>): Value {
    val obj = js("({})")
    for ((name, value) in fields) {
        obj[name] = value.toJs()
    }
    return Native(obj)
}

fun <T : Any> serialize(serializable: T, saver: KSerialSaver<T>): Value {
    return SerializableValue(serializable, saver)
}

@Suppress("UnsafeCastFromDynamic")
internal val list: Encoder<Collection<Value>> = { Native(it.map { it.toJs() }.toTypedArray()) }

internal fun <T> nullable(encoder: Encoder<T>): Encoder<T?> {
    return {
        if (it != null) encoder(it)
        else Native(null)
    }
}

internal fun _encode(value: Value): String {
    @Suppress("UnsafeCastFromDynamic")
    return kotlin.js.JSON.stringify(value.toJs())
}