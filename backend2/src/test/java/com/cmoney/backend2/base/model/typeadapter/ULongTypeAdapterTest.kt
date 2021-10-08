package com.cmoney.backend2.base.model.typeadapter

import com.google.common.truth.Truth
import com.google.gson.GsonBuilder

import org.junit.Test

@ExperimentalUnsignedTypes
class ULongTypeAdapterTest {

    private val gson = GsonBuilder()
        .registerTypeAdapter(ULong::class.java, ULongTypeAdapter())
        .create()

    @Test
    fun `write ULong`() {
        val uLongString = "18446744073709551614"
        val uLong = uLongString.toULong()
        val result = gson.toJson(uLong)
        Truth.assertThat(uLongString == result).isTrue()
    }

    @Test
    fun `read ULong`() {
        val uLongString = "18446744073709551613"
        val uLong = uLongString.toULong()
        val result = gson.fromJson(uLongString, ULong::class.java)
        Truth.assertThat(uLong == result).isTrue()
    }

}
