package com.cmoney.backend2.mobileocean.service

import com.cmoney.backend2.mobileocean.MainCoroutineRule
import com.cmoney.backend2.mobileocean.TestSetting
import com.cmoney.backend2.mobileocean.service.api.createarticletoocean.requestbody.SubmitAdviceParam
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MobileOceanWebImplVerify {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private val service = mockk<MobileOceanService>()
    private val setting = TestSetting()
    private lateinit var webImpl: MobileOceanWeb

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        webImpl = MobileOceanWebImpl(service, setting, Dispatchers.Main)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `createArticleToOcean_成功`() = mainCoroutineRule.runBlockingTest{

        webImpl.createArticleToOcean(
            SubmitAdviceParam(
                "test",
                "123456",
                "123456",
                "123456"
            )
        )

        coVerify {
            service.createArticleToOcean(any(),any(),any(),any(),any(),any(),any(),any(),any())
        }
    }
}

