package com.cmoney.backend2.additioninformationrevisit.model.settingadapter.us

import com.cmoney.core.CoroutineTestRule
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AdditionInformationRevisitUsSettingAdapterImplTest {

    private val testScope = TestScope()

    @get:Rule
    var mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    private lateinit var settingAdapter: AdditionInformationRevisitUsSettingAdapter

    @Before
    fun setUp() {
        settingAdapter = AdditionInformationRevisitUsSettingAdapterImpl()
    }

    @Test
    fun getDomain_internal() {
        val domain = settingAdapter.getDomain()
        Truth.assertThat(domain).isEqualTo("https://internal.cmoney.tw/")
    }

    @Test
    fun getPathName() {
        val pathName = settingAdapter.getPathName()
        Truth.assertThat(pathName).isEqualTo("AdditionInformationRevisit/")
    }
}