package com.minek.kotline.everywehre

import com.minek.kotlin.everywehre.keuson.convert.Converter
import com.minek.kotlin.everywehre.keuson.convert.Converters
import com.minek.kotlin.everywehre.keuson.convert.decoder
import com.minek.kotlin.everywehre.keuson.convert.encoder
import com.minek.kotlin.everywehre.keuson.decode.Decoder
import com.minek.kotlin.everywehre.keuson.decode.Decoders
import com.minek.kotlin.everywehre.keuson.decode.map
import com.minek.kotlin.everywehre.keuson.encode.Encoder
import com.minek.kotlin.everywehre.keuson.encode.Encoders
import com.minek.kotlin.everywehre.keuson.encode.encode
import com.minek.kotlin.everywhere.kelibs.result.Ok
import com.minek.kotlin.everywhere.kelibs.result.Result
import com.minek.kotlin.everywhere.kelibs.result.ok
import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON
import kotlin.test.Test
import kotlin.test.assertEquals

private operator fun <T> Converter<T>.invoke(input: T): Result<String, T> {
    return com.minek.kotlin.everywehre.keuson.decode.decodeString(decoder, com.minek.kotlin.everywehre.keuson.encode.encode(encoder.invoke(input)))
}

@Suppress("unused")
class TestConvert {
    @Test
    fun testString() {
        assertEquals(ok("abc"), Converters.string("abc"))
    }

    @Test
    fun testInt() {
        assertEquals(ok(123), Converters.int(123))
    }

    @Test
    fun testLong() {
        assertEquals(ok(123L), Converters.long(123L))
    }

    @Test
    fun testFloat() {
        assertEquals(ok(3.14f), Converters.float(3.14f))
    }

    @Test
    fun testBoolean() {
        assertEquals(ok(true), Converters.boolean(true))
    }

    @Test
    fun testUnit() {
        assertEquals(ok(Unit), Converters.unit(Unit))
    }

    @Test
    fun testNullable() {
        assertEquals(Ok(null), Converters.nullable(Converters.int)(null))
        assertEquals(Ok(123), Converters.nullable(Converters.int)(123))
    }

    @Test
    fun testList() {
        assertEquals(ok(listOf(1, 2, 3)), Converters.list(Converters.int)(listOf(1, 2, 3)))
    }

    @Test
    fun testSerializable() {
        val converter = Converters.serializable<Data>()
        assertEquals(ok(Data(42)), converter(Data(42)))

        data class Box<out T>(val value: T)

        val encoder: Encoder<Box<Data>> = { Encoders.object_("value" to converter.first(it.value)) }
        val decoder: Decoder<Box<Data>> = map(Decoders.field("value", converter.decoder), ::Box)
        val convert: Converter<Box<Data>> = encoder to decoder
        assertEquals(ok(Box(Data(42))), convert(Box(Data(42))))
    }

    @Serializable data class Data(val a: Int, @Optional val b: String = "42")
}