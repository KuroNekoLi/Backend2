package com.cmoney.backend2.portal.service

import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.portal.service.api.ForecastValue
import com.cmoney.backend2.portal.service.api.askallmemberlastforecastinfo.AskAllMemberLastForecastInfoWithError
import com.cmoney.backend2.portal.service.api.askmemberforecaststatus.AskMemberForecastStatusWithError
import com.cmoney.backend2.portal.service.api.askmemberlastforecastinfo.AskMemberLastForecastInfoWithError
import com.cmoney.backend2.portal.service.api.getactivitiesbaseinfo.GetActivitiesBaseInfoWithError
import com.cmoney.backend2.portal.service.api.getactivitynowinfo.GetActivityNowInfoWithError
import com.cmoney.backend2.portal.service.api.getadditionalinfo.CmPortalAdditionWithError
import com.cmoney.backend2.portal.service.api.getmemberperformance.GetMemberPerformanceWithError
import com.cmoney.backend2.portal.service.api.getpersonactivityhistory.GetPersonActivityHistory
import com.cmoney.backend2.portal.service.api.getranking.GetRanking
import com.cmoney.backend2.portal.service.api.getsignals.CmPortalSignalWithError
import com.cmoney.backend2.portal.service.api.gettarget.CmPortalTargetWithError
import com.cmoney.backend2.portal.service.api.joinactivity.JoinActivityWithError
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Response
import java.util.UUID

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class PortalWebImplTest {

    private val testScope = TestScope()

    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var portalService: PortalService

    private lateinit var portalWeb: PortalWeb
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()

    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        portalWeb = PortalWebImpl(
            manager = manager,
            gson = gson,
            service = portalService,
            dispatcher = TestDispatcherProvider()
        )
        coEvery {
            manager.getPortalSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @Test
    fun `getTarget_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}cmportal/api/pickstock/gettarget/"
        val urlSlot = slot<String>()
        val responseBody = CmPortalTargetWithError(
            tseAndOtc = listOf("1101", "1102"),
            stockFutures = listOf("1101", "1102", "1103"),
            dayTrade = listOf("1101", "1102", "1103", "1104"),
            issuanceOfConvertibleBonds = listOf("1101", "1102", "1103", "1104", "1105"),
            issuanceOfWarrants = listOf("1101", "1102", "1103", "1104", "1105", "1106")
        )
        coEvery {
            portalService.getTarget(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        portalWeb.getTarget()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `getTarget_response code is 1_success`() = testScope.runTest {
        val responseBody = CmPortalTargetWithError(
            tseAndOtc = listOf("1101", "1102"),
            stockFutures = listOf("1101", "1102", "1103"),
            dayTrade = listOf("1101", "1102", "1103", "1104"),
            issuanceOfConvertibleBonds = listOf("1101", "1102", "1103", "1104", "1105"),
            issuanceOfWarrants = listOf("1101", "1102", "1103", "1104", "1105", "1106")
        )
        coEvery {
            portalService.getTarget(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = portalWeb.getTarget()
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.tseAndOtc).isNotEmpty()
        Truth.assertThat(data.tseAndOtc!!.size).isEqualTo(2)
        Truth.assertThat(data.stockFutures).isNotEmpty()
        Truth.assertThat(data.stockFutures!!.size).isEqualTo(3)
        Truth.assertThat(data.dayTrade).isNotEmpty()
        Truth.assertThat(data.dayTrade!!.size).isEqualTo(4)
        Truth.assertThat(data.issuanceOfConvertibleBonds).isNotEmpty()
        Truth.assertThat(data.issuanceOfConvertibleBonds!!.size).isEqualTo(5)
        Truth.assertThat(data.issuanceOfWarrants).isNotEmpty()
        Truth.assertThat(data.issuanceOfWarrants!!.size).isEqualTo(6)
    }

    @Test
    fun `getTarget_response code is 100_wrongArguments`() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":100,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody = gson.fromJson<CmPortalTargetWithError>(
            responseBodyJson,
            CmPortalTargetWithError::class.java
        )
        coEvery {
            portalService.getTarget(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = portalWeb.getTarget()
        checkServerException(result, 100)
    }

    @Test
    fun `getTarget_response code is 101_noAuthorization`() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"身分驗證錯誤"}} 
        """.trimIndent()
        val responseBody = gson.fromJson<CmPortalTargetWithError>(
            responseBodyJson,
            CmPortalTargetWithError::class.java
        )
        coEvery {
            portalService.getTarget(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = portalWeb.getTarget()
        checkServerException(result, 101)
    }

    @Test
    fun `getSignals_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}cmportal/api/pickstock/getsignals/"
        val urlSlot = slot<String>()
        val responseBody = CmPortalSignalWithError(
            columns = listOf(
                "CommKey",
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10",
                "11",
                "12",
                "13",
                "14",
                "15"
            ),
            rows = listOf(
                listOf(
                    "3056",
                    "1",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "1",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0"
                ),
                listOf(
                    "3552",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "1",
                    "0",
                    "0",
                    "1",
                    "0",
                    "1",
                    "0"
                )
            )
        )
        coEvery {
            portalService.getSignals(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        portalWeb.getSignals()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `getSignals_response code is 1_success`() = testScope.runTest {
        val responseBody = CmPortalSignalWithError(
            columns = listOf(
                "CommKey",
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10",
                "11",
                "12",
                "13",
                "14",
                "15"
            ),
            rows = listOf(
                listOf(
                    "3056",
                    "1",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "1",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0"
                ),
                listOf(
                    "3552",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "1",
                    "0",
                    "0",
                    "1",
                    "0",
                    "1",
                    "0"
                )
            )
        )
        coEvery {
            portalService.getSignals(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = portalWeb.getSignals()
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.columns).isNotEmpty()
        Truth.assertThat(data.columns!!.size).isEqualTo(16)
        Truth.assertThat(data.rows).isNotEmpty()
        Truth.assertThat(data.rows!!.size).isEqualTo(2)
    }

    @Test
    fun `getSignals_response code is 100_wrongArguments`() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":100,"Message":"參數錯誤"}}
        """.trimIndent()

        val responseBody = gson.fromJson<CmPortalSignalWithError>(
            responseBodyJson,
            CmPortalSignalWithError::class.java
        )

        coEvery {
            portalService.getSignals(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = portalWeb.getSignals()
        checkServerException(result, 100)
    }

    @Test
    fun `getSignals_response code is 101_noAuthorization`() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"身分驗證錯誤"}} 
        """.trimIndent()
        val responseBody = gson.fromJson<CmPortalSignalWithError>(
            responseBodyJson,
            CmPortalSignalWithError::class.java
        )
        coEvery {
            portalService.getSignals(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = portalWeb.getSignals()
        checkServerException(result, 101)
    }

    @Test
    fun `getAdditionalInfo_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}cmportal/api/additionalinformation/getadditionalInfo/"
        val urlSlot = slot<String>()
        val responseBody = CmPortalAdditionWithError(
            columns = listOf("標的", "即時成交價", "漲跌幅", "總量", "五日均量"),
            rows = listOf(listOf("1301", "103", "-9.25", "26307", "8502.4"))
        )
        coEvery {
            portalService.getAdditionalInfo(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        portalWeb.getAdditionalInfo(STOCK_INFO)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `getAdditionalInfo_response code is 1_success`() = testScope.runTest {
        val responseBody = CmPortalAdditionWithError(
            columns = listOf("標的", "即時成交價", "漲跌幅", "總量", "五日均量"),
            rows = listOf(listOf("1301", "103", "-9.25", "26307", "8502.4"))
        )
        coEvery {
            portalService.getAdditionalInfo(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = portalWeb.getAdditionalInfo(STOCK_INFO)
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.columns).isNotEmpty()
        Truth.assertThat(data.columns!!.size).isEqualTo(5)
        Truth.assertThat(data.rows).isNotEmpty()
        Truth.assertThat(data.rows!!.size).isEqualTo(1)
    }

    @Test
    fun `getAdditionalInfo_response code is 100_wrongArguments`() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":100,"Message":"參數錯誤"}}
        """.trimIndent()

        val responseBody = gson.fromJson<CmPortalAdditionWithError>(
            responseBodyJson,
            CmPortalAdditionWithError::class.java
        )

        coEvery {
            portalService.getAdditionalInfo(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = portalWeb.getAdditionalInfo(STOCK_INFO)
        checkServerException(result, 100)
    }

    @Test
    fun `getAdditionalInfo_response code is 101_noAuthorization`() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"身分驗證錯誤"}} 
        """.trimIndent()
        val responseBody = gson.fromJson<CmPortalAdditionWithError>(
            responseBodyJson,
            CmPortalAdditionWithError::class.java
        )
        coEvery {
            portalService.getAdditionalInfo(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = portalWeb.getAdditionalInfo(BASIC_INFO)
        checkServerException(result, 101)
    }

    @Test
    fun `getActivitiesBaseInfo_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}CMPortal/api/GuessBullBear/GetActivitiesBaseInfo"
        val urlSlot = slot<String>()
        val baseInfoResponse = GetActivitiesBaseInfoWithError(
            activityBaseInfoList = emptyList()
        )
        coEvery {
            portalService.getActivitiesBaseInfo(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(baseInfoResponse)

        portalWeb.getActivitiesBaseInfo()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getActivitiesBaseInfo_success() = testScope.runTest {
        val baseInfoResponse = GetActivitiesBaseInfoWithError(
            activityBaseInfoList = emptyList()
        )
        coEvery {
            portalService.getActivitiesBaseInfo(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(baseInfoResponse)

        val result = portalWeb.getActivitiesBaseInfo()
        Truth.assertThat(result.isSuccess).isTrue()
        val responseInfo = result.getOrThrow()
        Truth.assertThat(responseInfo == baseInfoResponse.toRealResponse()).isTrue()
    }

    @Test
    fun `getActivitiesBaseInfo_response code is 101_noAuthorization`() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"身分驗證錯誤"}} 
        """.trimIndent()
        val responseBody = gson.fromJson<GetActivitiesBaseInfoWithError>(
            responseBodyJson,
            GetActivitiesBaseInfoWithError::class.java
        )
        coEvery {
            portalService.getActivitiesBaseInfo(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = portalWeb.getActivitiesBaseInfo()
        checkServerException(result, 101)
    }

    @Test
    fun `getActivityNowInfo_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}CMPortal/api/GuessBullBear/GetActivityNowInfo"
        val urlSlot = slot<String>()
        val nowInfoResponse = GetActivityNowInfoWithError(
            activityEndTime = null,
            endTradingTime = null,
            refPr = null,
            startTradingTime = null,
            tendToBearAmount = null,
            tendToBullAmount = null,
            totalBets = null,
            totalParticipants = null
        )
        coEvery {
            portalService.getActivityNowInfo(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(nowInfoResponse)

        portalWeb.getActivityNowInfo(commKey = "TWA00")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getActivityNowInfo_success() = testScope.runTest {
        val nowInfoResponse = GetActivityNowInfoWithError(
            activityEndTime = null,
            endTradingTime = null,
            refPr = null,
            startTradingTime = null,
            tendToBearAmount = null,
            tendToBullAmount = null,
            totalBets = null,
            totalParticipants = null
        )
        coEvery {
            portalService.getActivityNowInfo(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(nowInfoResponse)

        val result = portalWeb.getActivityNowInfo(commKey = "TWA00")
        Truth.assertThat(result.isSuccess).isTrue()
        val responseInfo = result.getOrThrow()
        Truth.assertThat(responseInfo == nowInfoResponse.toRealResponse()).isTrue()
    }

    @Test
    fun `getActivityNowInfo_response code is 101_noAuthorization`() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"身分驗證錯誤"}} 
        """.trimIndent()
        val responseBody = gson.fromJson<GetActivityNowInfoWithError>(
            responseBodyJson,
            GetActivityNowInfoWithError::class.java
        )
        coEvery {
            portalService.getActivityNowInfo(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = portalWeb.getActivityNowInfo(commKey = "TWA00")
        checkServerException(result, 101)
    }

    @Test
    fun `getMemberPerformance_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}CMPortal/api/GuessBullBear/GetMemberPerformance"
        val urlSlot = slot<String>()
        val performanceResponse = GetMemberPerformanceWithError(
            ratioOfWin = null,
            totalWinTimes = null
        )
        coEvery {
            portalService.getMemberPerformance(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(performanceResponse)

        portalWeb.getMemberPerformance(commKey = "TWA00", queryGuid = UUID.randomUUID().toString())
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getMemberPerformance_success() = testScope.runTest {
        val performanceResponse = GetMemberPerformanceWithError(
            ratioOfWin = null,
            totalWinTimes = null
        )
        coEvery {
            portalService.getMemberPerformance(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(performanceResponse)

        val result = portalWeb.getMemberPerformance(
            commKey = "TWA00",
            queryGuid = UUID.randomUUID().toString()
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val responseInfo = result.getOrThrow()
        Truth.assertThat(responseInfo == performanceResponse.toRealResponse()).isTrue()
    }

    @Test
    fun `getMemberPerformance_response code is 101_noAuthorization`() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"身分驗證錯誤"}} 
        """.trimIndent()
        val responseBody = gson.fromJson<GetMemberPerformanceWithError>(
            responseBodyJson,
            GetMemberPerformanceWithError::class.java
        )
        coEvery {
            portalService.getMemberPerformance(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = portalWeb.getMemberPerformance(
            commKey = "TWA00",
            queryGuid = UUID.randomUUID().toString()
        )
        checkServerException(result, 101)
    }

    @Test
    fun `getRanking_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}CMPortal/api/GuessBullBear/GetRanking"
        val urlSlot = slot<String>()
        coEvery {
            portalService.getRanking(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(JsonArray())

        portalWeb.getRanking(commKey = "TWA00", fetchCount = 30, skipCount = 0)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getRanking_success() = testScope.runTest {
        val origin = listOf(
            GetRanking(
                image = null,
                memberGuid = null,
                nickName = null,
                orderNumber = null,
                prize = null
            )
        )
        val jsonArray = gson.fromJson(gson.toJson(origin), JsonArray::class.java)
        coEvery {
            portalService.getRanking(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(jsonArray)

        val result = portalWeb.getRanking(
            commKey = "TWA00",
            fetchCount = 30,
            skipCount = 0
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val rankings = result.getOrThrow()
        Truth.assertThat(rankings).isEqualTo(origin)
    }

    @Test
    fun `getRanking_response code is 101_noAuthorization`() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"身分驗證錯誤"}} 
        """.trimIndent()
        val responseBody = gson.fromJson<JsonElement>(
            responseBodyJson,
            JsonElement::class.java
        )
        coEvery {
            portalService.getRanking(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = portalWeb.getRanking(
            commKey = "TWA00",
            fetchCount = 30,
            skipCount = 0
        )
        checkServerException(result, 101)
    }

    @Test
    fun `getPersonActivityHistory_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}CMPortal/api/GuessBullBear/GetPersonActivityHistory"
        val urlSlot = slot<String>()
        coEvery {
            portalService.getPersonActivityHistory(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(JsonArray())

        portalWeb.getPersonActivityHistory(
            commKey = "TWA00",
            fetchCount = 30,
            skipCount = 0,
            queryGuid = UUID.randomUUID().toString()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getPersonActivityHistory_success() = testScope.runTest {
        val origin = listOf(
            GetPersonActivityHistory(
                forecastValue = null,
                hasWin = null,
                prize = null,
                tradeDate = null
            )
        )
        val jsonArray = gson.fromJson(gson.toJson(origin), JsonArray::class.java)
        coEvery {
            portalService.getPersonActivityHistory(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(jsonArray)

        val result = portalWeb.getPersonActivityHistory(
            commKey = "TWA00",
            fetchCount = 30,
            skipCount = 0,
            queryGuid = UUID.randomUUID().toString()
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val rankings = result.getOrThrow()
        Truth.assertThat(rankings).isEqualTo(origin)
    }

    @Test
    fun `getPersonActivityHistory_response code is 101_noAuthorization`() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"身分驗證錯誤"}} 
        """.trimIndent()
        val responseBody = gson.fromJson<JsonElement>(
            responseBodyJson,
            JsonElement::class.java
        )
        coEvery {
            portalService.getPersonActivityHistory(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = portalWeb.getPersonActivityHistory(
            commKey = "TWA00",
            fetchCount = 30,
            skipCount = 0,
            queryGuid = UUID.randomUUID().toString()
        )
        checkServerException(result, 101)
    }

    @Test
    fun `askMemberForecastStatus_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}CMPortal/api/GuessBullBear/AskMemberForecastStatus"
        val urlSlot = slot<String>()
        val forecastStatusWithError = AskMemberForecastStatusWithError(
            status = null
        )
        coEvery {
            portalService.askMemberForecastStatus(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(forecastStatusWithError)

        portalWeb.askMemberForecastStatus(commKey = "TWA00")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun askMemberForecastStatus_success() = testScope.runTest {
        val forecastStatusWithError = AskMemberForecastStatusWithError(
            status = null
        )
        coEvery {
            portalService.askMemberForecastStatus(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(forecastStatusWithError)

        val result = portalWeb.askMemberForecastStatus(commKey = "TWA00")
        Truth.assertThat(result.isSuccess).isTrue()
        val responseInfo = result.getOrThrow()
        Truth.assertThat(responseInfo == forecastStatusWithError.toRealResponse()).isTrue()
    }

    @Test
    fun `askMemberForecastStatus_response code is 101_noAuthorization`() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"身分驗證錯誤"}} 
        """.trimIndent()
        val responseBody = gson.fromJson<AskMemberForecastStatusWithError>(
            responseBodyJson,
            AskMemberForecastStatusWithError::class.java
        )
        coEvery {
            portalService.askMemberForecastStatus(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = portalWeb.askMemberForecastStatus(commKey = "TWA00")
        checkServerException(result, 101)
    }

    @Test
    fun `askMemberLastForecastInfo_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}CMPortal/api/GuessBullBear/AskMemberLastForecastInfo"
        val urlSlot = slot<String>()
        val forecastInfoWithError = AskMemberLastForecastInfoWithError(
            result = null
        )
        coEvery {
            portalService.askMemberLastForecastInfo(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(forecastInfoWithError)

        portalWeb.askMemberLastForecastInfo(commKey = "TWA00")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun askMemberLastForecastInfo_success() = testScope.runTest {
        val forecastInfoWithError = AskMemberLastForecastInfoWithError(
            result = null
        )
        coEvery {
            portalService.askMemberLastForecastInfo(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(forecastInfoWithError)

        val result = portalWeb.askMemberLastForecastInfo(commKey = "TWA00")
        Truth.assertThat(result.isSuccess).isTrue()
        val responseInfo = result.getOrThrow()
        Truth.assertThat(responseInfo == forecastInfoWithError.toRealResponse()).isTrue()
    }

    @Test
    fun `askMemberLastForecastInfo_response code is 101_noAuthorization`() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"身分驗證錯誤"}} 
        """.trimIndent()
        val responseBody = gson.fromJson<AskMemberLastForecastInfoWithError>(
            responseBodyJson,
            AskMemberLastForecastInfoWithError::class.java
        )
        coEvery {
            portalService.askMemberLastForecastInfo(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = portalWeb.askMemberLastForecastInfo(commKey = "TWA00")
        checkServerException(result, 101)
    }

    @Test
    fun `joinActivity_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}CMPortal/api/GuessBullBear/JoinActivity"
        val urlSlot = slot<String>()
        val joinActivityResponse = JoinActivityWithError(
            isSuccess = true
        )
        coEvery {
            portalService.joinActivity(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(joinActivityResponse)

        portalWeb.joinActivity(commKey = "TWA00", forecastValue = ForecastValue.Bull)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun joinActivity_success() = testScope.runTest {
        val joinActivityResponse = JoinActivityWithError(
            isSuccess = true
        )
        coEvery {
            portalService.joinActivity(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(joinActivityResponse)

        val result = portalWeb.joinActivity(
            commKey = "TWA00",
            forecastValue = ForecastValue.Bull
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val responseInfo = result.getOrThrow()
        Truth.assertThat(responseInfo == joinActivityResponse.toRealResponse()).isTrue()
    }

    @Test
    fun `joinActivity_response code is 101_noAuthorization`() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"身分驗證錯誤"}} 
        """.trimIndent()
        val responseBody = gson.fromJson<JoinActivityWithError>(
            responseBodyJson,
            JoinActivityWithError::class.java
        )
        coEvery {
            portalService.joinActivity(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = portalWeb.joinActivity(
            commKey = "TWA00",
            forecastValue = ForecastValue.Bull
        )
        checkServerException(result, 101)
    }

    @Test
    fun `askAllMemberLastForecastInfo_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}CMPortal/api/GuessBullBear/AskAllMemberLastForecastInfo"
        val urlSlot = slot<String>()
        val forecastInfoWithError = AskAllMemberLastForecastInfoWithError(
            result = null
        )
        coEvery {
            portalService.askAllMemberLastForecastInfo(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(forecastInfoWithError)

        portalWeb.askAllMemberLastForecastInfo()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun askAllMemberLastForecastInfo_success() = testScope.runTest {
        val forecastInfoWithError = AskAllMemberLastForecastInfoWithError(
            result = null
        )
        coEvery {
            portalService.askAllMemberLastForecastInfo(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(forecastInfoWithError)

        val result = portalWeb.askAllMemberLastForecastInfo()
        Truth.assertThat(result.isSuccess).isTrue()
        val responseInfo = result.getOrThrow()
        Truth.assertThat(responseInfo == forecastInfoWithError.toRealResponse()).isTrue()
    }

    @Test
    fun `askAllMemberLastForecastInfo_response code is 101_noAuthorization`() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"身分驗證錯誤"}} 
        """.trimIndent()
        val responseBody = gson.fromJson<AskAllMemberLastForecastInfoWithError>(
            responseBodyJson,
            AskAllMemberLastForecastInfoWithError::class.java
        )
        coEvery {
            portalService.askAllMemberLastForecastInfo(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = portalWeb.askAllMemberLastForecastInfo()
        checkServerException(result, 101)
    }

    private fun <T> checkServerException(result: Result<T>, errorCode: Int) {
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception.code).isEqualTo(errorCode)
    }

    companion object {
        // getAdditionalInfo settingId type
        private const val STOCK_INFO = 1 // 1: 價量區 股票資訊
        private const val BASIC_INFO = 2 // 2: 基本資料區

        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }
}