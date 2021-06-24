package com.cmoney.backend2.common.extension

import com.google.common.truth.Truth
import org.junit.Test

class StringKtTest {

    @Test
    fun md5() {
        val actual = "1234".md5()
        Truth.assertThat(actual).isEqualTo("81dc9bdb52d04dc20036dbd8313ed055")
    }
}