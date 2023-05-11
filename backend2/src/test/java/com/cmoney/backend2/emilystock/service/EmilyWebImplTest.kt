package com.cmoney.backend2.emilystock.service

import com.cmoney.backend2.base.model.exception.EmptyBodyException
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.emilystock.service.api.getemilycommkeys.GetEmilyCommKeysResponse
import com.cmoney.backend2.emilystock.service.api.getfiltercondition.GetFilterConditionResponse
import com.cmoney.backend2.emilystock.service.api.getstockinfos.GetStockInfosResponse
import com.cmoney.backend2.emilystock.service.api.gettargetconstitution.Constitution
import com.cmoney.backend2.emilystock.service.api.gettargetconstitution.GetTargetConstitutionWithError
import com.cmoney.backend2.emilystock.service.api.gettargetstockinfos.GetTargetStockInfosWithError
import com.cmoney.backend2.emilystock.service.api.gettargetstockinfos.StockInfo
import com.cmoney.backend2.emilystock.service.api.gettrafficlightrecord.GetTrafficLightRecordWithError
import com.cmoney.backend2.emilystock.service.api.gettrafficlightrecord.TrafficLightRecord
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.truth.Truth
import com.google.gson.Gson
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class EmilyWebImplTest {
    private val testScope = TestScope()
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private val emilyService = mockk<EmilyService>()
    private val gson = Gson()
    private lateinit var emilyWeb: EmilyWeb
    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        emilyWeb = EmilyWebImpl(
            manager = manager,
            service = emilyService,
            gson = gson,
            dispatcher = TestDispatcherProvider()
        )
        coEvery {
            manager.getEmilyStockSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @Test
    fun `searchStocksV2_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}EmilyFixedStock/api/EmilyStock/GetEmilyCommKeys"
        val urlSlot = slot<String>()
        coEvery {
            emilyService.getEmilyCommKeys(
                url = capture(urlSlot),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(GetEmilyCommKeysResponse(listOf()))
        emilyWeb.getEmilyCommKeys()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getEmilyCommKeys_success() = testScope.runTest {
        val response = GetEmilyCommKeysResponse(
            listOf(
                "0050",
                "0051",
                "0056",
                "006205",
                "00646",
                "1101",
                "1102",
                "1210",
                "1215",
                "1216",
                "1229"
            )
        )
        coEvery {
            emilyService.getEmilyCommKeys(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(response)
        val result = emilyWeb.getEmilyCommKeys()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = EmptyBodyException::class)
    fun `getEmilyCommKeys_no data`() = testScope.runTest {
        coEvery {
            emilyService.getEmilyCommKeys(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(null)
        val result = emilyWeb.getEmilyCommKeys()
        result.getOrThrow()
    }

    @Test
    fun `getStockInfos_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}EmilyFixedStock/api/EmilyStock/GetStockInfos"
        val urlSlot = slot<String>()
        coEvery {
            emilyService.getStockInfos(
                url = capture(urlSlot),
                authorization = any(),
                isTeacherDefault = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(GetStockInfosResponse(listOf()))
        emilyWeb.getStockInfos(isTeacherDefault = true)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getStockInfos_success() = testScope.runTest {
        val response = GetStockInfosResponse(
            listOf(
                GetStockInfosResponse.StockInfo(
                    commKey = "0050",
                    commName = "元大台灣50",
                    stockClassifyType = 2,
                    yields = "0.0251748251748252",
                    marginOfSafetyDiscount = "1",
                    emilyPriceGroups = listOf(
                        GetStockInfosResponse.StockInfo.EmilyPriceGroup(
                            priceCheap = "57.82",
                            priceNormal = "66.13",
                            priceExpensive = "72.91",
                            priceCaculateType = 3,
                            isDefault = null
                        )
                    )
                )
            )
        )
        coEvery {
            emilyService.getStockInfos(
                url = any(),
                authorization = any(),
                isTeacherDefault = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(response)
        val result = emilyWeb.getStockInfos(isTeacherDefault = true)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = EmptyBodyException::class)
    fun `getStockInfos_no data`() = testScope.runTest {
        coEvery {
            emilyService.getStockInfos(
                url = any(),
                authorization = any(),
                isTeacherDefault = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(null)
        val result = emilyWeb.getStockInfos(isTeacherDefault = true)
        result.getOrThrow()
    }

    @Test
    fun `getTargetStockInfos_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}EmilyFixedStock/api/EmilyStock/GetTargetStockInfos"
        val urlSlot = slot<String>()
        coEvery {
            emilyService.getTargetStockInfos(
                url = capture(urlSlot),
                authorization = any(),
                isTeacherDefault = any(),
                appId = any(),
                guid = any(),
                commKeys = any()
            )
        } returns Response.success(GetTargetStockInfosWithError(listOf()))
        emilyWeb.getTargetStockInfos(isTeacherDefault = true, commKeyList = listOf("1565"))
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getTargetStockInfos_success() = testScope.runTest {
        val response = GetTargetStockInfosWithError(
            listOf(
                StockInfo(
                    commKey = "1565",
                    commName = "精華",
                    stockClassifyType = 0,
                    yields = "0.0411184210526316",
                    marginOfSafetyDiscount = "0.85",
                    emilyPriceGroups = listOf(
                        StockInfo.EmilyPriceGroup(
                            priceCheap = "375",
                            priceNormal = "500",
                            priceExpensive = "750",
                            priceCaculateType = 1,
                            isDefault = false
                        )
                    )
                )
            )
        )
        coEvery {
            emilyService.getTargetStockInfos(
                url = any(),
                authorization = any(),
                isTeacherDefault = any(),
                appId = any(),
                guid = any(),
                commKeys = any()
            )
        } returns Response.success(response)
        val result =
            emilyWeb.getTargetStockInfos(isTeacherDefault = true, commKeyList = listOf("1565"))
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun getTargetStockInfos_failure() = testScope.runTest {
        val errorText = """
            {
                "Error": {
                    "Code": 102,
                    "Message": "服務錯誤"
                }
            }
        """.trimIndent()
        val errorResponse = gson.fromJson(
            errorText,
            GetTargetStockInfosWithError::class.java
        )
        coEvery {
            emilyService.getTargetStockInfos(
                url = any(),
                authorization = any(),
                isTeacherDefault = any(),
                appId = any(),
                guid = any(),
                commKeys = any()
            )
        } returns Response.success(errorResponse)
        val result =
            emilyWeb.getTargetStockInfos(isTeacherDefault = true, commKeyList = listOf("1565"))
        result.getOrThrow()
    }

    @Test(expected = EmptyBodyException::class)
    fun `getTargetStockInfos_no data`() = testScope.runTest {
        coEvery {
            emilyService.getTargetStockInfos(
                url = any(),
                authorization = any(),
                isTeacherDefault = any(),
                appId = any(),
                guid = any(),
                commKeys = any()
            )
        } returns Response.success(null)
        val result =
            emilyWeb.getTargetStockInfos(isTeacherDefault = true, commKeyList = listOf("1565"))
        result.getOrThrow()
    }

    @Test
    fun `getTargetConstitution_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}EmilyFixedStock/api/EmilyStock/GetTargetConstitution"
        val urlSlot = slot<String>()
        coEvery {
            emilyService.getTargetConstitution(
                url = capture(urlSlot),
                authorization = any(),
                isTeacherDefault = any(),
                appId = any(),
                guid = any(),
                commKey = any()
            )
        } returns Response.success(GetTargetConstitutionWithError(null))
        emilyWeb.getTargetConstitution(isTeacherDefault = true, commKey = "2330")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getTargetConstitution_success() = testScope.runTest {
        val response = GetTargetConstitutionWithError(
            response = Constitution(
                commKey = "2330",
                commName = "台積電",
                marginOfSafetyDiscount = "0.9",
                avgEmilyPriceGroup = Constitution.EmilyPriceGroup(
                    priceCheap = "375",
                    priceNormal = "500",
                    priceExpensive = "750",
                    priceCaculateType = 1,
                    isDefault = false
                ),
                emilyPriceGroups = listOf(
                    Constitution.EmilyPriceGroup(
                        priceCheap = "375",
                        priceNormal = "500",
                        priceExpensive = "750",
                        priceCaculateType = 1,
                        isDefault = false
                    ),
                    Constitution.EmilyPriceGroup(
                        priceCheap = "281.805",
                        priceNormal = "375.74",
                        priceExpensive = "563.61",
                        priceCaculateType = 2,
                        isDefault = false
                    )
                ),
                dataDate = "201801",
                returnOnPriceDifference = "0.244882857571982",
                yields = "0.036036036036036",
                yieldsNear10Year = "1.74",
                subIndustry = "電子上游-IC-代工",
                stockScaleType = 1,
                CapitalStock = "259304000000",
                marketType = 2,
                publicYear = "23",
                per = "16.7",
                pbr = "3.58",
                taxDeductible = "14.69",
                epsNearSeason = "3.46000003814697",
                epsNearYear = "13.32",
                stockClassifyType = 0,
                near10YearSurplusAllotment = "52.97",
                emilyCaseScores = listOf(
                    Constitution.EmilyCaseScore(
                        caseScoreType = 3,
                        caseScoreGroupType = 1,
                        caseValue = "21.6",
                        IsGood = true
                    ),
                    Constitution.EmilyCaseScore(
                        caseScoreType = 3,
                        caseScoreGroupType = 1,
                        caseValue = "21.6",
                        IsGood = true
                    ),
                    Constitution.EmilyCaseScore(
                        caseScoreType = 3,
                        caseScoreGroupType = 1,
                        caseValue = "21.6",
                        IsGood = true
                    )
                )
            )
        )
        coEvery {
            emilyService.getTargetConstitution(
                url = any(),
                authorization = any(),
                isTeacherDefault = any(),
                appId = any(),
                guid = any(),
                commKey = any()
            )
        } returns Response.success(response)
        val result = emilyWeb.getTargetConstitution(isTeacherDefault = true, commKey = "2330")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun getTargetConstitution_failure() = testScope.runTest {
        val errorText = """
            {
                "Error": {
                    "Code": 102,
                    "Message": "服務錯誤"
                }
            }
        """.trimIndent()
        val errorResponse = gson.fromJson<GetTargetConstitutionWithError>(
            errorText,
            GetTargetConstitutionWithError::class.java
        )
        coEvery {
            emilyService.getTargetConstitution(
                url = any(),
                authorization = any(),
                isTeacherDefault = any(),
                appId = any(),
                guid = any(),
                commKey = any()
            )
        } returns Response.success(errorResponse)
        val result = emilyWeb.getTargetConstitution(isTeacherDefault = true, commKey = "1565")
        result.getOrThrow()
    }

    @Test(expected = EmptyBodyException::class)
    fun `getTargetConstitution_not data`() = testScope.runTest {
        coEvery {
            emilyService.getTargetConstitution(
                url = any(),
                authorization = any(),
                isTeacherDefault = any(),
                appId = any(),
                guid = any(),
                commKey = any()
            )
        } returns Response.success(null)
        val result = emilyWeb.getTargetConstitution(isTeacherDefault = true, commKey = "1565")
        result.getOrThrow()
    }

    @Test
    fun `getFilterCondition_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}EmilyFixedStock/api/EmilyStock/GetFilterCondition"
        val urlSlot = slot<String>()
        coEvery {
            emilyService.getFilterCondition(
                url = capture(urlSlot),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(GetFilterConditionResponse(null))
        emilyWeb.getFilterCondition()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getFilterCondition_success() = testScope.runTest {
        val response = GetFilterConditionResponse(
            response = listOf(
                GetFilterConditionResponse.FilterCondition(
                    commKey = "0050",
                    qualified = true,
                    yieldRate = true,
                    earnMoneyYr = false,
                    cashDividendYr = false,
                    listingAndOTC10Yr = true,
                    mediumLargeStocks = true
                )
            )
        )
        coEvery {
            emilyService.getFilterCondition(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(response)
        val result = emilyWeb.getFilterCondition()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = EmptyBodyException::class)
    fun `getFilterCondition_no data`() = testScope.runTest {
        coEvery {
            emilyService.getFilterCondition(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(null)
        val result = emilyWeb.getFilterCondition()
        result.getOrThrow()
    }

    @Test
    fun `getTrafficLightRecord_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}EmilyFixedStock/api/EmilyStock/GetTrafficLightRecord"
        val urlSlot = slot<String>()
        coEvery {
            emilyService.getTrafficLightRecord(
                url = capture(urlSlot),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(GetTrafficLightRecordWithError(null))
        emilyWeb.getTrafficLightRecord()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getTrafficLightRecord_success() = testScope.runTest {
        val response = GetTrafficLightRecordWithError(
            listOf(
                TrafficLightRecord(
                    commKey = "1101",
                    name = "台泥",
                    salePrice = 40.0,
                    priceChange = 1.0,
                    quoteChange = 0.25,
                    trafficLightType = 4,
                    createTime = 1611189934,
                    trafficLightText = ""
                )
            )
        )
        coEvery {
            emilyService.getTrafficLightRecord(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(response)
        val result = emilyWeb.getTrafficLightRecord()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = EmptyBodyException::class)
    fun `getTrafficLightRecord_no data`() = testScope.runTest {
        coEvery {
            emilyService.getTrafficLightRecord(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(null)
        val result = emilyWeb.getTrafficLightRecord()
        result.getOrThrow()
    }

    @Test(expected = ServerException::class)
    fun getTrafficLightRecord_error() = testScope.runTest {
        val errorText = """
            {
                "Error": {
                    "Code": 102,
                    "Message": "服務錯誤"
                }
            }
        """.trimIndent()
        val errorResponse = gson.fromJson<GetTrafficLightRecordWithError>(
            errorText,
            GetTrafficLightRecordWithError::class.java
        )
        coEvery {
            emilyService.getTrafficLightRecord(
                url = any(),
                authorization = any(),
                appId = any(),
                guid = any()
            )
        } returns Response.success(errorResponse)
        val result = emilyWeb.getTrafficLightRecord()
        result.getOrThrow()
    }

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }
}

