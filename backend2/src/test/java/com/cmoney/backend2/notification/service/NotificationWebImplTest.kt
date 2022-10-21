package com.cmoney.backend2.notification.service

import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.core.CoroutineTestRule
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class NotificationWebImplTest {
    private val testScope = TestScope()
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var service: NotificationService
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var setting: Setting
    private lateinit var web: NotificationWeb

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        setting = TestSetting()
        web = NotificationWebImpl(
            gson = gson,
            service = service,
            setting = setting,
            dispatcher = TestDispatcher()
        )
    }

    @Test
    fun `updateArrivedCount_成功`() = testScope.runTest {
        coEvery {
            service.updateArriveCount(
                authorization = any(), body = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.updateArriveCount(
            sn = 0,
            pushToken = "",
            title = "",
            content = "",
            analyticsLabels = listOf("test"),
            createTime = 1001L
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(Unit)
    }

    @Test(expected = ServerException::class)
    fun `updateArrivedCount_失敗_ServerException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.updateArriveCount(
                authorization = any(), body = any()
            )
        } returns Response.error(400, errorBody)
        val result = web.updateArriveCount(
            sn = 0,
            pushToken = "",
            title = "",
            content = "",
            analyticsLabels = listOf("test"),
            createTime = 1001L
        )
        Truth.assertThat(result.isFailure).isTrue()
        result.getOrThrow()
    }

    @Test
    fun `updateClickCount_成功`() = testScope.runTest {
        coEvery {
            service.updateClickCount(
                authorization = any(), body = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.updateClickCount(
            sn = 0,
            pushToken = "",
            title = "",
            content = "",
            analyticsLabels = listOf("test"),
            createTime = 1001L
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(Unit)
    }

    @Test(expected = ServerException::class)
    fun `updateClickCount_失敗_ServerException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.updateClickCount(
                authorization = any(), body = any()
            )
        } returns Response.error(400, errorBody)
        val result = web.updateClickCount(
            sn = 0,
            pushToken = "",
            title = "",
            content = "",
            analyticsLabels = listOf("test"),
            createTime = 1001L
        )
        Truth.assertThat(result.isFailure).isTrue()
        result.getOrThrow()
    }

    @Test
    fun `updateGuestPushToken_成功`() = testScope.runTest {
        coEvery {
            service.updateGuestPushToken(
                authorization = any(), body = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.updateGuestPushToken(pushToken = "")
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(Unit)
    }

    @Test(expected = ServerException::class)
    fun `updateGuestPushToken_失敗_ServerException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.updateGuestPushToken(
                authorization = any(), body = any()
            )
        } returns Response.error(400, errorBody)
        val result = web.updateGuestPushToken(pushToken = "")
        Truth.assertThat(result.isFailure).isTrue()
        result.getOrThrow()
    }

    @Test
    fun `updateMemberPushToken_成功`() = testScope.runTest {
        coEvery {
            service.updateMemberPushToken(
                authorization = any(), body = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.updateMemberPushToken(pushToken = "")
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(Unit)
    }

    @Test(expected = ServerException::class)
    fun `updateMemberPushToken_失敗_ServerException`() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.updateMemberPushToken(
                authorization = any(), body = any()
            )
        } returns Response.error(400, errorBody)
        val result = web.updateMemberPushToken(pushToken = "")
        Truth.assertThat(result.isFailure).isTrue()
        result.getOrThrow()
    }

    @Test
    fun `deprecate updateArriveCount will invoke new function`() = testScope.runTest {
        coEvery {
            service.updateArriveCount(
                authorization = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)

        val spyWeb = spyk(web)
        val result = spyWeb.updateArriveCount(
            sn = 0,
            pushToken = "",
            analyticsId = 0,
            title = "",
            content = ""
        )

        coVerifyOrder {
            spyWeb.updateArriveCount(
                sn = any(),
                pushToken = any(),
                analyticsId = any(),
                title = any(),
                content = any()
            )
            spyWeb.updateArriveCount(
                sn = any(),
                pushToken = any(),
                title = any(),
                content = any(),
                analyticsLabels = any(),
                createTime = any()
            )
        }
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `deprecate updateClickCount will invoke new function`() = testScope.runTest {
        coEvery {
            service.updateClickCount(
                authorization = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)

        val spyWeb = spyk(web)
        val result = spyWeb.updateClickCount(
            sn = 0,
            pushToken = "",
            analyticsId = 0,
            title = "",
            content = ""
        )

        coVerifyOrder {
            spyWeb.updateClickCount(
                sn = any(),
                pushToken = any(),
                analyticsId = any(),
                title = any(),
                content = any()
            )
            spyWeb.updateClickCount(
                sn = any(),
                pushToken = any(),
                title = any(),
                content = any(),
                analyticsLabels = any(),
                createTime = any()
            )
        }
        Truth.assertThat(result.isSuccess).isTrue()
    }
}