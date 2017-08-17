package com.minek.kotlin.everywehre.keuson.decode

import com.minek.kotlin.everywhere.kelibs.result.Err
import com.minek.kotlin.everywhere.kelibs.result.Ok
import com.minek.kotlin.everywhere.kelibs.result.Result
import com.minek.kotlin.everywhere.kelibs.result.andThen

object Decoders {
    val string = com.minek.kotlin.everywehre.keuson.decode.string
    val boolean = com.minek.kotlin.everywehre.keuson.decode.boolean
    val int = com.minek.kotlin.everywehre.keuson.decode.int
    val long = com.minek.kotlin.everywehre.keuson.decode.long
    val float = com.minek.kotlin.everywehre.keuson.decode.float

    fun <T> nullable(decoder: Decoder<T>): Decoder<T?> {
        return {
            if (isNull(it)) {
                Ok(null)
            } else {
                decoder(it)
            }
        }
    }

    fun <T> field(name: String, decoder: Decoder<T>): Decoder<T> {
        return com.minek.kotlin.everywehre.keuson.decode.field(name, decoder)
    }

    fun <T> list(decoder: Decoder<T>): Decoder<List<T>> {
        return com.minek.kotlin.everywehre.keuson.decode.list(decoder)
    }

    fun <T> success(value: T): Decoder<T> {
        return { Ok(value) }
    }

    fun <T> fail(message: String): Decoder<T> {
        return { Err(message) }
    }
}

fun <T, U> andThen(decoder: Decoder<T>, transformer: (T) -> Decoder<U>): Decoder<U> {
    return { e ->
        decoder.invoke(e).andThen { v ->
            transformer(v)(e)
        }
    }
}

fun <T> decodeString(decoder: Decoder<T>, jsonString: String): Result<String, T> {
    return parse(jsonString).andThen(decoder)
}
