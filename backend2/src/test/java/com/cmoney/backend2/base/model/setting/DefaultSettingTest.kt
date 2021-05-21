package com.cmoney.backend2.base.model.setting

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DefaultSettingTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private lateinit var setting: Setting

    @Before
    fun setUp() {
        val sharedPreference = BackendSettingSharedPreference(
            context.getSharedPreferences(
                BackendSettingSharedPreference.TAG,
                Context.MODE_PRIVATE
            )
        )
        setting = DefaultSetting(sharedPreference)
    }

    @Test
    fun `getDomainUrl_設定完後取資料_上次設定的資料`() {
        val domain = "https://google.com"
        setting.domainUrl = domain
        Truth.assertThat(setting.domainUrl).isEqualTo(domain)
    }
}