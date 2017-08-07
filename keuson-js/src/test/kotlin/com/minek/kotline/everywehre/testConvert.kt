package com.minek.kotline.everywehre

import com.minek.kotlin.everywhere.kelibs.result.Ok
import com.minek.kotlin.everywhere.kelibs.result.Result
import com.minek.kotlin.everywhere.kelibs.result.ok
import com.minek.kotline.everywehre.keuson.convert.Converter
import com.minek.kotline.everywehre.keuson.convert.Converters
import com.minek.kotline.everywehre.keuson.convert.decoder
import com.minek.kotline.everywehre.keuson.convert.encoder
import com.minek.kotline.everywehre.keuson.decode.decodeString
import com.minek.kotline.everywehre.keuson.encode.encode
import org.junit.Test
import kotlin.test.assertEquals

private operator fun <T> Converter<T>.invoke(input: T): Result<String, T> {
    return decodeString(decoder, encode(encoder.invoke(input)))
}

class TestConvert {
    @Test
    fun testString() {
        assertEquals(ok("abc"), Converters.string("abc"))
    }

    @Test
    fun testInt() {
        assertEquals(ok(123), Converters.int(123))
    }

    @Test
    fun testLong() {
        assertEquals(ok(123L), Converters.long(123L))
    }

    @Test
    fun testFloat() {
        assertEquals(ok(3.14f), Converters.float(3.14f))
    }

    @Test
    fun testBoolean() {
        assertEquals(ok(true), Converters.boolean(true))
    }

    @Test
    fun testNullable() {
        assertEquals(Ok(null), Converters.nullable(Converters.int)(null))
        assertEquals(Ok(123), Converters.nullable(Converters.int)(123))
    }

    @Test
    fun testList() {
        assertEquals(ok(listOf(1, 2, 3)), Converters.list(Converters.int)(listOf(1, 2, 3)))
    }
}