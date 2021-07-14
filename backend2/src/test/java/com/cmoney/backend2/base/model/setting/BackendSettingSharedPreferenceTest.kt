package com.cmoney.backend2.base.model.setting

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class BackendSettingSharedPreferenceTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private lateinit var sharedPreference: BackendSettingSharedPreference

    @Before
    fun setUp() {
        sharedPreference = BackendSettingSharedPreference(
            context.getSharedPreferences(
                BackendSettingSharedPreference.TAG,
                Context.MODE_PRIVATE
            )
        )
    }

    @Test
    fun `getDomainUrl_設定完後取資料_上次設定的資料`() {
        val domain = "https://google.com"
        sharedPreference.domainUrl = domain
        Truth.assertThat(sharedPreference.domainUrl).isEqualTo(domain)
    }

    @Test
    fun `clear_清除資料後取Domain_是公司預設API domain`() {
        val domain = "https://google.com"
        sharedPreference.domainUrl = domain
        sharedPreference.clear()
        Truth.assertThat(sharedPreference.domainUrl).isEqualTo("https://www.cmoney.tw/")
    }
}