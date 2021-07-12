package com.cmoney.backend2.notification2.service

import com.cmoney.backend2.MainCoroutineRule
import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.notification2.service.api.getmonitor.GetMonitorResponseBody
import com.cmoney.backend2.notification2.service.api.getmonitorhistory.GetMonitorHistoryResponseBody
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
class MonitorTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var service: Notification2Service
    private lateinit var notification2Web: Notification2Web
    private val gson = GsonBuilder().serializeNulls().create()
    private lateinit var setting: Setting

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        setting = TestSetting()
        notification2Web = Notification2WebImpl(
            gson = gson,
            service = service,
            setting = setting,
            dispatcher = TestDispatcher()
        )
    }

    @Test
    fun `getMonitorList_成功_沒有資料`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.getMonitorList(
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(emptyList())

        val result = notification2Web.getMonitorList()
        val monitorList = result.getOrThrow()
        Truth.assertThat(monitorList).isEmpty()
    }

    @Test
    fun `getMonitorList_成功_有資料`() = mainCoroutineRule.runBlockingTest {
        val responseBody = listOf(
            GetMonitorResponseBody(
                conditionId = 10000,
                commonKey = null,
                strategyId = null,
                isNeedPush = null,
                condition = null
            ),
            GetMonitorResponseBody(
                conditionId = 10001,
                commonKey = null,
                strategyId = null,
                isNeedPush = null,
                condition = null
            )
        )
        coEvery {
            service.getMonitorList(
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(responseBody)

        val result = notification2Web.getMonitorList()
        val monitorList = result.getOrThrow()
        Truth.assertThat(monitorList).isNotEmpty()
    }

    @Test(expected = ServerException::class)
    fun `getMonitorList_失敗_ServerException`() = mainCoroutineRule.runBlockingTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.getMonitorList(
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.error(400, errorBody)

        val result = notification2Web.getMonitorList()
        result.getOrThrow()
    }

    @Test
    fun `insertMonitor_成功204`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.insertMonitor(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)

        val result = notification2Web.insertMonitor("2330", 1, 400.0)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow()).isEqualTo(Unit)
    }

    @Test(expected = ServerException::class)
    fun `insertMonitor_失敗_ServerException`() = mainCoroutineRule.runBlockingTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 10001
                )
            )
        ).toResponseBody()
        coEvery {
            service.getMonitorList(
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.error(400, errorBody)
        val result = notification2Web.getMonitorList()
        result.getOrThrow()
    }

    @Test
    fun `updateMonitor_成功204`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.updateMonitor(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)

        val result = notification2Web.updateMonitor(1000, 1, 400.0)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow()).isEqualTo(Unit)
    }

    @Test(expected = ServerException::class)
    fun `updateMonitor_失敗_ServerException`() = mainCoroutineRule.runBlockingTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 10001
                )
            )
        ).toResponseBody()
        coEvery {
            service.updateMonitor(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, errorBody)
        val result = notification2Web.updateMonitor(1000, 1, 400.0)
        result.getOrThrow()
    }

    @Test
    fun `deleteMonitor_成功204`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.deleteMonitor(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)

        val result = notification2Web.deleteMonitor(1000)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow()).isEqualTo(Unit)
    }

    @Test(expected = ServerException::class)
    fun `deleteMonitor_失敗_ServerException`() = mainCoroutineRule.runBlockingTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 10001
                )
            )
        ).toResponseBody()
        coEvery {
            service.deleteMonitor(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, errorBody)
        val result = notification2Web.deleteMonitor(1000)
        result.getOrThrow()
    }

    @Test
    fun `getMonitorHistoryList_成功_有資料`() = mainCoroutineRule.runBlockingTest {
        val responseBody = listOf(
            GetMonitorHistoryResponseBody(
                sn = null,
                body = null,
                parameter = null,
                createTime = null
            ),
            GetMonitorHistoryResponseBody(
                sn = null,
                body = null,
                parameter = null,
                createTime = null
            )
        )
        coEvery {
            service.getMonitorHistoryList(
                authorization = any(),
                guid = any(),
                appId = any()
            )
        } returns Response.success(responseBody)

        val result = notification2Web.getMonitorHistoryList()
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow()).isNotEmpty()
    }

    @Test(expected = ServerException::class)
    fun `getMonitorHistoryList_失敗_ServerException`() = mainCoroutineRule.runBlockingTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 10001
                )
            )
        ).toResponseBody()
        coEvery {
            service.getMonitorHistoryList(
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.error(400, errorBody)
        val result = notification2Web.getMonitorHistoryList()
        result.getOrThrow()
    }

    @Test
    fun `updateMonitorPushNotification_成功204`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.updateMonitorPushNotification(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)

        val result = notification2Web.updateMonitorPushNotification(1000, true)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow()).isEqualTo(Unit)
    }

    @Test(expected = ServerException::class)
    fun `updateMonitorPushNotification_失敗_ServerException`() = mainCoroutineRule.runBlockingTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 10001
                )
            )
        ).toResponseBody()
        coEvery {
            service.updateMonitorPushNotification(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, errorBody)
        val result = notification2Web.updateMonitorPushNotification(1000, true)
        result.getOrThrow()
    }
}