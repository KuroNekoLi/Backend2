package com.cmoney.backend2.vtwebapi.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.vtwebapi.service.api.createaccount.AccountType
import com.cmoney.backend2.vtwebapi.service.api.createaccount.CreateAccountRequestBody
import com.cmoney.backend2.vtwebapi.service.api.getaccount.GetAccountResponseBody
import com.cmoney.backend2.vtwebapi.service.api.getattendgroup.GetAttendGroupResponseBody
import com.cmoney.backend2.vtwebapi.service.api.getcardinstancesns.GetCardInstanceSnsResponseBody
import com.cmoney.backend2.vtwebapi.service.api.getcardinstancesns.UsageType
import com.cmoney.backend2.vtwebapi.service.api.getstockinventorylist.GetStockInventoryListResponseBody
import com.cmoney.backend2.vtwebapi.service.api.purchaseproductcard.PurchaseProductCardRequestBody
import com.cmoney.backend2.vtwebapi.service.api.purchaseproductcard.PurchaseProductCardResponseBody
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class VirtualTradeWebImplTest {

    private val testScope = TestScope()
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var service: VirtualTradeService
    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager
    private lateinit var web: VirtualTradeWebImpl
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        web = VirtualTradeWebImpl(
            globalBackend2Manager = manager,
            service = service,
            dispatcher = TestDispatcherProvider(),
            gson = gson
        )
        coEvery {
            manager.getVirtualTradeSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getAccount_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}vt.webapi/Account"
        val urlSlot = slot<String>()
        coEvery {
            service.getAccount(
                url = capture(urlSlot),
                authorization = any(),
                destMemberPk = any(),
                skipCount = any(),
                fetchSize = any(),
                needGroupAccount = any(),
                needExtendInfo = any()
            )
        } returns Response.success(listOf())

        web.getAccount()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getAccountTestSuccess() = testScope.runTest {
        val responseBody = GetAccountResponseBody(
            cardInstanceSn = 0,
            payType = 0,
            account = 1,
            name = "小資族",
            groupId = 0,
            memberPK = 0,
            defaultFunds = 2000000.0,
            extendFunds = 0.0,
            funds = 0.0,
            allPrePayment = 0.0,
            inventoryValue = 0.0,
            foInventoryValue = 0.0,
            ratio = 0.0,
            isTracked = false,
            beTrackedCount = 0,
            accountType = 7,
            maintenanceRate = 0.0,
            activeDate = "2022-03-08T13:48:09.793",
            needFee = false,
            needTax = false,
            canWatch = false,
            isDefault = true,
            isDelete = false,
            ratioRankType = 0,
            viewTime = "2022-03-14T11:02:19.308",
            avgMonthOrderCount = 0.0,
            isEmail = false,
            punishment = 0.0,
            dividendMemoney = 0.0,
            tradedWarrantDate = 0,
            incomeLoss = 0.0,
            totalFunds = 0.0,
            borrowLimit = 0.0,
            borrowFunds = 0.0,
            arenaAdInfoList = listOf()
        )
        coEvery {
            service.getAccount(
                url = any(),
                authorization = any(),
                destMemberPk = any(),
                skipCount = any(),
                fetchSize = any(),
                needGroupAccount = any(),
                needExtendInfo = any()
            )
        } returns Response.success(listOf(responseBody))

        val result = web.getAccount()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getAccountTestFailure() = testScope.runTest {
        coEvery {
            service.getAccount(
                url = any(),
                authorization = any(),
                destMemberPk = any(),
                skipCount = any(),
                fetchSize = any(),
                needGroupAccount = any(),
                needExtendInfo = any()
            )
        } returns Response.error(409, "".toResponseBody())

        val result = web.getAccount()
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `createAccount_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}vt.webapi/Account"
        val urlSlot = slot<String>()
        val responseBody = GetAccountResponseBody(
            cardInstanceSn = 0,
            payType = 0,
            account = 0,
            name = null,
            groupId = 0,
            memberPK = 0,
            defaultFunds = 0.0,
            extendFunds = 0.0,
            funds = 0.0,
            allPrePayment = 0.0,
            inventoryValue = 0.0,
            foInventoryValue = 0.0,
            ratio = 0.0,
            isTracked = false,
            beTrackedCount = 0,
            accountType = 0,
            maintenanceRate = 0.0,
            activeDate = "",
            needFee = false,
            needTax = false,
            canWatch = false,
            isDefault = false,
            isDelete = false,
            ratioRankType = 0,
            viewTime = "",
            avgMonthOrderCount = 0.0,
            isEmail = false,
            punishment = 0.0,
            dividendMemoney = 0.0,
            tradedWarrantDate = 0,
            incomeLoss = 0.0,
            totalFunds = 0.0,
            borrowLimit = 0.0,
            borrowFunds = 0.0,
            arenaAdInfoList = listOf()
        )
        coEvery {
            service.createAccount(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        web.createAccount(type = AccountType.STOCK, isn = 0)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun createAccountTestSuccess() = testScope.runTest {
        val requestBody = CreateAccountRequestBody(
            type = AccountType.STOCK.typeNum,
            isn = 0
        )
        val responseBody = GetAccountResponseBody(
            cardInstanceSn = 0,
            payType = 0,
            account = 1,
            name = "小資族",
            groupId = 0,
            memberPK = 0,
            defaultFunds = 2000000.0,
            extendFunds = 0.0,
            funds = 0.0,
            allPrePayment = 0.0,
            inventoryValue = 0.0,
            foInventoryValue = 0.0,
            ratio = 0.0,
            isTracked = false,
            beTrackedCount = 0,
            accountType = 7,
            maintenanceRate = 0.0,
            activeDate = "2022-03-08T13:48:09.793",
            needFee = false,
            needTax = false,
            canWatch = false,
            isDefault = true,
            isDelete = false,
            ratioRankType = 0,
            viewTime = "2022-03-14T11:02:19.308",
            avgMonthOrderCount = 0.0,
            isEmail = false,
            punishment = 0.0,
            dividendMemoney = 0.0,
            tradedWarrantDate = 0,
            incomeLoss = 0.0,
            totalFunds = 0.0,
            borrowLimit = 0.0,
            borrowFunds = 0.0,
            arenaAdInfoList = listOf()
        )
        coEvery {
            service.createAccount(
                url = any(),
                authorization = any(),
                body = requestBody
            )
        } returns Response.success(responseBody)

        val result = web.createAccount(type = AccountType.STOCK, isn = 0)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun createAccountTestFailure() = testScope.runTest {
        val requestBody = CreateAccountRequestBody(
            type = AccountType.STOCK.typeNum,
            isn = 0
        )
        coEvery {
            service.createAccount(
                url = any(),
                authorization = any(),
                body = requestBody
            )
        } returns Response.error(409, "".toResponseBody())

        val result = web.createAccount(type = AccountType.STOCK, isn = 0)
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `getCardInstanceSns_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}vt.webapi/ByProductSn"
        val urlSlot = slot<String>()
        val responseBody = GetCardInstanceSnsResponseBody(cardInstanceSns = listOf())
        coEvery {
            service.getCardInstanceSns(
                url = capture(urlSlot),
                authorization = any(),
                productSn = any(),
                productUsage = any()
            )
        } returns Response.success(responseBody)

        web.getCardInstanceSns(
            productSn = 20,
            productUsage = UsageType.UNUSED
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getCardInstanceSnsTestSuccess() = testScope.runTest {
        val responseBody = GetCardInstanceSnsResponseBody(cardInstanceSns = listOf(1L))
        coEvery {
            service.getCardInstanceSns(
                url = any(),
                authorization = any(),
                productSn = any(),
                productUsage = any()
            )
        } returns Response.success(responseBody)

        val result = web.getCardInstanceSns(
            productSn = 20,
            productUsage = UsageType.UNUSED
        )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getCardInstanceSnsTestFailure() = testScope.runTest {
        coEvery {
            service.getCardInstanceSns(
                url = any(),
                authorization = any(),
                productSn = any(),
                productUsage = any()
            )
        } returns Response.error(409, "".toResponseBody())

        val result = web.getCardInstanceSns(
            productSn = 20,
            productUsage = UsageType.UNUSED
        )
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `purchaseProductCard_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}vt.webapi/ProductCard/PurchaseProductCard"
        val urlSlot = slot<String>()
        val responseBody = PurchaseProductCardResponseBody(instanceSn = 99)
        coEvery {
            service.purchaseProductCard(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        web.purchaseProductCard(
            giftFromMember = 1,
            ownerMemberPk = 200,
            productSn = 20
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun purchaseProductCardTestSuccess() = testScope.runTest {
        val requestBody = PurchaseProductCardRequestBody(
            giftFromMember = 1,
            ownerMemberPk = 200,
            productSn = 20
        )
        val responseBody = PurchaseProductCardResponseBody(instanceSn = 99)
        coEvery {
            service.purchaseProductCard(
                url = any(),
                authorization = any(),
                body = requestBody
            )
        } returns Response.success(responseBody)

        val result = web.purchaseProductCard(
            giftFromMember = 1,
            ownerMemberPk = 200,
            productSn = 20
        )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun purchaseProductCardTestFailure() = testScope.runTest {
        val requestBody = PurchaseProductCardRequestBody(
            giftFromMember = 1,
            ownerMemberPk = 200,
            productSn = 20
        )
        coEvery {
            service.purchaseProductCard(
                url = any(),
                authorization = any(),
                body = requestBody
            )
        } returns Response.error(409, "".toResponseBody())

        val result = web.purchaseProductCard(
            giftFromMember = 1,
            ownerMemberPk = 200,
            productSn = 20
        )
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `getAttendGroup_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}vt.webapi/Group/Attend"
        val urlSlot = slot<String>()
        coEvery {
            service.getAttendGroup(
                url = capture(urlSlot),
                authorization = any(),
                fetchIndex = any(),
                fetchSize = any()
            )
        } returns Response.success(listOf())

        web.getAttendGroup()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getAttendGroupTestSuccess() = testScope.runTest {
        val responseBody = GetAttendGroupResponseBody(
            account = 1,
            accountType = null,
            arenaAdInfoList = listOf(),
            contactInfo = null,
            defaultFunds = 500000.0,
            description = null,
            effectiveFunctions = null,
            endTime = "2022-04-20T00:00:00",
            fee = null,
            feeDiscountRatio = null,
            foFeeDiscountRatioObjs = listOf(),
            groupChannelId = null,
            groupId = 1,
            instanceSn = null,
            isClose = null,
            isGroupManager = null,
            isPublic = null,
            isRelease = null,
            joinMethod = null,
            link = null,
            logoImg = null,
            managerPk = 20000,
            marginInterest = null,
            maxMemberCount = 10,
            memberFunds = null,
            memberRank = null,
            memberRatio = null,
            memberRegStatus = 2,
            name = "Test",
            otcMargin = null,
            positionLimit = listOf(),
            priorityOrder = null,
            registrationEndDate = "0001-01-01T00:00:00",
            registrationStartDate = "0001-01-01T00:00:00",
            remainMemberCount = null,
            shortSellingEntrust = null,
            shortSellingFee = null,
            shortSellingInterest = null,
            signboardImg = null,
            startTime = "2022-03-22T00:00:00",
            tradingTax = null,
            tseMargin = null,
            warrantMargin = null,
            warrantTradingTax = null
        )
        coEvery {
            service.getAttendGroup(
                url = any(),
                authorization = any(),
                fetchIndex = any(),
                fetchSize = any()
            )
        } returns Response.success(listOf(responseBody))

        val result = web.getAttendGroup()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getAttendGroupTestFailure() = testScope.runTest {
        coEvery {
            service.getAttendGroup(
                url = any(),
                authorization = any(),
                fetchIndex = any(),
                fetchSize = any()
            )
        } returns Response.error(409, "".toResponseBody())

        val result = web.getAttendGroup()
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `getStockInventoryList_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}vt.webapi/Inventory/SecInventoryListByAccount/1"
        val urlSlot = slot<String>()
        coEvery {
            service.getStockInventoryList(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(listOf())

        web.getStockInventoryList(account = 1)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getStockInventoryListTestSuccess() = testScope.runTest {
        val responseBody = GetStockInventoryListResponseBody(
            account = 1,
            boardLostSize = 1,
            canOrderQty = 2000,
            commName = "台積電",
            commkey = "2330",
            cost = 200000.0,
            dealAvgPr = null,
            groupId = null,
            incomeLoss = null,
            incomeLossWithoutPreFree = null,
            inventoryQty = 2000,
            mainRatio = null,
            marketValue = null,
            nowPr = 570.0,
            ratio = null,
            shortSellingFee = null,
            showCost = null,
            taxcost = null,
            todayInventoryQty = null,
            tradeKind = null,
            tradeName = null
        )
        coEvery {
            service.getStockInventoryList(
                url = any(),
                authorization = any()
            )
        } returns Response.success(listOf(responseBody))

        val result = web.getStockInventoryList(account = 1)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getStockInventoryListTestFailure() = testScope.runTest {
        coEvery {
            service.getStockInventoryList(
                url = any(),
                authorization = any()
            )
        } returns Response.error(409, "".toResponseBody())

        val result = web.getStockInventoryList(account = 1)
        Truth.assertThat(result.isSuccess).isFalse()
    }

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }
}