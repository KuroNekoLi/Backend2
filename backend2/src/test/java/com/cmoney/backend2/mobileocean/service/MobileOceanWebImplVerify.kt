package com.cmoney.backend2.mobileocean.service

import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.mobileocean.service.api.createarticletoocean.requestbody.SubmitAdviceParam
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.extension.runTest
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    val mainCoroutineRule = CoroutineTestRule()

    @MockK
    private lateinit var service: MobileOceanService
    private lateinit var webImpl: MobileOceanWeb

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        webImpl = MobileOceanWebImpl(service, TestSetting(), TestDispatcher())
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `createArticleToOcean_成功`() = mainCoroutineRule.runTest {

        webImpl.createArticleToOcean(
            SubmitAdviceParam(
                "test",
                "123456",
                "123456",
                "123456"
            )
        )

        coVerify {
            service.createArticleToOcean(
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        }
    }
}

