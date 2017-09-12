package com.minek.kotlin.everywehre

import com.minek.kotlin.everywehre.keuson.convert.Converters
import com.minek.kotlin.everywehre.keuson.decode.Decoders
import com.minek.kotlin.everywehre.keuson.decode.decodeString
import com.minek.kotlin.everywehre.keuson.encode.Encoders
import com.minek.kotlin.everywehre.keuson.encode.encode
import com.minek.kotlin.everywhere.kelibs.result.Err
import com.minek.kotlin.everywhere.kelibs.result.Ok
import com.minek.kotlin.everywhere.kelibs.result.err
import com.minek.kotlin.everywhere.kelibs.result.ok
import org.junit.Assert.assertEquals
import org.junit.Test

class TestResultConverter {
    @Test
    fun testEncoder() {
        val encoder = Encoders.result(Encoders.string, Encoders.int)
        assertEquals("{\"type\":\"Ok\",\"value\":1}", encode(encoder(Ok(1))))
        assertEquals("{\"type\":\"Err\",\"error\":\"not int\"}", encode(encoder(Err("not int"))))
    }

    @Test
    fun testDecoder() {
        val decoder = Decoders.result(Decoders.string, Decoders.int)
        assertEquals(ok(ok(1)), decodeString(decoder, "{\"type\":\"Ok\",\"value\":1}"))
        assertEquals(ok(err("not int")), decodeString(decoder, "{\"type\":\"Err\",\"error\":\"not int\"}"))
        assertEquals(err("Expecting Ok or Err but instead got: Result"), decodeString(decoder, "{\"type\":\"Result\",\"error\":\"not int\"}"))
    }

    @Test
    fun testConverter() {
        val (encoder, decoder) = Converters.result(Converters.string, Converters.int)
        assertEquals(ok(ok(1)), decodeString(decoder, encode(encoder(Ok(1)))))
        assertEquals(ok(err("not int")), decodeString(decoder, encode(encoder(Err("not int")))))
    }
}