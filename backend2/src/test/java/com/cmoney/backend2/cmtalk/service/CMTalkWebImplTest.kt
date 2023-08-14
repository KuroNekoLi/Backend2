package com.cmoney.backend2.cmtalk.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.cmtalk.service.api.TargetMediaListInfo
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class CMTalkWebImplTest {

    private val testScope = TestScope()
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private val service = mockk<CMTalkService>()
    private lateinit var cmTalkWeb: CMTalkWeb

    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        cmTalkWeb = CMTalkWebImpl(
            manager = manager,
            service = service,
            dispatcherProvider = TestDispatcherProvider()
        )
        coEvery {
            manager.getCMTalkSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @Test
    fun `getTargetMediaList_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}CMTalk/Ashx/media.ashx"
        val urlSlot = slot<String>()
        val responseBody = TargetMediaListInfo(
            listOf(),
            1,
            ""
        )
        coEvery {
            service.getTargetMediaList(
                url = capture(urlSlot),
                mediaType = any(),
                baseId = any(),
                fetchSize = any()
            )
        } returns Response.success(responseBody)

        cmTalkWeb.getTargetMediaList(0, 0, 0)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `getTargetMediaList_response code is 1_成功`() = testScope.runTest {
        val responseBody = TargetMediaListInfo(
            listOf(),
            1,
            ""
        )
        coEvery {
            service.getTargetMediaList(
                url = any(),
                mediaType = any(),
                baseId = any(),
                fetchSize = any()
            )
        } returns Response.success(responseBody)

        val result = cmTalkWeb.getTargetMediaList(0, 0, 0)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()?.responseCode).isEqualTo(1)
    }

    @Test
    fun `getTargetMediaList_失敗`() = testScope.runTest {
        coEvery {
            service.getTargetMediaList(
                url = any(),
                mediaType = any(),
                baseId = any(),
                fetchSize = any()
            )
        } returns Response.error(400, "".toResponseBody())
        val result = cmTalkWeb.getTargetMediaList(0, 0, 0)
        Truth.assertThat(result.isSuccess).isFalse()
    }

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }
}