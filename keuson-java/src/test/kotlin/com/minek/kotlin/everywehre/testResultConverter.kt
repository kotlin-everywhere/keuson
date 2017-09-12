package com.minek.kotlin.everywehre

import com.minek.kotlin.everywehre.keuson.encode.Encoders
import com.minek.kotlin.everywehre.keuson.encode.encode
import com.minek.kotlin.everywhere.kelibs.result.Err
import com.minek.kotlin.everywhere.kelibs.result.Ok
import org.junit.Assert
import org.junit.Test

class TestResultConverter {
    @Test
    fun testEncoder() {
        val encoder = Encoders.result(Encoders.string, Encoders.int)
        Assert.assertEquals("{\"type\":\"Ok\",\"value\":1}", encode(encoder(Ok(1))))
        Assert.assertEquals("{\"type\":\"Err\",\"error\":\"not int\"}", encode(encoder(Err("not int"))))
    }
}