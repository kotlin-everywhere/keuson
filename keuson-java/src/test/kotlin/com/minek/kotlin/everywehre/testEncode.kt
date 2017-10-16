package com.minek.kotlin.everywehre

import com.minek.kotlin.everywehre.keuson.encode.Encoders
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
import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable
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
    }

    @Test
    fun testSerialize() {

        assertEquals("""{"a":42,"b":"42"}""", encode(Encoders.serialize(Data(42))))

        assertEquals("""{"value":{"a":42,"b":"42"}}""", encode(object_("value" to Encoders.serialize(Data(42)))))
    }

    @Serializable data class Data(val a: Int, @Optional val b: String = "42")
}

