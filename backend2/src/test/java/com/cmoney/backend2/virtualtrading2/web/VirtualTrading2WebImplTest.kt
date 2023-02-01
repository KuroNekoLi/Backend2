package com.cmoney.backend2.virtualtrading2.web

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.virtualtrading2.*
import com.cmoney.backend2.virtualtrading2.model.requestconfig.VirtualTradingRequestConfig
import com.cmoney.backend2.virtualtrading2.service.VirtualTrading2Service
import com.cmoney.backend2.virtualtrading2.service.VirtualTrading2Web
import com.cmoney.backend2.virtualtrading2.service.VirtualTrading2WebImpl
import com.cmoney.backend2.virtualtrading2.service.api.createaccount.CreateAccountRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.createdelegate.CreateDelegateRequestBody
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
class VirtualTrading2WebImplTest {

    private val testScope = TestScope()

    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @MockK
    private lateinit var service: VirtualTrading2Service
    private lateinit var web: VirtualTrading2Web
    private val gson = GsonBuilder().setLenient().setPrettyPrinting().create()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        web = VirtualTrading2WebImpl(
            dispatcher = TestDispatcherProvider(),
            gson = gson,
            requestConfig = object : VirtualTradingRequestConfig {
                override fun getDomain(): String {
                    return ""
                }

                override fun getBearerToken(): String {
                    return ""
                }
            },
            service = service
        )
    }

    @Test
    fun `createAccount_輸入的AccountInvestType是現股_Code是1`() = testScope.runTest {
        val responseBody = getCreateAccountSuccess()
        val requestBodySlot = slot<CreateAccountRequestBody>()
        coEvery {
            service.createAccount(
                url = any(),
                authorization = any(),
                body = capture(requestBodySlot)
            )
        } returns Response.success(responseBody)
        web.createAccount(
            accountInvestType = 1,
            cardSn = 0
        ).getOrThrow()
        Truth.assertThat(requestBodySlot.captured.accountInvestType).isEqualTo(1)
    }

    @Test
    fun `createAccount_輸入的AccountInvestType是期權_Code是2`() =
        testScope.runTest {
            val responseBody = getCreateAccountSuccess()
            val requestBodySlot = slot<CreateAccountRequestBody>()
            coEvery {
                service.createAccount(
                    url = any(),
                    authorization = any(),
                    body = capture(requestBodySlot)
                )
            } returns Response.success(responseBody)
            web.createAccount(
                accountInvestType = 2,
                cardSn = 0
            ).getOrThrow()
            Truth.assertThat(requestBodySlot.captured.accountInvestType).isEqualTo(2)
        }

    @Test
    fun `createAccount_回應的AccountPayType是免費道具卡_Code是0`() = testScope.runTest {
        val responseBody = getCreateAccountSuccess(accountPayType = 0)
        coEvery {
            service.createAccount(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        val result = web.createAccount(
            accountInvestType = 1,
            cardSn = 0
        ).getOrThrow()
        Truth.assertThat(result.accountPayType).isEqualTo(0)
    }

    @Test
    fun `createAccount_回應的AccountPayType是道具卡租用_Code是1`() = testScope.runTest {
        val responseBody = getCreateAccountSuccess(accountPayType = 1)
        coEvery {
            service.createAccount(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        val result = web.createAccount(
            accountInvestType = 1,
            cardSn = 0
        ).getOrThrow()
        Truth.assertThat(result.accountPayType).isEqualTo(1)
    }

    @Test
    fun `createAccount_回應的AccountPayType是道具卡過期_Code是2`() = testScope.runTest {
        val responseBody = getCreateAccountSuccess(accountPayType = 2)
        coEvery {
            service.createAccount(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        val result = web.createAccount(
            accountInvestType = 1,
            cardSn = 0
        ).getOrThrow()
        Truth.assertThat(result.accountPayType).isEqualTo(2)
    }

    @Test
    fun `createAccount_回應的AccountPayType是競技帳戶是3`() = testScope.runTest {
        val responseBody = getCreateAccountSuccess(accountPayType = 3)
        coEvery {
            service.createAccount(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        val result = web.createAccount(
            accountInvestType = 1,
            cardSn = 0
        ).getOrThrow()
        Truth.assertThat(result.accountPayType).isEqualTo(3)
    }

    @Test
    fun `createAccount_回應的AccountPayType是競技帳戶凍結_Code是4`() = testScope.runTest {
        val responseBody = getCreateAccountSuccess(accountPayType = 4)
        coEvery {
            service.createAccount(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        val result = web.createAccount(
            accountInvestType = 1,
            cardSn = 0
        ).getOrThrow()
        Truth.assertThat(result.accountPayType).isEqualTo(4)
    }

    @Test
    fun `createTseOtcDelegate_輸入的BuySellType是買_Code是66`() = testScope.runTest {
        val responseBody = getCreateTseOtcDelegateSuccess()
        val requestBodySlot = slot<CreateDelegateRequestBody>()
        coEvery {
            service.createTseOtcDelegate(
                url = any(),
                authorization = any(),
                body = capture(requestBodySlot)
            )
        } returns Response.success(responseBody)
        web.createTseOtcDelegate(
            accountId = 0,
            buySellType = 66,
            commodityId = "",
            subsistingType = 82,
            groupId = 0,
            delegatePrice = "0",
            delegateVolume = 0,
            marketUnit = 1,
            transactionType = 1
        ).getOrThrow()
        Truth.assertThat(requestBodySlot.captured.buySellType).isEqualTo(66)
    }

    @Test
    fun `createTseOtcDelegate_輸入的BuySellType是賣_Code是83`() = testScope.runTest {
        val responseBody = getCreateTseOtcDelegateSuccess()
        val requestBodySlot = slot<CreateDelegateRequestBody>()
        coEvery {
            service.createTseOtcDelegate(
                url = any(),
                authorization = any(),
                body = capture(requestBodySlot)
            )
        } returns Response.success(responseBody)
        web.createTseOtcDelegate(
            accountId = 0,
            buySellType = 83,
            commodityId = "",
            subsistingType = 82,
            groupId = 0,
            delegatePrice = "0",
            delegateVolume = 0,
            marketUnit = 1,
            transactionType = 1
        ).getOrThrow()
        Truth.assertThat(requestBodySlot.captured.buySellType).isEqualTo(83)
    }

    @Test
    fun `createTseOtcDelegate_輸入的SubsistingType是Rod_Code是82`() = testScope.runTest {
        val responseBody = getCreateTseOtcDelegateSuccess()
        val requestBodySlot = slot<CreateDelegateRequestBody>()
        coEvery {
            service.createTseOtcDelegate(
                url = any(),
                authorization = any(),
                body = capture(requestBodySlot)
            )
        } returns Response.success(responseBody)
        web.createTseOtcDelegate(
            accountId = 0,
            buySellType = 66,
            commodityId = "",
            subsistingType = 82,
            groupId = 0,
            delegatePrice = "0",
            delegateVolume = 0,
            marketUnit = 1,
            transactionType = 1
        ).getOrThrow()
        Truth.assertThat(requestBodySlot.captured.subsistingType).isEqualTo(82)
    }

    @Test
    fun `createTseOtcDelegate_輸入的SubsistingType是Ioc_Code是73`() = testScope.runTest {
        val responseBody = getCreateTseOtcDelegateSuccess()
        val requestBodySlot = slot<CreateDelegateRequestBody>()
        coEvery {
            service.createTseOtcDelegate(
                url = any(),
                authorization = any(),
                body = capture(requestBodySlot)
            )
        } returns Response.success(responseBody)
        web.createTseOtcDelegate(
            accountId = 0,
            buySellType = 66,
            commodityId = "",
            subsistingType = 73,
            groupId = 0,
            delegatePrice = "0",
            delegateVolume = 0,
            marketUnit = 1,
            transactionType = 1
        ).getOrThrow()
        Truth.assertThat(requestBodySlot.captured.subsistingType).isEqualTo(73)
    }

    @Test
    fun `createTseOtcDelegate_輸入的SubsistingType是Rod_Code是70`() = testScope.runTest {
        val responseBody = getCreateTseOtcDelegateSuccess()
        val requestBodySlot = slot<CreateDelegateRequestBody>()
        coEvery {
            service.createTseOtcDelegate(
                url = any(),
                authorization = any(),
                body = capture(requestBodySlot)
            )
        } returns Response.success(responseBody)
        web.createTseOtcDelegate(
            accountId = 0,
            buySellType = 66,
            commodityId = "",
            subsistingType = 70,
            groupId = 0,
            delegatePrice = "0",
            delegateVolume = 0,
            marketUnit = 1,
            transactionType = 1
        ).getOrThrow()
        Truth.assertThat(requestBodySlot.captured.subsistingType).isEqualTo(70)
    }

    @Test
    fun `createTseOtcDelegate_輸入的TradingMarketUnit是整股_Code是1`() = testScope.runTest {
        val responseBody = getCreateTseOtcDelegateSuccess()
        val requestBodySlot = slot<CreateDelegateRequestBody>()
        coEvery {
            service.createTseOtcDelegate(
                url = any(),
                authorization = any(),
                body = capture(requestBodySlot)
            )
        } returns Response.success(responseBody)
        web.createTseOtcDelegate(
            accountId = 0,
            buySellType = 66,
            commodityId = "",
            subsistingType = 82,
            groupId = 0,
            delegatePrice = "0",
            delegateVolume = 0,
            marketUnit = 1,
            transactionType = 1
        ).getOrThrow()
        Truth.assertThat(requestBodySlot.captured.marketUnit).isEqualTo(1)
    }

    @Test
    fun `createTseOtcDelegate_輸入的TransactionType是現股_Code是1`() = testScope.runTest {
        val responseBody = getCreateTseOtcDelegateSuccess()
        val requestBodySlot = slot<CreateDelegateRequestBody>()
        coEvery {
            service.createTseOtcDelegate(
                url = any(),
                authorization = any(),
                body = capture(requestBodySlot)
            )
        } returns Response.success(responseBody)
        web.createTseOtcDelegate(
            accountId = 0,
            buySellType = 66,
            commodityId = "",
            subsistingType = 82,
            groupId = 0,
            delegatePrice = "0",
            delegateVolume = 0,
            marketUnit = 1,
            transactionType = 1
        ).getOrThrow()
        Truth.assertThat(requestBodySlot.captured.transactionType).isEqualTo(1)
    }

    @Test
    fun `createTseOtcDelegate_輸入的TransactionType是融資_Code是2`() = testScope.runTest {
        val responseBody = getCreateTseOtcDelegateSuccess()
        val requestBodySlot = slot<CreateDelegateRequestBody>()
        coEvery {
            service.createTseOtcDelegate(
                url = any(),
                authorization = any(),
                body = capture(requestBodySlot)
            )
        } returns Response.success(responseBody)
        web.createTseOtcDelegate(
            accountId = 0,
            buySellType = 66,
            commodityId = "",
            subsistingType = 82,
            groupId = 0,
            delegatePrice = "0",
            delegateVolume = 0,
            marketUnit = 1,
            transactionType = 2
        ).getOrThrow()
        Truth.assertThat(requestBodySlot.captured.transactionType).isEqualTo(2)
    }

    @Test
    fun `createTseOtcDelegate_輸入的TransactionType是融券_Code是3`() = testScope.runTest {
        val responseBody = getCreateTseOtcDelegateSuccess()
        val requestBodySlot = slot<CreateDelegateRequestBody>()
        coEvery {
            service.createTseOtcDelegate(
                url = any(),
                authorization = any(),
                body = capture(requestBodySlot)
            )
        } returns Response.success(responseBody)
        web.createTseOtcDelegate(
            accountId = 0,
            buySellType = 66,
            commodityId = "",
            subsistingType = 82,
            groupId = 0,
            delegatePrice = "0",
            delegateVolume = 0,
            marketUnit = 1,
            transactionType = 3
        ).getOrThrow()
        Truth.assertThat(requestBodySlot.captured.transactionType).isEqualTo(3)
    }

    @Test
    fun `createTseOtcDelegate_輸入的delegatePrice隨機的產生String_轉成Decimal是一樣的`() =
        testScope.runTest {
            val except = listOf(
                "0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1.0",
                "1.1", "1.2", "1.3", "1.4", "1.5", "1.6", "1.7", "1.8", "1.9", "2.0",
                "1.11", "1.21", "1.31", "1.41", "1.51", "1.61", "1.71", "1.81", "1.91", "2.01"
            ).map {
                it.toBigDecimal()
            }
            listOf(
                "0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1.0",
                "1.1", "1.2", "1.3", "1.4", "1.5", "1.6", "1.7", "1.8", "1.9", "2.0",
                "1.11", "1.21", "1.31", "1.41", "1.51", "1.61", "1.71", "1.81", "1.91", "2.01"
            ).forEachIndexed { index, price ->
                val responseBody = getCreateTseOtcDelegateSuccess()
                val requestBodySlot = slot<CreateDelegateRequestBody>()
                coEvery {
                    service.createTseOtcDelegate(
                        url = any(),
                        authorization = any(),
                        body = capture(requestBodySlot)
                    )
                } returns Response.success(responseBody)
                web.createTseOtcDelegate(
                    accountId = 0,
                    buySellType = 66,
                    commodityId = "",
                    subsistingType = 82,
                    groupId = 0,
                    delegatePrice = price,
                    delegateVolume = 0,
                    marketUnit = 1,
                    transactionType = 1
                ).getOrThrow()
                Truth.assertThat(requestBodySlot.captured.delegatePrice).isEqualTo(except[index].toPlainString())
            }
        }

    @Test
    fun `createTseOtcDelegate_輸入的delegateVolume隨機的產生String的Decimal_轉成Long是一樣的`() =
        testScope.runTest {
            val except = listOf(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 0
            )
            listOf(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 0
            ).forEachIndexed { index, volume ->
                val responseBody = getCreateTseOtcDelegateSuccess()
                val requestBodySlot = slot<CreateDelegateRequestBody>()
                coEvery {
                    service.createTseOtcDelegate(
                        url = any(),
                        authorization = any(),
                        body = capture(requestBodySlot)
                    )
                } returns Response.success(responseBody)
                web.createTseOtcDelegate(
                    accountId = 0,
                    buySellType = 66,
                    commodityId = "",
                    subsistingType = 82,
                    groupId = 0,
                    delegatePrice = "0",
                    delegateVolume = volume,
                    marketUnit = 1,
                    transactionType = 1
                ).getOrThrow()
                Truth.assertThat(requestBodySlot.captured.delegateVolume).isEqualTo(except[index])
            }
        }

    @Test
    fun `deleteTseOtcDelegate_成功`() = testScope.runTest {
        val responseBody = getDeleteTseOtcDelegateSuccess()
        coEvery {
            service.deleteTseOtcDelegate(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        val result = web.deleteTseOtcDelegate(
            accountId = 0,
            groupId = 0,
            delegateId = 1
        )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `getAccount_成功`() = testScope.runTest {
        val responseBody = getAccountSuccess()
        coEvery {
            service.getAccount(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        val result = web.getAccount(
            query = """
                {
    accountInfo(accountId: 1234) {
    account
    accountPayType
    accountType
    avgMonthOrderCount
    borrowFunds
    borrowLimit
    canWatch
    createTime
    defaultFunds
    extendFunds
    funds
    groupId
    isDefault
    isDelete
    isEmail
    maxReadSn
    memberId
    name
    needFee
    needTax
    optIncomeLoss
    stockIncomeLoss
    tmxIncomeLoss
    totalPunishment
    tradedWarrantDate
    updateTime
    viewTime
    warrantIncomeLoss
    }
    }
            """.trimIndent()
        ).getOrThrow()
        Truth.assertThat(result.content?.accountInfo).isNotNull()
    }

    @Test(expected = ServerException::class)
    fun `getAccount_失敗_ServerException`() = testScope.runTest {
        val error = CMoneyError(message = "")
        val responseBody = gson.toJson(error, CMoneyError::class.java).toResponseBody()
        coEvery {
            service.getAccount(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(400, responseBody)
        web.getAccount(
            query = """
                {
    accountInfo(accountId: 1234) {
    account
    }
    }
            """.trimIndent()
        ).getOrThrow()
    }

    @Test
    fun `getAllAccount_成功`() = testScope.runTest {
        val responseBody = getAllAccountSuccess()
        coEvery {
            service.getAllAccount(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        val result = web.getAllAccount(
            query = """
                {
                  allAccountInfo {
                    account
                    accountPayType
                    accountType
                    avgMonthOrderCount
                    borrowFunds
                    borrowLimit
                    canWatch
                    createTime
                    defaultFunds
                    extendFunds
                    funds
                    groupId
                    isDefault
                    isDelete
                    isEmail
                    maxReadSn
                    memberId
                    name
                    needFee
                    needTax
                    optIncomeLoss
                    stockIncomeLoss
                    tmxIncomeLoss
                    totalPunishment
                    tradedWarrantDate
                    updateTime
                    viewTime
                    warrantIncomeLoss
                  }
                }
            """.trimIndent()
        ).getOrThrow()
        Truth.assertThat(result.content?.allAccountInfo).isNotEmpty()
    }

    @Test(expected = ServerException::class)
    fun `getAllAccount_失敗_ServerException`() = testScope.runTest {
        val error = CMoneyError(message = "")
        val responseBody = gson.toJson(error, CMoneyError::class.java).toResponseBody()
        coEvery {
            service.getAllAccount(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(400, responseBody)
        web.getAllAccount(
            query = """
                {
                  allAccountInfo {
                    account
                  }
                }
            """.trimIndent()
        ).getOrThrow()
    }

    @Test
    fun `getAccountRatio_成功`() = testScope.runTest {
        val responseBody = getAccountRatioSuccess()
        coEvery {
            service.getAccountRatio(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        val result = web.getAccountRatio(
            query = """
                {
                    accountRatios(accountId: 1234, mkType: 1, dateCount: 360) {
                        account
                        dataDe
                        funds
                        inventoryValues
                        isWeekend
                        ratio
                    }
                }
            """.trimIndent()
        ).getOrThrow()
        Truth.assertThat(result.content?.accountRatios).isNotEmpty()
    }

    @Test(expected = ServerException::class)
    fun `getAccountRatio_失敗_ServerException`() = testScope.runTest {
        val error = CMoneyError(message = "")
        val responseBody = gson.toJson(error, CMoneyError::class.java).toResponseBody()
        coEvery {
            service.getAccountRatio(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(400, responseBody)
        web.getAccountRatio(
            query = """
                {
                accountRatios(accountId: 1234, mkType: 1, dateCount: 180) {
                account
                }
                }
            """.trimIndent()
        ).getOrThrow()
    }

    @Test
    fun `getTseOtcAllDelegate_成功`() = testScope.runTest {
        val responseBody = getTseOtcAllDelegateSuccess()
        coEvery {
            service.getTseOtcAllDelegate(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        val result = web.getTseOtcAllDelegate(
            query = """
                {
                    tseOtcOrderByCustomPeriod(
                    accountId: 1234,
                    beginTime: "2023/01/01",
                    endTime: "2023/01/31",
                    tradeType: 1
                ) {
                    ordNo
                    targetOrdNo
                    account
                    groupId
                    tradeTime
                    status
                    ordType
                    condition
                    tradeType
                    stockMarketType
                    buySellType
                    commKey
                    ordPr
                    ordQty
                    dealAvgPr
                    dealQty
                    avQty
                    cutQty
                    prePayment
                    serverRcvTe
                    serverRcvNo
                    marginCredit
                    marginOwn
                    shortSellingCollateral
                    shortSellingEntrust
                    memo
                    noteId
                    modifyTime
                }
            }
            """.trimIndent()
        ).getOrThrow()
        Truth.assertThat(result.content?.delegateList).isNotEmpty()
    }

    @Test(expected = ServerException::class)
    fun `getTseOtcAllDelegate_失敗_ServerException`() = testScope.runTest {
        val error = CMoneyError(message = "")
        val responseBody = gson.toJson(error, CMoneyError::class.java).toResponseBody()
        coEvery {
            service.getTseOtcAllDelegate(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(400, responseBody)
        web.getTseOtcAllDelegate(
            query = """
                {
                    tseOtcOrderByCustomPeriod(
                        accountId: acountId,
                        beginTime: "2023/01/01",
                        endTime: "2023/01/31",
                        tradeType: 1
                    ) {
                        ordNo
                    }
                }
            """.trimIndent()
        ).getOrThrow()
    }

    @Test
    fun `getTseOtcDelegateDetail_成功`() = testScope.runTest {
        val responseBody = getTseOtcDelegateDetailSuccess()
        coEvery {
            service.getTseOtcDelegateDetail(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        val result = web.getTseOtcDelegateDetail(
            query = """
            {
                tseOtcOrder(accountId: 1234, orderNo: 1234) {
                    ordNo
                    targetOrdNo
                    account
                    groupId
                    tradeTime
                    status
                    ordType
                    condition
                    tradeType
                    stockMarketType
                    buySellType
                    commKey
                    ordPr
                    ordQty
                    dealAvgPr
                    dealQty
                    avQty
                    cutQty
                    prePayment
                    serverRcvTe
                    serverRcvNo
                    marginCredit
                    marginOwn
                    shortSellingCollateral
                    shortSellingEntrust
                    memo
                    noteId
                    modifyTime
                }
            }
            """.trimIndent()
        ).getOrThrow()
        Truth.assertThat(result.content?.delegate).isNotNull()
    }

    @Test(expected = ServerException::class)
    fun `getTseOtcDelegateDetail_失敗_ServerException`() = testScope.runTest {
        val error = CMoneyError(message = "")
        val responseBody = gson.toJson(error, CMoneyError::class.java).toResponseBody()
        coEvery {
            service.getTseOtcDelegateDetail(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(400, responseBody)
        web.getTseOtcDelegateDetail(
            query = """
            {
                tseOtcOrder(accountId: 1234, orderNo: 1234) {
                    ordNo
                }
            }
            """.trimIndent()
        ).getOrThrow()
    }

    @Test
    fun `getTseOtcAllSuccessDeal_成功`() = testScope.runTest {
        val responseBody = getTseOtcAllSuccessDealSuccess()
        coEvery {
            service.getTseOtcAllSuccessDeal(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        val result = web.getTseOtcAllSuccessDeal(
            query = """
                {
                    tseOtcDealByCustomPeriod(
                        accountId: 1234,
                        beginTime: "2023/01/01",
                        endTime: "2023/01/31",
                        tradeType: 1234
                    ) {
                        te
                        account
                        ordNo
                        stockMarketType
                        tradeType
                        buySellType
                        commKey
                        dealPr
                        dealQty
                        fee
                        tax
                        dealTno
                        flag
                        sn
                        shortSellingFee
                        memo
                        actualCost
                        borrow
                        bsAvgPr
                        remainQty
                        isSuccess
                    }
                }
            """.trimIndent()
        ).getOrThrow()
        Truth.assertThat(result.content?.successDealList).isNotEmpty()
    }

    @Test(expected = ServerException::class)
    fun `getTseOtcAllSuccessDeal_失敗_ServerException`() = testScope.runTest {
        val error = CMoneyError(message = "")
        val responseBody = gson.toJson(error, CMoneyError::class.java).toResponseBody()
        coEvery {
            service.getTseOtcAllSuccessDeal(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(400, responseBody)
        web.getTseOtcAllSuccessDeal(
            query = """
                {
                    tseOtcDealByCustomPeriod(
                        accountId: 1234,
                        beginTime: "yyyy/MM/dd",
                        endTime: "yyyy/MM/dd",
                        tradeType: 1
                    ) {
                        account
                    }
                }
        
            """.trimIndent()
        ).getOrThrow()
    }

    @Test
    fun `getTseOtcSuccessDealDetail_成功`() = testScope.runTest {
        val responseBody = getTseOtcSuccessDealDetailSuccess()
        coEvery {
            service.getTseOtcSuccessDealDetail(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        val result = web.getTseOtcSuccessDealDetail(
            query = """
                {
                  tseOtcDeal(
                    accountId: 1234,
                    orderNo: 1234
                  ) {
                      te
                      account
                      ordNo
                      stockMarketType
                      tradeType
                      buySellType
                      commKey
                      dealPr
                      dealQty
                      fee
                      tax
                      dealTno
                      flag
                      sn
                      shortSellingFee
                      memo
                      actualCost
                      borrow
                      bsAvgPr
                      remainQty
                      isSuccess
                  }
                }
            """.trimIndent()
        ).getOrThrow()
        Truth.assertThat(result.content?.delegate).isNotNull()
    }

    @Test(expected = ServerException::class)
    fun `getTseOtcSuccessDealDetail_失敗_ServerException`() = testScope.runTest {
        val error = CMoneyError(message = "")
        val responseBody = gson.toJson(error, CMoneyError::class.java).toResponseBody()
        coEvery {
            service.getTseOtcSuccessDealDetail(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(400, responseBody)
        web.getTseOtcSuccessDealDetail(
            query = """
                {
                    tseOtcDeal(
                        accountId: 1234,
                        orderNo: 1234
                    ) {
                        account
                    }
                }
            """.trimIndent()
        ).getOrThrow()
    }

    @Test
    fun `getTseOtcInventory_成功`() = testScope.runTest {
        val responseBody = getTseOtcInventorySuccess()
        coEvery {
            service.getTseOtcInventory(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        val result = web.getTseOtcInventory(
            query = """
                {
                  tseOtcPosition(accountId: 1234) {
                    account
                    bs
                    canOrdQty
                    commKey
                    commName
                    cost
                    createTime
                    dealAvgPr
                    incomeLoss
                    incomeLossWithoutPreFee
                    inventoryQty
                    nowPr
                    ratio
                    shortSellingFee
                    showCost
                    taxCost
                    todayInventoryQty
                    tradeName
                    tradeType
                  }
                }
            """.trimIndent()
        ).getOrThrow()
        Truth.assertThat(result.content?.inventoryList).isNotEmpty()
    }

    @Test(expected = ServerException::class)
    fun `getTseOtcInventory_失敗_ServerException`() = testScope.runTest {
        val error = CMoneyError(message = "")
        val responseBody = gson.toJson(error, CMoneyError::class.java).toResponseBody()
        coEvery {
            service.getTseOtcInventory(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(400, responseBody)
        web.getTseOtcInventory(
            query = """
                {
                  tseOtcPosition(accountId: 1234) {
                    account
                  }
                }
            """.trimIndent()
        ).getOrThrow()
    }
}