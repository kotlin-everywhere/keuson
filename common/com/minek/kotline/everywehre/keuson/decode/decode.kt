package com.minek.kotline.everywehre.keuson.decode

import com.minek.kotlin.everywhere.kelibs.result.Result

object Decoders {
    val string = com.minek.kotline.everywehre.keuson.decode.string
    val boolean = com.minek.kotline.everywehre.keuson.decode.boolean
    val int = com.minek.kotline.everywehre.keuson.decode.int
    val long = com.minek.kotline.everywehre.keuson.decode.long
}

fun <T> decodeString(decoder: Decoder<T>, jsonString: String): Result<String, T> {
    return decoder(parse(jsonString))
}