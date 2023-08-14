package com.cmoney.backend2.notification.service

import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.slot
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
    private lateinit var web: NotificationWeb

    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        web = NotificationWebImpl(
            manager = manager,
            gson = gson,
            service = service,
            dispatcher = TestDispatcherProvider()
        )
        coEvery {
            manager.getNotificationSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @Test
    fun `updateArrivedCount_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}NotificationService/Statistics/arrived"
        val urlSlot = slot<String>()
        coEvery {
            service.updateArriveCount(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)
        web.updateArriveCount(
            sn = 0,
            pushToken = "",
            title = "",
            content = "",
            analyticsLabels = listOf("test"),
            createTime = 1001L
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun updateArrivedCount_success() = testScope.runTest {
        coEvery {
            service.updateArriveCount(
                url = any(),
                authorization = any(),
                body = any()
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
    fun updateArrivedCount_failure_ServerException() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.updateArriveCount(
                url = any(),
                authorization = any(),
                body = any()
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
    fun `updateGuestPushToken_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}NotificationService/DeviceToken/guest"
        val urlSlot = slot<String>()
        coEvery {
            service.updateGuestPushToken(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)
        web.updateGuestPushToken(pushToken = "")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun updateGuestPushToken_success() = testScope.runTest {
        coEvery {
            service.updateGuestPushToken(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.updateGuestPushToken(pushToken = "")
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(Unit)
    }

    @Test(expected = ServerException::class)
    fun updateGuestPushToken_failure_ServerException() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.updateGuestPushToken(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(400, errorBody)
        val result = web.updateGuestPushToken(pushToken = "")
        Truth.assertThat(result.isFailure).isTrue()
        result.getOrThrow()
    }

    @Test
    fun `updateMemberPushToken_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}NotificationService/DeviceToken/member"
        val urlSlot = slot<String>()
        coEvery {
            service.updateMemberPushToken(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)
        web.updateMemberPushToken(pushToken = "")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun updateMemberPushToken_success() = testScope.runTest {
        coEvery {
            service.updateMemberPushToken(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.updateMemberPushToken(pushToken = "")
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(Unit)
    }

    @Test(expected = ServerException::class)
    fun updateMemberPushToken_failure_ServerException() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.updateMemberPushToken(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(400, errorBody)
        val result = web.updateMemberPushToken(pushToken = "")
        Truth.assertThat(result.isFailure).isTrue()
        result.getOrThrow()
    }

    @Test
    fun `updateClickCount_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}NotificationService/Statistics/clicked"
        val urlSlot = slot<String>()
        coEvery {
            service.updateClickCount(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)
        web.updateClickCount(
            sn = 0,
            pushToken = "",
            title = "",
            content = "",
            analyticsLabels = listOf("test"),
            createTime = 1001L
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }


    @Test
    fun updateClickCount_success() = testScope.runTest {
        coEvery {
            service.updateClickCount(
                url = any(),
                authorization = any(),
                body = any()
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
    fun updateClickCount_failure_ServerException() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.updateClickCount(
                url = any(),
                authorization = any(),
                body = any()
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

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }
}