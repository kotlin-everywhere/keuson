package com.minek.kotline.everywehre.keuson

import com.minek.kotlin.everywhere.kelibs.result.err
import com.minek.kotlin.everywhere.kelibs.result.ok
import com.minek.kotline.everywehre.keuson.decode.Decoders
import com.minek.kotline.everywehre.keuson.decode.decodeString
import org.junit.Test
import kotlin.test.assertEquals

class TestDecode {
    @Test
    fun testString() {
        assertEquals(err("Expecting a String but instead got: null"), decodeString(Decoders.string, "null"))
        assertEquals(err("Expecting a String but instead got: true"), decodeString(Decoders.string, "true"))
        assertEquals(err("Expecting a String but instead got: 42"), decodeString(Decoders.string, "42"))
        assertEquals(err("Expecting a String but instead got: 3.14"), decodeString(Decoders.string, "3.14"))
        assertEquals(ok("hello"), decodeString(Decoders.string, "\"hello\""))
        assertEquals(err("Expecting a String but instead got: {\"hello\":42}"), decodeString(Decoders.string, "{ \"hello\": 42 }"))
    }

    @Test
    fun testBoolean() {
        assertEquals(err("Expecting a Boolean but instead got: null"), decodeString(Decoders.boolean, "null"))
        assertEquals(ok(true), decodeString(Decoders.boolean, "true"))
        assertEquals(err("Expecting a Boolean but instead got: 42"), decodeString(Decoders.boolean, "42"))
        assertEquals(err("Expecting a Boolean but instead got: 3.14"), decodeString(Decoders.boolean, "3.14"))
        assertEquals(err("Expecting a Boolean but instead got: \"hello\""), decodeString(Decoders.boolean, "\"hello\""))
        assertEquals(err("Expecting a Boolean but instead got: {\"hello\":42}"), decodeString(Decoders.boolean, "{ \"hello\": 42 }"))
    }
}