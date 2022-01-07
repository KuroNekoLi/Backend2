package com.cmoney.backend2.customgroup2.service

import com.cmoney.backend2.MainCoroutineRule
import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.request.Language
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.customgroup2.service.api.createcustomgroup.CreateCustomGroupResponseBody
import com.cmoney.backend2.customgroup2.service.api.data.*
import com.cmoney.backend2.customgroup2.service.api.getcustomgroup.Documents
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
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

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var service: CustomGroup2Service
    private lateinit var web: CustomGroup2Web
    private lateinit var setting: Setting
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        setting = TestSetting()
        web = CustomGroup2WebImpl(
            setting = setting,
            gson = gson,
            service = service,
            dispatcher = TestDispatcher()
        )
    }

    @Test
    fun `檢查所有的MarketTypeV2類型`() = mainCoroutineRule.runBlockingTest {
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
    fun `MarketTypeV2的valueOf，尋找上市的台積電`() = mainCoroutineRule.runBlockingTest {
        val except = MarketTypeV2.Tse.Stock
        val result = MarketTypeV2.valueOf(2, 90)
        Truth.assertThat(result).isEqualTo(except)
    }

    @Test
    fun `searchStocks_成功`() = mainCoroutineRule.runBlockingTest {
        val expect = listOf(
            Stock(
                id = "2330",
                name = "台積電",
                marketType = MarketType.Tse()
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
            service.searchStocks(any(), any(), any())
        } returns Response.success(rawStocks)

        val result = web.searchStocks(keyword = "2330", languages = listOf(Language.zhTw()))
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(expect)
    }

    @Test
    fun `searchStocks_失敗`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.searchStocks(any(), any(), any())
        } returns Response.error(401, "".toResponseBody())

        val result = web.searchStocks(keyword = "2330", languages = listOf(Language.zhTw()))
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        requireNotNull(exception)
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        val httpException = exception as HttpException
        Truth.assertThat(httpException.code()).isEqualTo(401)
    }

    @Test
    fun `searchStocksV2_成功`() = mainCoroutineRule.runBlockingTest {
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
            service.searchStocks(any(), any(), any())
        } returns Response.success(rawStocks)

        val result = web.searchStocksV2(keyword = "2330", languages = listOf(Language.zhTw()))
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(expect)
    }

    @Test
    fun `searchStocksV2_失敗`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.searchStocks(any(), any(), any())
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
    fun `searchStocks_one_language_param_will_invoke_list_param_default`() =
        mainCoroutineRule.runBlockingTest {
            coEvery {
                service.searchStocks(any(), any(), any())
            } returns Response.success(emptyList())

            val spyWeb = spyk(web)
            spyWeb.searchStocks("2330", Language.zhTw())
            coVerify(exactly = 1) {
                spyWeb.searchStocks(any(), Language.zhTw())
                spyWeb.searchStocks(any(), listOf(Language.zhTw()))
            }
        }

    @Test
    fun `searchStocksV2_one_language_param_will_invoke_list_param_default`() =
        mainCoroutineRule.runBlockingTest {
            coEvery {
                service.searchStocks(any(), any(), any())
            } returns Response.success(emptyList())

            val spyWeb = spyk(web)
            spyWeb.searchStocksV2("2330", Language.zhTw())
            coVerify(exactly = 1) {
                spyWeb.searchStocksV2(any(), Language.zhTw())
                spyWeb.searchStocksV2(any(), listOf(Language.zhTw()))
            }
        }

    @Test
    fun `searchStocksByMarketTypes_成功`() = mainCoroutineRule.runBlockingTest {
        val expect = listOf(
            Stock(
                id = "2330",
                name = "台積電",
                marketType = MarketType.Tse()
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
            service.searchStocksByMarketType(any(), any(), any())
        } returns Response.success(rawStocks)

        val result = web.searchStocksByMarketTypes(
            keyword = "2330",
            languages = listOf(Language.zhTw()),
            marketTypes = listOf(MarketType.Tse())
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(expect)
    }

    @Test
    fun `searchStocksByMarketTypes_失敗`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.searchStocksByMarketType(any(), any(), any())
        } returns Response.error(401, "".toResponseBody())

        val result = web.searchStocksByMarketTypes(
            keyword = "2330",
            languages = listOf(Language.zhTw()),
            marketTypes = listOf(MarketType.Tse())
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
    fun `searchStocksByMarketTypesV2_成功`() = mainCoroutineRule.runBlockingTest {
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
            service.searchStocksByMarketType(any(), any(), any())
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
    fun `searchStocksByMarketTypesV2_失敗`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.searchStocksByMarketType(any(), any(), any())
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
    fun `searchStocksByMarketTypes_one_language_param_will_invoke_list_param_default`() =
        runBlockingTest {
            coEvery {
                service.searchStocksByMarketType(any(), any(), any())
            } returns Response.success(emptyList())

            val spyWeb = spyk(web)
            spyWeb.searchStocksByMarketTypes(
                keyword = "2330",
                language = Language.zhTw(),
                marketTypes = listOf(MarketType.Tse())
            )

            coVerify(exactly = 1) {
                spyWeb.searchStocksByMarketTypes(any(), Language.zhTw(), listOf(MarketType.Tse()))
                spyWeb.searchStocksByMarketTypes(
                    any(),
                    listOf(Language.zhTw()),
                    listOf(MarketType.Tse())
                )
            }
        }

    @Test
    fun `searchStocksByMarketTypesV2_one_language_param_will_invoke_list_param_default`() =
        runBlockingTest {
            coEvery {
                service.searchStocksByMarketType(any(), any(), any())
            } returns Response.success(emptyList())

            val spyWeb = spyk(web)
            spyWeb.searchStocksByMarketTypesV2(
                keyword = "2330",
                language = Language.zhTw(),
                marketTypes = MarketTypeV2.Tse.getAll()
            )

            coVerify(exactly = 1) {
                spyWeb.searchStocksByMarketTypesV2(
                    any(),
                    Language.zhTw(),
                    MarketTypeV2.Tse.getAll()
                )
                spyWeb.searchStocksByMarketTypesV2(
                    any(),
                    listOf(Language.zhTw()),
                    MarketTypeV2.Tse.getAll()
                )
            }
        }

    @Test
    fun getCustomGroup_成功() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.getCustomGroup(
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
        val result = web.getCustomGroup(DocMarketType.Stock)
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
    fun getCustomGroup_401_失敗() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.getCustomGroup(
                authorization = any(),
                filters = any()
            )
        } returns Response.error(401, "".toResponseBody())
        val result = web.getCustomGroup(DocMarketType.Stock)
        Truth.assertThat(result.isFailure).isTrue()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
    }

    @Test
    fun getCustomGroup_by_id_成功() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.getCustomGroupBy(
                authorization = any(),
                id = any()
            )
        } returns Response.success(
            Document(
                id = "1",
                displayName = "測試用群組",
                marketType = DocMarketType.Stock.value,
                stocks = listOf("2330")
            )
        )
        val result = web.getCustomGroup("1")
        Truth.assertThat(result.isSuccess).isTrue()
        val customGroup = result.getOrThrow()
        Truth.assertThat(customGroup.id).isEqualTo("1")
        Truth.assertThat(customGroup.name).isEqualTo("測試用群組")
        Truth.assertThat(customGroup.marketType).isEqualTo(DocMarketType.Stock)
        Truth.assertThat(customGroup.stocks).isEqualTo(listOf("2330"))
    }

    @Test
    fun getCustomGroup_by_id_401_失敗() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.getCustomGroupBy(
                authorization = any(),
                id = any()
            )
        } returns Response.error(401, "".toResponseBody())
        val result = web.getCustomGroup("1")
        Truth.assertThat(result.isFailure).isTrue()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
    }

    @Test
    fun updateCustomGroup_成功() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.updateCustomGroup(
                authorization = any(),
                id = any(),
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
    fun updateCustomGroup_401_失敗() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.updateCustomGroup(
                authorization = any(),
                id = any(),
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
    fun createCustomGroup_成功() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.createCustomGroup(
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
    fun createCustomGroup_401_失敗() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.createCustomGroup(
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
    fun deleteCustomGroup_成功() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.deleteCustomGroup(
                authorization = any(),
                id = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.deleteCustomGroup("1")
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow()).isEqualTo(Unit)
    }

    @Test
    fun deleteCustomGroup_401_失敗() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.deleteCustomGroup(
                authorization = any(),
                id = any()
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
    fun getUserConfiguration_成功() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.getUserConfiguration(authorization = any())
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
    fun getUserConfiguration_401_失敗() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.getUserConfiguration(authorization = any())
        } returns Response.error(401, "".toResponseBody())
        val result = web.getUserConfiguration()
        Truth.assertThat(result.isFailure).isTrue()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isInstanceOf(HttpException::class.java)
        require(exception is HttpException)
        Truth.assertThat(exception.code()).isEqualTo(401)
    }

    @Test
    fun updateUserConfiguration_成功() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.updateUserConfiguration(
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
    fun updateUserConfiguration_401_失敗() = mainCoroutineRule.runBlockingTest {
        coEvery {
            service.updateUserConfiguration(
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
}