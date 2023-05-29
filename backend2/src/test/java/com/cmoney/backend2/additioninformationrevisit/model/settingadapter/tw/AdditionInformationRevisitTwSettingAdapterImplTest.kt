package com.cmoney.backend2.additioninformationrevisit.model.settingadapter.tw

import com.cmoney.backend2.base.model.setting.backend.BackendSetting
import com.cmoney.core.CoroutineTestRule
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AdditionInformationRevisitTwSettingAdapterImplTest {

    private val testScope = TestScope()

    @get:Rule
    var mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    private lateinit var settingAdapter: AdditionInformationRevisitTwSettingAdapter
    @MockK(relaxed = true)
    private lateinit var backendSetting: BackendSetting

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        settingAdapter = AdditionInformationRevisitTwSettingAdapterImpl(
            backendSetting = backendSetting
        )
    }

    @Test
    fun getDomain() {
        coEvery { backendSetting.getDomainUrl() } returns "https://www.cmoney.tw"
        val domain = settingAdapter.getDomain()
        Truth.assertThat(domain).isEqualTo("https://www.cmoney.tw")
    }

    @Test
    fun getPathName() {
        val pathName = settingAdapter.getPathName()
        Truth.assertThat(pathName).isEqualTo("AdditionInformationRevisit/")
    }
}