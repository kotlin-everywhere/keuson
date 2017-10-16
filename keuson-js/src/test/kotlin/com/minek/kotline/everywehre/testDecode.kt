package com.minek.kotline.everywehre

import com.minek.kotlin.everywehre.keuson.decode.Decoder
import com.minek.kotlin.everywehre.keuson.decode.Decoders.boolean
import com.minek.kotlin.everywehre.keuson.decode.Decoders.deserialize
import com.minek.kotlin.everywehre.keuson.decode.Decoders.fail
import com.minek.kotlin.everywehre.keuson.decode.Decoders.field
import com.minek.kotlin.everywehre.keuson.decode.Decoders.float
import com.minek.kotlin.everywehre.keuson.decode.Decoders.int
import com.minek.kotlin.everywehre.keuson.decode.Decoders.list
import com.minek.kotlin.everywehre.keuson.decode.Decoders.long
import com.minek.kotlin.everywehre.keuson.decode.Decoders.nullable
import com.minek.kotlin.everywehre.keuson.decode.Decoders.string
import com.minek.kotlin.everywehre.keuson.decode.Decoders.success
import com.minek.kotlin.everywehre.keuson.decode.Decoders.unit
import com.minek.kotlin.everywehre.keuson.decode.andThen
import com.minek.kotlin.everywehre.keuson.decode.decodeString
import com.minek.kotlin.everywehre.keuson.decode.map
import com.minek.kotlin.everywhere.kelibs.result.Ok
import com.minek.kotlin.everywhere.kelibs.result.err
import com.minek.kotlin.everywhere.kelibs.result.ok
import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable
import kotlin.test.Test
import kotlin.test.assertEquals


@Suppress("unused")
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

    @Test
    fun testUnit() {
        assertEquals(ok(Unit), decodeString(unit, "null"))
        assertEquals(err("Expecting a Unit but instead got: true"), decodeString(unit, "true"))
        assertEquals(err("Expecting a Unit but instead got: 42"), decodeString(unit, "42"))
        assertEquals(err("Expecting a Unit but instead got: 3.14"), decodeString(unit, "3.14"))
        assertEquals(err("Expecting a Unit but instead got: \"hello\""), decodeString(unit, "\"hello\""))
        assertEquals(err("Expecting a Unit but instead got: {\"hello\":42}"), decodeString(unit, "{ \"hello\": 42 }"))
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
    fun testDeserialize() {
        assertEquals(ok(Data(42)), decodeString(deserialize(), """{"a":42}"""))

        data class Box<out T>(val value: T)
        assertEquals(ok(Box(Data(42))), decodeString(map(field("value", deserialize<Data>()), ::Box), """{"value":{"a":42}}"""))
    }

    @Test
    fun testList() {
        assertEquals(ok(listOf("john", "tom")), decodeString(list(string), """["john", "tom"]"""))
        assertEquals(err("Expecting an Array but instead got: \"john, tom\""), decodeString(list(string), "\"john, tom\""))
        assertEquals(err("Expecting a String but instead got: 42"), decodeString(list(string), """["john", 42]"""))
    }

    @Test
    fun testSuccess() {
        assertEquals(ok(42), decodeString(success(42), "42"))
        assertEquals(ok(42), decodeString(success(42), "[1, 2, 3]"))
        assertEquals(err("SyntaxError: Unexpected token i in JSON at position 1"), decodeString(success(42), "{invalid}"))
    }

    @Test
    fun testFail() {
        assertEquals(err("42"), decodeString(fail<Any>("42"), "42"))
        assertEquals(err("42"), decodeString(fail<Any>("42"), "[1, 2, 3]"))
        assertEquals(err("SyntaxError: Unexpected token i in JSON at position 1"), decodeString(fail<Any>("42"), "{invalid}"))
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

    @Test
    fun testAndThen() {
        val lifeDecoder: Decoder<String> = andThen(int) { if (it == 42) success("life") else fail("no life") }
        assertEquals(ok("life"), decodeString(lifeDecoder, "42"))
        assertEquals(err("no life"), decodeString(lifeDecoder, "24"))
    }

    @Serializable
    data class Data(val a: Int, @Optional val b: String = "42")
}