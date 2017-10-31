package com.minek.kotlin.everywehre

import com.minek.kotlin.everywehre.keuson.convert.Converters
import com.minek.kotlin.everywehre.keuson.conveter.javaConverter
import com.minek.kotlin.everywehre.keuson.decode.Decoders
import com.minek.kotlin.everywehre.keuson.decode.decodeString
import com.minek.kotlin.everywehre.keuson.decode.javaDecoder
import com.minek.kotlin.everywehre.keuson.encode.Encoders
import com.minek.kotlin.everywehre.keuson.encode.encode
import com.minek.kotlin.everywehre.keuson.encode.javaEncoder
import com.minek.kotlin.everywhere.kelibs.result.ok
import org.junit.Assert.assertEquals
import org.junit.Test

class TestJavaConverter {
    @Test
    fun testEncoder() {
        assertEquals(
                """{"first":1,"second":"2"}""",
                encode((Encoders.pair(javaEncoder<Int>(), javaEncoder<String>()))(1 to "2"))
        )
    }

    @Test
    fun testDecoder() {
        assertEquals(
                ok(1 to "2"),
                decodeString(Decoders.pair(javaDecoder<Int>(), javaDecoder<String>()), """{"first":1,"second":"2"}""")
        )
    }

    @Test
    fun testConverter() {
        val ic = javaConverter<Int>()
        val sc = javaConverter<String>()
        val (encoder, decoder) = Converters.pair(ic, sc)
        assertEquals(ok(1 to "2"), decodeString(decoder, encode(encoder(1 to "2"))))
    }
}
