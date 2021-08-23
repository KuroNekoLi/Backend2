package com.cmoney.backend2.base.extension

import com.cmoney.backend2.base.model.request.Language
import com.google.common.truth.Truth
import org.junit.Test

class LanguageExtKtTest {

    @Test
    fun asRequestHeader() {
        val expect = "*;q=1.0"
        val result = Language().asRequestHeader()
        Truth.assertThat(result).isEqualTo(expect)
    }

    @Test
    fun listAsRequestHeader() {
        val expect = "en-US;q=1.0,zh-TW;q=0.9"
        val result = listOf(Language.enUs(1.0), Language.zhTw(0.9)).asRequestHeader()
        Truth.assertThat(result).isEqualTo(expect)
    }
}