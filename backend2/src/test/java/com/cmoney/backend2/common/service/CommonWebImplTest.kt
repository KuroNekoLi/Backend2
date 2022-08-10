package com.cmoney.backend2.common.service

import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.common.service.api.adddeviceidentification.AddDeviceIdentificationComplete
import com.cmoney.backend2.common.service.api.changenickname.ChangeNicknameResponseWithError
import com.cmoney.backend2.common.service.api.changeuserimage.ChangeUserImageResponseWithError
import com.cmoney.backend2.common.service.api.forgotpasswordemail.EmailForgotPassword
import com.cmoney.backend2.common.service.api.getaccesstoken.GetAccessTokenResponseWithError
import com.cmoney.backend2.common.service.api.getconfig.AppStatus
import com.cmoney.backend2.common.service.api.getconfig.GetConfigResponseBody
import com.cmoney.backend2.common.service.api.getdailyheadline.HeadlineResponse
import com.cmoney.backend2.common.service.api.getdailyheadline.News
import com.cmoney.backend2.common.service.api.getmemberbonus.GetMemberBonusResponseBodyWithError
import com.cmoney.backend2.common.service.api.getmemberprofile.MemberProfile
import com.cmoney.backend2.common.service.api.getstockrssarticleswithfiltertype.StockNews
import com.cmoney.backend2.common.service.api.getstockrssarticleswithfiltertype.StockRssNewsResponse
import com.cmoney.backend2.common.service.api.hasreceivedcellphonebindreward.HasReceivedCellphoneBindRewardResponseBodyWithError
import com.cmoney.backend2.common.service.api.invocationserial.InvocationSerialComplete
import com.cmoney.backend2.common.service.api.loginreward.HasSentLoginRewardTodayComplete
import com.cmoney.backend2.common.service.api.loginreward.LoginRewardComplete
import com.cmoney.backend2.common.service.api.pausetrialtiming.TrialPauseStatus
import com.cmoney.backend2.common.service.api.registeraccount.EmailRegister
import com.cmoney.backend2.common.service.api.starttrialtiming.TrialBeginStatus
import com.cmoney.backend2.common.service.api.updateisneedpushcomplete.UpdateIsNeedPushComplete
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.extension.runTest
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class CommonWebImplTest {
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule()

    @MockK
    private lateinit var service: CommonService
    private lateinit var commonWeb: CommonWeb
    private lateinit var setting: Setting
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        setting = TestSetting()
        commonWeb = CommonWebImpl(
            gson = gson,
            service = service,
            setting = setting,
            dispatcher = TestDispatcher()
        )
    }

    @Test
    fun `getConfig_response code is 1_成功`() = mainCoroutineRule.runTest {
        val responseBody = GetConfigResponseBody(
            dpscPort = 0,
            dpscChkSum = 1,
            serverUrl = "https://api.cmoney.tw/",
            serverPushUrl = "https://api.cmoney.tw/",
            vipServerUrl = "https://api.cmoney.tw/",
            vipServerPushUrl = "https://api.cmoney.tw/",
            statusAnnouncement = "",
            appStatus = AppStatus.OK,
            isUnderReview = false,
            isSuccess = true,
            responseCode = 1,
            responseMsg = ""
        )
        coEvery {
            service.getConfig(
                appId = any(),
                version = any(),
                device = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.getAppConfig()
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.responseCode).isEqualTo(1)
        Truth.assertThat(data.appStatus).isEqualTo(AppStatus.OK)
    }

    @Test
    fun `getConfig_response code is 2_需要更新`() = mainCoroutineRule.runTest {
        val responseBody = GetConfigResponseBody(
            dpscPort = null,
            dpscChkSum = null,
            serverUrl = null,
            serverPushUrl = null,
            vipServerUrl = null,
            vipServerPushUrl = null,
            statusAnnouncement = null,
            appStatus = null,
            isUnderReview = null,
            isSuccess = false,
            responseCode = 2,
            responseMsg = "版本錯誤"
        )
        coEvery {
            service.getConfig(
                appId = any(),
                version = any(),
                device = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.getAppConfig()
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception.code).isEqualTo(2)
    }

    @Test
    fun `getConfig_response code is 3_參數錯誤`() = mainCoroutineRule.runTest {
        val responseBody = GetConfigResponseBody(
            dpscPort = null,
            dpscChkSum = null,
            serverUrl = null,
            serverPushUrl = null,
            vipServerUrl = null,
            vipServerPushUrl = null,
            statusAnnouncement = null,
            appStatus = null,
            isUnderReview = null,
            isSuccess = false,
            responseCode = 3,
            responseMsg = "傳入參數有誤"
        )
        coEvery {
            service.getConfig(
                appId = any(),
                version = any(),
                device = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.getAppConfig()
        checkServerException(result, 3)
    }

    @Test
    fun `forgotPasswordForEmail_response code is 1_成功`() = mainCoroutineRule.runTest {
        val responseBody = EmailForgotPassword(
            responseCode = 1,
            responseMsg = ""
        )
        coEvery {
            service.forgotPasswordForEmail(
                action = any(),
                account = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.forgotPasswordForEmail("")
        Truth.assertThat(result.isSuccess).isTrue()
        val responseCode = result.getOrThrow().responseCode
        Truth.assertThat(responseCode).isEqualTo(1)
    }

    @Test
    fun `forgotPasswordForEmail_response code is 5_格式錯誤`() = mainCoroutineRule.runTest {
        val responseBody = EmailForgotPassword(
            responseCode = 5,
            responseMsg = "格式錯誤"
        )
        coEvery {
            service.forgotPasswordForEmail(
                action = any(),
                account = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.forgotPasswordForEmail("")
        checkServerException(result, 5)
    }

    @Test
    fun `forgotPasswordForEmail_response code is 6_帳號不存在`() = mainCoroutineRule.runTest {
        val responseBody = EmailForgotPassword(
            responseCode = 6,
            responseMsg = "帳號不存在"
        )
        coEvery {
            service.forgotPasswordForEmail(
                action = any(),
                account = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.forgotPasswordForEmail("")
        checkServerException(result, 6)
    }

    @Test
    fun `forgotPasswordForEmail_response code is 3_帳號不存在`() = mainCoroutineRule.runTest {
        val responseBody = EmailForgotPassword(
            responseCode = 3,
            responseMsg = "已申請,請等一分鐘"
        )
        coEvery {
            service.forgotPasswordForEmail(
                action = any(),
                account = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.forgotPasswordForEmail("")
        checkServerException(result, 3)
    }

    @Test
    fun `registerByEmail_response code is 1_成功`() = mainCoroutineRule.runTest {
        val responseBody = EmailRegister(
            responseCode = 1,
            responseMsg = ""
        )
        coEvery {
            service.registerByEmail(
                xApiLog = any(),
                account = any(),
                password = any(),
                device = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.registerByEmail("", "")
        Truth.assertThat(result.isSuccess).isTrue()
        val responseCode = result.getOrThrow().responseCode
        Truth.assertThat(responseCode).isEqualTo(1)
    }


    @Test
    fun `registerByEmail_response code is 4_帳號已註冊`() = mainCoroutineRule.runTest {
        val responseBody = EmailRegister(
            responseCode = 4,
            responseMsg = "帳號已註冊"
        )
        coEvery {
            service.registerByEmail(
                xApiLog = any(),
                account = any(),
                password = any(),
                device = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.registerByEmail("", "")
        checkServerException(result, 4)
    }

    @Test
    fun `registerByEmail_response code is 6_帳號已註冊`() = mainCoroutineRule.runTest {
        val responseBody = EmailRegister(
            responseCode = 6,
            responseMsg = "帳號已註冊"
        )
        coEvery {
            service.registerByEmail(
                xApiLog = any(),
                account = any(),
                password = any(),
                device = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.registerByEmail("", "")
        checkServerException(result, 6)
    }

    @Test
    fun `registerByEmail_response code is 11_帳號已註冊`() = mainCoroutineRule.runTest {
        val responseBody = EmailRegister(
            responseCode = 11,
            responseMsg = "帳號已註冊"
        )
        coEvery {
            service.registerByEmail(
                xApiLog = any(),
                account = any(),
                password = any(),
                device = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.registerByEmail("", "")
        checkServerException(result, 11)
    }

    @Test
    fun `registerByEmail_response code is 10_該帳號已加入黑名單`() = mainCoroutineRule.runTest {
        val responseBody = EmailRegister(
            responseCode = 10,
            responseMsg = "該帳號已加入黑名單"
        )
        coEvery {
            service.registerByEmail(
                xApiLog = any(),
                account = any(),
                password = any(),
                device = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.registerByEmail("", "")
        checkServerException(result, 10)
    }

    @Test
    fun `getMemberProfile_response code is 1_成功`() = mainCoroutineRule.runTest {
        val responseBody = MemberProfile(
            channelId = 3915521,
            channelImage = "https://fsv.cmoney.tw/cmstatic/t/images/channel/34025791.png",
            email = "boring12@yopmail.com",
            email2 = "boring12@yopmail.com",
            fBImage = "",
            hasBindedFb = false,
            imagePath = "https://fsv.cmoney.tw/cmstatic/t/images/channel/34025791.png",
            isUseFbImage = false,
            nickName = "Poll",
            registerTime = 1558599387,
            responseCode = 1,
            responseMsg = ""
        )
        coEvery {
            service.getMemberProfile(
                guid = any(),
                authorization = any(),
                appId = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.getMemberProfile()
        Truth.assertThat(result.isSuccess).isTrue()
        val responseCode = result.getOrThrow().responseCode
        Truth.assertThat(responseCode).isEqualTo(1)
    }

    @Test
    fun `getMemberProfile_response code is 101_授權失敗`() = mainCoroutineRule.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"Auth Failed"},"error":{"Code":101,"Message":"Auth Failed"}}
        """.trimIndent()
        val responseBody = gson.fromJson<MemberProfile>(responseBodyJson, MemberProfile::class.java)
        coEvery {
            service.getMemberProfile(
                guid = any(),
                authorization = any(),
                appId = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.getMemberProfile()
        checkServerException(result, 101)
    }

    @Test
    fun `logInReward_response code is 1_成功`() = mainCoroutineRule.runTest {
        val responseBody = LoginRewardComplete(true, 0)
        coEvery {
            service.loginReward(
                guid = any(),
                authorization = any(),
                appId = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.loginReward()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `logInReward_response code is 101_授權失敗`() = mainCoroutineRule.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"Auth Failed"},"error":{"Code":101,"Message":"Auth Failed"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson<LoginRewardComplete>(responseBodyJson, LoginRewardComplete::class.java)
        coEvery {
            service.loginReward(
                guid = any(),
                authorization = any(),
                appId = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.loginReward()
        checkServerException(result, 101)
    }

    @Test
    fun `logInReward_response code is 105_不適用此功能`() = mainCoroutineRule.runTest {
        val responseBodyJson = """
            {"Error":{"Code":105,"Message":"不適用此功能"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson<LoginRewardComplete>(responseBodyJson, LoginRewardComplete::class.java)
        coEvery {
            service.loginReward(
                guid = any(),
                authorization = any(),
                appId = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.loginReward()
        checkServerException(result, 105)
    }

    @Test
    fun `hasSentLogInReward_response code is 1_成功`() = mainCoroutineRule.runTest {
        val responseBody = HasSentLoginRewardTodayComplete(true, 0)
        coEvery {
            service.hasSentLoginRewardToday(
                guid = any(),
                authorization = any(),
                appId = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.hasSentLoginRewardToday()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `hasSentLogInReward_response code is 101_授權失敗`() = mainCoroutineRule.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"Auth Failed"},"error":{"Code":101,"Message":"Auth Failed"}}
        """.trimIndent()
        val responseBody = gson.fromJson<HasSentLoginRewardTodayComplete>(
            responseBodyJson,
            HasSentLoginRewardTodayComplete::class.java
        )
        coEvery {
            service.hasSentLoginRewardToday(
                guid = any(),
                authorization = any(),
                appId = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.hasSentLoginRewardToday()
        checkServerException(result, 101)
    }

    @Test
    fun `startTrial_response code is 1_成功`() = mainCoroutineRule.runTest {
        val responseBody = TrialBeginStatus(canTrial = false, trialRemainingSeconds = 0)
        coEvery {
            service.startTrial(
                authorization = any(),
                guid = any(),
                appId = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.startTrial()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `startTrial_response code is 101_授權失敗`() = mainCoroutineRule.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"Auth Failed"},"error":{"Code":101,"Message":"Auth Failed"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson<TrialBeginStatus>(responseBodyJson, TrialBeginStatus::class.java)
        coEvery {
            service.startTrial(
                authorization = any(),
                guid = any(),
                appId = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.startTrial()
        checkServerException(result, 101)
    }

    @Test
    fun `pauseTrial_response code is 1_成功`() = mainCoroutineRule.runTest {
        val responseBody = TrialPauseStatus(isSuccess = false)
        coEvery {
            service.pauseTrial(
                guid = any(),
                authorization = any(),
                appId = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.pauseTrial()
        Truth.assertThat(result.isSuccess).isTrue()
    }


    @Test
    fun `pauseTrial_response code is 101_授權失敗`() = mainCoroutineRule.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"Auth Failed"},"error":{"Code":101,"Message":"Auth Failed"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson<TrialPauseStatus>(responseBodyJson, TrialPauseStatus::class.java)
        coEvery {
            service.pauseTrial(
                guid = any(),
                authorization = any(),
                appId = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.pauseTrial()
        checkServerException(result, 101)
    }

    @Test
    fun `addDeviceIdentification_isSuccess=true_成功`() = mainCoroutineRule.runTest {
        val responseBody = AddDeviceIdentificationComplete(
            isSuccess = true,
            responseCode = null,
            responseMsg = null
        )
        coEvery {
            service.addDeviceIdentification(
                authorization = any(),
                guid = any(),
                aaid = any(),
                appId = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.addDeviceIdentification("")
        Truth.assertThat(result.isSuccess).isTrue()
        val isSuccess = result.getOrThrow().isSuccess
        Truth.assertThat(isSuccess).isTrue()
    }

    @Test
    fun `addDeviceIdentification_isSuccess=false_失敗`() = mainCoroutineRule.runTest {
        val responseBody = AddDeviceIdentificationComplete(
            isSuccess = false,
            responseCode = null,
            responseMsg = null
        )
        coEvery {
            service.addDeviceIdentification(
                authorization = any(),
                guid = any(),
                aaid = any(),
                appId = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.addDeviceIdentification("")
        Truth.assertThat(result.isSuccess).isTrue()
        val isSuccess = result.getOrThrow().isSuccess
        Truth.assertThat(isSuccess).isFalse()
    }

    private fun <T> checkServerException(result: Result<T>, errorCode: Int) {
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception.code).isEqualTo(errorCode)
    }

    @Test
    fun `getAccessToken 成功`() = mainCoroutineRule.runTest {
        val fakeToken = UUID.randomUUID().toString()
        val responseBody = GetAccessTokenResponseWithError(
            accessToken = fakeToken
        )
        coEvery {
            service.getAccessToken(
                authorization = any(), action = any(), guid = any(), appId = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.getAccessToken()
        Truth.assertThat(result.isSuccess).isTrue()
        val accessTokenResponse = result.getOrNull()
        Truth.assertThat(accessTokenResponse).isNotNull()
        requireNotNull(accessTokenResponse)
        Truth.assertThat(accessTokenResponse.accessToken).isEqualTo(fakeToken)
    }

    @Test
    fun `getAccessToken 101 授權失敗`() = mainCoroutineRule.runTest {
        val errorBody = CMoneyError(
            detail = CMoneyError.Detail(
                code = 101,
                message = "授權未通過"
            )
        )
        coEvery {
            service.getAccessToken(
                authorization = any(), guid = any(), appId = any()
            )
        } returns Response.success<GetAccessTokenResponseWithError>(
            200,
            gson.fromJson<GetAccessTokenResponseWithError>(
                gson.toJson(errorBody),
                GetAccessTokenResponseWithError::class.java
            )
        )

        val result = commonWeb.getAccessToken()
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        requireNotNull(exception)
        Truth.assertThat(exception).isInstanceOf(ServerException::class.java)
        require(exception is ServerException)
        Truth.assertThat(exception.code).isEqualTo(101)
    }

    @Test
    fun `invocationSerialNumber 開通序號成功`() = mainCoroutineRule.runTest {
        val responseBody = InvocationSerialComplete(
            isSuccess = true,
            responseCode = 1,
            responseMsg = ""
        )
        coEvery {
            service.invocationSerialNumber(
                guid = any(),
                authorization = any(),
                appId = any(),
                serial = any()
            )
        } returns Response.success(responseBody)


        val result = commonWeb.invocationSerialNumber("6JE-LMK-7SL")

        Truth.assertThat(result.isSuccess).isTrue()
        val isSuccess = result.getOrThrow().isSuccess
        val responseCode = result.getOrThrow().responseCode
        val responseMsg = result.getOrThrow().responseMsg
        Truth.assertThat(isSuccess).isTrue()
        Truth.assertThat(responseCode == 1).isTrue()
        Truth.assertThat(responseMsg?.isEmpty()).isTrue()
    }

    @Test
    fun `invocationSerialNumber開通序號失敗(已經開通過)`() = mainCoroutineRule.runTest {
        val responseBody = InvocationSerialComplete(
            isSuccess = false,
            responseCode = 14,
            responseMsg = "使用者已使用該序號"
        )
        coEvery {
            service.invocationSerialNumber(
                guid = any(),
                authorization = any(),
                appId = any(),
                serial = any()
            )
        } returns Response.success(responseBody)
        val result = commonWeb.invocationSerialNumber("6JE-LMK-7SL")

        Truth.assertThat(result.isSuccess).isTrue()
        val isSuccess = result.getOrThrow().isSuccess
        val responseCode = result.getOrThrow().responseCode
        val responseMsg = result.getOrThrow().responseMsg
        Truth.assertThat(isSuccess).isFalse()
        Truth.assertThat(responseCode == 14).isTrue()
        Truth.assertThat(responseMsg == "使用者已使用該序號").isTrue()
    }

    @Test
    fun `invocationSerialNumber 失敗 序號不得為空`() = mainCoroutineRule.runTest {
        val responseBody = InvocationSerialComplete(
            isSuccess = false,
            responseCode = 11,
            responseMsg = "序號不得為空"
        )
        coEvery {
            service.invocationSerialNumber(
                guid = any(),
                authorization = any(),
                appId = any(),
                serial = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.invocationSerialNumber("")
        Truth.assertThat(result.isSuccess).isTrue()
        val content = result.getOrThrow()
        Truth.assertThat(content.isSuccess).isFalse()
        Truth.assertThat(content.responseCode).isEqualTo(11)
        Truth.assertThat(content.responseMsg).isEqualTo("序號不得為空")
    }

    @Test
    fun `getDailyHeadline 取得清單成功`() = mainCoroutineRule.runTest {
        val responseBody = HeadlineResponse(
            newsList = listOf(
                News(
                    hasLiked = null,
                    id = null,
                    likeCount = null,
                    newsTime = null,
                    reTweetCount = null,
                    sourceName = null,
                    tags = null,
                    title = null,
                    url = null,
                    viewCount = null
                )
            )
        )
        coEvery {
            service.getDailyHeadLine(
                authorization = any(),
                action = any(),
                appId = any(),
                guid = any(),
                baseArticleId = any(),
                newsType = any(),
                fetchSize = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.getDailyHeadLine(0, 1, 20)
        Truth.assertThat(result.isSuccess).isTrue()
        val newsList = result.getOrNull()
        Truth.assertThat(newsList).isNotNull()
        requireNotNull(newsList)
        Truth.assertThat(newsList.size).isEqualTo(1)
    }

    @Test
    fun `getDailyHeadline 取得清單失敗`() = mainCoroutineRule.runTest {
        val errorBody = CMoneyError(detail = CMoneyError.Detail(104, ""))
        val errorString = gson.toJson(errorBody)
        val errorResponse =
            gson.fromJson<HeadlineResponse>(errorString, HeadlineResponse::class.java)
        coEvery {
            service.getDailyHeadLine(
                authorization = any(),
                action = any(),
                appId = any(),
                guid = any(),
                baseArticleId = any(),
                newsType = any(),
                fetchSize = any()
            )
        } returns Response.success(errorResponse)

        val result = commonWeb.getDailyHeadLine(0, 1, 20)
        Truth.assertThat(result.isSuccess).isFalse()
        val newsList = result.getOrNull()
        Truth.assertThat(newsList).isNull()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        requireNotNull(exception)
        Truth.assertThat(exception).isInstanceOf(ServerException::class.java)
        require(exception is ServerException)
        Truth.assertThat(exception.code).isEqualTo(104)
    }

    @Test
    fun `getStockRssArticleResponse 取得清單成功`() = mainCoroutineRule.runTest {
        val responseBody = StockRssNewsResponse(
            stockNewsList = listOf(
                StockNews(
                    hasLiked = null,
                    id = null,
                    likeCount = null,
                    newsTime = null,
                    reTweetCount = null,
                    sourceName = null,
                    tags = null,
                    title = null,
                    url = null,
                    viewCount = null
                )
            )
        )
        coEvery {
            service.getStockRssArticlesWithFilterType(
                authorization = any(),
                action = any(),
                appId = any(),
                guid = any(),
                stockId = any(),
                baseArticleId = any(),
                condition = any(),
                fromDate = any(),
                beforeDays = any(),
                filterType = any(),
                fetchSize = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.getStockRssArticlesWithFilterType(
            stockId = "",
            baseArticleId = 0,
            condition = "",
            fromDate = "",
            beforeDays = 0,
            filterType = 0,
            fetchSize = 0
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val newsList = result.getOrNull()
        Truth.assertThat(newsList).isNotNull()
        requireNotNull(newsList)
        Truth.assertThat(newsList.size).isEqualTo(1)
    }

    @Test
    fun `getStockRssArticleResponse 取得清單失敗`() = mainCoroutineRule.runTest {
        val errorBody = CMoneyError(detail = CMoneyError.Detail(103, ""))
        val errorString = gson.toJson(errorBody)
        val errorResponse =
            gson.fromJson<StockRssNewsResponse>(errorString, StockRssNewsResponse::class.java)
        coEvery {
            service.getStockRssArticlesWithFilterType(
                authorization = any(),
                action = any(),
                appId = any(),
                guid = any(),
                stockId = any(),
                baseArticleId = any(),
                condition = any(),
                fromDate = any(),
                beforeDays = any(),
                filterType = any(),
                fetchSize = any()
            )
        } returns Response.success(errorResponse)

        val result = commonWeb.getStockRssArticlesWithFilterType(
            stockId = "",
            baseArticleId = 0,
            condition = "",
            fromDate = "",
            beforeDays = 0,
            filterType = 0,
            fetchSize = 0
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val newsList = result.getOrNull()
        Truth.assertThat(newsList).isNull()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        requireNotNull(exception)
        Truth.assertThat(exception).isInstanceOf(ServerException::class.java)
        require(exception is ServerException)
        Truth.assertThat(exception.code).isEqualTo(103)
    }

    @Test
    fun `addRssStockNewsResponse 增加成功`() = mainCoroutineRule.runTest {
        coEvery {
            service.addRssArticleClickCount(
                authorization = any(),
                action = any(),
                appId = any(),
                guid = any(),
                memberPk = any(),
                articleId = any()
            )
        } returns Response.success("".toResponseBody())

        val result = commonWeb.addStockRssArticleClickCount(0, 0L)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `addRssStockNewsResponse 增加失敗`() = mainCoroutineRule.runTest {
        val error = CMoneyError(detail = CMoneyError.Detail(101, ""))
        val errorString = gson.toJson(error)
        coEvery {
            service.addRssArticleClickCount(
                authorization = any(),
                action = any(),
                appId = any(),
                guid = any(),
                memberPk = any(),
                articleId = any()
            )
        } returns Response.success(errorString.toResponseBody())

        val result = commonWeb.addStockRssArticleClickCount(0, 0L)
        Truth.assertThat(result.isFailure).isTrue()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        requireNotNull(exception)
        Truth.assertThat(exception).isInstanceOf(ServerException::class.java)
        require(exception is ServerException)
        Truth.assertThat(exception.code).isEqualTo(101)
    }

    @Test
    fun `changeNickname 成功`() = mainCoroutineRule.runTest {
        coEvery {
            service.changeNickname(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                nickname = any()
            )
        } returns Response.success(
            ChangeNicknameResponseWithError(
                isSuccess = true,
                nickname = ""
            )
        )

        val result = commonWeb.changeNickname("")
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()?.isSuccess).isTrue()
    }

    @Test
    fun `changeNickname 失敗`() = mainCoroutineRule.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, ChangeNicknameResponseWithError::class.java)
        coEvery {
            service.changeNickname(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                nickname = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.changeNickname("")
        Truth.assertThat(result.isFailure).isTrue()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        requireNotNull(exception)
        Truth.assertThat(exception).isInstanceOf(ServerException::class.java)
        require(exception is ServerException)
        Truth.assertThat(exception.code).isEqualTo(100)
    }

    @Test
    fun `changeUserImage 成功`() = mainCoroutineRule.runTest {
        coEvery {
            service.changeUserImage(
                authorization = any(),
                body = any()
            )
        } returns Response.success(
            ChangeUserImageResponseWithError(
                isSuccess = true,
                imagePath = null
            )
        )

        val result = commonWeb.changeUserImage(false, null)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()?.isSuccess).isTrue()
    }

    @Test
    fun `changeUserImage 失敗`() = mainCoroutineRule.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, ChangeUserImageResponseWithError::class.java)
        coEvery {
            service.changeUserImage(
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.changeUserImage(false, null)
        Truth.assertThat(result.isFailure).isTrue()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        requireNotNull(exception)
        Truth.assertThat(exception).isInstanceOf(ServerException::class.java)
        require(exception is ServerException)
        Truth.assertThat(exception.code).isEqualTo(100)
    }

    @Test
    fun `getMemberBonus 成功`() = mainCoroutineRule.runTest {
        coEvery {
            service.getMemberBonus(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(
            GetMemberBonusResponseBodyWithError(
                bonus = 399
            )
        )

        val result = commonWeb.getMemberBonus()
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()?.bonus).isEqualTo(399)
    }

    @Test
    fun `getMemberBonus 失敗`() = mainCoroutineRule.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, GetMemberBonusResponseBodyWithError::class.java)
        coEvery {
            service.getMemberBonus(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.getMemberBonus()
        Truth.assertThat(result.isFailure).isTrue()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        requireNotNull(exception)
        Truth.assertThat(exception).isInstanceOf(ServerException::class.java)
        require(exception is ServerException)
        Truth.assertThat(exception.code).isEqualTo(100)
    }

    @Test
    fun `hasReceivedCellphoneBindRewards 成功`() = mainCoroutineRule.runTest {
        coEvery {
            service.hasReceivedCellphoneBindReward(
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(
            HasReceivedCellphoneBindRewardResponseBodyWithError(
                hasReceived = false
            )
        )

        val result = commonWeb.hasReceivedCellphoneBindReward()
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()?.hasReceived).isFalse()
    }

    @Test(expected = ServerException::class)
    fun `hasReceivedCellphoneBindReward 失敗_權限失效`() = mainCoroutineRule.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"Auth Failed"},"error":{"Code":101,"Message":"Auth Failed"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson<HasReceivedCellphoneBindRewardResponseBodyWithError>(
                responseBodyJson,
                HasReceivedCellphoneBindRewardResponseBodyWithError::class.java
            )
        coEvery {
            service.hasReceivedCellphoneBindReward(
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.hasReceivedCellphoneBindReward()
        result.getOrThrow()
    }

    @Test
    fun `updateIsNeedPush 成功`() = mainCoroutineRule.runTest {
        val responseBody = UpdateIsNeedPushComplete(true)
        coEvery {
            service.updateIsNeedPush(
                guid = any(),
                appId = any(),
                authorization = any(),
                isNeedPush = any()
            )
        } returns Response.success(responseBody)

        val result = commonWeb.updateIsNeedPush(true)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `updateIsNeedPush 參數錯誤`() = mainCoroutineRule.runTest {
        val responseBodyJson = """{"Error":{"Code":9001,"Message":"參數錯誤"}}""".trimIndent()
        val responseBody =
            gson.fromJson<UpdateIsNeedPushComplete>(
                responseBodyJson,
                UpdateIsNeedPushComplete::class.java
            )
        coEvery {
            service.updateIsNeedPush(
                guid = any(),
                appId = any(),
                authorization = any(),
                isNeedPush = any()
            )
        } returns Response.success(responseBody)
        val result = commonWeb.updateIsNeedPush(true)
        checkServerException(result, 9001)
    }
}