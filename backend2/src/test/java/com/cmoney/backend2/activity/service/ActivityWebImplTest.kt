package com.cmoney.backend2.activity.service

import com.cmoney.backend2.activity.service.api.getdaycount.GetDayCountResponseBody
import com.cmoney.backend2.activity.service.api.getreferralcount.GetReferralCountResponseBody
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
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
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class ActivityWebImplTest {
    private val testScope = TestScope()
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)
    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager
    @MockK
    private lateinit var activityService: ActivityService
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var service: ActivityWeb

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        service = ActivityWebImpl(
            manager,
            gson,
            activityService,
            TestDispatcherProvider()
        )
        coEvery {
            manager.getActivitySettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @Test
    fun `getDayCount_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}ActivityService/Statistics/ActiveApp/Month/DayCount"
        val urlSlot = slot<String>()
        val responseBody = GetDayCountResponseBody(dayCount = 1)
        coEvery {
            activityService.getDayCount(
                authorization = any(),
                requestBody = any(),
                url = capture(urlSlot)
            )
        } returns Response.success(responseBody)
        service.getDayCount()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getDayCount_success() = testScope.runTest {
        val responseBody = GetDayCountResponseBody(dayCount = 1)
        coEvery {
            activityService.getDayCount(
                authorization = any(),
                requestBody = any(),
                url = any()
            )
        } returns Response.success(responseBody)
        val result = service.getDayCount()
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.dayCount).isEqualTo(1)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun getDayCount_failure() = testScope.runTest {
        val json = ""

        //設定api成功時的回傳
        coEvery {
            activityService.getDayCount(
                authorization = any(),
                requestBody = any(),
                url = any()
            )
        } returns Response.error(401, json.toResponseBody())

        //確認api是否成功
        val result = service.getDayCount()
        checkHttpException(result)
    }

    @Test
    fun `requestBonus_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}ActivityService/Referral/Request"
        val urlSlot = slot<String>()
        coEvery {
            activityService.requestBonus(
                authorization = any(),
                requestBody = any(),
                url = capture(urlSlot)
            )
        } returns Response.success<Void>(204, null)
        service.requestBonus(123504, 7)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun requestBonus_success() = testScope.runTest {
        //準備api成功時的回傳(後端在成功時回傳204和空值)
        //設定api成功時的回傳
        coEvery {
            activityService.requestBonus(
                authorization = any(),
                requestBody = any(),
                url = any()
            )
        } returns Response.success<Void>(204, null)         //確認api是否成功
        val result = service.requestBonus(123504, 7)
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(Unit)
    }

    @Test
    fun requestBonus_failure_ServerException() = testScope.runTest {
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
                requestBody = any(),
                url = any()
            )
        } returns Response.error(400, json.toResponseBody())

        //確認api是否成功
        val result = service.requestBonus(123504, 7)
        checkServerException(result)
    }

    @Test
    fun `getReferralCount_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}ActivityService/Referral/ReferralCount"
        val urlSlot = slot<String>()
        val responseBody = GetReferralCountResponseBody(referralCount = 1)
        coEvery {
            activityService.getReferralCount(
                authorization = any(),
                requestBody = any(),
                url = capture(urlSlot)
            )
        } returns Response.success(responseBody)

        service.getReferralCount(7)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }


    @Test
    fun getReferralCount_success() = testScope.runTest {
        //準備api成功時的回傳
        val responseBody = GetReferralCountResponseBody(referralCount = 1)
        //設定api成功時的回傳
        coEvery {
            activityService.getReferralCount(
                authorization = any(),
                requestBody = any(),
                url = any()
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
    fun getReferralCount_failure_ServerException() = testScope.runTest {
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
                requestBody = any(),
                url = any()
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

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }
}