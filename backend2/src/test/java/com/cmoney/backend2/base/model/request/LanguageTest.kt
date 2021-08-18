package com.cmoney.backend2.base.model.request

import org.junit.Test

class LanguageTest {

    @Test(expected = IllegalArgumentException::class)
    fun `quality set over 1_0 will failed`() {
        Language(quality = 2.0)
    }
}