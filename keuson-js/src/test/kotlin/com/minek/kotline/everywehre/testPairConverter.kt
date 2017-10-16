package com.minek.kotline.everywehre

import com.minek.kotlin.everywehre.keuson.convert.Converters
import com.minek.kotlin.everywehre.keuson.decode.Decoders
import com.minek.kotlin.everywehre.keuson.decode.decodeString
import com.minek.kotlin.everywehre.keuson.encode.Encoders
import com.minek.kotlin.everywehre.keuson.encode.encode
import com.minek.kotlin.everywhere.kelibs.result.ok
import org.junit.Test
import kotlin.test.assertEquals

class TestPairConverter {
    @Test
    fun testEncoder() {
        assertEquals("""{"first":1,"second":2}""", encode((Encoders.pair(Encoders.int, Encoders.int))(1 to 2)))
    }

    @Test
    fun testDecoder() {
        assertEquals(ok(1 to 2), decodeString(Decoders.pair(Decoders.int, Decoders.int), """{"first":1,"second":2}"""))
    }

    @Test
    fun testConverter() {
        val (encoder, decoder) = Converters.pair(Converters.int, Converters.int)
        assertEquals(ok(1 to 2), decodeString(decoder, encode(encoder(1 to 2))))
    }
}