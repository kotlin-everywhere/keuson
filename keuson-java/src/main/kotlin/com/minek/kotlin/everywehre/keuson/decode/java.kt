package com.minek.kotlin.everywehre.keuson.decode

import com.google.gson.JsonSyntaxException
import com.minek.kotlin.everywehre.keuson.encode.gson
import com.minek.kotlin.everywhere.kelibs.result.Err
import com.minek.kotlin.everywhere.kelibs.result.Ok

inline fun <reified T> javaDecoder(): Decoder<T> {
    return javaDecoder(T::class.java)
}

fun <T> javaDecoder(clazz: Class<T>): Decoder<T> {
    return {
        try {
            Ok(gson.fromJson(it, clazz))
        } catch (e: JsonSyntaxException) {
            Err(e.localizedMessage)
        }
    }
}