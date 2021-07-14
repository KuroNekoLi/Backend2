package com.cmoney.backend2.activity.service

import com.cmoney.backend2.MainCoroutineRule
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.activity.service.api.getdaycount.GetDayCountResponseBody
import com.cmoney.backend2.activity.service.api.getreferralcount.GetReferralCountResponseBody
import com.cmoney.backend2.base.model.exception.ServerException
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.HttpException
import retrofit2.Response

@RunWith(RobolectricTestRunner::class)
class ActivityWebImplTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private val activityService = mockk<ActivityService>()
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private val service: ActivityWeb = ActivityWebImpl(
        TestSetting(),
        gson,
        activityService,
        Dispatchers.Main
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `getDayCount取得用戶這個月開啟幾天APP 成功`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = GetDayCountResponseBody(dayCount = 1)
        //設定api成功時的回傳
        coEvery {
            activityService.getDayCount(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getDayCount()
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.dayCount).isEqualTo(1)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `getDayCount取得用戶這個月開啟幾天APP 失敗`() = mainCoroutineRule.runBlockingTest {
        val json = ""

        //設定api成功時的回傳
        coEvery {
            activityService.getDayCount(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(401, json.toResponseBody())

        //確認api是否成功
        val result = service.getDayCount()
        checkHttpException(result)
    }

    @Test
    fun `requestBonus請求獎勵 成功`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳(後端在成功時回傳204和空值)
        //設定api成功時的回傳
        coEvery {
            activityService.requestBonus(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success<Void>(204, null)         //確認api是否成功
        val result = service.requestBonus(123504, 7)
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(Unit)
    }

    @Test
    fun `requestBonus請求獎勵 失敗 發生ServerException`() = mainCoroutineRule.runBlockingTest {
        val json = """{
          "Error": {
            "Code": 10002,
            "Message": "Member id: x已取得過此活動獎勵"
          }
        }""".trimIndent()

        //設定api成功時的回傳
        coEvery {
            activityService.requestBonus(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, json.toResponseBody())

        //確認api是否成功
        val result = service.requestBonus(123504, 7)
        checkServerException(result)
    }

    @Test
    fun `getReferralCount取得推薦人總共成功推薦次數 成功`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = GetReferralCountResponseBody(referralCount = 1)
        //設定api成功時的回傳
        coEvery {
            activityService.getReferralCount(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getReferralCount(7)
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.referralCount).isEqualTo(1)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `getReferralCount取得推薦人總共成功推薦次數 失敗 發生ServerException`() = mainCoroutineRule.runBlockingTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()

        //設定api成功時的回傳
        coEvery {
            activityService.getReferralCount(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, json.toResponseBody())

        //確認api是否成功
        val result = service.getReferralCount(7)
        checkServerException(result)
    }

    private fun <T> checkServerException(result: Result<T>) {
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }

    private fun <T> checkHttpException(result: Result<T>) {
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as HttpException
        Truth.assertThat(exception).isNotNull()
    }
}