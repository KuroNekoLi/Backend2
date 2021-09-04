package com.cmoney.backend2.cmtalk.service

import com.cmoney.backend2.MainCoroutineRule
import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.cmtalk.service.api.TargetMediaListInfo
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.util.*

class CMTalkWebImplTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()
    private lateinit var apiParam: MemberApiParam

    @MockK
    private val service = mockk<CMTalkService>()
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var webImpl: CMTalkWeb

    @Before
    fun setUp() {
        apiParam = MemberApiParam(99, UUID.randomUUID().toString(), UUID.randomUUID().toString())
        MockKAnnotations.init(this)
        webImpl = CMTalkWebImpl(service, TestDispatcher())
    }

    @Test
    fun `getTargetMediaList_response code is 1_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = TargetMediaListInfo(
            listOf(),
            1,
            ""
        )
        coEvery {
            service.getTargetMediaList(
                mediaType = any(),
                baseId = any(),
                fetchSize = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getTargetMediaList(0, 0, 0)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()?.responseCode).isEqualTo(1)
    }

    @Test
    fun `getTargetMediaList_失敗`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.getTargetMediaList(
                mediaType = any(),
                baseId = any(),
                fetchSize = any()
            )
        } returns Response.error(400, "".toResponseBody())
        val result = webImpl.getTargetMediaList(0, 0, 0)
        Truth.assertThat(result.isSuccess).isFalse()
    }


    @After
    fun tearDown() {

    }
}