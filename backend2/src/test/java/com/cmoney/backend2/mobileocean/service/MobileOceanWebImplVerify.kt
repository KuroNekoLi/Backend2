package com.cmoney.backend2.mobileocean.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.mobileocean.service.api.createarticletoocean.requestbody.SubmitAdviceParam
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class MobileOceanWebImplVerify {

    private val testScope = TestScope()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var service: MobileOceanService
    private lateinit var webImpl: MobileOceanWeb

    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        webImpl = MobileOceanWebImpl(
            manager = manager,
            service = service,
            dispatcher = TestDispatcherProvider()
        )
        coEvery {
            manager.getMobileOceanSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `createArticleToOcean_成功`() = testScope.runTest {

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
                url = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                device = any(),
                content = any(),
                osVersion = any(),
                appVersion = any(),
                deviceName = any()
            )
        }
    }

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }
}

