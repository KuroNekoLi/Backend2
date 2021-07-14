package com.cmoney.backend2.notification2.service

import com.cmoney.backend2.MainCoroutineRule
import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.notification2.service.api.getbranchfcm.BranchSettingRequestBody
import com.cmoney.backend2.notification2.service.api.getclubfcm.ClubFcmSettingResponseBody
import com.cmoney.backend2.notification2.service.api.gethistorynotifyall.GetNotifyAllResponseBody
import com.cmoney.backend2.notification2.service.api.getmainfcm.GetMainFCMResponseBody
import com.cmoney.backend2.notification2.service.api.updatebranchfcmlistrequestbody.PushSetting
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
import java.util.concurrent.TimeoutException

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class UserSettingTest {

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

    @Test(expected = TimeoutException::class)
    fun `getHistoryNotifyAll_TimeoutException`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.getHistoryNotifyAll(
                appId = any(), authorization = any()
            )
        } answers {
            throw TimeoutException()
        }

        val result = notification2Web.getHistoryNotifyAll(Any::class.java)
        result.getOrThrow()
    }

    @Test
    fun `getHistoryNotifyAll_成功無資料_是空列表`() = mainCoroutineRule.runBlockingTest {
        val response = listOf<GetNotifyAllResponseBody>()
        coEvery {
            service.getHistoryNotifyAll(
                authorization = any(),
                appId = any()
            )
        } returns Response.success(response)
        val result2 = notification2Web.getHistoryNotifyAll(Any::class.java)
        Truth.assertThat(result2.isSuccess).isTrue()
        val list2 = result2.getOrThrow()
        Truth.assertThat(list2).isEmpty()
    }

    @Test
    fun `getHistoryNotifyAll_成功有資料_不是空值`() = mainCoroutineRule.runBlockingTest {
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
                authorization = any(),
                appId = any()
            )
        } returns Response.success(response)

        val result = notification2Web.getHistoryNotifyAll(TestParameter26::class.java)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.size).isEqualTo(2)
        for (actual in data) {
            Truth.assertThat(actual).isNotEqualTo(expect)
            Truth.assertThat(actual.parameter is TestParameter26).isTrue()
        }
    }

    @Test
    fun `getBranchFcm_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = listOf(
            BranchSettingRequestBody(
                isNeedPush = true, pushSettingId = 6, settingName = "123"
            )
        )
        coEvery {
            service.getBranchFcm(
                appId = any(), authorization = any(), guid = any()
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
    fun `getBranchFcm_失敗_ServerException`() = mainCoroutineRule.runBlockingTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.getBranchFcm(
                appId = any(), authorization = any(), guid = any()
            )
        } returns Response.error(400, errorBody)

        val result = notification2Web.getBranchFcm()
        result.getOrThrow()
    }

    @Test
    fun `updateBranchFcm_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = "".toResponseBody()
        coEvery {
            service.updateBranchFcm(
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
    fun `updateBranchFcm_失敗_ServerException`() = mainCoroutineRule.runBlockingTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.updateBranchFcm(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, errorBody)

        val result = notification2Web.updateBranchFcm(6, true)
        result.getOrThrow()
    }

    @Test
    fun `updateBranchFcmMultipleSettings_成功204`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.updateBranchFcmMultipleSettings(
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
    fun `updateBranchFcmMultipleSettings_失敗_ServerException`() = mainCoroutineRule.runBlockingTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.updateBranchFcmMultipleSettings(
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
    fun `getClubFcm_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = listOf(
            ClubFcmSettingResponseBody(
                isSelected = true,
                settingName = "123",
                pushSettingType = null
            )
        )
        coEvery {
            service.getClubFcm(
                appId = any(), authorization = any(), guid = any(), clubId = any()
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
    fun `getClubFcm_失敗_ServerException`() = mainCoroutineRule.runBlockingTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.getClubFcm(
                appId = any(), authorization = any(), guid = any(), clubId = any()
            )
        } returns Response.error(400, errorBody)

        val result = notification2Web.getClubFcm(6)
        result.getOrThrow()
    }

    @Test
    fun `updateClubFcm_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = "".toResponseBody()
        coEvery {
            service.updateClubFcm(
                authorization = any(), requestBody = any()
            )
        } returns Response.success(202, responseBody)

        val result = notification2Web.updateClubFcm(1, 6)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(Unit)
    }

    @Test(expected = ServerException::class)
    fun `updateClubFcm_失敗_ServerException`() = mainCoroutineRule.runBlockingTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.updateClubFcm(
                authorization = any(), requestBody = any()
            )
        } returns Response.error(400, errorBody)

        val result = notification2Web.updateClubFcm(1, 6)
        result.getOrThrow()
    }

    @Test
    fun `getMainFcm_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = GetMainFCMResponseBody(isNeedPush = true)
        coEvery {
            service.getMainFcm(
                appId = any(), authorization = any(), guid = any()
            )
        } returns Response.success(responseBody)

        val result = notification2Web.getMainFcm()
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.isNeedPush).isTrue()
    }

    @Test(expected = ServerException::class)
    fun `getMainFcm_失敗_ServerException`() = mainCoroutineRule.runBlockingTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.getMainFcm(
                appId = any(), authorization = any(), guid = any()
            )
        } returns Response.error(400, errorBody)

        val result = notification2Web.getMainFcm()
        result.getOrThrow()
    }

    @Test
    fun `updateMainFcm_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = "".toResponseBody()
        coEvery {
            service.updateMainFcm(
                authorization = any(), requestBody = any()
            )
        } returns Response.success(202, responseBody)

        val result = notification2Web.updateMainFcm(true)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(Unit)
    }

    @Test(expected = ServerException::class)
    fun `updateMainFcm_失敗_ServerException`() = mainCoroutineRule.runBlockingTest {
        val errorBody = gson.toJson(
            CMoneyError(
                detail = CMoneyError.Detail(
                    code = 101
                )
            )
        ).toResponseBody()
        coEvery {
            service.updateMainFcm(
                authorization = any(), requestBody = any()
            )
        } returns Response.error(400, errorBody)

        val result = notification2Web.updateMainFcm(true)
        result.getOrThrow()
    }
}