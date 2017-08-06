package com.minek.kotline.everywehre

import com.minek.kotlin.everywhere.kelibs.result.err
import com.minek.kotlin.everywhere.kelibs.result.ok
import com.minek.kotline.everywehre.keuson.decode.Decoders.boolean
import com.minek.kotline.everywehre.keuson.decode.Decoders.string
import com.minek.kotline.everywehre.keuson.decode.decodeString
import org.junit.Assert.assertEquals
import org.junit.Test

class TestDecode {
    @Test
    fun testString() {
        assertEquals(err("Expecting a String but instead got: null"), decodeString(string, "null"))
        assertEquals(err("Expecting a String but instead got: true"), decodeString(string, "true"))
        assertEquals(err("Expecting a String but instead got: 42"), decodeString(string, "42"))
        assertEquals(err("Expecting a String but instead got: 3.14"), decodeString(string, "3.14"))
        assertEquals(ok("hello"), decodeString(string, "\"hello\""))
        assertEquals(err("Expecting a String but instead got: {\"hello\":42}"), decodeString(string, "{ \"hello\": 42 }"))
    }

    @Test
    fun testBoolean() {
        assertEquals(err("Expecting a Boolean but instead got: null"), decodeString(boolean, "null"))
        assertEquals(ok(true), decodeString(boolean, "true"))
        assertEquals(err("Expecting a Boolean but instead got: 42"), decodeString(boolean, "42"))
        assertEquals(err("Expecting a Boolean but instead got: 3.14"), decodeString(boolean, "3.14"))
        assertEquals(err("Expecting a Boolean but instead got: \"hello\""), decodeString(boolean, "\"hello\""))
        assertEquals(err("Expecting a Boolean but instead got: {\"hello\":42}"), decodeString(boolean, "{ \"hello\": 42 }"))
    }
}