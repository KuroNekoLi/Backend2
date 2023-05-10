package com.cmoney.backend2.customgroup2.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.base.model.request.Language
import com.cmoney.backend2.customgroup2.service.api.createcustomgroup.CreateCustomGroupResponseBody
import com.cmoney.backend2.customgroup2.service.api.data.CustomGroup
import com.cmoney.backend2.customgroup2.service.api.data.DocMarketType
import com.cmoney.backend2.customgroup2.service.api.data.Document
import com.cmoney.backend2.customgroup2.service.api.data.MarketTypeV2
import com.cmoney.backend2.customgroup2.service.api.data.RawStock
import com.cmoney.backend2.customgroup2.service.api.data.StockV2
import com.cmoney.backend2.customgroup2.service.api.data.UserConfigurationDocument
import com.cmoney.backend2.customgroup2.service.api.getcustomgroup.Documents
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

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class CustomGroup2WebImplTest {

    private val testScope = TestScope()

    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var service: CustomGroup2Service
    private lateinit var web: CustomGroup2Web
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()

    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        web = CustomGroup2WebImpl(
            manager = manager,
            gson = gson,
            service = service,
            dispatcher = TestDispatcherProvider()
        )
        coEvery {
            manager.getCustomGroup2SettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @Test
    fun `check all MarketTypeV2 enum`() = testScope.runTest {
        val except = MarketTypeV2::class.sealedSubclasses
            .flatMap { market ->
                market.sealedSubclasses.map { subType ->
                    subType.objectInstance as MarketTypeV2
                }
            }
        val result = MarketTypeV2.getAll().flatMap { entry ->
            entry.value
        }
        result.forEach {
            Truth.assertThat(it).isIn(except)
        }
    }

    @Test
    fun `MarketTypeV2 valueOf_input type is 2 and subType is 90_MarketTypeV2 Tse Stock`() =
        testScope.runTest {
            val except = MarketTypeV2.Tse.Stock
            val result = MarketTypeV2.valueOf(2, 90)
            Truth.assertThat(result).isEqualTo(except)
        }

    @Test
    fun `searchStocksV2_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}CustomGroupService/api/searchstocksbyappid"
        val urlSlot = slot<String>()
        coEvery {
            service.searchStocks(
                url = capture(urlSlot),
                authorization = any(),
                language = any(),
                requestBody = any()
            )
        } returns Response.success(emptyList())
        web.searchStocksV2(keyword = "2330", language = Language.zhTw())
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun searchStocksV2_success() = testScope.runTest {
        val expect = listOf(
            StockV2(
                id = "2330",
                name = "台積電",
                marketType = MarketTypeV2.Tse.Stock
            )
        )

        val rawStocks = listOf(
            RawStock(
                id = "2330",
                name = "台積電",
                marketType = 2,
                type = 90
            )
        )
        coEvery {
            service.searchStocks(
                url = any(),
                authorization = any(),
                language = any(),
                requestBody = any()
            )
        } returns Response.success(rawStocks)

        val result = web.searchStocksV2(keyword = "2330", language = Language.zhTw())
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(expect)
    }

    @Test
    fun searchStocksV2_failure() = testScope.runTest {
        coEvery {
            service.searchStocks(
                url = any(),
                authorization = any(),
                language = any(),
                requestBody = any()
            )
        } returns Response.error(401, "".toResponseBody())

        val result = web.searchStocksV2(keyword = "2330", language = Language.zhTw())
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        requireNotNull(exception)
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        val httpException = exception as HttpException
        Truth.assertThat(httpException.code()).isEqualTo(401)
    }

    @Test
    fun `searchStocksV2(List)_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}CustomGroupService/api/searchstocksbyappid"
        val urlSlot = slot<String>()
        coEvery {
            service.searchStocks(
                url = capture(urlSlot),
                authorization = any(),
                language = any(),
                requestBody = any()
            )
        } returns Response.success(emptyList())
        web.searchStocksV2(keyword = "2330", languages = listOf(Language.zhTw()))
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `searchStocksV2(List)_success`() = testScope.runTest {
        val expect = listOf(
            StockV2(
                id = "2330",
                name = "台積電",
                marketType = MarketTypeV2.Tse.Stock
            )
        )

        val rawStocks = listOf(
            RawStock(
                id = "2330",
                name = "台積電",
                marketType = 2,
                type = 90
            )
        )
        coEvery {
            service.searchStocks(
                url = any(),
                authorization = any(),
                language = any(),
                requestBody = any()
            )
        } returns Response.success(rawStocks)

        val result = web.searchStocksV2(keyword = "2330", languages = listOf(Language.zhTw()))
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(expect)
    }

    @Test
    fun `searchStocksV2(List)_failure`() = testScope.runTest {
        coEvery {
            service.searchStocks(
                url = any(),
                authorization = any(),
                language = any(),
                requestBody = any()
            )
        } returns Response.error(401, "".toResponseBody())

        val result = web.searchStocksV2(keyword = "2330", languages = listOf(Language.zhTw()))
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        requireNotNull(exception)
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        val httpException = exception as HttpException
        Truth.assertThat(httpException.code()).isEqualTo(401)
    }

    @Test
    fun `searchStocksByMarketTypesV2_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}CustomGroupService/api/searchstocksbycommoditytype"
        val urlSlot = slot<String>()
        coEvery {
            service.searchStocksByMarketType(
                url = capture(urlSlot),
                authorization = any(),
                language = any(),
                requestBody = any()
            )
        } returns Response.success(emptyList())
        web.searchStocksByMarketTypesV2(
            keyword = "2330",
            language = Language.zhTw(),
            marketTypes = MarketTypeV2.Tse.getAll()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun searchStocksByMarketTypesV2_success() = testScope.runTest {
        val expect = listOf(
            StockV2(
                id = "2330",
                name = "台積電",
                marketType = MarketTypeV2.Tse.Stock
            )
        )

        val rawStocks = listOf(
            RawStock(
                id = "2330",
                name = "台積電",
                marketType = 2,
                type = 90
            )
        )
        coEvery {
            service.searchStocksByMarketType(
                url = any(),
                authorization = any(),
                language = any(),
                requestBody = any()
            )
        } returns Response.success(rawStocks)

        val result = web.searchStocksByMarketTypesV2(
            keyword = "2330",
            language = Language.zhTw(),
            marketTypes = MarketTypeV2.Tse.getAll()
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(expect)
    }

    @Test
    fun searchStocksByMarketTypesV2_failure() = testScope.runTest {
        coEvery {
            service.searchStocksByMarketType(
                url = any(),
                authorization = any(),
                language = any(),
                requestBody = any()
            )
        } returns Response.error(401, "".toResponseBody())

        val result = web.searchStocksByMarketTypesV2(
            keyword = "2330",
            language = Language.zhTw(),
            marketTypes = MarketTypeV2.Tse.getAll()
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        requireNotNull(exception)
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        val httpException = exception as HttpException
        Truth.assertThat(httpException.code()).isEqualTo(401)
    }

    @Test
    fun `searchStocksByMarketTypesV2(List)_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}CustomGroupService/api/searchstocksbycommoditytype"
        val urlSlot = slot<String>()
        coEvery {
            service.searchStocksByMarketType(
                url = capture(urlSlot),
                authorization = any(),
                language = any(),
                requestBody = any()
            )
        } returns Response.success(emptyList())
        web.searchStocksByMarketTypesV2(
            keyword = "2330",
            languages = listOf(Language.zhTw()),
            marketTypes = MarketTypeV2.Tse.getAll()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `searchStocksByMarketTypesV2(List)_success`() = testScope.runTest {
        val expect = listOf(
            StockV2(
                id = "2330",
                name = "台積電",
                marketType = MarketTypeV2.Tse.Stock
            )
        )

        val rawStocks = listOf(
            RawStock(
                id = "2330",
                name = "台積電",
                marketType = 2,
                type = 90
            )
        )
        coEvery {
            service.searchStocksByMarketType(
                url = any(),
                authorization = any(),
                language = any(),
                requestBody = any()
            )
        } returns Response.success(rawStocks)

        val result = web.searchStocksByMarketTypesV2(
            keyword = "2330",
            languages = listOf(Language.zhTw()),
            marketTypes = MarketTypeV2.Tse.getAll()
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(expect)
    }

    @Test
    fun `searchStocksByMarketTypesV2(List)_failure`() = testScope.runTest {
        coEvery {
            service.searchStocksByMarketType(
                url = any(),
                authorization = any(),
                language = any(),
                requestBody = any()
            )
        } returns Response.error(401, "".toResponseBody())

        val result = web.searchStocksByMarketTypesV2(
            keyword = "2330",
            languages = listOf(Language.zhTw()),
            marketTypes = MarketTypeV2.Tse.getAll()
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        requireNotNull(exception)
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        val httpException = exception as HttpException
        Truth.assertThat(httpException.code()).isEqualTo(401)
    }

    @Test
    fun `getCustomGroup(Default)_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}custom-group/api"
        val urlSlot = slot<String>()
        coEvery {
            service.getCustomGroup(
                url = capture(urlSlot),
                authorization = any(),
                filters = any()
            )
        } returns Response.success(
            Documents(
                documents = emptyList()
            )
        )
        web.getCustomGroup(marketType = DocMarketType.Stock)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `getCustomGroup(Default)_success`() = testScope.runTest {
        coEvery {
            service.getCustomGroup(
                url = any(),
                authorization = any(),
                filters = any()
            )
        } returns Response.success(
            Documents(
                documents = listOf(
                    Document(
                        id = "1",
                        displayName = "測試用群組",
                        marketType = DocMarketType.Stock.value,
                        stocks = listOf("2330")
                    )
                )
            )
        )
        val result = web.getCustomGroup(marketType = DocMarketType.Stock)
        Truth.assertThat(result.isSuccess).isTrue()
        val customGroupDataList = result.getOrThrow()
        Truth.assertThat(customGroupDataList).hasSize(1)
        val customGroup = customGroupDataList.first()
        Truth.assertThat(customGroup.id).isEqualTo("1")
        Truth.assertThat(customGroup.name).isEqualTo("測試用群組")
        Truth.assertThat(customGroup.marketType).isEqualTo(DocMarketType.Stock)
        Truth.assertThat(customGroup.stocks).isEqualTo(listOf("2330"))
    }

    @Test
    fun `getCustomGroup(Default)p_401_failure`() = testScope.runTest {
        coEvery {
            service.getCustomGroup(
                url = any(),
                authorization = any(),
                filters = any()
            )
        } returns Response.error(401, "".toResponseBody())
        val result = web.getCustomGroup(marketType = DocMarketType.Stock)
        Truth.assertThat(result.isFailure).isTrue()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
    }

    @Test
    fun `getCustomGroup(id)_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}custom-group/api/1"
        val urlSlot = slot<String>()
        coEvery {
            service.getCustomGroupBy(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(
            Document(
                id = "1",
                displayName = "測試用群組",
                marketType = DocMarketType.Stock.value,
                stocks = listOf("2330")
            )
        )
        web.getCustomGroup(id = "1")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `getCustomGroup(id)_success`() = testScope.runTest {
        coEvery {
            service.getCustomGroupBy(
                url = any(),
                authorization = any()
            )
        } returns Response.success(
            Document(
                id = "1",
                displayName = "測試用群組",
                marketType = DocMarketType.Stock.value,
                stocks = listOf("2330")
            )
        )
        val result = web.getCustomGroup(id = "1")
        Truth.assertThat(result.isSuccess).isTrue()
        val customGroup = result.getOrThrow()
        Truth.assertThat(customGroup.id).isEqualTo("1")
        Truth.assertThat(customGroup.name).isEqualTo("測試用群組")
        Truth.assertThat(customGroup.marketType).isEqualTo(DocMarketType.Stock)
        Truth.assertThat(customGroup.stocks).isEqualTo(listOf("2330"))
    }

    @Test
    fun `getCustomGroup(id)_401_failure`() = testScope.runTest {
        coEvery {
            service.getCustomGroupBy(
                url = any(),
                authorization = any()
            )
        } returns Response.error(401, "".toResponseBody())
        val result = web.getCustomGroup(id = "1")
        Truth.assertThat(result.isFailure).isTrue()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
    }

    @Test
    fun `updateCustomGroup_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}custom-group/api/1"
        val urlSlot = slot<String>()
        val newGroup = CustomGroup(
            id = "1",
            name = "測試群組",
            marketType = DocMarketType.Stock,
            stocks = listOf("2330", "0050")
        )
        coEvery {
            service.updateCustomGroup(
                url = capture(urlSlot),
                authorization = any(),
                newGroup = any()
            )
        } returns Response.success<Void>(204, null)
        web.updateCustomGroup(newGroup)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun updateCustomGroup_success() = testScope.runTest {
        coEvery {
            service.updateCustomGroup(
                url = any(),
                authorization = any(),
                newGroup = any()
            )
        } returns Response.success<Void>(204, null)
        val newGroup = CustomGroup(
            id = "1",
            name = "測試群組",
            marketType = DocMarketType.Stock,
            stocks = listOf("2330", "0050")
        )
        val result = web.updateCustomGroup(newGroup)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow()).isEqualTo(Unit)
    }

    @Test
    fun updateCustomGroup_401_failure() = testScope.runTest {
        coEvery {
            service.updateCustomGroup(
                url = any(),
                authorization = any(),
                newGroup = any()
            )
        } returns Response.error(401, "".toResponseBody())
        val newGroup = CustomGroup(
            id = "1",
            name = "測試群組",
            marketType = DocMarketType.Stock,
            stocks = listOf("2330", "0050")
        )
        val result = web.updateCustomGroup(newGroup)
        Truth.assertThat(result.isFailure).isTrue()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
    }

    @Test
    fun `createCustomGroup_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}custom-group/api"
        val urlSlot = slot<String>()
        coEvery {
            service.createCustomGroup(
                url = capture(urlSlot),
                authorization = any(),
                baseDocument = any()
            )
        } returns Response.success(
            CreateCustomGroupResponseBody("1")
        )
        web.createCustomGroup(displayName = "自選股清單", marketType = DocMarketType.Stock)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun createCustomGroup_success() = testScope.runTest {
        coEvery {
            service.createCustomGroup(
                url = any(),
                authorization = any(),
                baseDocument = any()
            )
        } returns Response.success(
            CreateCustomGroupResponseBody("1")
        )
        val displayName = "自選股清單"
        val marketType = DocMarketType.Stock
        val result = web.createCustomGroup(displayName = displayName, marketType = marketType)
        Truth.assertThat(result.isSuccess).isTrue()
        val customGroup = result.getOrThrow()
        Truth.assertThat(customGroup.id).isEqualTo("1")
        Truth.assertThat(customGroup.marketType).isEqualTo(marketType)
        Truth.assertThat(customGroup.name).isEqualTo(displayName)
        Truth.assertThat(customGroup.stocks).isEmpty()
    }

    @Test
    fun createCustomGroup_401_failure() = testScope.runTest {
        coEvery {
            service.createCustomGroup(
                url = any(),
                authorization = any(),
                baseDocument = any()
            )
        } returns Response.error(401, "".toResponseBody())
        val result = web.createCustomGroup(displayName = "", marketType = DocMarketType.Stock)
        Truth.assertThat(result.isFailure).isTrue()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
    }

    @Test
    fun `deleteCustomGroup_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}custom-group/api/1"
        val urlSlot = slot<String>()
        coEvery {
            service.deleteCustomGroup(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        web.deleteCustomGroup("1")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun deleteCustomGroup_success() = testScope.runTest {
        coEvery {
            service.deleteCustomGroup(
                url = any(),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.deleteCustomGroup("1")
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow()).isEqualTo(Unit)
    }

    @Test
    fun deleteCustomGroup_401_failure() = testScope.runTest {
        coEvery {
            service.deleteCustomGroup(
                url = any(),
                authorization = any()
            )
        } returns Response.error(401, "".toResponseBody())
        val result = web.deleteCustomGroup("1")
        Truth.assertThat(result.isFailure).isTrue()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
    }

    @Test
    fun `getUserConfiguration_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}custom-group/api/_configuration"
        val urlSlot = slot<String>()
        coEvery {
            service.getUserConfiguration(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(
            UserConfigurationDocument(
                documentOrders = mapOf(
                    "10000" to 1,
                    "10001" to 2
                )
            )
        )
        web.getUserConfiguration()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getUserConfiguration_success() = testScope.runTest {
        coEvery {
            service.getUserConfiguration(
                url = any(),
                authorization = any()
            )
        } returns Response.success(
            UserConfigurationDocument(
                documentOrders = mapOf(
                    "10000" to 1,
                    "10001" to 2
                )
            )
        )
        val result = web.getUserConfiguration()
        Truth.assertThat(result.isSuccess).isTrue()
        val userConfiguration = result.getOrThrow()
        Truth.assertThat(userConfiguration.customGroupOrders).hasSize(2)
        userConfiguration.customGroupOrders?.entries?.forEachIndexed { index, entry ->
            val (id, order) = entry
            Truth.assertThat(id).isEqualTo("1000$index")
            Truth.assertThat(order).isEqualTo(index + 1)
        }
    }

    @Test
    fun getUserConfiguration_401_failure() = testScope.runTest {
        coEvery {
            service.getUserConfiguration(
                url = any(),
                authorization = any()
            )
        } returns Response.error(401, "".toResponseBody())
        val result = web.getUserConfiguration()
        Truth.assertThat(result.isFailure).isTrue()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
    }

    @Test
    fun `updateUserConfiguration_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}custom-group/api/_configuration"
        val urlSlot = slot<String>()
        coEvery {
            service.updateUserConfiguration(
                url = capture(urlSlot),
                authorization = any(),
                newUserConfigurationDocument = any()
            )
        } returns Response.success<Void>(204, null)
        web.updateConfiguration(emptyList())
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `updateUserConfiguration_UserConfigurationDocument id _configuration and is type is UserConfiguration`() =
        testScope.runTest {
            val slot = slot<UserConfigurationDocument>()
            coEvery {
                service.updateUserConfiguration(
                    url = any(),
                    authorization = any(),
                    newUserConfigurationDocument = capture(slot)
                )
            } returns Response.success<Void>(204, null)
            web.updateConfiguration(emptyList())
            slot.captured.id
            Truth.assertThat(slot.captured.id).isEqualTo("_configuration")
            Truth.assertThat(slot.captured.type).isEqualTo("UserConfiguration")
        }

    @Test
    fun `updateUserConfiguration_input CustomGroup 10000 and 10001_documentOrders key is 10000 10001 and value is 1 2`() =
        testScope.runTest {
            val slot = slot<UserConfigurationDocument>()
            coEvery {
                service.updateUserConfiguration(
                    url = any(),
                    authorization = any(),
                    newUserConfigurationDocument = capture(slot)
                )
            } returns Response.success<Void>(204, null)
            val newCustomGroups = listOf(
                CustomGroup(id = "10000", name = null, marketType = null, stocks = listOf()),
                CustomGroup(id = "10001", name = null, marketType = null, stocks = listOf())
            )
            web.updateConfiguration(newCustomGroups)
            Truth.assertThat(slot.captured.documentOrders?.keys).isEqualTo(setOf("10000", "10001"))
            Truth.assertThat(slot.captured.documentOrders?.values?.toList()).isEqualTo(listOf(1, 2))
        }

    @Test
    fun updateUserConfiguration_success() = testScope.runTest {
        coEvery {
            service.updateUserConfiguration(
                url = any(),
                authorization = any(),
                newUserConfigurationDocument = any()
            )
        } returns Response.success<Void>(204, null)
        val newCustomGroups = listOf(
            CustomGroup(id = "10000", name = null, marketType = null, stocks = listOf()),
            CustomGroup(id = "10001", name = null, marketType = null, stocks = listOf())
        )
        val result = web.updateConfiguration(newCustomGroups)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow()).isEqualTo(Unit)
    }

    @Test
    fun updateUserConfiguration_401_failure() = testScope.runTest {
        coEvery {
            service.updateUserConfiguration(
                url = any(),
                authorization = any(),
                newUserConfigurationDocument = any()
            )
        } returns Response.error(401, "".toResponseBody())
        val result = web.updateConfiguration(emptyList())
        Truth.assertThat(result.isFailure).isTrue()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
    }

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }
}