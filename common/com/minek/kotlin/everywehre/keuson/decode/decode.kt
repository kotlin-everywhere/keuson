package com.minek.kotlin.everywehre.keuson.decode

import com.minek.kotlin.everywhere.kelibs.result.Err
import com.minek.kotlin.everywhere.kelibs.result.Ok
import com.minek.kotlin.everywhere.kelibs.result.Result
import com.minek.kotlin.everywhere.kelibs.result.andThen
import kotlinx.serialization.serializer

object Decoders {
    val string = com.minek.kotlin.everywehre.keuson.decode.string
    val boolean = com.minek.kotlin.everywehre.keuson.decode.boolean
    val int = com.minek.kotlin.everywehre.keuson.decode.int
    val long = com.minek.kotlin.everywehre.keuson.decode.long
    val float = com.minek.kotlin.everywehre.keuson.decode.float
    val unit = com.minek.kotlin.everywehre.keuson.decode.unit

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

    inline fun <reified T: Any> deserialize(): Decoder<T> {
        return com.minek.kotlin.everywehre.keuson.decode.deserialize(T::class.serializer())
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

    fun <E, T> result(error: Decoder<E>, ok: Decoder<T>): Decoder<Result<E, T>> {
        return andThen(field("type", string)) {
            when (it) {
                "Ok" -> map(field("value", ok), { Ok<E, T>(it) })
                "Err" -> map(field("error", error), { Err<E, T>(it) })
                else -> fail("Expecting Ok or Err but instead got: $it")
            }
        }
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
