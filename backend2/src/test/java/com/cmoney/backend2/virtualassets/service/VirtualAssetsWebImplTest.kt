package com.cmoney.backend2.virtualassets.service

import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.virtualassets.service.api.getexchangeproductlist.GetExchangeProductListResponseBody
import com.cmoney.backend2.virtualassets.service.api.getexchangeproductlist.ProductInfo
import com.cmoney.backend2.virtualassets.service.api.getgrouplastexchangetime.GetGroupLastExchangeTimeResponseBody
import com.cmoney.core.CoroutineTestRule

import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
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
import retrofit2.HttpException
import retrofit2.Response

@RunWith(RobolectricTestRunner::class)
class VirtualAssetsWebImplTest {

    private val testScope = TestScope()
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var virtualAssetsService: VirtualAssetsService
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var service: VirtualAssetsWeb

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        service =
            VirtualAssetsWebImpl(
                TestSetting(),
                gson,
                virtualAssetsService,
                TestDispatcher()
            )
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `getExchangeProductList取得可兌換商品清單 成功`() = testScope.runTest {
        //準備api成功時的回傳
        val responseBody = GetExchangeProductListResponseBody(
            list = listOf<ProductInfo>()
        )
        //設定api成功時的回傳
        coEvery {
            virtualAssetsService.getExchangeProductList(
                pathAppId = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getExchangeProductList()
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.list?.size).isEqualTo(0)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `getExchangeProductList取得可兌換商品清單 失敗`() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100103,
            "Message": "Error"
          }
        }""".trimIndent()
        //設定api成功時的回傳
        coEvery {
            virtualAssetsService.getExchangeProductList(
                pathAppId = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.error(400, json.toResponseBody())

        //確認api是否成功
        val result = service.getExchangeProductList()
        checkServerException(result)
    }

    @Test
    fun `getGroupLastExchangeTime批次取得會員最後一次兌換指定商品的時間 成功`() = testScope.runTest {
        //準備api成功時的回傳
        val map = HashMap<String, Long>()
        map["1"] = 123456L
        val responseBody = GetGroupLastExchangeTimeResponseBody(
            lastExchangeTime = map
        )
        //設定api成功時的回傳
        coEvery {
            virtualAssetsService.getGroupLastExchangeTime(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getGroupLastExchangeTime(listOf(1L, 2L, 3L, 4L))
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.lastExchangeTime?.get("1")).isEqualTo(123456L)
    }

    @Test
    fun `getGroupLastExchangeTime批次取得會員最後一次兌換指定商品的時間 失敗`() = testScope.runTest {
        val json = ""

        //設定api成功時的回傳
        coEvery {
            virtualAssetsService.getGroupLastExchangeTime(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(401, json.toResponseBody())

        //確認api是否成功
        val result = service.getGroupLastExchangeTime(listOf(1L, 2L, 3L, 4L))
        checkHttpException(result)
    }

    @Test
    fun `exchange會員兌換指定商品 成功`() = testScope.runTest {
        //準備api成功時的回傳(後端在成功時回傳204和空值)
        //設定api成功時的回傳
        coEvery {
            virtualAssetsService.exchange(
                authorization = any(),
                pathExchangeId = 1L,
                requestBody = any()
            )
        } returns Response.success(204, null as? Void)         //確認api是否成功
        val result = service.exchange(1L)
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data).isEqualTo(Unit)
    }

    @Test
    fun `exchange會員兌換指定商品 失敗 發生ServerException`() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100103,
            "Message": "期限內兌換過不能再換"
          }
        }""".trimIndent()

        //設定api成功時的回傳
        coEvery {
            virtualAssetsService.exchange(
                pathExchangeId = 1L,
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, json.toResponseBody())

        //確認api是否成功
        val result = service.exchange(1L)
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
}