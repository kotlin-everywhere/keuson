package com.minek.kotlin.everywehre

import com.minek.kotlin.everywehre.keuson.encode.Encoders.boolean
import com.minek.kotlin.everywehre.keuson.encode.Encoders.float
import com.minek.kotlin.everywehre.keuson.encode.Encoders.int
import com.minek.kotlin.everywehre.keuson.encode.Encoders.list
import com.minek.kotlin.everywehre.keuson.encode.Encoders.long
import com.minek.kotlin.everywehre.keuson.encode.Encoders.nullable
import com.minek.kotlin.everywehre.keuson.encode.Encoders.object_
import com.minek.kotlin.everywehre.keuson.encode.Encoders.string
import com.minek.kotlin.everywehre.keuson.encode.encode
import com.minek.kotlin.everywehre.keuson.encode.unit
import org.junit.Assert.assertEquals
import org.junit.Test

class TestEncode {
    @Test
    fun testString() {
        assertEquals("\"hello\"", encode(string("hello")))
    }

    @Test
    fun testInt() {
        assertEquals("42", encode(int(42)))
    }

    @Test
    fun testLong() {
        assertEquals("42", encode(long(42)))
    }

    @Test
    fun testFloat() {
        assertEquals("3.14", encode(float(3.14f)))
    }

    @Test
    fun testBool() {
        assertEquals("false", encode(boolean(false)))
    }

    @Test
    fun testUnit() {
        assertEquals("null", encode(unit(Unit)))
    }

    @Test
    fun testObject() {
        assertEquals(
                """{"message":"hello","age":42}""",
                encode(object_("message" to string("hello"), "age" to int(42)))
        )
    }

    @Test
    fun testArray() {
        assertEquals("[1,2,3]", encode(list(listOf(1, 2, 3).map(int))))
    }

    @Test
    fun testNullable() {
        assertEquals("1", encode(nullable(int)(1)))
        assertEquals("null", encode(nullable(int)(null)))
        assertEquals(
                """{"name":"john","age":null}""",
                encode(object_("name" to string("john"), "age" to nullable(int)(null)))
        )
    }
}

