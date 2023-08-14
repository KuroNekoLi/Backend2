package com.cmoney.backend2.notification2.service

import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.notification2.service.api.getbranchfcm.BranchSettingRequestBody
import com.cmoney.backend2.notification2.service.api.getclubfcm.ClubFcmSettingResponseBody
import com.cmoney.backend2.notification2.service.api.gethistorynotifyall.GetNotifyAllResponseBody
import com.cmoney.backend2.notification2.service.api.getmainfcm.GetMainFCMResponseBody
import com.cmoney.backend2.notification2.service.api.getmonitor.GetMonitorResponseBody
import com.cmoney.backend2.notification2.service.api.getmonitorhistory.GetMonitorHistoryResponseBody
import com.cmoney.backend2.notification2.service.api.getmroptionlist.GetMrOptionListResponseBody
import com.cmoney.backend2.notification2.service.api.updatebranchfcmlistrequestbody.PushSetting
import com.cmoney.backend2.notification2.service.api.updatemroptionlist.UpdateMrOptionConditionRequestBody
import com.cmoney.backend2.notification2.service.data.TestParameter
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
import retrofit2.Response
import java.util.concurrent.TimeoutException

@OptIn(ExperimentalCoroutinesApi::class)
class Notification2WebImplTest {

    private val testScope = TestScope()
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var service: Notification2Service
    private lateinit var notification2Web: Notification2Web
    private val gson = GsonBuilder().serializeNulls().create()

    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        notification2Web = Notification2WebImpl(
            manager = manager,
            gson = gson,
            service = service,
            dispatcher = TestDispatcherProvider()
        )
        coEvery {
            manager.getNotification2SettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @Test
    fun `getHistoryNotifyAll_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}notification/History/NotifyAll"
        val urlSlot = slot<String>()
        val response = listOf<GetNotifyAllResponseBody>()
        coEvery {
            service.getHistoryNotifyAll(
                url = capture(urlSlot),
                authorization = any(),
                appId = any()
            )
        } returns Response.success(response)
        notification2Web.getHistoryNotifyAll(Any::class.java)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getHistoryNotifyAll_success_hasData() = testScope.runTest {
        val expect =
            GetNotifyAllResponseBody(
                sn = null,
                notificationId = null,
                body = null,
                parameter = null,
                createTime = null
            )

        val response = listOf(
            GetNotifyAllResponseBody(
                1,
                1,
                "3479 安勤 進入低估區間",
                """
                    {"commonKey" : "3479",
                    "closingPrice" : 64.70,
                    "changeRate": 0.47,
                    "changeAmount": -0.2,
                    "valueRangeType": "低估",
                    "evaluationType": "盈餘評價法"
                    }
                """,
                1594083604
            ),
            GetNotifyAllResponseBody(
                2,
                1,
                "2067 嘉鋼 進入低估區間",
                """
                    {"commonKey" : "3479",
                    "closingPrice" : 64.75,
                    "changeRate": 0.43,
                    "changeAmount": -0.2,
                    "valueRangeType": "低估",
                    "evaluationType": "盈餘評價法"
                    }
                """,
                1594170022
            )
        )
        coEvery {
            service.getHistoryNotifyAll(
                url = any(),
                authorization = any(),
                appId = any()
            )
        } returns Response.success(response)

        val result = notification2Web.getHistoryNotifyAll(TestParameter::class.java)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.size).isEqualTo(2)
        for (actual in data) {
            Truth.assertThat(actual).isNotEqualTo(expect)
            Truth.assertThat(actual.parameter is TestParameter).isTrue()
        }
    }

    @Test(expected = TimeoutException::class)
    fun getHistoryNotifyAll_failure_TimeoutException() = testScope.runTest {
        coEvery {
            service.getHistoryNotifyAll(
                url = any(),
                authorization = any(),
                appId = any(),
            )
        } answers {
            throw TimeoutException()
        }

        val result = notification2Web.getHistoryNotifyAll(Any::class.java)
        result.getOrThrow()
    }

    @Test
    fun `getBranchFcm_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}notification/usersetting/Branch"
        val urlSlot = slot<String>()
        val responseBody = listOf(
            BranchSettingRequestBody(
                isNeedPush = true, pushSettingId = 6, settingName = "123"
            )
        )
        coEvery {
            service.getBranchFcm(
                url = capture(urlSlot),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(responseBody)

        notification2Web.getBranchFcm()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getBranchFcm_success() = testScope.runTest {
        val responseBody = listOf(
            BranchSettingRequestBody(
                isNeedPush = true, pushSettingId = 6, settingName = "123"
            )
        )
        coEvery {
            service.getBranchFcm(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(responseBody)

        val result = notification2Web.getBranchFcm()
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data[0].isNeedPush).isTrue()
        Truth.assertThat(data[0].pushSettingId).isEqualTo(6)
        Truth.assertThat(data[0].settingName).isEqualTo("123")
    }

    @Test(expected = ServerException::class)
    fun getBranchFcm_failure_ServerException() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.getBranchFcm(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.error(400, errorBody)

        val result = notification2Web.getBranchFcm()
        result.getOrThrow()
    }

    @Test
    fun `updateBranchFcm_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}notification/usersetting/Branch"
        val urlSlot = slot<String>()
        val responseBody = "".toResponseBody()
        coEvery {
            service.updateBranchFcm(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(202, responseBody)

        notification2Web.updateBranchFcm(6, true)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun updateBranchFcm_success() = testScope.runTest {
        val responseBody = "".toResponseBody()
        coEvery {
            service.updateBranchFcm(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(202, responseBody)

        val result = notification2Web.updateBranchFcm(6, true)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(Unit)
    }

    @Test(expected = ServerException::class)
    fun updateBranchFcm_failure_ServerException() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.updateBranchFcm(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, errorBody)

        val result = notification2Web.updateBranchFcm(6, true)
        result.getOrThrow()
    }

    @Test
    fun `updateBranchFcmMultipleSettings_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}notification/usersetting/Branch"
        val urlSlot = slot<String>()
        coEvery {
            service.updateBranchFcmMultipleSettings(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)

        notification2Web.updateBranchFcmMultipleSettings(
            listOf(
                PushSetting(true, 17),
                PushSetting(true, 18)
            )
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun updateBranchFcmMultipleSettings_success_204() = testScope.runTest {
        coEvery {
            service.updateBranchFcmMultipleSettings(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)

        val result = notification2Web.updateBranchFcmMultipleSettings(
            listOf(
                PushSetting(true, 17),
                PushSetting(true, 18)
            )
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(Unit)
    }

    @Test(expected = ServerException::class)
    fun updateBranchFcmMultipleSettings_failure_ServerException() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.updateBranchFcmMultipleSettings(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, errorBody)

        val result = notification2Web.updateBranchFcmMultipleSettings(
            listOf(
                PushSetting(true, 17),
                PushSetting(true, 18)
            )
        )
        result.getOrThrow()
    }

    @Test
    fun `getClubFcm_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}notification/usersetting/Club"
        val urlSlot = slot<String>()
        val responseBody = listOf(
            ClubFcmSettingResponseBody(
                isSelected = true,
                settingName = "123",
                pushSettingType = null
            )
        )
        coEvery {
            service.getClubFcm(
                url = capture(urlSlot),
                authorization = any(),
                appId = any(),
                guid = any(),
                clubId = any()
            )
        } returns Response.success(responseBody)

        notification2Web.getClubFcm(6)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getClubFcm_success() = testScope.runTest {
        val responseBody = listOf(
            ClubFcmSettingResponseBody(
                isSelected = true,
                settingName = "123",
                pushSettingType = null
            )
        )
        coEvery {
            service.getClubFcm(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any(),
                clubId = any()
            )
        } returns Response.success(responseBody)

        val result = notification2Web.getClubFcm(6)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data[0].isSelected).isTrue()
        Truth.assertThat(data[0].settingName).isEqualTo("123")
        Truth.assertThat(data[0].pushSettingType).isNull()
    }

    @Test(expected = ServerException::class)
    fun getClubFcm_failure_ServerException() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.getClubFcm(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any(),
                clubId = any()
            )
        } returns Response.error(400, errorBody)

        val result = notification2Web.getClubFcm(6)
        result.getOrThrow()
    }

    @Test
    fun `updateClubFcm_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}notification/usersetting/Club"
        val urlSlot = slot<String>()
        val responseBody = "".toResponseBody()
        coEvery {
            service.updateClubFcm(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(202, responseBody)

        notification2Web.updateClubFcm(1, 6)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun updateClubFcm_success() = testScope.runTest {
        val responseBody = "".toResponseBody()
        coEvery {
            service.updateClubFcm(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(202, responseBody)

        val result = notification2Web.updateClubFcm(1, 6)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(Unit)
    }

    @Test(expected = ServerException::class)
    fun updateClubFcm_failure_ServerException() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.updateClubFcm(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, errorBody)

        val result = notification2Web.updateClubFcm(1, 6)
        result.getOrThrow()
    }

    @Test
    fun `getMainFcm_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}notification/usersetting/Main"
        val urlSlot = slot<String>()
        val responseBody = GetMainFCMResponseBody(isNeedPush = true)
        coEvery {
            service.getMainFcm(
                url = capture(urlSlot),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(responseBody)

        notification2Web.getMainFcm()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getMainFcm_success() = testScope.runTest {
        val responseBody = GetMainFCMResponseBody(isNeedPush = true)
        coEvery {
            service.getMainFcm(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(responseBody)

        val result = notification2Web.getMainFcm()
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.isNeedPush).isTrue()
    }

    @Test(expected = ServerException::class)
    fun getMainFcm_failure_ServerException() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.getMainFcm(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.error(400, errorBody)

        val result = notification2Web.getMainFcm()
        result.getOrThrow()
    }

    @Test
    fun `updateMainFcm_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}notification/usersetting/Main"
        val urlSlot = slot<String>()
        val responseBody = "".toResponseBody()
        coEvery {
            service.updateMainFcm(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(202, responseBody)

        notification2Web.updateMainFcm(true)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun updateMainFcm_success() = testScope.runTest {
        val responseBody = "".toResponseBody()
        coEvery {
            service.updateMainFcm(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(202, responseBody)

        val result = notification2Web.updateMainFcm(true)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(Unit)
    }

    @Test(expected = ServerException::class)
    fun updateMainFcm_failure_ServerException() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.updateMainFcm(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, errorBody)

        val result = notification2Web.updateMainFcm(true)
        result.getOrThrow()
    }

    @Test
    fun `getMonitorList_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}notification/userCondition/Monitor"
        val urlSlot = slot<String>()
        coEvery {
            service.getMonitorList(
                url = capture(urlSlot),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(emptyList())

        notification2Web.getMonitorList()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getMonitorList_success_emptyList() = testScope.runTest {
        coEvery {
            service.getMonitorList(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(emptyList())

        val result = notification2Web.getMonitorList()
        val monitorList = result.getOrThrow()
        Truth.assertThat(monitorList).isEmpty()
    }

    @Test
    fun getMonitorList_success_hasData() = testScope.runTest {
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
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(responseBody)

        val result = notification2Web.getMonitorList()
        val monitorList = result.getOrThrow()
        Truth.assertThat(monitorList).isNotEmpty()
    }

    @Test(expected = ServerException::class)
    fun getMonitorList_failure_ServerException() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.getMonitorList(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.error(400, errorBody)

        val result = notification2Web.getMonitorList()
        result.getOrThrow()
    }

    @Test
    fun `insertMonitor_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}notification/userCondition/Monitor"
        val urlSlot = slot<String>()
        coEvery {
            service.insertMonitor(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)

        notification2Web.insertMonitor("2330", 1, 400.0)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun insertMonitor_success_204() = testScope.runTest {
        coEvery {
            service.insertMonitor(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)

        val result = notification2Web.insertMonitor("2330", 1, 400.0)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow()).isEqualTo(Unit)
    }

    @Test(expected = ServerException::class)
    fun insertMonitor_failure_ServerException() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 10001
                )
            )
        ).toResponseBody()
        coEvery {
            service.getMonitorList(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.error(400, errorBody)
        val result = notification2Web.getMonitorList()
        result.getOrThrow()
    }

    @Test
    fun `updateMonitor_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}notification/userCondition/Monitor"
        val urlSlot = slot<String>()
        coEvery {
            service.updateMonitor(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)

        notification2Web.updateMonitor(1000, 1, 400.0)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun updateMonitor_success_204() = testScope.runTest {
        coEvery {
            service.updateMonitor(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)

        val result = notification2Web.updateMonitor(1000, 1, 400.0)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow()).isEqualTo(Unit)
    }

    @Test(expected = ServerException::class)
    fun updateMonitor_failure_ServerException() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 10001
                )
            )
        ).toResponseBody()
        coEvery {
            service.updateMonitor(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, errorBody)
        val result = notification2Web.updateMonitor(1000, 1, 400.0)
        result.getOrThrow()
    }

    @Test
    fun `deleteMonitor_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}notification/userCondition/Monitor"
        val urlSlot = slot<String>()
        coEvery {
            service.deleteMonitor(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)

        notification2Web.deleteMonitor(1000)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun deleteMonitor_success_204() = testScope.runTest {
        coEvery {
            service.deleteMonitor(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)

        val result = notification2Web.deleteMonitor(1000)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow()).isEqualTo(Unit)
    }

    @Test(expected = ServerException::class)
    fun deleteMonitor_failure_ServerException() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 10001
                )
            )
        ).toResponseBody()
        coEvery {
            service.deleteMonitor(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, errorBody)
        val result = notification2Web.deleteMonitor(1000)
        result.getOrThrow()
    }

    @Test
    fun `getMonitorHistoryList_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}notification/userCondition/Monitor/history"
        val urlSlot = slot<String>()
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
                url = capture(urlSlot),
                authorization = any(),
                guid = any(),
                appId = any()
            )
        } returns Response.success(responseBody)

        notification2Web.getMonitorHistoryList()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getMonitorHistoryList_success_hasData() = testScope.runTest {
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
                url = any(),
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
    fun getMonitorHistoryList_failure_ServerException() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 10001
                )
            )
        ).toResponseBody()
        coEvery {
            service.getMonitorHistoryList(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.error(400, errorBody)
        val result = notification2Web.getMonitorHistoryList()
        result.getOrThrow()
    }

    @Test
    fun `updateMonitorPushNotification_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}notification/userCondition/Monitor/isNeedPush"
        val urlSlot = slot<String>()
        coEvery {
            service.updateMonitorPushNotification(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)

        notification2Web.updateMonitorPushNotification(1000, true)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun updateMonitorPushNotification_success_204() = testScope.runTest {
        coEvery {
            service.updateMonitorPushNotification(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)

        val result = notification2Web.updateMonitorPushNotification(1000, true)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow()).isEqualTo(Unit)
    }

    @Test(expected = ServerException::class)
    fun updateMonitorPushNotification_failure_ServerException() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 10001
                )
            )
        ).toResponseBody()
        coEvery {
            service.updateMonitorPushNotification(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, errorBody)
        val result = notification2Web.updateMonitorPushNotification(1000, true)
        result.getOrThrow()
    }

    @Test
    fun `getMrOptionOptionList_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}notification/userCondition/MrOption/Option"
        val urlSlot = slot<String>()
        coEvery {
            service.getMrOptionOptionConditionList(
                url = capture(urlSlot),
                authorization = any(),
                guid = any(),
                appId = any()
            )
        } returns Response.success(emptyList())

        notification2Web.getMrOptionOptionList()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getMrOptionOptionList_success() = testScope.runTest {
        val responseBody = GetMrOptionListResponseBody(
            strategyId = null,
            strategyName = null,
            allValues = emptyList(),
            defaultValues = emptyList(),
            monitorValues = emptyList()
        )
        coEvery {
            service.getMrOptionOptionConditionList(
                url = any(),
                authorization = any(),
                guid = any(),
                appId = any()
            )
        } returns Response.success(listOf(responseBody))

        val result = notification2Web.getMrOptionOptionList()
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).hasSize(1)
        val oneData = data.first()
        Truth.assertThat(oneData === responseBody).isTrue()
    }

    @Test(expected = ServerException::class)
    fun getMrOptionOptionList_failure_ServerException() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 10001
                )
            )
        ).toResponseBody()
        coEvery {
            service.getMrOptionOptionConditionList(
                url = any(),
                authorization = any(),
                guid = any(),
                appId = any()
            )
        } returns Response.error(400, errorBody)
        val result = notification2Web.getMrOptionOptionList()
        result.getOrThrow()
    }

    @Test
    fun `getMrOptionSpotGoodsList_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}notification/userCondition/MrOption/SpotGoods"
        val urlSlot = slot<String>()
        coEvery {
            service.getMrOptionSpotGoodsConditionList(
                url = capture(urlSlot),
                authorization = any(),
                guid = any(),
                appId = any()
            )
        } returns Response.success(emptyList())

