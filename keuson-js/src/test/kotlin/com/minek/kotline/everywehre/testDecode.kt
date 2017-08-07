package com.minek.kotline.everywehre

import com.minek.kotlin.everywhere.kelibs.result.Ok
import com.minek.kotlin.everywhere.kelibs.result.err
import com.minek.kotlin.everywhere.kelibs.result.ok
import com.minek.kotline.everywehre.keuson.decode.Decoders
import com.minek.kotline.everywehre.keuson.decode.Decoders.field
import com.minek.kotline.everywehre.keuson.decode.Decoders.int
import com.minek.kotline.everywehre.keuson.decode.Decoders.nullable
import com.minek.kotline.everywehre.keuson.decode.Decoders.string
import com.minek.kotline.everywehre.keuson.decode.decodeString
import com.minek.kotline.everywehre.keuson.decode.map
import org.junit.Test
import kotlin.test.assertEquals

class TestDecode {
    @Test
    fun testString() {
        assertEquals(err("Expecting a String but instead got: null"), decodeString(string, "null"))
        assertEquals(err("Expecting a String but instead got: true"), decodeString(string, "true"))
        assertEquals(err("Expecting a String but instead got: 42"), decodeString(string, "42"))
        assertEquals(err("Expecting a String but instead got: 3.14"), decodeString(string, "3.14"))
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
        assertEquals(err("Expecting a Long but instead got: null"), decodeString(Decoders.long, "null"))
        assertEquals(err("Expecting a Long but instead got: true"), decodeString(Decoders.long, "true"))
        assertEquals(ok(42L), decodeString(Decoders.long, "42"))
        assertEquals(err("Expecting a Long but instead got: 3.14"), decodeString(Decoders.long, "3.14"))
        assertEquals(err("Expecting a Long but instead got: \"hello\""), decodeString(Decoders.long, "\"hello\""))
        assertEquals(err("Expecting a Long but instead got: {\"hello\":42}"), decodeString(Decoders.long, "{ \"hello\": 42 }"))
    }

    @Test
    fun testFloat() {
        assertEquals(err("Expecting a Float but instead got: null"), decodeString(Decoders.float, "null"))
        assertEquals(err("Expecting a Float but instead got: true"), decodeString(Decoders.float, "true"))
        assertEquals(err("Expecting a Float but instead got: 42"), decodeString(Decoders.float, "42"))
        assertEquals(ok(3.14f), decodeString(Decoders.float, "3.14"))
        assertEquals(err("Expecting a Float but instead got: \"hello\""), decodeString(Decoders.float, "\"hello\""))
        assertEquals(err("Expecting a Float but instead got: {\"hello\":42}"), decodeString(Decoders.float, "{ \"hello\": 42 }"))
    }

    @Test
    fun testNullable() {
        assertEquals(Ok<String, Int?>(null), decodeString(nullable(int), "null"))
        assertEquals(err("Expecting a Int but instead got: true"), decodeString(int, "true"))
    }

    @Test
    fun testField() {
        assertEquals(ok(3), decodeString(field("x", int), "{ \"x\": 3 }"))
        assertEquals(ok(3), decodeString(field("x", int), "{ \"x\": 3, \"y\": 4 }"))
        assertEquals(err("Expecting a Int but instead got: true"), decodeString(field("x", int), "{ \"x\": true }"))
        assertEquals(err("Expecting an object with a field named `x` but instead got: {\"y\":4}"), decodeString(field("x", int), "{ \"y\": 4 }"))
        assertEquals(err("Expecting an object with a field named `y` but instead got: {\"x\":4}"), decodeString(field("y", int), "{ \"x\": 4 }"))

        assertEquals(ok("tom"), decodeString(field("name", string), "{ \"name\": \"tom\" }"))
    }

    @Test
    fun map1() {
        assertEquals(ok("life"), decodeString(map(int) { if (it == 42) "life" else "no life" }, "42"))
        assertEquals(ok("no life"), decodeString(map(int) { if (it == 42) "life" else "no life" }, "41"))
    }

    @Test
    fun map2() {
        data class Point(val x: Int, val y: Int)

        val decoder = map(field("x", int), field("y", int), ::Point)
        assertEquals(ok(Point(x = 3, y = 4)), decodeString(decoder, """{ "x": 3, "y": 4 }"""))
    }

    @Test
    fun map3() {
        data class Point(val x: Int, val y: Int, val z: Int)

        val decoder = map(field("x", int), field("y", int), field("z", int), ::Point)
        assertEquals(ok(Point(x = 3, y = 4, z = 5)), decodeString(decoder, """{ "x": 3, "y": 4, "z": 5 }"""))
    }
}