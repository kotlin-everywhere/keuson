package com.minek.kotline.everywehre.keuson.decode

import com.minek.kotlin.everywhere.kelibs.result.Ok
import com.minek.kotlin.everywhere.kelibs.result.Result

object Decoders {
    val string = com.minek.kotline.everywehre.keuson.decode.string
    val boolean = com.minek.kotline.everywehre.keuson.decode.boolean
    val int = com.minek.kotline.everywehre.keuson.decode.int
    val long = com.minek.kotline.everywehre.keuson.decode.long
    val float = com.minek.kotline.everywehre.keuson.decode.float

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
        return com.minek.kotline.everywehre.keuson.decode.field(name, decoder)
    }
}

fun <T> decodeString(decoder: Decoder<T>, jsonString: String): Result<String, T> {
    return decoder(parse(jsonString))
}