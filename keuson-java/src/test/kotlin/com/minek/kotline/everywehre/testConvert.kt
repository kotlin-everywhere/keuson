package com.minek.kotline.everywehre

import com.minek.kotlin.everywhere.kelibs.result.Result
import com.minek.kotlin.everywhere.kelibs.result.ok
import com.minek.kotline.everywehre.keuson.convert.Converter
import com.minek.kotline.everywehre.keuson.convert.Converters
import com.minek.kotline.everywehre.keuson.convert.decoder
import com.minek.kotline.everywehre.keuson.convert.encoder
import com.minek.kotline.everywehre.keuson.decode.decodeString
import com.minek.kotline.everywehre.keuson.encode.encode
import org.junit.Assert
import org.junit.Test

private operator fun <T> Converter<T>.invoke(input: T): Result<String, T> {
    return decodeString(decoder, encode(encoder.invoke(input)))
}

class TestConvert {
    @Test
    fun testString() {
        Assert.assertEquals(ok("abc"), Converters.string("abc"))
    }

    @Test
    fun testInt() {
        Assert.assertEquals(ok(123), Converters.int(123))
    }

    @Test
    fun testLong() {
        Assert.assertEquals(ok(123L), Converters.long(123L))
    }

    @Test
    fun testFloat() {
        Assert.assertEquals(ok(3.14f), Converters.float(3.14f))
    }

    @Test
    fun testBoolean() {
        Assert.assertEquals(ok(true), Converters.boolean(true))
    }

    @Test
    fun testNullable() {
        Assert.assertEquals(ok(null), Converters.nullable(Converters.int)(null))
        Assert.assertEquals(ok(123), Converters.nullable(Converters.int)(123))
    }
}