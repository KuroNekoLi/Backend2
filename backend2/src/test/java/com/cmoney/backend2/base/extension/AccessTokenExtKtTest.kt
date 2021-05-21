package com.cmoney.backend2.base.extension

import com.cmoney.backend2.base.model.request.AccessToken
import com.google.common.truth.Truth
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AccessTokenExtKtTest {

    @Test
    fun createAuthorizationBearer() {
        val expect = "Bearer 123"
        val accessToken = AccessToken("123")
        val actual = accessToken.createAuthorizationBearer()
        Truth.assertThat(actual).isEqualTo(expect)
    }
}