        notification2Web.getMrOptionSpotGoodsList()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getMrOptionSpotGoodsList_success() = testScope.runTest {
        val responseBody = GetMrOptionListResponseBody(
            strategyId = null,
            strategyName = null,
            allValues = emptyList(),
            defaultValues = emptyList(),
            monitorValues = emptyList()
        )
        coEvery {
            service.getMrOptionSpotGoodsConditionList(
                url = any(),
                authorization = any(),
                guid = any(),
                appId = any()
            )
        } returns Response.success(listOf(responseBody))

        val result = notification2Web.getMrOptionSpotGoodsList()
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).hasSize(1)
        val oneData = data.first()
        Truth.assertThat(oneData === responseBody).isTrue()
    }

    @Test(expected = ServerException::class)
    fun getMrOptionSpotGoodsList_failure_ServerException() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 10001
                )
            )
        ).toResponseBody()
        coEvery {
            service.getMrOptionSpotGoodsConditionList(
                url = any(),
                authorization = any(),
                guid = any(),
                appId = any()
            )
        } returns Response.error(400, errorBody)
        val result = notification2Web.getMrOptionSpotGoodsList()
        result.getOrThrow()
    }

    @Test
    fun `updateMrOptionList_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}notification/userCondition/MrOption"
        val urlSlot = slot<String>()
        coEvery {
            service.updateMrOptionConditionList(
                url = capture(urlSlot),
                authorization = any(),
                updateMrOptionConditionRequestBody = any()
            )
        } returns Response.success<Void>(204, null)

        val conditionList = listOf(
            UpdateMrOptionConditionRequestBody.Condition(
                1,
                listOf(-1000, -500, 0, 500, 1000)
            )
        )
        notification2Web.updateMrOptionList(conditions = conditionList)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun updateMrOptionList_success_204() = testScope.runTest {
        coEvery {
            service.updateMrOptionConditionList(
                url = any(),
                authorization = any(),
                updateMrOptionConditionRequestBody = any()
            )
        } returns Response.success<Void>(204, null)

        val conditionList = listOf(
            UpdateMrOptionConditionRequestBody.Condition(
                1,
                listOf(-1000, -500, 0, 500, 1000)
            )
        )
        val result = notification2Web.updateMrOptionList(conditions = conditionList)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(Unit)
    }

    @Test(expected = ServerException::class)
    fun updateMrOptionList_failure_ServerException() = testScope.runTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.updateMrOptionConditionList(
                url = any(),
                authorization = any(),
                updateMrOptionConditionRequestBody = any()
            )
        } returns Response.error(400, errorBody)

        val conditionList = listOf(
            UpdateMrOptionConditionRequestBody.Condition(
                1,
                listOf(-1000, -500, 0, 500, 1000)
            )
        )
        val result = notification2Web.updateMrOptionList(conditions = conditionList)
        result.getOrThrow()
    }

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }
}