package com.minek.kotlin.everywehre.keuson.conveter

import com.minek.kotlin.everywehre.keuson.convert.Converter
import com.minek.kotlin.everywehre.keuson.decode.javaDecoder
import com.minek.kotlin.everywehre.keuson.encode.javaEncoder

inline fun <reified T> javaConverter(): Converter<T> {
    return javaEncoder<T>() to javaDecoder()
}

