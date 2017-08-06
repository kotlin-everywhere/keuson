package com.minek.kotline.everywehre

import com.minek.kotlin.everywhere.kelibs.result.err
import com.minek.kotlin.everywhere.kelibs.result.ok
import com.minek.kotline.everywehre.keuson.decode.Decoders.boolean
import com.minek.kotline.everywehre.keuson.decode.Decoders.string
import com.minek.kotline.everywehre.keuson.decode.Decoders.int
import com.minek.kotline.everywehre.keuson.decode.Decoders.long
import com.minek.kotline.everywehre.keuson.decode.Decoders.float
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

    @Test
    fun testInt() {
        assertEquals(err("Expecting a Int but instead got: null"), decodeString(int, "null"))
        assertEquals(err("Expecting a Int but instead got: true"), decodeString(int, "true"))
        assertEquals(ok(42), decodeString(int, "42"))
        assertEquals(err("Expecting a Int but instead got: 3.14"), decodeString(int, "3.14"))
        assertEquals(err("Expecting a Int but instead got: \"hello\""), decodeString(int, "\"hello\""))
        assertEquals(err("Expecting a Int but instead got: {\"hello\":42}"), decodeString(int, "{ \"hello\": 42 }"))
    }

    @Test
    fun testLong() {
        assertEquals(err("Expecting a Long but instead got: null"), decodeString(long, "null"))
        assertEquals(err("Expecting a Long but instead got: true"), decodeString(long, "true"))
        assertEquals(ok(42L), decodeString(long, "42"))
        assertEquals(err("Expecting a Long but instead got: 3.14"), decodeString(long, "3.14"))
        assertEquals(err("Expecting a Long but instead got: \"hello\""), decodeString(long, "\"hello\""))
        assertEquals(err("Expecting a Long but instead got: {\"hello\":42}"), decodeString(long, "{ \"hello\": 42 }"))
    }

    @Test
    fun testFloat() {
        assertEquals(err("Expecting a Float but instead got: null"), decodeString(float, "null"))
        assertEquals(err("Expecting a Float but instead got: true"), decodeString(float, "true"))
        assertEquals(err("Expecting a Float but instead got: 42"), decodeString(float, "42"))
        assertEquals(ok(3.14f), decodeString(float, "3.14"))
        assertEquals(err("Expecting a Float but instead got: \"hello\""), decodeString(float, "\"hello\""))
        assertEquals(err("Expecting a Float but instead got: {\"hello\":42}"), decodeString(float, "{ \"hello\": 42 }"))
    }
}