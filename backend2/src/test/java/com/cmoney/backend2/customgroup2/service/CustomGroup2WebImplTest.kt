package com.cmoney.backend2.customgroup2.service

import com.cmoney.backend2.MainCoroutineRule
import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.request.Language
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.customgroup2.service.api.data.MarketType
import com.cmoney.backend2.customgroup2.service.api.data.RawStock
import com.cmoney.backend2.customgroup2.service.api.data.Stock
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
                marketType = MarketType.Tse().value
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
    fun `searchStocks_one_language_param_will_invoke_list_param_default`() = mainCoroutineRule.runBlockingTest {
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
                marketType = MarketType.Tse().value
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
    fun `searchStocksByMarketTypes_one_language_param_will_invoke_list_param_default`() = runBlockingTest {
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
            spyWeb.searchStocksByMarketTypes(any(), listOf(Language.zhTw()), listOf(MarketType.Tse()))
        }
    }
}