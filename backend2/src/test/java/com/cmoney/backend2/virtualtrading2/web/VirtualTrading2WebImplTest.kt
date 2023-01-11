package com.cmoney.backend2.virtualtrading2.web

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.cmoney.backend2.virtualtrading2.getCreateAccountSuccess
import com.cmoney.backend2.virtualtrading2.getCreateTseOtcDelegateSuccess
import com.cmoney.backend2.virtualtrading2.model.requestconfig.VirtualTradingRequestConfig
import com.cmoney.backend2.virtualtrading2.service.VirtualTrading2Service
import com.cmoney.backend2.virtualtrading2.service.api.createaccount.CreateAccountRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.createdelegate.CreateDelegateRequestBody
import com.cmoney.backend2.virtualtrading2.web.createaccount.CreateAccountRequest
import com.cmoney.backend2.virtualtrading2.web.createaccount.CreateAccountResponse
import com.cmoney.backend2.virtualtrading2.web.tseotc.createdelegate.CreateDelegateRequest
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
    fun `createAccount_輸入的AccountInvestType是Stock_Code是1`() = testScope.runTest {
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
            request = CreateAccountRequest(
                accountInvestType = CreateAccountRequest.AccountInvestType.Stock
            )
        ).getOrThrow()
        Truth.assertThat(requestBodySlot.captured.accountInvestType).isEqualTo(1)
    }

    @Test
    fun `createAccount_輸入的AccountInvestType是Options_Code是2`() =
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
                request = CreateAccountRequest(
                    accountInvestType = CreateAccountRequest.AccountInvestType.Options
                )
            ).getOrThrow()
            Truth.assertThat(requestBodySlot.captured.accountInvestType).isEqualTo(2)
        }

    @Test
    fun `createAccount_回應的AccountPayType是Free_Code是0`() = testScope.runTest {
        val responseBody = getCreateAccountSuccess(accountPayType = 0)
        coEvery {
            service.createAccount(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        val result = web.createAccount(
            request = CreateAccountRequest(
                accountInvestType = CreateAccountRequest.AccountInvestType.Stock
            )
        ).getOrThrow()
        Truth.assertThat(result.accountPayType).isEqualTo(CreateAccountResponse.AccountPayType.Free)
        Truth.assertThat(result.accountPayType?.code).isEqualTo(0)
    }

    @Test
    fun `createAccount_回應的AccountPayType是CardInUse_Code是1`() = testScope.runTest {
        val responseBody = getCreateAccountSuccess(accountPayType = 1)
        coEvery {
            service.createAccount(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        val result = web.createAccount(
            request = CreateAccountRequest(
                accountInvestType = CreateAccountRequest.AccountInvestType.Stock
            )
        ).getOrThrow()
        Truth.assertThat(result.accountPayType).isEqualTo(CreateAccountResponse.AccountPayType.CardInUse)
        Truth.assertThat(result.accountPayType?.code).isEqualTo(1)
    }

    @Test
    fun `createAccount_回應的AccountPayType是CardExpired_Code是2`() = testScope.runTest {
        val responseBody = getCreateAccountSuccess(accountPayType = 2)
        coEvery {
            service.createAccount(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        val result = web.createAccount(
            request = CreateAccountRequest(
                accountInvestType = CreateAccountRequest.AccountInvestType.Stock
            )
        ).getOrThrow()
        Truth.assertThat(result.accountPayType).isEqualTo(CreateAccountResponse.AccountPayType.CardExpired)
        Truth.assertThat(result.accountPayType?.code).isEqualTo(2)
    }


    @Test
    fun `createAccount_回應的AccountPayType是Sports是3`() = testScope.runTest {
        val responseBody = getCreateAccountSuccess(accountPayType = 3)
        coEvery {
            service.createAccount(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        val result = web.createAccount(
            request = CreateAccountRequest(
                accountInvestType = CreateAccountRequest.AccountInvestType.Stock
            )
        ).getOrThrow()
        Truth.assertThat(result.accountPayType).isEqualTo(CreateAccountResponse.AccountPayType.Sports)
        Truth.assertThat(result.accountPayType?.code).isEqualTo(3)
    }

    @Test
    fun `createAccount_回應的AccountPayType是SportsFreeze_Code是4`() = testScope.runTest {
        val responseBody = getCreateAccountSuccess(accountPayType = 4)
        coEvery {
            service.createAccount(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        val result = web.createAccount(
            request = CreateAccountRequest(
                accountInvestType = CreateAccountRequest.AccountInvestType.Stock
            )
        ).getOrThrow()
        Truth.assertThat(result.accountPayType).isEqualTo(CreateAccountResponse.AccountPayType.SportsFreeze)
        Truth.assertThat(result.accountPayType?.code).isEqualTo(4)
    }
    @Test
    fun `createTseOtcDelegate_輸入的BuySellType是Buy_Code是66`() = testScope.runTest {
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
            request = CreateDelegateRequest(
                accountId = 0,
                buySellType = CreateDelegateRequest.BuySellType.Buy,
                commodityId = "",
                subsistingType = CreateDelegateRequest.SubsistingType.Rod,
                groupId = 0,
                delegatePrice = "0".toBigDecimal(),
                delegateVolume = "0".toBigDecimal(),
                marketUnit = CreateDelegateRequest.TradingMarketUnit.BoardLot,
                transactionType = CreateDelegateRequest.TransactionType.MoneyStock
            )
        ).getOrThrow()
        Truth.assertThat(requestBodySlot.captured.buySellType).isEqualTo(66)
    }

    @Test
    fun `createTseOtcDelegate_輸入的BuySellType是Sell_Code是83`() = testScope.runTest {
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
            request = CreateDelegateRequest(
                accountId = 0,
                buySellType = CreateDelegateRequest.BuySellType.Sell,
                commodityId = "",
                subsistingType = CreateDelegateRequest.SubsistingType.Rod,
                groupId = 0,
                delegatePrice = "0".toBigDecimal(),
                delegateVolume = "0".toBigDecimal(),
                marketUnit = CreateDelegateRequest.TradingMarketUnit.BoardLot,
                transactionType = CreateDelegateRequest.TransactionType.MoneyStock
            )
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
            request = CreateDelegateRequest(
                accountId = 0,
                buySellType = CreateDelegateRequest.BuySellType.Buy,
                commodityId = "",
                subsistingType = CreateDelegateRequest.SubsistingType.Rod,
                groupId = 0,
                delegatePrice = "0".toBigDecimal(),
                delegateVolume = "0".toBigDecimal(),
                marketUnit = CreateDelegateRequest.TradingMarketUnit.BoardLot,
                transactionType = CreateDelegateRequest.TransactionType.MoneyStock
            )
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
            request = CreateDelegateRequest(
                accountId = 0,
                buySellType = CreateDelegateRequest.BuySellType.Buy,
                commodityId = "",
                subsistingType = CreateDelegateRequest.SubsistingType.Ioc,
                groupId = 0,
                delegatePrice = "0".toBigDecimal(),
                delegateVolume = "0".toBigDecimal(),
                marketUnit = CreateDelegateRequest.TradingMarketUnit.BoardLot,
                transactionType = CreateDelegateRequest.TransactionType.MoneyStock
            )
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
            request = CreateDelegateRequest(
                accountId = 0,
                buySellType = CreateDelegateRequest.BuySellType.Buy,
                commodityId = "",
                subsistingType = CreateDelegateRequest.SubsistingType.Fok,
                groupId = 0,
                delegatePrice = "0".toBigDecimal(),
                delegateVolume = "0".toBigDecimal(),
                marketUnit = CreateDelegateRequest.TradingMarketUnit.BoardLot,
                transactionType = CreateDelegateRequest.TransactionType.MoneyStock
            )
        ).getOrThrow()
        Truth.assertThat(requestBodySlot.captured.subsistingType).isEqualTo(70)
    }

    @Test
    fun `createTseOtcDelegate_輸入的TradingMarketUnit是BoardLot_Code是1`() = testScope.runTest {
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
            request = CreateDelegateRequest(
                accountId = 0,
                buySellType = CreateDelegateRequest.BuySellType.Buy,
                commodityId = "",
                subsistingType = CreateDelegateRequest.SubsistingType.Rod,
                groupId = 0,
                delegatePrice = "0".toBigDecimal(),
                delegateVolume = "0".toBigDecimal(),
                marketUnit = CreateDelegateRequest.TradingMarketUnit.BoardLot,
                transactionType = CreateDelegateRequest.TransactionType.MoneyStock
            )
        ).getOrThrow()
        Truth.assertThat(requestBodySlot.captured.marketUnit).isEqualTo(1)
    }

    @Test
    fun `createTseOtcDelegate_輸入的TransactionType是MoneyStock_Code是1`() = testScope.runTest {
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
            request = CreateDelegateRequest(
                accountId = 0,
                buySellType = CreateDelegateRequest.BuySellType.Buy,
                commodityId = "",
                subsistingType = CreateDelegateRequest.SubsistingType.Rod,
                groupId = 0,
                delegatePrice = "0".toBigDecimal(),
                delegateVolume = "0".toBigDecimal(),
                marketUnit = CreateDelegateRequest.TradingMarketUnit.BoardLot,
                transactionType = CreateDelegateRequest.TransactionType.MoneyStock
            )
        ).getOrThrow()
        Truth.assertThat(requestBodySlot.captured.transactionType).isEqualTo(1)
    }

    @Test
    fun `createTseOtcDelegate_輸入的TransactionType是MarginPurchase_Code是2`() = testScope.runTest {
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
            request = CreateDelegateRequest(
                accountId = 0,
                buySellType = CreateDelegateRequest.BuySellType.Buy,
                commodityId = "",
                subsistingType = CreateDelegateRequest.SubsistingType.Rod,
                groupId = 0,
                delegatePrice = "0".toBigDecimal(),
                delegateVolume = "0".toBigDecimal(),
                marketUnit = CreateDelegateRequest.TradingMarketUnit.BoardLot,
                transactionType = CreateDelegateRequest.TransactionType.MarginPurchase
            )
        ).getOrThrow()
        Truth.assertThat(requestBodySlot.captured.transactionType).isEqualTo(2)
    }

    @Test
    fun `createTseOtcDelegate_輸入的TransactionType是ShortSale_Code是3`() = testScope.runTest {
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
            request = CreateDelegateRequest(
                accountId = 0,
                buySellType = CreateDelegateRequest.BuySellType.Buy,
                commodityId = "",
                subsistingType = CreateDelegateRequest.SubsistingType.Rod,
                groupId = 0,
                delegatePrice = "0".toBigDecimal(),
                delegateVolume = "0".toBigDecimal(),
                marketUnit = CreateDelegateRequest.TradingMarketUnit.BoardLot,
                transactionType = CreateDelegateRequest.TransactionType.ShortSale
            )
        ).getOrThrow()
        Truth.assertThat(requestBodySlot.captured.transactionType).isEqualTo(3)
    }

    @Test
    fun `createTseOtcDelegate_輸入的delegatePrice隨機的產生String的Decimal_轉成Double是一樣的`() = testScope.runTest {
        val except = listOf(
            0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0,
            1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9, 2.0,
            1.11, 1.21, 1.31, 1.41, 1.51, 1.61, 1.71, 1.81, 1.91, 2.01
        )
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
                request = CreateDelegateRequest(
                    accountId = 0,
                    buySellType = CreateDelegateRequest.BuySellType.Buy,
                    commodityId = "",
                    subsistingType = CreateDelegateRequest.SubsistingType.Rod,
                    groupId = 0,
                    delegatePrice = price.toBigDecimal(),
                    delegateVolume = "0".toBigDecimal(),
                    marketUnit = CreateDelegateRequest.TradingMarketUnit.BoardLot,
                    transactionType = CreateDelegateRequest.TransactionType.ShortSale
                )
            ).getOrThrow()
            Truth.assertThat(requestBodySlot.captured.delegatePrice).isEqualTo(except[index])
        }
    }
    @Test
    fun `createTseOtcDelegate_輸入的delegateVolume隨機的產生String的Decimal_轉成Long是一樣的`() = testScope.runTest {
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
                request = CreateDelegateRequest(
                    accountId = 0,
                    buySellType = CreateDelegateRequest.BuySellType.Buy,
                    commodityId = "",
                    subsistingType = CreateDelegateRequest.SubsistingType.Rod,
                    groupId = 0,
                    delegatePrice = "0".toBigDecimal(),
                    delegateVolume = volume.toBigDecimal(),
                    marketUnit = CreateDelegateRequest.TradingMarketUnit.BoardLot,
                    transactionType = CreateDelegateRequest.TransactionType.ShortSale
                )
            ).getOrThrow()
            Truth.assertThat(requestBodySlot.captured.delegateVolume).isEqualTo(except[index])
        }
    }
}