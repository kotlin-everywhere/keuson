package com.minek.kotline.everywehre.keuson.convert

import com.minek.kotline.everywehre.keuson.decode.Decoder
import com.minek.kotline.everywehre.keuson.decode.Decoders
import com.minek.kotline.everywehre.keuson.encode.Encoder
import com.minek.kotline.everywehre.keuson.encode.Encoders

typealias Converter<T> = Pair<Encoder<T>, Decoder<T>>

val <T> Converter<T>.encoder: Encoder<T>
    get() = first

val <T> Converter<T>.decoder: Decoder<T>
    get() = second

object Converters {
    val string = Encoders.string to Decoders.string
    val boolean = Encoders.boolean to Decoders.boolean
    val int = Encoders.int to Decoders.int
    val long = Encoders.long to Decoders.long
    val float = Encoders.float to Decoders.float
}