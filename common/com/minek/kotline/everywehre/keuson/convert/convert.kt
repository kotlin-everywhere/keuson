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

    fun <T> nullable(converter: Converter<T>): Converter<T?> {
        return Encoders.nullable(converter.encoder) to Decoders.nullable(converter.decoder)
    }

    fun <T> list(converter: Converter<T>): Converter<List<T>> {
        return { li: List<T> -> Encoders.list(li.map(converter.encoder)) } to Decoders.list(converter.decoder)
    }
}