package com.cmoney.backend2.notification.service

import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.notification.MainCoroutineRule
import com.cmoney.backend2.notification.TestDispatcher
import com.cmoney.backend2.notification.TestSetting
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
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

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

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
    fun `updateArrivedCount_成功`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.updateArriveCount(
                authorization = any(), body = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.updateArriveCount(
            sn = 0,
            pushToken = "",
            title = "",
            content = "", analyticsId = 0
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(Unit)
    }

    @Test(expected = ServerException::class)
    fun `updateArrivedCount_失敗_ServerException`() = mainCoroutineRule.runBlockingTest {
        val errorBody = gson.toJson(
            CMoneyError(
                CMoneyError.Detail(
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
            content = "", analyticsId = 0
        )
        Truth.assertThat(result.isFailure).isTrue()
        result.getOrThrow()
    }

    @Test
    fun `updateClickCount_成功`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.updateClickCount(
                authorization = any(), body = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.updateClickCount(
            sn = 0,
            pushToken = "",
            title = "",
            content = "", analyticsId = 0
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(Unit)
    }

    @Test(expected = ServerException::class)
    fun `updateClickCount_失敗_ServerException`() = mainCoroutineRule.runBlockingTest {
        val errorBody = gson.toJson(
            CMoneyError(
                CMoneyError.Detail(
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
            content = "", analyticsId = 0
        )
        Truth.assertThat(result.isFailure).isTrue()
        result.getOrThrow()
    }

    @Test
    fun `updateGuestPushToken_成功`() = mainCoroutineRule.runBlockingTest {
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
    fun `updateGuestPushToken_失敗_ServerException`() = mainCoroutineRule.runBlockingTest {
        val errorBody = gson.toJson(
            CMoneyError(
                CMoneyError.Detail(
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
    fun `updateMemberPushToken_成功`() = mainCoroutineRule.runBlockingTest {
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
    fun `updateMemberPushToken_失敗_ServerException`() = mainCoroutineRule.runBlockingTest {
        val errorBody = gson.toJson(
            CMoneyError(
                CMoneyError.Detail(
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
}